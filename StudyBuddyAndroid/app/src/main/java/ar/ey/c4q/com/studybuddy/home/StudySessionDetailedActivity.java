package ar.ey.c4q.com.studybuddy.home;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ar.ey.c4q.com.studybuddy.R;
import ar.ey.c4q.com.studybuddy.models.StudySession;

import static ar.ey.c4q.com.studybuddy.home.HomeActivity.EXTRA_DATA_STUDY_SESSION;
import static ar.ey.c4q.com.studybuddy.util.Constants.userLatLng;


public class StudySessionDetailedActivity extends AppCompatActivity {

    public GoogleMap googleMap;

    private MapView mapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detailed);

        final StudySession studySession = getIntent().getExtras()
                .getParcelable(EXTRA_DATA_STUDY_SESSION);

        TextView topic = (TextView) findViewById(R.id.topic);
        TextView time = (TextView) findViewById(R.id.time);
        TextView distance = (TextView) findViewById(R.id.distance);
        TextView material = (TextView) findViewById(R.id.material);
        TextView location = (TextView) findViewById(R.id.location);
        TextView additionalNotes = (TextView) findViewById(R.id.additional_notes);

        topic.setText(studySession.getTopicOfStudy());
        time.setText(
                getString(R.string.session_detailed_time_x) + studySession.getDateStart() + " - "
                        + studySession.getDateEnd());
        distance.setText(String.format(getString(R.string.detailed_session_x_miles_away),
                String.valueOf(studySession.getDistance())));
        material.setText(R.string.session_detailed_materials_x);
        location.setText(studySession.getLocationName() + "\n" + studySession.getLocationAddress());
        additionalNotes.setText(R.string.session_detailed_additional_note_x);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                StudySessionDetailedActivity.this.googleMap = googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                if (ActivityCompat.checkSelfPermission(StudySessionDetailedActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(StudySessionDetailedActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(userLatLng.first, userLatLng.second))
                        .title("You"));

                googleMap.addMarker(new MarkerOptions()
                        .position(
                                new LatLng(studySession.getLatitude(), studySession.getLongitude()))
                        .title(studySession.getLocationName())
                        .snippet(studySession.getLocationAddress()));

                CameraUpdate center = CameraUpdateFactory
                        .newLatLng(new LatLng(studySession.getLatitude(),
                                studySession.getLongitude()));

                CameraPosition zoom = new CameraPosition.Builder()
                        .target(new LatLng(userLatLng.first, userLatLng.second))
                        .zoom(13f)
                        .build();
                googleMap.moveCamera(center);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(zoom));

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
