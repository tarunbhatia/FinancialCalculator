package financialcalculators.tarun.com.financalculator.helper.pojos;

/**
 * Created by Tarun on 3/3/2015.
 */
public class Region {
    private int regionId;
    private String state;
    private String city;
    private int zipCode;
    private Float latitude;
    private Float longitude;
    private String zmmrateurl;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getZmmrateurl() {
        return zmmrateurl;
    }

    public void setZmmrateurl(String zmmrateurl) {
        this.zmmrateurl = zmmrateurl;
    }
}
