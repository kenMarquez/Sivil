package com.example.ken.materialdesginexample.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.parse.Parse;

public class Application extends android.app.Application {

    // Debugging switch
    public static final boolean APPDEBUG = false;

    // Debugging tag for the application
    public static final String APPTAG = "MXHACK";

    // Used to pass location from MainActivity to PostActivity
    public static final String INTENT_EXTRA_LOCATION = "location";

    // Key for saving the search distance preference
    private static final String KEY_SEARCH_DISTANCE = "searchDistance";

    private static final float DEFAULT_SEARCH_DISTANCE = 250.0f;

    private static SharedPreferences preferences;

    private static ConfigHelper configHelper;

    public Application() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //ParseObject.registerSubclass(AnywallPost.class);
//    Parse.initialize(this, "YOUR_PARSE_APPLICATION_ID",
//        "YOUR_PARSE_CLIENT_KEY");
        Parse.initialize(this, "HOSbo7SnGohYmujHXl50KSo2l7sDEC9skkgGoo5p",
                "nGIBnhGxJzc6Rrf2YDNlX2argoj9DjeO4pfes9Cy");

        preferences = getSharedPreferences("com.parse.mxhack", Context.MODE_PRIVATE);
        configHelper = new ConfigHelper();
        configHelper.fetchConfigIfNeeded();
    }

    public static float getSearchDistance() {
        return preferences.getFloat(KEY_SEARCH_DISTANCE, DEFAULT_SEARCH_DISTANCE);
    }

    public static ConfigHelper getConfigHelper() {
        return configHelper;
    }

    public static void setSearchDistance(float value) {
        preferences.edit().putFloat(KEY_SEARCH_DISTANCE, value).commit();
    }


}
