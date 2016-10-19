package ar.ey.c4q.com.studybuddy.home;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import ar.ey.c4q.com.studybuddy.R;
import ar.ey.c4q.com.studybuddy.models.StudySession;
import ar.ey.c4q.com.studybuddy.util.LoaderHelper;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static ar.ey.c4q.com.studybuddy.util.Constants.userLatLng;


public class MapViewActivity extends AppCompatActivity {

    private MapView mapView;

    private GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detailed);
        getSupportActionBar().setTitle("Active Study Sessions");
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapViewActivity.this.googleMap = googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                if (ActivityCompat.checkSelfPermission(MapViewActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(MapViewActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(userLatLng.first, userLatLng.second))
                        .title("You"));

                CameraUpdate center = CameraUpdateFactory
                        .newLatLng(new LatLng(userLatLng.first, userLatLng.second));

                CameraPosition zoom = new CameraPosition.Builder()
                        .target(new LatLng(userLatLng.first, userLatLng.second))
                        .zoom(13.5f)
                        .build();

                googleMap.moveCamera(center);
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(zoom));

                loadData();
            }
        });
    }

    private void loadData() {
        Observable.fromCallable(new Callable<ArrayList<StudySession>>() {
            @Override
            public ArrayList<StudySession> call() throws Exception {
                String raw = LoaderHelper
                        .parseFileToString(MapViewActivity.this, "study_session_list_data.json");
                JSONObject jsonObject = new JSONObject(raw);
                Type type = new TypeToken<ArrayList<StudySession>>() {
                }.getType();
                return new Gson().fromJson(jsonObject.getJSONArray("data").toString(), type);
            }
        }).subscribe(new Action1<ArrayList<StudySession>>() {
            @Override
            public void call(final ArrayList<StudySession> studySessions) {
                Observable.just(studySessions)
                        .flatMap(new Func1<ArrayList<StudySession>, Observable<StudySession>>() {
                            @Override
                            public Observable<StudySession> call(
                                    ArrayList<StudySession> studySessions) {
                                return Observable.from(studySessions);
                            }
                        })
                        .subscribe(new Action1<StudySession>() {
                            @Override
                            public void call(StudySession session) {
                                if (googleMap != null) {
                                    googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(session.getLatitude(),
                                                    session.getLongitude()))
                                            .snippet(session.getLocationName())
                                            .title(session.getTopicOfStudy()));
                                }
                            }
                        });
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(MapViewActivity.this, throwable.getMessage(), Toast.LENGTH_LONG)
                        .show();
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
