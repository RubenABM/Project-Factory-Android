package com.myapplication;

import static android.content.ContentValues.TAG;

import static com.myapplication.TinyWebServer.startTripFlag;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable cameraUpdateRunnable;
    private boolean coordflag = false;
    private  float zoomLevel = 25.0f;

    private List<LatLng> latLngList = new ArrayList<>();
    private Polyline polyline;

    private boolean firstPoint;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MAP_FRAG);


        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                String previouslatstr = DataHolder.getInstance().getDataMap().get("gpslat");
                String previouslongstr = DataHolder.getInstance().getDataMap().get("gpslong");
                if(!DataHolder.getInstance().getDataMap().isEmpty()){
                    Log.d(TAG,"Dataholder was not empty!");
                    double previouslat = Double.parseDouble(previouslatstr);
                    double previouslong = Double.parseDouble(previouslongstr);
                    LatLng latLng = new LatLng(previouslat, previouslong);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(latLng.latitude +"KG"+ latLng.longitude);
                    googleMap.addMarker(markerOptions);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel));
                    latLngList.add(new LatLng(previouslat, previouslong));
                    PolylineOptions polylineOptions = new PolylineOptions()
                            .addAll(latLngList)
                            .width(5) // Set the line width
                            .color(Color.RED); // Set the line color
                    polyline = googleMap.addPolyline(polylineOptions);

                }else {
                    Log.d(TAG, "FALLBACK POSITION");
                    double latitude = 38.7073618;
                    previouslatstr = "38.7073618";
                    double longitude = -9.152648;
                    previouslongstr = "-9.152648";
                    LatLng latLng = new LatLng(latitude, longitude);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("fallback position");
                    googleMap.addMarker(markerOptions);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel));
                }
                String finalPreviouslatstr = previouslatstr;
                String finalPreviouslongstr = previouslongstr;

                cameraUpdateRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG,"position was updated!");
                        String currentlatitudestr = DataHolder.getInstance().getDataMap().get("gpslat");
                        String currentlongitudestr = DataHolder.getInstance().getDataMap().get("gpslong");
                        if(currentlatitudestr == null && currentlongitudestr == null){
                            //Log.d(TAG, "FALLBACK POSITION");
                            double latfallback = 38.7073618;
                            double longfallback = -9.152648;
                            LatLng latLng = new LatLng(latfallback, longfallback);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);
                            markerOptions.title("fallback position");
                            googleMap.addMarker(markerOptions);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        }else{
                            checkCoordinates(finalPreviouslatstr, finalPreviouslongstr,currentlatitudestr,currentlongitudestr);
                            if(coordflag) {
                                Log.d(TAG, "Position was different!");
                                assert currentlatitudestr != null;
                                double currentlatitude = Double.parseDouble(currentlatitudestr);
                                assert currentlongitudestr != null;
                                double currentlongitude = Double.parseDouble(currentlongitudestr);
                                LatLng latLng = new LatLng(currentlatitude, currentlongitude);
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.title(latLng.latitude + "KG" + latLng.longitude);
                                googleMap.addMarker(markerOptions);
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                addPointToLine(latLng);
                                if (firstPoint){
                                    latLngList.add(latLng);
                                    PolylineOptions polylineOptions = new PolylineOptions()
                                            .addAll(latLngList)
                                            .width(5) // Set the line width
                                            .color(Color.RED); // Set the line color
                                    polyline = googleMap.addPolyline(polylineOptions);
                                }
                            }
                        }
                        handler.postDelayed(this, 5000); // Update every 5 seconds
                    }
                };
                handler.post(cameraUpdateRunnable);

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {

                        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude +"KG"+ latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });
        return view;
    }
    private void checkCoordinates(String previouslatstr, String previouslongstr,String currentlatitudestr, String currentlongitudestr){

        double previouslat = Double.parseDouble(previouslatstr);
        double previouslong = Double.parseDouble(previouslongstr);
        double currentlatitude = Double.parseDouble(currentlatitudestr);
        double currentlongitude = Double.parseDouble(currentlongitudestr);
        if(currentlatitude != previouslat && currentlongitude != previouslong){
            coordflag = true;
        } else if (currentlatitude == previouslat && currentlongitude == previouslong) {
            coordflag = false;
        }
    }

    private boolean addPointToLine(LatLng latLng) {
        if(!latLngList.isEmpty()){
            latLngList.add(latLng);
            polyline.setPoints(latLngList);
            return firstPoint = false;
        }
        return firstPoint = true;

    }
}