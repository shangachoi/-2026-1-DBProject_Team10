package dto;

public class Mood {
    private int moodId;
    private String moodName;
    private int studioId;
    private String description;

    public Mood(int moodId, String moodName, int studioId) {
        this(moodId, moodName, studioId, null);
    }

    public Mood(int moodId, String moodName, int studioId, String description) {
        this.moodId = moodId;
        this.moodName = moodName;
        this.studioId = studioId;
        this.description = description;
    }

    public int getMoodId() {
        return moodId;
    }

    public String getMoodName() {
        return moodName;
    }

    public int getStudioId() {
        return studioId;
    }

    public String getDescription() {
        return description;
    }

    public void setMoodId(int moodId) {
        this.moodId = moodId;
    }

    public void setMoodName(String moodName) {
        this.moodName = moodName;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}