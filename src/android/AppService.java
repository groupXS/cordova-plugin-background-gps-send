/***
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Advanced Android Development_
    http://commonsware.com/AdvAndroid
*/

package com.groupxs.cordova.plugin.backgroundgpssend;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpStatus;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class AppService extends WakefulIntentService {
	public AppService() {
		super("AppService");
	}

	@Override
	protected void doWakefulWork(Intent intent) {
		String token = intent.getStringExtra("token");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
			nameValuePairs.add(new BasicNameValuePair("Sender", token));
			nameValuePairs.add(new BasicNameValuePair("Latitude", "0"));
			nameValuePairs.add(new BasicNameValuePair("Longitude", "0"));
			nameValuePairs.add(new BasicNameValuePair("Accuracy", "0"));
			nameValuePairs.add(new BasicNameValuePair("Altitude", "0"));
			nameValuePairs.add(new BasicNameValuePair("Heading", "0"));
			nameValuePairs.add(new BasicNameValuePair("Speed", "0"));
			nameValuePairs.add(new BasicNameValuePair("AltitudeAccuracy", "0"));

			getJSONfromURL("https://bikeheldapi-dev.azurewebsites.net/api/GpsUpdate", nameValuePairs);
		}
		catch (Exception e) {
			Log.d("CordovaPlugin", "Exception sending data to server");
		}
	}

	//private LocationManager locationManager;    
    //private GPSListener gpsListener;
    //private NetworkListener networkListener;

    public void getJSONfromURL(String url, List<NameValuePair> nameValuePairs) throws Exception {
        try {
		    HttpClient httpclient = new DefaultHttpClient();

			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(post);
			int responseStatus = response.getStatusLine().getStatusCode();

			if(responseStatus == HttpStatus.SC_NO_CONTENT)
				Log.d("CordovaPlugin", "Data sent");
			else
				Log.d("CordovaPlugin", "Data not sent. Response status: " + responseStatus);

        } catch (Exception e) {
        	throw e;
        }        	
    }   
}