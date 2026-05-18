package dto;

public class Photographer {
    private int photographerId;
    private String photographerName;
    private int studioId;
    private String career;
    private String specialty;

    public Photographer(int photographerId, String photographerName, int studioId, String career, String specialty) {
        this.photographerId = photographerId;
        this.photographerName = photographerName;
        this.studioId = studioId;
        this.career = career;
        this.specialty = specialty;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public void setPhotographerName(String photographerName) {
        this.photographerName = photographerName;
    }

    public int getStudioId() {
        return studioId;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

}