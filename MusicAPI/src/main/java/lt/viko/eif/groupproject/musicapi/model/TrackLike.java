package lt.viko.eif.groupproject.musicapi.model;

import javax.persistence.*;

@Entity
@Table(name = "track_like") // Pavadinimas pakeistas, kad išvengtume konflikto su SQL rezervuotais žodžiais
public class TrackLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long trackId;

    // Getteriai ir Setteriai

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }
}
