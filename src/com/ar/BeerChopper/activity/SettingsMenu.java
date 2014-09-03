package com.ar.BeerChopper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.ar.BeerChopper.R;

@SuppressWarnings("ALL")
public class SettingsMenu extends PreferenceActivity {
    private Boolean changed = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        final Preference chopperPreference = getPreferenceScreen().findPreference("CHOPPER_NUMBER");
        if (getPreferenceManager().getSharedPreferences().getString("APP_MODE","").equals("Bares"))
        {
            chopperPreference.setEnabled(true);
        }
        else chopperPreference.setEnabled(false);
        chopperPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                try {
                    if (Integer.valueOf(o.toString())>0)
                    {
                        return true;
                    }
                }
                catch (Exception e)
                {
                }
                return false;
            }
        });

        Preference modoPreference = getPreferenceScreen().findPreference("APP_MODE");
        modoPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                changed = true;
                if (o.toString().equals("Bares"))
                {
                    chopperPreference.setEnabled(true);
                }
                else if (o.toString().equals("Ferias"))
                {
                    chopperPreference.setEnabled(false);

                }
                return true;
            }
        });

        Preference imgurPreference = getPreferenceScreen().findPreference("ALBUM_ID");
        imgurPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                changed = true;
                return true;
            }
        });

        Preference ipPreference = getPreferenceScreen().findPreference("HOST_NAME");
        ipPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                changed = true;
                if (o.toString()=="")
                {
                    return false;
                }

                else return true;
            }
        });

    }

    public void onBackPressed() {

        if (changed)
        {
            setResult(2);
            changed = false;
            Intent newIntent = new Intent();
            newIntent.setClass(this,LoginActivity.class);
            startActivity(newIntent);
            finish();
        }
        else super.onBackPressed();

    }
}