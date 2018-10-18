package car.com.cartique.client.model;

/**
 * Created by napster on 1/18/18.
 */

import java.io.Serializable;

public class FeedItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id, shots, ufdn;
    private boolean isChecked;
    private String name, status, image, profilePic, timeStamp, url, location;

    public FeedItem() {
    }

    public FeedItem(int id, String name, String location, String image, String status,
                    String profilePic, String timeStamp, String url, int shots, int ufdn) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = status;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
        this.url = url;
        this.shots = shots;
        this.location = location;
        this.ufdn = ufdn;
    }

    public int getUfdn() {
        return ufdn;
    }

    public void setUfdn(int ufdn) {
        this.ufdn = ufdn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImge() {
        return image;
    }

    public void setImge(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

