package car.com.cartique.client.model;


/**
 * Created by napster on 7/21/16.
 */
public class Location {

    private String name;
    private String address;
    private String suburb;
    private String city;
    private String latitude;
    private String longitude;

    public Location() {

    }

    public Location(String name, String address, String suburb, String city, String latitude, String longitude) {
        this.name = name;
        this.address = address;
        this.suburb = suburb;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
