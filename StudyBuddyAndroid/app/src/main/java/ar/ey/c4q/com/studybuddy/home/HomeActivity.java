package ar.ey.c4q.com.studybuddy.home;

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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import ar.ey.c4q.com.studybuddy.R;
import ar.ey.c4q.com.studybuddy.models.StudySession;
import ar.ey.c4q.com.studybuddy.util.LoaderHelper;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


public class HomeActivity extends AppCompatActivity implements StudySessionViewHolder.SessionItemClickListener {

    MapView map;
    GoogleMap gMap;
    private ActiveStudySessionListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ActiveStudySessionListAdapter(this, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                ContextCompat.getDrawable(this, R.drawable.line_divider)));
        recyclerView.setAdapter(adapter);
        map = (MapView) findViewById(R.id.map);
        map.onCreate(savedInstanceState);

        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
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
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.767929, -73.964001))
                        .title("You"));

                CameraUpdate center = CameraUpdateFactory
                        .newLatLng(new LatLng(40.7659217, -73.96370569999999));
                CameraPosition zoom = new CameraPosition.Builder()
                        .target(new LatLng(40.7659217, -73.96370569999999))
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

    private void loadData() {
        Observable.fromCallable(new Callable<ArrayList<StudySession>>() {
            @Override
            public ArrayList<StudySession> call() throws Exception {
                String raw = LoaderHelper.parseFileToString(HomeActivity.this, "study_session_list_data.json");
                JSONObject jsonObject = new JSONObject(raw);
                Type type = new TypeToken<ArrayList<StudySession>>() {
                }.getType();
                return new Gson().fromJson(jsonObject.getJSONArray("data").toString(), type);
            }
        }).subscribe(new Action1<ArrayList<StudySession>>() {
            @Override
            public void call(final ArrayList<StudySession> studySessions) {
                adapter.setData(studySessions);

                Observable.just(studySessions)
                        .flatMap(new Func1<ArrayList<StudySession>, Observable<StudySession>>() {
                            @Override
                            public Observable<StudySession> call(ArrayList<StudySession> studySessions) {
                                return Observable.from(studySessions);
                            }
                        })
                        .subscribe(new Action1<StudySession>() {
                            @Override
                            public void call(StudySession session) {
                                if (gMap != null) {
                                    gMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(session.getLatitude(), session.getLongitude()))
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

    @Override
    public void onClickSessions(StudySession session) {
        Intent i = new Intent(HomeActivity.this, StudySessionDetailedActivity.class);
        i.putExtra("data", session);
        startActivity(i);
    }
}
