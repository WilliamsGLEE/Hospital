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
import com.example.hospital.table.Nurse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wachsbeere on 2019/1/1.
 */

public class NurseDao {

    private static final String TAG = "NurseDao";

    // 列定义
    private final String[] ORDER_COLUMNS = new String[] {"nurseNo", "name", "jobName"};

    private Context context;
    private HospitalDBHelper ordersDBHelper;

    public NurseDao(Context context) {
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
            cursor = db.query(HospitalDBHelper.TABLE_NURSE, new String[]{"COUNT(nurseNo)"}, null, null, null, null, null);

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

    public int getNurseCount(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select count(Id) from Orders where Country = 'China'
            cursor = db.query(HospitalDBHelper.TABLE_NURSE,
                    new String[]{"COUNT(nurseNo)"}, null,
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
    public List<Nurse> getAllDate() {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(HospitalDBHelper.TABLE_NURSE, ORDER_COLUMNS, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<Nurse> nurseList = new ArrayList<Nurse>(cursor.getCount());
                while (cursor.moveToNext()) {
                    nurseList.add(parseOrder(cursor));
                }
                return nurseList;
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

            contentValues.put("nurseNo", id);
            contentValues.put("name", name);
            contentValues.put("jobName", job);

            db.insertOrThrow(HospitalDBHelper.TABLE_NURSE, null, contentValues);

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

            db.delete(HospitalDBHelper.TABLE_NURSE, "nurseNo = ?", new String[]{String.valueOf(no)});
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

    public boolean update(int nurseNo, String name, String jobName) {
        SQLiteDatabase db = null;
        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            // update Orders set OrderPrice = 800 where Id = 6
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("jobName", jobName);
            db.update(HospitalDBHelper.TABLE_NURSE,
                    cv,
                    "nurseNo = ?",
                    new String[]{String.valueOf(nurseNo)});
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

    public boolean addTrigger() {
        SQLiteDatabase db = null;
        try {
            db = ordersDBHelper.getWritableDatabase();
            db.execSQL("create trigger trig_Delete\n" +
                    "before delete on Nurse\n" +
                    "for each row\n" +
                    "begin\n" +
                    "     delete from Bed where nurseNo = old.nurseNo;\n" +
                    "end ;");
            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public boolean addIndex() {
        SQLiteDatabase db = null;
        try {
            db = ordersDBHelper.getWritableDatabase();
            db.execSQL("CREATE UNIQUE INDEX nurse_index ON Nurse(name)");
            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    private Nurse parseOrder(Cursor cursor){
        Nurse nurse = new Nurse();
        nurse.setNurseNo(cursor.getInt(cursor.getColumnIndex("nurseNo")));
        nurse.setName(cursor.getString(cursor.getColumnIndex("name")));
        nurse.setJobName(cursor.getString(cursor.getColumnIndex("jobName")));
        return nurse;
    }
}
