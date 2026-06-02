package dto;

public class Photographer {
    private int photographerId;
    private String photographerName;
    private int studioId;
    private String career;
    private String specialty;

    public Photographer(int photographerId, String photographerName, int studioId) {
        this(photographerId, photographerName, studioId, null, null);
    }

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

    public String getPhotographerName() {
        return photographerName;
    }

    public int getStudioId() {
        return studioId;
    }
    
    public String getCareer() { 
        return career; 
    } 
    
    public String getSpecialty() {
         return specialty; 
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

    public void setCareer(String career) { 
        this.career = career; 
    }

    public void setSpecialty(String specialty) { 
        this.specialty = specialty; 
    }
    
}