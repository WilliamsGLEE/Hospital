package com.example.hospital.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.hospital.db.HospitalDBHelper;
import com.example.hospital.table.Bed;
import com.example.hospital.table.BedAndNurse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wachsbeere on 2019/1/1.
 */

public class BedDao {

    private static final String TAG = "BedDao";

    // 列定义
    private final String[] ORDER_COLUMNS = new String[] {"roomNo", "bedNo", "nurseNo"};

    private Context context;
    private HospitalDBHelper ordersDBHelper;

    public BedDao(Context context) {
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
            cursor = db.query(HospitalDBHelper.TABLE_BED, new String[]{"COUNT(roomNo)"}, null, null, null, null, null);

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
    public List<Bed> getAllDate(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Bed
            cursor = db.query(HospitalDBHelper.TABLE_BED, ORDER_COLUMNS, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<Bed> bedList = new ArrayList<Bed>(cursor.getCount());
                while (cursor.moveToNext()) {
                    bedList.add(parseOrder(cursor));
                }
                return bedList;
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

    public List<BedAndNurse> getAllLinkQuery(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();

            cursor = db.rawQuery("SELECT roomNo, bedNo, Bed.nurseNo, name, jobName FROM Bed, Nurse WHERE Bed.nurseNo = Nurse.nurseNo" ,new String[]{});

            if (cursor.getCount() > 0) {
                List<BedAndNurse> bedAndNurseList = new ArrayList<BedAndNurse>(cursor.getCount());
                while (cursor.moveToNext()) {
                    bedAndNurseList.add(parseBedAndNurse(cursor));
                }
                return bedAndNurseList;
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
    public boolean insert(int roomNo, int bedNo, int nurseNo) {
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("roomNo", roomNo);
            contentValues.put("bedNo", bedNo);
            contentValues.put("nurseNo", nurseNo);

            db.insertOrThrow(HospitalDBHelper.TABLE_BED, null, contentValues);

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

    public boolean delete(int roomNo) {
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();
            db.delete(HospitalDBHelper.TABLE_BED, "roomNo = ?", new String[]{String.valueOf(roomNo)});
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

    public boolean update(int roomNo, int bedNo, int nurseNo) {
        SQLiteDatabase db = null;
        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues cv = new ContentValues();
            cv.put("bedNo", bedNo);
            cv.put("nurseNo", nurseNo);
            db.update(HospitalDBHelper.TABLE_BED,
                    cv,
                    "roomNo = ?",
                    new String[]{String.valueOf(roomNo)});
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

    public int getBedCount(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select count(Id) from Orders where Country = 'China'
            cursor = db.query(HospitalDBHelper.TABLE_BED,
                    new String[]{"COUNT(roomNo)"}, null,
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

    private Bed parseOrder(Cursor cursor){
        Bed bed = new Bed();
        bed.setRoomNo(cursor.getInt(cursor.getColumnIndex("roomNo")));
        bed.setBedNo(cursor.getInt(cursor.getColumnIndex("bedNo")));
        bed.setNurseNo(cursor.getInt(cursor.getColumnIndex("nurseNo")));
        return bed;
    }

    private BedAndNurse parseBedAndNurse(Cursor cursor){
        BedAndNurse bedAndNurse = new BedAndNurse();
        bedAndNurse.setRoomNo(cursor.getInt(cursor.getColumnIndex("roomNo")));
        bedAndNurse.setBedNo(cursor.getInt(cursor.getColumnIndex("bedNo")));
        bedAndNurse.setNurseNo(cursor.getInt(cursor.getColumnIndex("nurseNo")));
        bedAndNurse.setName(cursor.getString(cursor.getColumnIndex("name")));
        bedAndNurse.setJobName(cursor.getString(cursor.getColumnIndex("jobName")));
        return bedAndNurse;
    }
}
