package org.geoloc.android.mapview;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientActivity {
	public String getInternetData() throws Exception
	{
		BufferedReader input=null;
		String data=null;
		try
		{
			HttpClient client =new DefaultHttpClient();
			URI server=new URI("http://107.20.156.81:8080/horse-0.0.1-SNAPSHOT/ws/getAllUserLocation");
			HttpGet request=new HttpGet();
			request.setURI(server);
			HttpResponse response=client.execute(request);
			input=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			//////////
			StringBuffer sb=new StringBuffer("");
			String l="";
			String newl=System.getProperty("line.separator");
			
			while((l=input.readLine())!=null)
			{
				sb.append(l+newl);		
			}
			input.close();
			data=sb.toString();
			return data;
			
		}
		finally
		{
			if(input!=null)
			{
				try
				{
					input.close();
					return data;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		
	
	}

}
