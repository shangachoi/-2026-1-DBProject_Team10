package dto;

public class Photographer {
    private int photographerId;
    private String photographerName;
    private int studioId;
    private String phone;
    private String description;

    public Photographer(int photographerId, String photographerName, int studioId) {
        this(photographerId, photographerName, studioId, null, null);
    }

    public Photographer(int photographerId, String photographerName, int studioId, String phone, String description) {
        this.photographerId = photographerId;
        this.photographerName = photographerName;
        this.studioId = studioId;
        this.phone = phone;
        this.description = description;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public int getStudioId() {
        return studioId;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }

    public void setPhotographerName(String photographerName) {
        this.photographerName = photographerName;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}