package org.openmrs.client.applications;

import android.app.Application;
import android.content.SharedPreferences;
import org.openmrs.client.utils.ApplicationConstants;

public class ACApplication extends Application {

    private static ACApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ACApplication getInstance() {
        return instance;
    }

    public void setUsernamePreference(String username) {

        SharedPreferences.Editor editor = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE).edit();
        editor.putString(ApplicationConstants.USER_NAME, username);
        editor.commit();
    }

    public void setServerUrlPreference(String serverUrl) {

        SharedPreferences.Editor editor = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE).edit();
        editor.putString(ApplicationConstants.SERVER_URL, serverUrl);
        editor.commit();
    }

    public void setSessionIdPreference(String serverUrl) {

        SharedPreferences.Editor editor = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE).edit();
        editor.putString(ApplicationConstants.SESSION_ID, serverUrl);
        editor.commit();
    }

    public String getUsernamePreference() {

        SharedPreferences sp = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE);
        return sp.getString(ApplicationConstants.USER_NAME, "");
    }

    public String getServerUrlPreference() {

        SharedPreferences sp = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE);
        return sp.getString(ApplicationConstants.SERVER_URL, "");
    }

    public String getSessionIdPreference() {

        SharedPreferences sp = getSharedPreferences(ApplicationConstants.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE);
        return sp.getString(ApplicationConstants.SESSION_ID, "");
    }
}
