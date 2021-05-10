package com.xiaomi.parts.touch;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SELinux;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.xiaomi.parts.preferences.SecureSettingSwitchPreference;
import com.xiaomi.parts.touch.TouchUtils;
import com.xiaomi.parts.R;

public class TouchSettings extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "TouchSettings";

    public static final String GLOVE_MODE = "glove_mode";
    public static final String GLOVE_PATH = "/sys/devices/virtual/tp_glove/device/glove_enable";

    private static Context mContext;
    private SecureSettingSwitchPreference mGlove;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.touch, rootKey);
        mContext = this.getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        mGlove = (SecureSettingSwitchPreference) findPreference(GLOVE_MODE);
        mGlove.setEnabled(TouchUtils.fileWritable(GLOVE_PATH));
        mGlove.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        switch (key) {
            case GLOVE_MODE:
		TouchUtils.setValue(GLOVE_PATH, (boolean) value);
                break;
        }
        return true;
    }
}
