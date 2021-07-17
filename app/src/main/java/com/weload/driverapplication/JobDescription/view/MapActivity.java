package com.weload.driverapplication.JobDescription.view;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
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
import com.google.gson.JsonObject;
import com.weload.driverapplication.JobDescription.model.JobDescriptionRespons;
import com.weload.driverapplication.R;
import com.weload.driverapplication.util.Common;
import com.weload.driverapplication.util.interfaces.IGoogleAPi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback{
    MapView mMapView;
    private GoogleMap googleMap;
    private List<LatLng> polylineList;
    private PolylineOptions polylineOptions,blackPloylineOption;
    private Polyline blackPolyline,grayPolyline;
    private LatLng myLocation;

    IGoogleAPi myService;
    ArrayList<JobDescriptionRespons.Data.GeoEncodes> geoEncodesArrayList;
    String requestUrl=null;
    LatLng pune;
    LatLng source,dest,first;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivBackwhite)
    ImageView ivBackwhite;
    @BindView(R.id.toolTitleWhite)
    TextView tvTitle;
    @BindView(R.id.layout_File_blue)
    LinearLayout layoutFile;
    @BindView(R.id.layoutMap)
    LinearLayout layoutMap;
    BitmapDrawable bitmapdraw;
    Bitmap b;
    Bitmap smallMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        ivBack.setVisibility(View.GONE);
        layoutFile.setVisibility(View.GONE);
        layoutMap.setVisibility(View.GONE);
        tvTitle.setText("Map");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        polylineList = new ArrayList<>();
        myService = Common.getGoogleApi();
        BitmapDescriptor customIcon ;
        geoEncodesArrayList = new ArrayList<>();
        geoEncodesArrayList = getIntent().getParcelableArrayListExtra("location");

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapFragment.getMapAsync(this);

         bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.map_marker);
         b = bitmapdraw.getBitmap();
         smallMarker = Bitmap.createScaledBitmap(b, 70, 80, false);
        /*mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

              /*  pune = new LatLng( 18.6505,73.7786);
                LatLng pune1 = new LatLng(18.4529,73.8652);
                LatLng pune2 = new LatLng(18.5089,73.9259);*/

                // For dropping a marker at a point on the Map

            /*    googleMap.addMarker(new MarkerOptions().position(first).title("Marker Title"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target((first)).zoom(17).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
              /*


                googleMap.setMyLocationEnabled(true);
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.setTrafficEnabled(false);
                googleMap.setIndoorEnabled(false);
                googleMap.setBuildingsEnabled(false);
                googleMap.getUiSettings().setZoomControlsEnabled(true);


               *//* try {

                    for(int i=0;i<geoEncodesArrayList.size()-1;i++){
                        source = new LatLng(geoEncodesArrayList.get(i).latitude,geoEncodesArrayList.get(i).longitude);
                        dest = new LatLng(geoEncodesArrayList.get(i+1).latitude,geoEncodesArrayList.get(i+1).longitude);
                        drawPolyline(source,dest);
                    }
                *//**//*    drawPolyline(pune,pune1);
                    drawPolyline(pune1,pune2);*//**//*
                    CameraPosition cameraPosition = new CameraPosition.Builder().target((first)).zoom(17).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("Log","Error "+e.getMessage());
                }*//*
            }

        });*/

    }

    @OnClick(R.id.ivBackwhite)
    public void onBack(){
        finish();
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

/*
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

*/


    public void drawPolyline(LatLng source,LatLng dest){
        requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                "mode=driving&" +
                "transit_routing_preference=less_driving&" +
                "origin=" + source.latitude+","+source.longitude + "&" +
                "destination=" +  dest.latitude+","+dest.longitude + "&" +
                "key=" + getResources().getString(R.string.map_key);
        Log.d("Log","url ="+requestUrl);
        myService.getDataFromGoogleApi(requestUrl).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

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
                    googleMap.animateCamera(mCameraUpdate);

                    polylineOptions = new PolylineOptions();
                    polylineOptions.color(R.color.splash_color);
                    polylineOptions.width(10);
                    polylineOptions.startCap(new SquareCap());
                    polylineOptions.endCap(new SquareCap());
                    polylineOptions.jointType(JointType.ROUND);
                    polylineOptions.addAll(polylineList);
                    grayPolyline= googleMap.addPolyline(polylineOptions);

                   /* blackPloylineOption = new PolylineOptions();
                    blackPloylineOption.color(Color.BLACK);
                    blackPloylineOption.width(10);
                    blackPloylineOption.startCap(new SquareCap());
                    blackPloylineOption.endCap(new SquareCap());
                    blackPloylineOption.jointType(JointType.ROUND);
                    blackPloylineOption.addAll(polylineList);
                    blackPolyline= googleMap.addPolyline(polylineOptions);*/

                    googleMap.addMarker(new MarkerOptions().position(polylineList.get(polylineList.size()-1))
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));


                    /*ValueAnimator polyLineAnimator =ValueAnimator.ofInt(0,100);
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
                    marker = googleMap.addMarker(new MarkerOptions().position(pune)
                            .flat(true)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_truck)));

                    handler = new Handler();
                    indext =-1;
                    next= 1;
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
                            ValueAnimator valueAnimator =ValueAnimator.ofFloat(0,1);
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
                                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                            .target(newPostion)
                                            .zoom(15.5f)
                                            .build()));

                                }
                            });
                             valueAnimator.start();
                            handler.postDelayed(this,3000);
                        }
                    },3000);
*/
                }catch (Exception e){
                    Log.d("Log","eroor "+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Log","error "+t.getMessage());
            }
        });
    }
    public List<LatLng> decodePoly(String encoded) {
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

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;

        // For showing a move to my location button
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        first = new LatLng(geoEncodesArrayList.get(0).latitude,geoEncodesArrayList.get(0).longitude);

        googleMap.addMarker(new MarkerOptions().position(first)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(false);
        googleMap.setBuildingsEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);


                try {

                    for(int i=0;i<geoEncodesArrayList.size()-1;i++){
                        source = new LatLng(geoEncodesArrayList.get(i).latitude,geoEncodesArrayList.get(i).longitude);
                        dest = new LatLng(geoEncodesArrayList.get(i+1).latitude,geoEncodesArrayList.get(i+1).longitude);
                        drawPolyline(source,dest);
                    }
                    CameraPosition cameraPosition = new CameraPosition.Builder().target((first)).zoom(17).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("Log","Error "+e.getMessage());
                }
    }
}