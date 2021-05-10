package com.xiaomi.parts.vibration;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceManager;

import com.xiaomi.parts.vibration.VibrationSettings;
import com.xiaomi.parts.vibration.VibrationUtils;

public class Vibration implements OnPreferenceChangeListener {

    private Context mContext;

    public Vibration(Context context) {
        mContext = context;
    }

    public static String getFile() {
        if (VibrationUtils.fileWritable(VibrationSettings.PREF_VIBRATION_PATH)) {
            return VibrationSettings.PREF_VIBRATION_PATH;
        }
        return null;
    }

    public static boolean isSupported() {
        return VibrationUtils.fileWritable(getFile());
    }

    public static boolean isCurrentlyEnabled(Context context) {
        return VibrationUtils.getFileValueAsBoolean(getFile(), false);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        switch (key) {
            case VibrationSettings.PREF_VIBRATION_OVERRIDE:
                VibrationUtils.setValue(VibrationSettings.PREF_VIBRATION_PATH, (boolean) value);
                break;

            default:
                break;
        }
        return true;
    }
}
