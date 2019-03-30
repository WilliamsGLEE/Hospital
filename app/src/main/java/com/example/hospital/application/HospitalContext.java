package com.example.hospital.application;

import android.content.Context;

public class HospitalContext {
    private static HospitalContext instance;

    private Context applicationContext;
    private static int sFlag = 0;
    private static String sUsername = "";

    public static HospitalContext getInstance() {
        if (instance == null){
            throw new RuntimeException(HospitalContext.class.getSimpleName() + "has not been initialized!");
        }

        return instance;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public HospitalContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void setFlag(int flag) {
        sFlag = flag;
    }

    public static int getFlag() {
        return sFlag;
    }

    public static void setUsername(String username) {
        sUsername = username;
    }

    public static String getUsername() {
        return sUsername;
    }

    /**
     * 全局信息 只能调用一次
     */
    public static void init(Context applicationContext) {
        if (instance != null) {
            throw new RuntimeException(HospitalContext.class.getSimpleName() + " can not be initialized multiple times!");
        }
        instance = new HospitalContext(applicationContext);
    }

    public static boolean isInitialized() {
        return (instance != null);
    }
}
