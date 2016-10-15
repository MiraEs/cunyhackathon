package ar.ey.c4q.com.studybuddy.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GoogleAddressResults {

    @SerializedName("results")
    private ArrayList<Result> result;

    public double getLongitude() {
        return result.get(0).getLocation().getLongitude();
    }

    public double getLatitude() {
        return result.get(0).getLocation().getLatitude();
    }

    public static class Result {


        @SerializedName("geometry")
        private Geometry location;

        public Geometry getLocation() {
            return location;
        }
    }

    public static class Geometry {

        @SerializedName("lng")
        private double longitude;

        @SerializedName("lat")
        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }
    }

}
