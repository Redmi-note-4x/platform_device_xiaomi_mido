package com.xiaomi.parts;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceManager;

import com.xiaomi.parts.DeviceSettings;

public class Vibration implements OnPreferenceChangeListener {

    private Context mContext;

    public Vibration(Context context) {
        mContext = context;
    }

    public static String getFile() {
        if (FileUtils.fileWritable(DeviceSettings.PREF_VIBRATION_PATH)) {
            return DeviceSettings.PREF_VIBRATION_PATH;
        }
        return null;
    }

    public static boolean isSupported() {
        return FileUtils.fileWritable(getFile());
    }

    public static boolean isCurrentlyEnabled(Context context) {
        return FileUtils.getFileValueAsBoolean(getFile(), false);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        switch (key) {
            case DeviceSettings.PREF_VIBRATION_OVERRIDE:
                FileUtils.setValue(DeviceSettings.PREF_VIBRATION_PATH, (boolean) value);
                break;

            default:
                break;
        }
        return true;
    }
}
