package com.example.hospital.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hospital.application.HospitalContext;
import com.example.hospital.R;
import com.example.hospital.adapter.DoctorListAdapter;
import com.example.hospital.dao.DoctorDao;
import com.example.hospital.table.Dector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wachsbeere on 2018/12/31.
 */

public class DoctorActivity extends AppCompatActivity {

    private static final String TAG = "DoctorActivity";
    private DoctorDao doctorDao;
    private TextView tv_doctorNum;
    private ListView showDateListView;
    private List<Dector> dectorList;
    private DoctorListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

//        this.getActionBar().setTitle("医生管理界面");
        setTitle("医生管理界面");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        doctorDao = new DoctorDao(this);
        initComponent();
        dectorList = new ArrayList<>();//...
        if (doctorDao.isDataExist()) {
            dectorList = doctorDao.getAllDate();
        }
        adapter = new DoctorListAdapter(this, dectorList);
        showDateListView.setAdapter(adapter);


        calcuateCount();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initComponent(){
        Button insertButton = (Button)findViewById(R.id.insertButton);
        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        Button updateButton = (Button)findViewById(R.id.updateButton);
        Button btn_query = (Button)findViewById(R.id.btn_query);
        Button btn_addandquery_view = (Button)findViewById(R.id.btn_addandquery_view);

        if (HospitalContext.getFlag() == 0) {
            insertButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
            btn_query.setVisibility(View.GONE);
            btn_addandquery_view.setVisibility(View.GONE);
        }


        DoctorActivity.SQLBtnOnclickListener onclickListener = new DoctorActivity.SQLBtnOnclickListener();
        insertButton.setOnClickListener(onclickListener);
        deleteButton.setOnClickListener(onclickListener);
        updateButton.setOnClickListener(onclickListener);
        btn_query.setOnClickListener(onclickListener);
        btn_addandquery_view.setOnClickListener(onclickListener);

        tv_doctorNum = (TextView)findViewById(R.id.tv_doctorNum);
        showDateListView = (ListView)findViewById(R.id.showDateListView);
        showDateListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_doctor, null), null, false);
    }

    private void refreshOrderList(){
        if (dectorList != null) {
            dectorList.clear();
        }

        if (doctorDao.getAllDate() != null && dectorList != null) {
            dectorList.addAll(doctorDao.getAllDate());
        }
        adapter.notifyDataSetChanged();

        calcuateCount();
    }

    private void refreshViewList(){
        if (dectorList != null) {
            dectorList.clear();
        }

        if (doctorDao.createView() != null && dectorList != null) {
            dectorList.addAll(doctorDao.createView());
        }
        adapter.notifyDataSetChanged();

        calcuateCount();
    }

    private void calcuateCount() {

        int count = doctorDao.getDoctorCount();
        tv_doctorNum.setText("医生总数 ：" + String.valueOf(count));
    }

    public class SQLBtnOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.insertButton:

                    AlertDialog.Builder customizeDialog =
                            new AlertDialog.Builder(DoctorActivity.this);
                    final View dialogView = LayoutInflater.from(DoctorActivity.this)
                            .inflate(R.layout.dialog_doctor,null);
                    customizeDialog.setTitle("添加医生");
                    customizeDialog.setView(dialogView);
                    customizeDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_doctor_id = (EditText) dialogView.findViewById(R.id.et_doctor_id);
                                    EditText et_doctor_name = (EditText) dialogView.findViewById(R.id.et_doctor_name);
                                    EditText et_doctor_job = (EditText) dialogView.findViewById(R.id.et_doctor_job);

                                    doctorDao.insert(Integer.parseInt(et_doctor_id.getText().toString()), et_doctor_name.getText().toString(), et_doctor_job.getText().toString());
                                    refreshOrderList();
                                }
                            });
                    customizeDialog.show();

                    break;

                case R.id.deleteButton:

                    AlertDialog.Builder deleteDialog =
                            new AlertDialog.Builder(DoctorActivity.this);
                    final View deleteView = LayoutInflater.from(DoctorActivity.this)
                            .inflate(R.layout.dialog_doctor_delete,null);
                    deleteDialog.setTitle("删除医生");
                    deleteDialog.setView(deleteView);
                    deleteDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_doctor_delete = (EditText) deleteView.findViewById(R.id.et_doctor_delete);

                                    doctorDao.delete(Integer.parseInt(et_doctor_delete.getText().toString()));
                                    refreshOrderList();
                                }
                            });
                    deleteDialog.show();

                    break;

                case R.id.updateButton:

                    AlertDialog.Builder updateDialog =
                            new AlertDialog.Builder(DoctorActivity.this);
                    final View updateView = LayoutInflater.from(DoctorActivity.this)
                            .inflate(R.layout.dialog_doctor_update,null);
                    updateDialog.setTitle("删除医生");
                    updateDialog.setView(updateView);
                    updateDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_doctor_update_id = (EditText) updateView.findViewById(R.id.et_doctor_update_id);
                                    EditText et_doctor_update_name = (EditText) updateView.findViewById(R.id.et_doctor_update_name);
                                    EditText et_doctor_update_job = (EditText) updateView.findViewById(R.id.et_doctor_update_job);

                                    doctorDao.update(Integer.parseInt(et_doctor_update_id.getText().toString()), et_doctor_update_name.getText().toString(), et_doctor_update_job.getText().toString());
                                    refreshOrderList();
                                }
                            });
                    updateDialog.show();
                    break;

                case R.id.btn_query:
                    refreshOrderList();
                    break;


                case R.id.btn_addandquery_view:
                    refreshViewList();
                    break;

                default:

                    break;
            }
        }
    }
}
