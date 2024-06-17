package lt.viko.eif.groupproject.musicapi.model;

import javax.persistence.*;

@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;

    @Lob
    @Column(name = "audio_clip", columnDefinition = "LONGBLOB")
    private byte[] audioClip;

    @Lob
    @Column(name = "track_photo", columnDefinition = "LONGBLOB")
    private byte[] trackPhoto;

    private String audioUrl;

    @Transient
    private String username; // Papildomas laikinas laukas vartotojo vardui

    @Transient
    private int likeCount; // Papildomas laikinas laukas "patinka" skaičiui

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getAudioClip() {
        return audioClip;
    }

    public void setAudioClip(byte[] audioClip) {
        this.audioClip = audioClip;
    }

    public byte[] getTrackPhoto() {
        return trackPhoto;
    }

    public void setTrackPhoto(byte[] trackPhoto) {
        this.trackPhoto = trackPhoto;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    // Metodas, skirtas gauti takelio nuotraukos URL
    public String getTrackPhotoUrl() {
        return "/api/tracks/" + id + "/photo";
    }
}
