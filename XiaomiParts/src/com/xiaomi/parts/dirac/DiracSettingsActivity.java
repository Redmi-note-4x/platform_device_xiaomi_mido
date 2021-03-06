package com.xiaomi.parts.dirac;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

public class DiracSettingsActivity extends Activity {

    private DiracSettings DiracSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = getFragmentManager().findFragmentById(android.R.id.content);
        DiracSettings DiracSettingsFragment;
        if (fragment == null) {
            DiracSettingsFragment = new DiracSettings();
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, DiracSettingsFragment)
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
