package al.edu.feut.financaime.model;

public class ExpenseLocation {
    private String description;
    private double latitude;
    private double longitude;

    public ExpenseLocation(String description, double latitude, double longitude) {
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
