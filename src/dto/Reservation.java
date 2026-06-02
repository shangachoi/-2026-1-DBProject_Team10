package dto;

import java.time.LocalDate;

public class Reservation {
    private int reservationId;
    private String memberId;
    private int studioId;
    private int photographerId;
    private int backgroundId;
    private int moodId;
    private LocalDate reservationDate;
    private String reservationTime; // optional
    private String state;

    // Constructor used when only state (no reservationTime) is provided
    public Reservation(int reservationId, String memberId, int studioId, int photographerId,
                       int backgroundId, int moodId, LocalDate reservationDate, String state) {
        this(reservationId, memberId, studioId, photographerId, backgroundId, moodId, reservationDate, null, state);
    }

    // Full constructor including reservationTime and state
    public Reservation(int reservationId, String memberId, int studioId, int photographerId,
                       int backgroundId, int moodId, LocalDate reservationDate, String reservationTime, String state) {
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.studioId = studioId;
        this.photographerId = photographerId;
        this.backgroundId = backgroundId;
        this.moodId = moodId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.state = state;
    }

    // Insert-style constructors
    public Reservation(String memberId, int studioId, int photographerId,
                       int backgroundId, int moodId, LocalDate reservationDate, String reservationTime, String state) {
        this(0, memberId, studioId, photographerId, backgroundId, moodId, reservationDate, reservationTime, state);
    }

    public Reservation(String memberId, int studioId, int photographerId,
                       int backgroundId, int moodId, LocalDate reservationDate, String state) {
        this(0, memberId, studioId, photographerId, backgroundId, moodId, reservationDate, null, state);
    }

    // Getters
    public int getReservationId() {
        return reservationId;
    }

    public String getMemberId() {
        return memberId;
    }

    public int getStudioId() {
        return studioId;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public int getMoodId() {
        return moodId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public String getState() {
        return state;
    }

    // Setters
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public void setMoodId(int moodId) {
        this.moodId = moodId;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public void setState(String state) {
        this.state = state;
    }

}