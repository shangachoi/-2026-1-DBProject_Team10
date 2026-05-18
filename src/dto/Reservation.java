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
    private String state;

    public Reservation(int reservationId, String memberId, int studioId, int photographerId,
                       int backgroundId, int moodId, LocalDate reservationDate, String state) {
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.studioId = studioId;
        this.photographerId = photographerId;
        this.backgroundId = backgroundId;
        this.moodId = moodId;
        this.reservationDate = reservationDate;
        this.state = state;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getStudioId() {
        return studioId;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public int getMoodId() {
        return moodId;
    }

    public void setMoodId(int moodId) {
        this.moodId = moodId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}