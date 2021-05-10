package com.xiaomi.parts.vibration;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.xiaomi.parts.preferences.VibrationSeekBarPreference;
import com.xiaomi.parts.preferences.SecureSettingSwitchPreference;
import com.xiaomi.parts.vibration.VibrationUtils;
import android.util.Log;

import com.xiaomi.parts.R;

public class VibrationSettings extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "VibrationSettings";

    // Vibration override will use bool instead of integer
    public static final String PREF_VIBRATION_OVERRIDE = "vmax_override";
    public static final String PREF_VIBRATION_PATH = "/sys/devices/platform/soc/200f000.qcom,spmi/spmi-0/spmi0-03/200f000.qcom,spmi:qcom,pmi8950@3:qcom,haptics@c000/leds/vibrator/vmax_override";

    public static final String PREF_VIBRATION_SYSTEM_STRENGTH = "vibration_system";
    public static final String PREF_VIBRATION_NOTIFICATION_STRENGTH = "vibration_notification";
    public static final String PREF_VIBRATION_CALL_STRENGTH = "vibration_call";
    public static final String VIBRATION_SYSTEM_PATH = "/sys/devices/platform/soc/200f000.qcom,spmi/spmi-0/spmi0-03/200f000.qcom,spmi:qcom,pmi8950@3:qcom,haptics@c000/leds/vibrator/vmax_mv_user";
    public static final String VIBRATION_NOTIFICATION_PATH = "/sys/devices/platform/soc/200f000.qcom,spmi/spmi-0/spmi0-03/200f000.qcom,spmi:qcom,pmi8950@3:qcom,haptics@c000/leds/vibrator/vmax_mv_strong";
    public static final String VIBRATION_CALL_PATH = "/sys/devices/platform/soc/200f000.qcom,spmi/spmi-0/spmi0-03/200f000.qcom,spmi:qcom,pmi8950@3:qcom,haptics@c000/leds/vibrator/vmax_mv_call";

    // value of vtg_min and vtg_max
    public static final int MIN_VIBRATION = 116;
    public static final int MAX_VIBRATION = 3596;

    private static Context mContext;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.vibration, rootKey);
        mContext = this.getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        SecureSettingSwitchPreference vib = (SecureSettingSwitchPreference) findPreference(PREF_VIBRATION_OVERRIDE);
        vib.setEnabled(Vibration.isSupported());
        vib.setChecked(Vibration.isCurrentlyEnabled(this.getContext()));
        vib.setOnPreferenceChangeListener(new Vibration(getContext()));

        VibrationSeekBarPreference vibrationSystemStrength = (VibrationSeekBarPreference) findPreference(PREF_VIBRATION_SYSTEM_STRENGTH);
        vibrationSystemStrength.setEnabled(VibrationUtils.fileWritable(VIBRATION_SYSTEM_PATH));
        vibrationSystemStrength.setOnPreferenceChangeListener(this);

        VibrationSeekBarPreference vibrationNotificationStrength = (VibrationSeekBarPreference) findPreference(PREF_VIBRATION_NOTIFICATION_STRENGTH);
        vibrationNotificationStrength.setEnabled(VibrationUtils.fileWritable(VIBRATION_NOTIFICATION_PATH));
        vibrationNotificationStrength.setOnPreferenceChangeListener(this);

        VibrationSeekBarPreference vibrationCallStrength = (VibrationSeekBarPreference) findPreference(PREF_VIBRATION_CALL_STRENGTH);
        vibrationCallStrength.setEnabled(VibrationUtils.fileWritable(VIBRATION_CALL_PATH));
        vibrationCallStrength.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        switch (key) {
            case PREF_VIBRATION_SYSTEM_STRENGTH:
                double VibrationSystemValue = (int) value / 100.0 * (MAX_VIBRATION - MIN_VIBRATION) + MIN_VIBRATION;
                VibrationUtils.setValue(VIBRATION_SYSTEM_PATH, VibrationSystemValue);
                break;

            case PREF_VIBRATION_NOTIFICATION_STRENGTH:
                double VibrationNotificationValue = (int) value / 100.0 * (MAX_VIBRATION - MIN_VIBRATION) + MIN_VIBRATION;
                VibrationUtils.setValue(VIBRATION_NOTIFICATION_PATH, VibrationNotificationValue);
                break;

            case PREF_VIBRATION_CALL_STRENGTH:
                double VibrationCallValue = (int) value / 100.0 * (MAX_VIBRATION - MIN_VIBRATION) + MIN_VIBRATION;
                VibrationUtils.setValue(VIBRATION_CALL_PATH, VibrationCallValue);
                break;
        }
        return true;
    }
}
