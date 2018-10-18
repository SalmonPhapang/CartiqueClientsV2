package car.com.cartique.client.model;

public class FeedItemDetails {
    int ufdn, feedid;
    private String name, details, timeStamp, location;

    public FeedItemDetails() {
    }

    public FeedItemDetails(int ufdn, int feedid, String name, String details, String dateTime, String location) {
        this.ufdn = ufdn;
        this.feedid = feedid;
        this.details = details;
        this.name = name;
        this.details = details;
        this.timeStamp = dateTime;
        this.location = location;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUfdn() {
        return ufdn;
    }

    public void setUfdn(int ufdn) {
        this.ufdn = ufdn;
    }

    public int getFeedid() {
        return feedid;
    }

    public void setFeedid(int feedid) {
        this.feedid = feedid;
    }
}
