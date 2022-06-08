package com.example.attraction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Map;


public class MapActivity extends AppCompatActivity {

    MapView map,mMap;
    MyLocationNewOverlay myLocationOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);
        mapView();
    }

    public void mapView(){
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(20);
        GeoPoint startPoint = new GeoPoint(23.7183197, 120.4501789);
        mapController.setCenter(startPoint);
        //this.myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx),map);
        //this.myLocationOverlay.enableMyLocation();
        //map.getOverlays().add(this.myLocationOverlay);
        putItems();
    }

    public void putItems(){
        Context ctx = getApplicationContext();
        ArrayList<OverlayItem> items = new ArrayList<>();
        items.add(new OverlayItem("堀頭里社區活動中心", "",new GeoPoint(23.7183197, 120.4501789)));
        items.add(new OverlayItem("堀頭里社區公園","",new GeoPoint( 23.7180002, 120.4505321)));
        mMap = (MapView)findViewById(R.id.map);

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        if(index==0){
                            startActivity(new Intent(MapActivity.this,FirstLevel.class));
                        }
                        else {
                            startActivity(new Intent(MapActivity.this,SecondLevel.class));
                        }
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        return false;
                    }
                },ctx);
        mOverlay.setFocusItemsOnTap(true);
        mMap.getOverlays().add(mOverlay);
    }
}