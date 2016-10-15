package ar.ey.c4q.com.studybuddy.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StudySession implements Parcelable {

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudySession)) return false;

        StudySession that = (StudySession) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.topicOfStudy);
        dest.writeString(this.imageUrl);
        dest.writeString(this.dateStart);
        dest.writeString(this.dateEnd);
        dest.writeString(this.locationName);
        dest.writeString(this.locationAddress);
        dest.writeDouble(this.distance);
        dest.writeInt(this.numberOfScholars);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
    }

    public StudySession() {
    }

    protected StudySession(Parcel in) {
        this.id = in.readString();
        this.topicOfStudy = in.readString();
        this.imageUrl = in.readString();
        this.dateStart = in.readString();
        this.dateEnd = in.readString();
        this.locationName = in.readString();
        this.locationAddress = in.readString();
        this.distance = in.readDouble();
        this.numberOfScholars = in.readInt();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
    }

    public static final Parcelable.Creator<StudySession> CREATOR = new Parcelable.Creator<StudySession>() {
        @Override
        public StudySession createFromParcel(Parcel source) {
            return new StudySession(source);
        }

        @Override
        public StudySession[] newArray(int size) {
            return new StudySession[size];
        }
    };
}
