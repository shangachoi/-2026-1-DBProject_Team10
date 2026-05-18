package dto;

public class StudioMood {
    private int studioId;
    private int moodId;

    public StudioMood(int studioId, int moodId) {
        this.studioId = studioId;
        this.moodId = moodId;
    }

    public int getStudioId() { return studioId; }
    public void setStudioId(int studioId) { this.studioId = studioId; }

    public int getMoodId() { return moodId; }
    public void setMoodId(int moodId) { this.moodId = moodId; }

}