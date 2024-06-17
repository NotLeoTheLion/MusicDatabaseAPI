package lt.viko.eif.groupproject.musicapi.service;

import lt.viko.eif.groupproject.musicapi.model.TrackLike;
import lt.viko.eif.groupproject.musicapi.repository.TrackLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviso klasė, skirta tvarkyti muzikos takų patinkamųjų žymių operacijas.
 */
@Service
public class TrackLikeService {

    @Autowired
    private TrackLikeRepository trackLikeRepository;

    /**
     * Grąžina visus muzikos takų patinkamųjų žymių įrašus.
     *
     * @return Visi muzikos takų patinkamieji žymėjimai
     */
    public List<TrackLike> getAllTrackLikes() {
        return trackLikeRepository.findAll();
    }

    /**
     * Grąžina muzikos takų patinkamųjų žymių įrašus pagal vartotojo ID.
     *
     * @param userId vartotojo ID
     * @return Muzikos takų patinkamieji žymėjimai pagal vartotojo ID
     */
    public List<TrackLike> getTrackLikesByUserId(Long userId) {
        return trackLikeRepository.findByUserId(userId);
    }

    /**
     * Grąžina muzikos takų patinkamųjų žymių įrašus pagal takų ID.
     *
     * @param trackId muzikos takų ID
     * @return Muzikos takų patinkamieji žymėjimai pagal takų ID
     */
    public List<TrackLike> getTrackLikesByTrackId(Long trackId) {
        return trackLikeRepository.findByTrackId(trackId);
    }

    /**
     * Išsaugo muzikos takų patinkamąjį žymėjimą. Patikrina, ar vartotojas jau žymėjo šį taką prieš išsaugant.
     *
     * @param trackLike muzikos takų patinkamasis žymėjimas
     * @return Išsaugotas muzikos takų patinkamasis žymėjimas
     */
    public TrackLike saveTrackLike(TrackLike trackLike) {
        TrackLike existingLike = trackLikeRepository.findByTrackIdAndUserId(trackLike.getTrackId(), trackLike.getUserId());
        if (existingLike == null) {
            return trackLikeRepository.save(trackLike);
        } else {
            return existingLike;
        }
    }

    /**
     * Ištrina muzikos takų patinkamąjį žymėjimą pagal takų ID ir vartotojo ID.
     *
     * @param trackId takų ID
     * @param userId  vartotojo ID
     */
    public void deleteTrackLikeByTrackIdAndUserId(Long trackId, Long userId) {
        TrackLike trackLike = trackLikeRepository.findByTrackIdAndUserId(trackId, userId);
        if (trackLike != null) {
            trackLikeRepository.delete(trackLike);
        }
    }

    /**
     * Grąžina muzikos takų patinkamųjų žymių skaičių pagal takų ID.
     *
     * @param trackId takų ID
     * @return Muzikos takų patinkamųjų žymių skaičius pagal takų ID
     */
    public int getTrackLikeCountForTrack(Long trackId) {
        return trackLikeRepository.findByTrackId(trackId).size();
    }
}
