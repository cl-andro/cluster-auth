package com.zk.cluster.auth.ui.fragments.preferences;

import android.os.Bundle;

import com.zk.cluster.auth.R;

public class MainPreferencesFragment extends PreferencesFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
