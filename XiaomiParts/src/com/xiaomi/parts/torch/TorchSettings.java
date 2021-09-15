package com.xiaomi.parts.torch;

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

import android.os.SystemProperties;
import com.xiaomi.parts.preferences.CustomSeekBarPreference;
import com.xiaomi.parts.R;

public class TorchSettings extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "TorchSettings";

    public static final  String KEY_YELLOW_TORCH_BRIGHTNESS = "yellow_torch_brightness";
    public static final  String TORCH1_PROPERTY = "ro.parts.torch1";
    public static final  String KEY_WHITE_TORCH_BRIGHTNESS = "white_torch_brightness";
    public static final  String TORCH2_PROPERTY = "ro.parts.torch2";

    private static Context mContext;
    private CustomSeekBarPreference mWhiteTorchBrightness;
    private CustomSeekBarPreference mYellowTorchBrightness;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.torch, rootKey);
        mContext = this.getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        mWhiteTorchBrightness = (CustomSeekBarPreference) findPreference(KEY_WHITE_TORCH_BRIGHTNESS);
	mWhiteTorchBrightness.setValue(SystemProperties.getInt(TORCH1_PROPERTY, 255));
        mWhiteTorchBrightness.setOnPreferenceChangeListener(this);

        mYellowTorchBrightness = (CustomSeekBarPreference) findPreference(KEY_YELLOW_TORCH_BRIGHTNESS);
        mYellowTorchBrightness.setValue(SystemProperties.getInt(TORCH2_PROPERTY, 255));
        mYellowTorchBrightness.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        switch (key) {
            case KEY_WHITE_TORCH_BRIGHTNESS:
		SystemProperties.set(TORCH1_PROPERTY, String.valueOf(value));
                break;

            case KEY_YELLOW_TORCH_BRIGHTNESS:
                SystemProperties.set(TORCH2_PROPERTY, String.valueOf(value));
                break;
        }
        return true;
    }
}
