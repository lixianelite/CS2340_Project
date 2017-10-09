package cs2340.gatech.edu.m4.model;

/**
 * Created by tianyunan on 10/8/17.
 */

public class DataItem {
    private int id;
    private String createdDate;
    private String locationType;
    private String zip;
    private String address;
    private String city;
    private String borough;
    private float latitude;
    private float longitude;

    public DataItem(int id, String cd, String lo, String zip, String add, String city, String bo, float la, float lon) {
        this.id = id;
        createdDate = cd;
        locationType = lo;
        this.zip = zip;
        address = add;
        this.city = city;
        borough = bo;
        latitude = la;
        longitude = lon;
    }

    //public String toString() {
       // return name + " " + id;
    //}

    public int getId() { return id; }
    public String getCreatedDate() { return createdDate; }
    public String getLocationType() { return locationType; }
    public String getZip() { return zip; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getBorough() { return borough; }
    public float getLatitude() { return latitude; }
    public float getLongitude() { return longitude; }

}
