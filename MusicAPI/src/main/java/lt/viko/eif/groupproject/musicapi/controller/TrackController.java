package lt.viko.eif.groupproject.musicapi.controller;

import lt.viko.eif.groupproject.musicapi.model.Track;
import lt.viko.eif.groupproject.musicapi.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Šis kontroleris tvarko API užklausimus, susijusius su takeliais.
 */
@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    @Autowired
    private TrackService trackService;

    /**
     * Grąžina visų takelių sąrašą.
     *
     * @return visų takelių sąrašas
     */
    @GetMapping
    public List<Track> getAllTracks() {
        List<Track> tracks = trackService.getAllTracks();
        // Remove audioClip and trackPhoto for performance reasons
        tracks.forEach(track -> {
            track.setAudioClip(null);
            track.setTrackPhoto(null);
        });
        return tracks;
    }

    /**
     * Grąžina takelį pagal ID be didelių BLOB duomenų.
     *
     * @param id takelio ID
     * @return rastas takelis arba null, jei takelis nerastas
     */
    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
        Track track = trackService.getTrackById(id);
        if (track == null) {
            return ResponseEntity.notFound().build();
        }
        track.setAudioClip(null);  // Remove BLOB data
        track.setTrackPhoto(null); // Remove BLOB data
        return ResponseEntity.ok(track);
    }

    /**
     * Grąžina takelio garso failą pagal ID.
     *
     * @param id takelio ID
     * @return ResponseEntity su garso failo duomenimis arba 404, jei garso failas nerastas
     */
    @GetMapping("/{id}/audio")
    public ResponseEntity<byte[]> getTrackAudio(@PathVariable Long id) {
        Track track = trackService.getTrackById(id);
        if (track == null || track.getAudioClip() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(track.getAudioClip().length);

        return new ResponseEntity<>(track.getAudioClip(), headers, HttpStatus.OK);
    }

    /**
     * Grąžina takelio nuotrauką pagal ID.
     *
     * @param id takelio ID
     * @return ResponseEntity su nuotraukos duomenimis arba 404, jei nuotrauka nerasta
     */
    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getTrackPhoto(@PathVariable Long id) {
        Track track = trackService.getTrackById(id);
        if (track == null || track.getTrackPhoto() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
        headers.setContentLength(track.getTrackPhoto().length);

        return new ResponseEntity<>(track.getTrackPhoto(), headers, HttpStatus.OK);
    }

    /**
     * Sukuria naują takelį.
     *
     * @param userId vartotojo ID, kuris įkelia takelį
     * @param title takelio pavadinimas
     * @param file takelio failas
     * @param trackPhoto takelio nuotrauka
     * @return sukurtas takelis
     * @throws IOException jei įkelti failai negali būti apdoroti
     */
    @PostMapping(consumes = "multipart/form-data")
    public Track createTrack(@RequestParam Long userId,
                             @RequestParam String title,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("trackPhoto") MultipartFile trackPhoto) throws IOException {
        return trackService.saveTrack(userId, title, file, trackPhoto);
    }

    /**
     * Ištrina takelį pagal ID.
     *
     * @param id takelio ID
     */
    @DeleteMapping("/{id}")
    public void deleteTrack(@PathVariable Long id) {
        trackService.deleteTrack(id);
    }
}
