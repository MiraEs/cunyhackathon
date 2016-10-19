package ar.ey.c4q.com.studybuddy.home;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


public class HomeActivity extends AppCompatActivity
        implements StudySessionViewHolder.SessionItemClickListener {

    public static final String EXTRA_DATA_STUDY_SESSION = "EXTRA_DATA_STUDY_SESSION";

    private MapView mapView;

    private GoogleMap googleMap;

    private ActiveStudySessionListAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                ContextCompat.getDrawable(this, R.drawable.line_divider)));

        listAdapter = new ActiveStudySessionListAdapter(this, this);
        recyclerView.setAdapter(listAdapter);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                HomeActivity.this.googleMap = googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return true;
                    }
                });
                if (ActivityCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(HomeActivity.this,
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
                        .zoom(13f)
                        .build();
                googleMap.moveCamera(center);
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(zoom));

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        startActivity(new Intent(HomeActivity.this, MapViewActivity.class));
                    }
                });

                loadData();

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

    private void loadData() {
        Observable.fromCallable(new Callable<ArrayList<StudySession>>() {
            @Override
            public ArrayList<StudySession> call() throws Exception {
                String raw = LoaderHelper
                        .parseFileToString(HomeActivity.this, "study_session_list_data.json");
                JSONObject jsonObject = new JSONObject(raw);
                Type type = new TypeToken<ArrayList<StudySession>>() {
                }.getType();
                return new Gson()
                        .fromJson(jsonObject.getJSONArray("EXTRA_DATA_STUDY_SESSION").toString(),
                                type);
            }
        }).subscribe(new Action1<ArrayList<StudySession>>() {
            @Override
            public void call(final ArrayList<StudySession> studySessions) {
                listAdapter.setData(studySessions);

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
                Toast.makeText(HomeActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClickSessions(StudySession session) {
        Intent i = new Intent(HomeActivity.this, StudySessionDetailedActivity.class);
        i.putExtra(EXTRA_DATA_STUDY_SESSION, session);
        startActivity(i);
    }
}
