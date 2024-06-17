package lt.viko.eif.groupproject.musicapi.controller;

import lt.viko.eif.groupproject.musicapi.model.TrackLike;
import lt.viko.eif.groupproject.musicapi.service.TrackLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Šis RESTful kontroleris tvarko API užklausimus, susijusius su takelių įvertinimais.
 */
@RestController
@RequestMapping("/api/likes")
public class TrackLikeController {

    @Autowired
    private TrackLikeService trackLikeService;

    /**
     * Grąžina visų takelių įvertinimų sąrašą.
     *
     * @return visų takelių įvertinimų sąrašas
     */
    @GetMapping
    public List<TrackLike> getAllTrackLikes() {
        return trackLikeService.getAllTrackLikes();
    }

    /**
     * Grąžina vartotojo įvertintų takelių sąrašą pagal vartotojo ID.
     *
     * @param userId vartotojo ID
     * @return vartotojo įvertintų takelių sąrašas
     */
    @GetMapping("/user/{userId}")
    public List<TrackLike> getTrackLikesByUserId(@PathVariable Long userId) {
        return trackLikeService.getTrackLikesByUserId(userId);
    }

    /**
     * Grąžina takelio įvertinimų sąrašą pagal takelio ID.
     *
     * @param trackId takelio ID
     * @return takelio įvertinimų sąrašas
     */
    @GetMapping("/track/{trackId}")
    public List<TrackLike> getTrackLikesByTrackId(@PathVariable Long trackId) {
        return trackLikeService.getTrackLikesByTrackId(trackId);
    }

    /**
     * Sukuria naują takelio įvertinimą.
     *
     * @param trackLike naujas takelio įvertinimo objektas
     * @return sukurtas takelio įvertinimas
     */
    @PostMapping
    public TrackLike createTrackLike(@RequestBody TrackLike trackLike) {
        return trackLikeService.saveTrackLike(trackLike);
    }

    /**
     * Ištrina takelio įvertinimą pagal takelio ID ir vartotojo ID.
     *
     * @param trackId takelio ID
     * @param userId vartotojo ID
     */
    @DeleteMapping("/track/{trackId}/user/{userId}")
    public void deleteTrackLike(@PathVariable Long trackId, @PathVariable Long userId) {
        trackLikeService.deleteTrackLikeByTrackIdAndUserId(trackId, userId);
    }
}
