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
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("helloWorld")) {
			JSONObject obj = new JSONObject();
            obj.put("argument", args.getString(0));
			obj.put("hello", "Hello, world!");
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
