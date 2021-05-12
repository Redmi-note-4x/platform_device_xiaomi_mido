package com.xiaomi.parts.action;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.xiaomi.parts.preferences.SecureSettingSwitchPreference;
import com.xiaomi.parts.action.ChopChopService;
import com.xiaomi.parts.R;

public class ChopChopSettings extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "ChopChopSettings";

    public static final String CHOPCHOP = "chopchop";

    private static Context mContext;
    private SecureSettingSwitchPreference mChopChop;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.chopchop, rootKey);
        mContext = this.getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        SwitchPreference mChopChop = (SecureSettingSwitchPreference) findPreference(CHOPCHOP);
        mChopChop.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        switch (key) {
            case CHOPCHOP:
                boolean enabled = (Boolean) value;
                Intent mChopChop = new Intent(this.getContext(), ChopChopService.class);
                if (enabled) {
                    this.getContext().startService(mChopChop);
                } else {
                    this.getContext().stopService(mChopChop);
                }
                break;
        }
        return true;
    }

}
