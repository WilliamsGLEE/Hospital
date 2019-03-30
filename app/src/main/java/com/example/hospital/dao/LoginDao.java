package com.example.hospital.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.hospital.application.HospitalContext;
import com.example.hospital.db.HospitalDBHelper;

/**
 * Created by Wachsbeere on 2019/1/6.
 */

public class LoginDao {

    private static final String TAG = "LoginDao";

    private Context context;
    private HospitalDBHelper ordersDBHelper;

    public LoginDao(Context context) {
        this.context = context;
        ordersDBHelper = new HospitalDBHelper(context);
    }

    //登录用
    public boolean login(String username, String password) {
        SQLiteDatabase sdb = ordersDBHelper.getReadableDatabase();
        String sql = "select * from Administrator where username=? and password=?";
        Cursor cursor = sdb.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst() == true) {

            Log.d("FFFLAG", String.valueOf(cursor.getInt(2)));

            HospitalContext.setFlag(cursor.getInt(2));
            HospitalContext.setUsername(cursor.getString(0));

            cursor.close();


            return true;
        }
        return false;
    }

    //注册用
    public boolean register(String username, String password, int isManager) {
        try {
            SQLiteDatabase sdb = ordersDBHelper.getReadableDatabase();
            String sql = "insert into Administrator(username,password,isManager) values(?,?,?)";
            Object obj[] = {username, password, isManager};
            sdb.execSQL(sql, obj);
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
