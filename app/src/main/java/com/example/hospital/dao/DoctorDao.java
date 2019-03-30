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
import com.example.hospital.table.Dector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wachsbeere on 2018/12/31.
 */

public class DoctorDao {

    private static final String TAG = "DoctorDao";

    // 列定义
    private final String[] ORDER_COLUMNS = new String[] {"dectorNo", "name", "jobName"};

    private Context context;
    private HospitalDBHelper ordersDBHelper;

    public DoctorDao(Context context) {
        this.context = context;
        ordersDBHelper = new HospitalDBHelper(context);
    }

    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(HospitalDBHelper.TABLE_DECTOR, new String[]{"COUNT(dectorNo)"}, null, null, null, null, null);

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

    public int getDoctorCount(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            cursor = db.query(HospitalDBHelper.TABLE_DECTOR,
                    new String[]{"COUNT(dectorNo)"}, null,
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

    /**
     * 查询数据库中所有数据
     */
    public List<Dector> getAllDate(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(HospitalDBHelper.TABLE_DECTOR, ORDER_COLUMNS, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<Dector> dectorList = new ArrayList<Dector>(cursor.getCount());
                while (cursor.moveToNext()) {
                    dectorList.add(parseOrder(cursor));
                }
                return dectorList;
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
    public boolean insert(int id, String name, String job) {
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();

            contentValues.put("dectorNo", id);
            contentValues.put("name", name);
            contentValues.put("jobName", job);

            db.insertOrThrow(HospitalDBHelper.TABLE_DECTOR, null, contentValues);

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

    public boolean delete(int no) {
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            // delete from Orders where Id = 7
            db.delete(HospitalDBHelper.TABLE_DECTOR, "dectorNo = ?", new String[]{String.valueOf(no)});
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

    public List<Dector> createView() {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getWritableDatabase();

            db.execSQL("CREATE VIEW IF NOT EXISTS Doctor_View AS SELECT * FROM Dector");

            cursor = db.rawQuery("SELECT * FROM Doctor_View" ,new String[]{});

            if (cursor.getCount() > 0) {
                List<Dector> dectorList = new ArrayList<Dector>(cursor.getCount());
                while (cursor.moveToNext()) {
                    dectorList.add(parseOrder(cursor));
                }
                return dectorList;
            }

        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    public boolean update(int dectorNo, String name, String jobName) {
        SQLiteDatabase db = null;
        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            // update Orders set OrderPrice = 800 where Id = 6
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("jobName", jobName);
            db.update(HospitalDBHelper.TABLE_DECTOR,
                    cv,
                    "dectorNo = ?",
                    new String[]{String.valueOf(dectorNo)});
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

    private Dector parseOrder(Cursor cursor){
        Dector dector = new Dector();
        dector.setDectorNo(cursor.getInt(cursor.getColumnIndex("dectorNo")));
        dector.setName(cursor.getString(cursor.getColumnIndex("name")));
        dector.setJobName(cursor.getString(cursor.getColumnIndex("jobName")));
        return dector;
    }
}
