package org.geoloc.android.mapview;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;

public class MainActivity extends MapActivity {

	MapView Gmap;
	MyLocationOverlay FavoritePlace;
	MapController controller;
	long start,stop;
	int x,y;
	Drawable d;
	GeoPoint touchedPoint;
	List<Overlay> overlayList ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Gmap = (MapView) findViewById(R.id.mapview);
        Gmap.setBuiltInZoomControls(true);
       
        Touchy t = new Touchy();
        overlayList = Gmap.getOverlays();
        overlayList.add(t);
      
        FavoritePlace = new MyLocationOverlay(MainActivity.this, Gmap);
        overlayList.add(FavoritePlace);
        
        controller = Gmap.getController();
        GeoPoint point = new GeoPoint(40991204, 39777067);
        controller.animateTo(point);
        controller.setZoom(8);
        
        d = getResources().getDrawable(R.drawable.pin);
        
        ArrayList<LocationData> datas = new ArrayList<LocationData>();
        datas = CustomHttpClient.getAllUserLocations();
        Log.d("MainAct", "Objects : "+datas.size());
        
        CustomHttpClient.getAllUsers();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	class Touchy extends Overlay{

		@Override
		public boolean onTouchEvent(MotionEvent e, MapView mapView) {
			
			if(e.getAction() == MotionEvent.ACTION_DOWN){
				start = e.getEventTime();
				x = (int) e.getX();
				y = (int) e.getY();
				
				touchedPoint = mapView.getProjection().fromPixels(x, y);
			}
			
			if(e.getAction() == MotionEvent.ACTION_UP){
				stop = e.getEventTime();
			}
			
			if((stop - start) > 1500){
				AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
				alert.setTitle("Options");
				String option;
				if(Gmap.isSatellite())
					option = "Street View";
				else
					option = "Satellite View";
				
				alert.setButton(option, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						if(Gmap.isSatellite()){
							Gmap.setSatellite(false);
							Gmap.setStreetView(true);
						}else
						{
							Gmap.setStreetView(false);
							Gmap.setSatellite(true);
						}
						
						
						
					}
				});
				
				alert.setButton2("Place a pin", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
						OverlayItem item = new OverlayItem(touchedPoint, "What'a place", "Snipppet");
						CustomPinpoint pin = new CustomPinpoint(d,MainActivity.this);
						pin.insertPinpoint(item);
						overlayList.add(pin);
					}
				});
				
			alert.show();
			
			}
			
			return false;
		}
		
	}
	

}