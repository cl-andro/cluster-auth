package com.zk.cluster.auth;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import androidx.preference.PreferenceManager;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnitRunner;

import com.zk.cluster.auth.util.IOUtils;

public class ClusterTestRunner extends AndroidJUnitRunner {
    static {
        BuildConfig.TEST.set(true);
    }

    @Override
    public Application newApplication(ClassLoader cl, String name, Context context)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return Instrumentation.newApplication(ClusterTestApplication_Application.class, context);
    }

    @Override
    public void callApplicationOnCreate(Application app) {
        Context context = app.getApplicationContext();

        // clear internal storage so that there is no vault file
        IOUtils.clearDirectory(context.getFilesDir(), false);

        // clear preferences so that the intro is started from MainActivity
        ApplicationProvider.getApplicationContext().getFilesDir();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .clear()
                .apply();

        super.callApplicationOnCreate(app);
    }
}
