/***
 * Copyright (c) 2010 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.geoloc.android.mapview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

public class SimpleItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Context c;
	
	public SimpleItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		c = mapView.getContext();
	}

	public void addOverlay(OverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
	//	Toast.makeText(c, "onBalloonTap for overlay index " + index,
	//			Toast.LENGTH_LONG).show();
		
		Geocoder geocoder = new Geocoder(c,Locale.ENGLISH);
		String addr;
		Log.d("Geocode", item.getPoint().getLongitudeE6()/1E6+ " ||||| "+item.getPoint().getLongitudeE6()*1E6 );
		
		try {
			List<Address> addresses = geocoder.getFromLocation(item.getPoint().getLatitudeE6()/1E6, item.getPoint().getLongitudeE6()/1E6, 1);
		
			if(addresses != null){
				
				   Address returnedAddress = addresses.get(0);
				   StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
				   for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
				    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				   }
			addr = strReturnedAddress.toString();	
			}
			else{				
			addr = "Bu nokta için adres alýnamadý";	
			}
			
		} catch (IOException e) {
			addr = "Bu nokta için adres alýnamadý.";
			e.printStackTrace();
		}
		
		if(addr == null){
			addr = "Bu nokta için adres alýnamadý.";
		}
		
		Toast.makeText(c, "Nerede ? : "+addr,
				Toast.LENGTH_LONG).show();
		
		return true;
	}
	
}
