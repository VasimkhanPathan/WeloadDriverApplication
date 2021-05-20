package com.vasim.driverapplication.JobDescription.view;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.vasim.driverapplication.R;
import com.vasim.driverapplication.util.Common;
import com.vasim.driverapplication.util.interfaces.IGoogleAPi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowMapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private List<LatLng> polylineList;
    private Marker marker;
    private float v;
    private double lat,lng;
    private Handler handler;
    private LatLng startPosition,endPosition;
    private int indext,next;
    private Button btnGo;
    private String destination;
    private PolylineOptions polylineOptions,blackPloylineOption;
    private Polyline blackPolyline,grayPolyline;
    private LatLng myLocation;

    IGoogleAPi myService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        polylineList = new ArrayList<>();
        myService = Common.getGoogleApi();


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }


                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(18.5204, 73.8567);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(8).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
               /* mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().
                        target(googleMap.getCameraPosition().target).zoom(12)
                .bearing(30)
                .tilt(45)
                .build()));*/

                googleMap.setMyLocationEnabled(true);
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.setTrafficEnabled(false);
                googleMap.setIndoorEnabled(false);
                googleMap.setBuildingsEnabled(false);
                googleMap.getUiSettings().setZoomControlsEnabled(true);

                destination="Solapur,Maharashtra";
                String origin = "Pune,Maharashtra";
                String requestUrl=null;
                try {
                    requestUrl = "https://maps.googleapis.com/maps/api/directions/jons?" +
                            "mode=driving&" +
                            "transit_routing_preference=less_driving&" +
                            "origin=" + origin + "&" +
                            "destination=" + destination + "&" +
                            "key=" + getResources().getString(R.string.map_key);
                    Log.d("Log","url ="+requestUrl);

                    myService.getDataFromGoogleApi(requestUrl).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            try{
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("routes");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject routes= jsonArray.getJSONObject(i);
                                    JSONObject poly= routes.getJSONObject("overview_polyline");
                                    String polyline= poly.getString("points");
                                    polylineList = decodePoly(polyline);
                                }

                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for(LatLng latLng:polylineList)
                                    builder.include(latLng);
                                LatLngBounds bounds = builder.build();
                                CameraUpdate mCameraUpdate= CameraUpdateFactory.newLatLngBounds(bounds,2);
                                mMap.animateCamera(mCameraUpdate);

                                polylineOptions = new PolylineOptions();
                                polylineOptions.color(Color.GRAY);
                                polylineOptions.width(5);
                                polylineOptions.startCap(new SquareCap());
                                polylineOptions.endCap(new SquareCap());
                                polylineOptions.jointType(JointType.ROUND);
                                polylineOptions.addAll(polylineList);
                                grayPolyline= mMap.addPolyline(polylineOptions);

                                blackPloylineOption = new PolylineOptions();
                                blackPloylineOption.color(Color.BLACK);
                                blackPloylineOption.width(5);
                                blackPloylineOption.startCap(new SquareCap());
                                blackPloylineOption.endCap(new SquareCap());
                                blackPloylineOption.jointType(JointType.ROUND);
                                blackPloylineOption.addAll(polylineList);
                                blackPolyline= mMap.addPolyline(polylineOptions);

                                mMap.addMarker(new MarkerOptions().position(polylineList.get(polylineList.size()-1)));


                               final ValueAnimator polyLineAnimator =ValueAnimator.ofInt(0,100);
                                polyLineAnimator.setDuration(2000);
                                polyLineAnimator.setInterpolator(new LinearInterpolator());
                                polyLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        List<LatLng> points=grayPolyline.getPoints();
                                        int percetValue= (int) animation.getAnimatedValue();
                                        int size = points.size();
                                        int newPoint = (int) (size *(percetValue/100.0f));
                                        List<LatLng> p = points.subList(0,newPoint);
                                        blackPolyline.setPoints(p);
                                    }
                                });
                                polyLineAnimator.start();
                                marker = mMap.addMarker(new MarkerOptions().position(sydney)
                                .flat(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_truck_top__02)));

                                handler = new Handler();
                                indext =-1;
                                next=-1;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(indext<polylineList.size()-1){
                                            indext++;
                                            next=indext+1;
                                        }
                                        if(indext<polylineList.size()-1){
                                            startPosition= polylineList.get(indext);
                                            endPosition= polylineList.get(next);

                                        }
                                        ValueAnimator valueAnimator =ValueAnimator.ofInt(0,1);
                                        valueAnimator.setDuration(3000);
                                        valueAnimator.setInterpolator(new LinearInterpolator());
                                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator animation) {
                                                v =animation.getAnimatedFraction();
                                                lng=v*endPosition.longitude+(1-v)
                                                        *startPosition.longitude;
                                                lat=v*endPosition.latitude+(1-v)
                                                        *startPosition.latitude;
                                                LatLng newPostion = new LatLng(lat,lng);
                                                marker.setPosition(newPostion);
                                                marker.setAnchor(0.5f,0.5f);
                                                marker.setRotation(getBearing(startPosition,newPostion));
                                                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                                .target(newPostion)
                                                .zoom(15.5f)
                                                .build()));

                                            }
                                        });
                                        valueAnimator.start();
                                        handler.postDelayed(this,3000);
                                    }
                                },3000);

                            }catch (Exception e){
                                Log.d("Log","eroor "+e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("Log","Error "+e.getMessage());
                }
            }

            private List<LatLng> decodePoly(String encoded) {
                List poly = new ArrayList();
                int index = 0, len = encoded.length();
                int lat = 0, lng = 0;

                while (index < len) {
                    int b, shift = 0, result = 0;
                    do {
                        b = encoded.charAt(index++) - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                    } while (b >= 0x20);
                    int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                    lat += dlat;

                    shift = 0;
                    result = 0;
                    do {
                        b = encoded.charAt(index++) - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                    } while (b >= 0x20);
                    int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                    lng += dlng;

                    LatLng p = new LatLng((((double) lat / 1E5)),
                            (((double) lng / 1E5)));
                    poly.add(p);
                }

                return poly;
            }
        });
        return view;
    }

    private float getBearing(LatLng startPosition, LatLng newPostion) {

        double lat = Math.abs(startPosition.latitude - newPostion.latitude);
        double lng=Math.abs(startPosition.longitude-newPostion.longitude);
        if(startPosition.latitude<newPostion.latitude&& startPosition.longitude<newPostion.longitude)
            return (float) (Math.toDegrees(Math.atan(lng/lat)));
        else    if(startPosition.latitude>=newPostion.latitude&& startPosition.longitude<newPostion.longitude)
            return (float) ((90-Math.toDegrees(Math.atan(lng/lat)))+90);
        else    if(startPosition.latitude>=newPostion.latitude&& startPosition.longitude>=newPostion.longitude)
            return (float) (Math.toDegrees(Math.atan(lng/lat))+180);
        else    if(startPosition.latitude<newPostion.latitude&& startPosition.longitude>=newPostion.longitude)
            return (float) ((90-Math.toDegrees(Math.atan(lng/lat)))+270);

        return -1;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}