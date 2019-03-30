package com.example.hospital.application;

import android.app.Application;


public class HospitalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (!HospitalContext.isInitialized()) {
            HospitalContext.init(getApplicationContext());
        }
    }
}
