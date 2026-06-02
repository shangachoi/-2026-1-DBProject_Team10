package dto;

public class StudioSearchResult {
    private int studioId;
    private String studioName;
    private String location;
    private String studioPhone;
    private String photographerName;
    private String color;
    private String moodName;

    public StudioSearchResult(int studioId, String studioName, String location, String studioPhone,
                              String photographerName, String color, String moodName) {
        this.studioId = studioId;
        this.studioName = studioName;
        this.location = location;
        this.studioPhone = studioPhone;
        this.photographerName = photographerName;
        this.color = color;
        this.moodName = moodName;
    }

    public int getStudioId() {
        return studioId;
    }

    public String getStudioName() {
        return studioName;
    }

    public String getLocation() {
        return location;
    }

    public String getStudioPhone() {
        return studioPhone;
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public String getColor() {
        return color;
    }

    public String getMoodName() {
        return moodName;
    }
}