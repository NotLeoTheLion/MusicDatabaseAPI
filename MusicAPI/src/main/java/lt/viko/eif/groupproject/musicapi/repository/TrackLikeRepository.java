package lt.viko.eif.groupproject.musicapi.repository;

import lt.viko.eif.groupproject.musicapi.model.TrackLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackLikeRepository extends JpaRepository<TrackLike, Long> {
    List<TrackLike> findByUserId(Long userId);
    List<TrackLike> findByTrackId(Long trackId);
    TrackLike findByTrackIdAndUserId(Long trackId, Long userId);
}
