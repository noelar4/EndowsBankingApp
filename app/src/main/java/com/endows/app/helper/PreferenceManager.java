package com.endows.app.helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class PreferenceManager {

    public interface PreferenceKeys {
        String PREFERENCE_NAME = "pref_endows";

        String PREF_USER_CARD_NO = "pref_card_no";

    }

    private SharedPreferences preferences;

    public PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(PreferenceKeys.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void setPreference(String key,@Nullable String value) {
        if (value == null) return;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setPreference(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setPreference(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void setPreference(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void setPreference(String key, float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public String getStringPreference(String key) {
        return preferences.getString(key, null);
    }

    public int getIntPreference(String key) {
        return preferences.getInt(key, -1);
    }

    public long getLongPreference(String key) {
        return preferences.getLong(key, -1);
    }

    public boolean getBooleanPreference(String key) {
        return preferences.getBoolean(key, false);
    }

    public float getFloatPreference(String key) {
        return preferences.getFloat(key, -1);
    }
}
