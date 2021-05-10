package com.xiaomi.parts.touch;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

public class TouchSettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = getFragmentManager().findFragmentById(android.R.id.content);
        TouchSettings TouchSettingsFragment;
        if (fragment == null) {
            TouchSettingsFragment = new TouchSettings();
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, TouchSettingsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
