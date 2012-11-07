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

import android.os.AsyncTask;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends MapActivity {

	MapView Gmap;
	MapController controller;
	long start,stop;
	int x,y;
	Drawable d;
	GeoPoint touchedPoint;
	List<Overlay> overlayList ;
	boolean isrunning = true;
	ArrayList<User> users;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*
         *  Google Map View bileþeninin hazýrlanmasý
         */
        Gmap = (MapView) findViewById(R.id.mapview);
        Gmap.setBuiltInZoomControls(true);
   
        overlayList = Gmap.getOverlays();
              
        controller = Gmap.getController();
        
        /*
         * KTÜ D Kapýsý Koordinatlarý 
         */
        GeoPoint point = new GeoPoint(40991204, 39777067);
        controller.animateTo(point);
        controller.setZoom(8);
        
        d = getResources().getDrawable(R.drawable.pin);
        
        /*
         *  Baþlangýçta tüm kullanýcýlarýn servisten alýnmasý
         */
        users = new ArrayList<User>();
        new InitializeTask().execute();
        
        if(users != null)
        {
	        for(User user : users){
	        	double latitude = user.getLocationData().latitude;
	        	double longitude = user.getLocationData().longitude;
	        	int userID = user.getUserID();
	        	String userName = user.getUserFullName();
	        	
	        	point = new GeoPoint( (int) (latitude*1E6) , (int) (longitude*1E6) );
				
	        	OverlayItem item = new OverlayItem(point, ""+userID, userName);
				CustomPinpoint pin = new CustomPinpoint(d,MainActivity.this);
				pin.insertPinpoint(item);
				overlayList.add(pin);
				
	        }
	                
	        	new UpdateTask().execute();
        
        }else{
        	Toast.makeText(getApplicationContext(), "Connection error. Couldn't get users.", Toast.LENGTH_SHORT).show();
        }
        
        
    }

    /*
     *  Menü tuþuna dokunulduðunda gösterilecek seçenekler.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.activity_login, menu);
    	
        return true;
    }

    
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		
			case R.id.About:
				break;
			
			case R.id.Terrain:
				if(Gmap.isSatellite()){
					Gmap.setSatellite(false);
					Gmap.setStreetView(true);
				}else
				{
					Gmap.setStreetView(false);
					Gmap.setSatellite(true);
				}
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	class InitializeTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			users = CustomHttpClient.getAllUsers();
			return null;
		}
		
	}
	
	class UpdateTask extends AsyncTask<String,ArrayList<LocationData>, Void>{

		@Override
		protected Void doInBackground(String... params) {
			
			while(isrunning){
			try {
				Log.d("MapAct", "Update started...Thread sleeping now.");
				Thread.sleep(3000);
				Log.d("MapAct","Thread woke up");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<LocationData> datas = new ArrayList<LocationData>();
	        datas = CustomHttpClient.getAllUserLocations();
	        
		        if(datas != null){
		        	Log.d("MainAct", "Objects : "+datas.size());
		        }else{
		        	Log.d("MainAct", "Objects : "+" 0 - No Data");
		        }
		        publishProgress(datas);
				}
			return null;
		}

		@Override
		protected void onProgressUpdate(ArrayList<LocationData>... locations) {
			
			if(locations != null){
				
				overlayList.clear();
				Gmap.invalidate();
				overlayList = Gmap.getOverlays();
				
		        for(LocationData locs : locations[0]){
		        	double latitude = locs.getLatitude();
		        	double longitude = locs.getLongitude();
		        	int userID = locs.getUserID();
		        	
		        	GeoPoint point = new GeoPoint( (int) (latitude*1E6) , (int) (longitude*1E6) );
					
		        	OverlayItem item = new OverlayItem(point, ""+userID, "usname");
					CustomPinpoint pin = new CustomPinpoint(d,MainActivity.this);
					pin.insertPinpoint(item);
					overlayList.add(pin);
					
		        }
				
			}else{
				Toast.makeText(getApplicationContext(), "Güncel veriler alýnamadý, baðlantýnýzý kontrol ediniz..", Toast.LENGTH_SHORT).show();
			}
			super.onProgressUpdate(locations);
		}



		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
	}
}

