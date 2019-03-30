package com.example.hospital.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.hospital.R;
import com.example.hospital.db.HospitalDBHelper;
import com.example.hospital.table.Pacient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wachsbeere on 2019/1/1.
 */

public class PacientDao {
    private static final String TAG = "PacientDao";

    // 列定义
    private final String[] ORDER_COLUMNS = new String[] {"hospitalizationNo", "name", "sex", "admissionDate", "dischargedDate", "roomNo", "dectorNo"};


    private Context context;
    private HospitalDBHelper ordersDBHelper;

    public PacientDao(Context context) {
        this.context = context;
        ordersDBHelper = new HospitalDBHelper(context);
    }

    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist() {
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(HospitalDBHelper.TABLE_PACIENT, new String[]{"COUNT(hospitalizationNo)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    /**
     * 查询数据库中所有数据
     */
    public List<Pacient> getAllDate(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(HospitalDBHelper.TABLE_PACIENT, ORDER_COLUMNS, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<Pacient> pacientList = new ArrayList<Pacient>(cursor.getCount());
                while (cursor.moveToNext()) {
                    pacientList.add(parseOrder(cursor));
                }
                return pacientList;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    public List<Pacient> getKeyWordQuery(String queryContent) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();

            String sql  = "select * from " + HospitalDBHelper.TABLE_PACIENT +
                    " where name like ? or admissionDate like ? or dischargedDate like ?";
            String [] selectionArgs  = new String[]{
                    "%" + queryContent + "%",
                    "%" + queryContent + "%",
                    "%" + queryContent + "%"};
            cursor = db.rawQuery(sql, selectionArgs);

            if (cursor.getCount() > 0) {
                List<Pacient> pacientList = new ArrayList<Pacient>(cursor.getCount());
                while (cursor.moveToNext()) {
                    pacientList.add(parseOrder(cursor));
                }
                return pacientList;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    /**
     * 新增一条数据
     */
    public boolean insert(int hospitalizationNo, String name, String sex, String admissionDate, String dischargedDate, int roomNo, int dectorNo) {
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();

            contentValues.put("hospitalizationNo", hospitalizationNo);
            contentValues.put("name", name);
            contentValues.put("sex", sex);
            contentValues.put("admissionDate", admissionDate);
            contentValues.put("dischargedDate", dischargedDate);
            contentValues.put("roomNo", roomNo);
            contentValues.put("dectorNo", dectorNo);

            db.insertOrThrow(HospitalDBHelper.TABLE_PACIENT, null, contentValues);

            db.setTransactionSuccessful();
            return true;

        } catch (SQLiteConstraintException e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    public boolean delete(int hospitalizationNo) {
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            // delete from Orders where Id = 7
            db.delete(HospitalDBHelper.TABLE_PACIENT, "hospitalizationNo = ?", new String[]{String.valueOf(hospitalizationNo)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    public boolean update(int hospitalizationNo, String name, String sex, String admissionDate, String dischargedDate, int roomNo, int dectorNo) {
        SQLiteDatabase db = null;
        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("sex", sex);
            cv.put("admissionDate", admissionDate);
            cv.put("dischargedDate", dischargedDate);
            cv.put("roomNo", roomNo);
            cv.put("dectorNo", dectorNo);
            db.update(HospitalDBHelper.TABLE_PACIENT,
                    cv,
                    "hospitalizationNo = ?",
                    new String[]{String.valueOf(hospitalizationNo)});
            db.setTransactionSuccessful();
            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        return false;
    }

    public int getPacientCount(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select count(Id) from Orders where Country = 'China'
            cursor = db.query(HospitalDBHelper.TABLE_PACIENT,
                    new String[]{"COUNT(hospitalizationNo)"}, null,
                    null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return count;
    }


    private Pacient parseOrder(Cursor cursor){
        Pacient pacient = new Pacient();
        pacient.setHospitalizationNo(cursor.getInt(cursor.getColumnIndex("hospitalizationNo")));
        pacient.setName(cursor.getString(cursor.getColumnIndex("name")));
        pacient.setSex(cursor.getString(cursor.getColumnIndex("sex")));
        pacient.setAdmissionDate(cursor.getString(cursor.getColumnIndex("admissionDate")));
        pacient.setDischargedDate(cursor.getString(cursor.getColumnIndex("dischargedDate")));
        pacient.setRoomNo(cursor.getInt(cursor.getColumnIndex("roomNo")));
        pacient.setDoctorNo(cursor.getInt(cursor.getColumnIndex("dectorNo")));

        return pacient;
    }
}
