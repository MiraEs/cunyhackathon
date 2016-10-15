package ar.ey.c4q.com.studybuddy.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ar.ey.c4q.com.studybuddy.R;
import ar.ey.c4q.com.studybuddy.models.StudySession;


public class StudySessionDetailedActivity extends AppCompatActivity {

    private MapView map;
    public GoogleMap gMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detailed);

        final StudySession studySession = getIntent().getExtras().getParcelable("data");

        TextView topic = (TextView) findViewById(R.id.topic);
        TextView time = (TextView) findViewById(R.id.time);
        TextView distance = (TextView) findViewById(R.id.distance);
        TextView material = (TextView) findViewById(R.id.material);
        TextView location = (TextView) findViewById(R.id.location);
        TextView additionalNotes = (TextView) findViewById(R.id.additional_notes);

        topic.setText(studySession.getTopicOfStudy());
        time.setText("Time: " + studySession.getDateStart() + " - " + studySession.getDateEnd());
        distance.setText(String.valueOf(studySession.getDistance()) + " miles away");
        material.setText("Materials: Some Textbook ISBN:13082103810283");
        location.setText(studySession.getLocationName() + "\n" + studySession.getLocationAddress());
        additionalNotes.setText("Additional Notes: For remote users, use Google Hangout");

        map = (MapView) findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        map.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                if (ActivityCompat.checkSelfPermission(StudySessionDetailedActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(StudySessionDetailedActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.7659217, -73.96370569999999))
                        .title("You"));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(studySession.getLatitude(), studySession.getLongitude()))
                        .title(studySession.getLocationName())
                        .snippet(studySession.getLocationAddress()));

                CameraUpdate center = CameraUpdateFactory
                        .newLatLng(new LatLng(studySession.getLatitude(), studySession.getLongitude()));

                CameraPosition zoom = new CameraPosition.Builder()
                        .target(new LatLng(40.7659217, -73.96370569999999))
                        .zoom(13.7f)
                        .build();
                googleMap.moveCamera(center);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(zoom));

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }


}
