package dto;

public class Background {
    private int backgroundId;
    private String color;
    private String description;

    public Background(int backgroundId, String color, String description) {
        this.backgroundId = backgroundId;
        this.color = color;
        this.description = description;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}