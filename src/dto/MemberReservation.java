package dto;

import java.time.LocalDate;
public class MemberReservation {
    private String memberId;
    private String memberName;
    private int reservationId;
    private String studioName;
    private String color;
    private String moodName;
    private String photographerName;
    private LocalDate reservationDate;
    private String reservationTime;
    private String state;

    public MemberReservation(String memberId, String memberName, int reservationId, String studioName,
                                String color, String moodName, String photographerName,
                                LocalDate reservationDate, String reservationTime, String state) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.reservationId = reservationId;
        this.studioName = studioName;
        this.color = color;
        this.moodName = moodName;
        this.photographerName = photographerName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.state = state;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getStudioName() {
        return studioName;
    }

    public String getPhotographerName() {
        return photographerName;
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

    public String getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }
}