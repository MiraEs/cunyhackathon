package ar.ey.c4q.com.studybuddy.models;


import com.google.gson.annotations.SerializedName;

public class StudySession {

    @SerializedName("id")
    private String id;

    @SerializedName("topic_of_study")
    private String topicOfStudy;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("time_start")
    private String dateStart;

    @SerializedName("time_end")
    private String dateEnd;

    @SerializedName("location_name")
    private String locationName;

    @SerializedName("location_address")
    private String locationAddress;

    @SerializedName("distance")
    private double distance;

    @SerializedName("number_of_scholars")
    private int numberOfScholars;

    @SerializedName("lng")
    private double longitude;

    @SerializedName("lat")
    private double latitude;

    public String getId() {
        return id;
    }

    public String getTopicOfStudy() {
        return topicOfStudy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public double getDistance() {
        return distance;
    }

    public int getNumberOfScholars() {
        return numberOfScholars;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
