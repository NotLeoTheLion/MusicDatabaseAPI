package lt.viko.eif.groupproject.musicapi.repository;

import lt.viko.eif.groupproject.musicapi.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByTitleContaining(String searchTerm);
}
