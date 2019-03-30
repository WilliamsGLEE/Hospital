package com.example.hospital.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jne
 * Date: 2015/1/6.
 */
public class HospitalDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myTest.db";

    public static final String TABLE_BED = "Bed";
    public static final String TABLE_DECTOR = "Dector";
    public static final String TABLE_PACIENT = "Pacient";
    public static final String TABLE_NURSE = "Nurse";
    public static final String TABLE_ADMINISTRATOR = "Administrator";

    public HospitalDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //ok
        String sql1 = "create table if not exists " + TABLE_DECTOR + " (dectorNo integer primary key, name text, jobName text)";
        String sql2 = "create table if not exists " + TABLE_NURSE + " (nurseNo integer primary key, name text, jobName text)";
        String sql3 = "create table if not exists " + TABLE_BED + " (roomNo integer primary key, bedNo Integer, nurseNo integer, FOREIGN KEY (nurseNo) REFERENCES Nurse(nurseNo))";
        String sql4 = "create table if not exists " + TABLE_PACIENT + " (hospitalizationNo integer primary key, name text, sex text, admissionDate text, dischargedDate text, roomNo integer, dectorNo integer, FOREIGN KEY (roomNo) REFERENCES Bed(roomNo), FOREIGN KEY (dectorNo) REFERENCES Dector(dectorNo))";
        String sql5 = "create table if not exists " + TABLE_ADMINISTRATOR + " (username varchar(20) primary key, password varchar(20), isManager integer)";


        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
        sqLiteDatabase.execSQL(sql5);
//        sqLiteDatabase.execSQL("PRAGMA foreign_keys=YES");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        super.onOpen(db);
        if(!db.isReadOnly()) { // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=1;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql1 = "DROP TABLE IF EXISTS " + TABLE_DECTOR;
        String sql2 = "DROP TABLE IF EXISTS " + TABLE_NURSE;
        String sql3 = "DROP TABLE IF EXISTS " + TABLE_BED;
        String sql4 = "DROP TABLE IF EXISTS " + TABLE_PACIENT;
        String sql5 = "DROP TABLE IF EXISTS " + TABLE_ADMINISTRATOR;
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
        sqLiteDatabase.execSQL(sql5);
        onCreate(sqLiteDatabase);
    }
}
