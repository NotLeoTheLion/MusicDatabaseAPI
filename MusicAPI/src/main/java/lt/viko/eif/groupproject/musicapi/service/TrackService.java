package lt.viko.eif.groupproject.musicapi.service;

import lt.viko.eif.groupproject.musicapi.model.Track;
import lt.viko.eif.groupproject.musicapi.model.User;
import lt.viko.eif.groupproject.musicapi.repository.TrackRepository;
import lt.viko.eif.groupproject.musicapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Serviso klasė, skirta tvarkyti muzikos takų operacijas.
 */
@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackLikeService trackLikeService;

    /**
     * Grąžina visus muzikos takus, prideda vartotojo vardą ir patinkamųjų žymių skaičių.
     *
     * @return Visi muzikos takai
     */
    public List<Track> getAllTracks() {
        List<Track> tracks = trackRepository.findAll();
        for (Track track : tracks) {
            User user = userRepository.findById(track.getUserId()).orElse(null);
            if (user != null) {
                track.setUsername(user.getUsername());
            }
            int likeCount = trackLikeService.getTrackLikeCountForTrack(track.getId());
            track.setLikeCount(likeCount);
        }
        return tracks;
    }

    /**
     * Grąžina muzikos taką pagal ID, prideda vartotojo vardą ir patinkamųjų žymių skaičių.
     *
     * @param id muzikos takų ID
     * @return Muzikos takas pagal ID
     */
    public Track getTrackById(Long id) {
        Track track = trackRepository.findById(id).orElse(null);
        if (track != null) {
            User user = userRepository.findById(track.getUserId()).orElse(null);
            if (user != null) {
                track.setUsername(user.getUsername());
            }
            int likeCount = trackLikeService.getTrackLikeCountForTrack(track.getId());
            track.setLikeCount(likeCount);
        }
        return track;
    }

    /**
     * Ištrina muzikos taką pagal ID.
     *
     * @param id muzikos takų ID
     */
    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }

    /**
     * Išsaugo naują muzikos taką su nurodytu vartotoju, pavadinimu ir failais.
     *
     * @param userId     vartotojo ID
     * @param title      pavadinimas
     * @param audioFile  garso failas
     * @param trackPhoto takelio nuotrauka
     * @return Išsaugotas muzikos takas
     * @throws IOException jei nepavyksta įkelti failų
     */
    public Track saveTrack(Long userId, String title, MultipartFile audioFile, MultipartFile trackPhoto) throws IOException {
        Track track = new Track();
        track.setUserId(userId);
        track.setTitle(title);

        // Save audio file as BLOB
        track.setAudioClip(audioFile.getBytes());

        // Save audio file URL
        String audioDirectory = "audio/";
        Files.createDirectories(Paths.get(audioDirectory));
        String audioFileName = System.currentTimeMillis() + "_" + audioFile.getOriginalFilename();
        String audioFilePath = audioDirectory + audioFileName;
        Files.copy(audioFile.getInputStream(), Paths.get(audioFilePath), StandardCopyOption.REPLACE_EXISTING);
        track.setAudioUrl("/" + audioFilePath);

        // Save track photo as BLOB
        track.setTrackPhoto(trackPhoto.getBytes());

        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            track.setUsername(user.getUsername());
        }

        return trackRepository.save(track);
    }

    /**
     * Ieško muzikos takų pagal pavadinimo fragmentą, prideda vartotojo vardą ir patinkamųjų žymių skaičių.
     *
     * @param searchTerm paieškos fragmentas
     * @return Muzikos takai pagal pavadinimo fragmentą
     */
    public List<Track> searchTracksByTitle(String searchTerm) {
        List<Track> tracks = trackRepository.findByTitleContaining(searchTerm);
        for (Track track : tracks) {
            User user = userRepository.findById(track.getUserId()).orElse(null);
            if (user != null) {
                track.setUsername(user.getUsername());
            }
            int likeCount = trackLikeService.getTrackLikeCountForTrack(track.getId());
            track.setLikeCount(likeCount);
        }
        return tracks;
    }
}
