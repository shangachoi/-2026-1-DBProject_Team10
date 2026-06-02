package dto;

public class Studio {
    private int studioId;
    private String studioName;
    private String location;
    private String phone;

    public Studio(int studioId, String studioName, String location, String phone) {
        this.studioId = studioId;
        this.studioName = studioName;
        this.location = location;
        this.phone = phone;
    }

    public Studio(int studioId, String name, String address) {
        this(studioId, name, address, /* set other fields or defaults */ "");
    }

    public int getStudioId() { return studioId; }
    public void setStudioId(int studioId) { this.studioId = studioId; }

    public String getStudioName() { return studioName; }
    public void setStudioName(String studioName) { this.studioName = studioName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

}