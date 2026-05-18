package dto;

public class Mood {
    private int moodId;
    private String moodName;
    private String description;

    public Mood(int moodId, String moodName, String description) {
        this.moodId = moodId;
        this.moodName = moodName;
        this.description = description;
    }

    public int getMoodId() { return moodId; }
    public void setMoodId(int moodId) { this.moodId = moodId; }

    public String getMoodName() { return moodName; }
    public void setMoodName(String moodName) { this.moodName = moodName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}