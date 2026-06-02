package dto;

public class Background {
    private int backgroundId;
    private String color;
    private int studioId;
    private String description;

    public Background(int backgroundId, String color, int studioId) {
        this(backgroundId, color, studioId, null);
    }

    public Background(int backgroundId, String color, int studioId, String description) {
        this.backgroundId = backgroundId;
        this.color = color;
        this.studioId = studioId;
        this.description = description;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public String getColor() {
        return color;
    }

    public int getStudioId() {
        return studioId;
    }

    public String getDescription() {
        return description;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}