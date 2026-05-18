
package dto;

public class StudioBackground {
    private int studioId;
    private int backgroundId;

    public StudioBackground(int studioId, int backgroundId) {
        this.studioId = studioId;
        this.backgroundId = backgroundId;
    }

    public int getStudioId() { return studioId; }
    public void setStudioId(int studioId) { this.studioId = studioId; }

    public int getBackgroundId() { return backgroundId; }
    public void setBackgroundId(int backgroundId) { this.backgroundId = backgroundId; }

}