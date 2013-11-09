package com.groupxs.cordova.plugin.backgroundgpssend;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import android.app.AlarmManager;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/*
 * This class is the interface to com.groupxs.cordova.plugin.backgroundgpssend.
 */

public class BackgroundSender extends CordovaPlugin {
    /**
     * Executes the request and returns PluginResult.
     *
     * @param action 		The action to execute.
     * @param args 		JSONArry of arguments for the plugin.
     * @param callbackContext	The callback id used when calling back into JavaScript.
     * @return 			True if the action was valid, or false if not.
     */

	AlarmManager mgr = null;
	PendingIntent pi = null;
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("helloWorld")) {
			JSONObject obj = new JSONObject();
            obj.put("argument", args.getString(0));
			obj.put("hello", "Hello, world!");
            PluginResult result = new PluginResult(PluginResult.Status.OK, obj);
            callbackContext.sendPluginResult(result);
		} else if (action.equals("startService")) {
			Activity context = cordova.getActivity();
			mgr=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			
			Intent i=new Intent(context, OnAlarmReceiver.class);

			String token = args.getString(0);
			i.putExtra("token", token);

			pi = PendingIntent.getBroadcast(context, 0, i, 0);
    
			int PERIOD=30000;  // 1/2 minute

			mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
							  SystemClock.elapsedRealtime()+60000,
							  PERIOD,
							  pi);		
					
			JSONObject obj = new JSONObject();
            obj.put("message", "Service started");
            PluginResult result = new PluginResult(PluginResult.Status.OK, obj);
            callbackContext.sendPluginResult(result);
		} else if (action.equals("stopService")) {
			String message = "Service is not running";
			if (mgr != null && pi != null){
				mgr.cancel(pi);
				pi = null;
				mgr = null;
				message = "Service stopped";
			}

			JSONObject obj = new JSONObject();
            obj.put("message", message);
            PluginResult result = new PluginResult(PluginResult.Status.OK, obj);
            callbackContext.sendPluginResult(result);
        } else {
            String message = "This call to the API is unknown.";
            PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, message);
            callbackContext.sendPluginResult(result);
        }
		
        return true;
    }
}
