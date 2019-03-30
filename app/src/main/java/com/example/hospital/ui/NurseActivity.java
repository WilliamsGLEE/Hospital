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
import com.example.hospital.adapter.NurseListAdapter;
import com.example.hospital.dao.NurseDao;
import com.example.hospital.table.Nurse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wachsbeere on 2019/1/1.
 */

public class NurseActivity extends AppCompatActivity {

    private static final String TAG = "NurseActivity";
    private NurseDao nurseDao;
    private TextView tv_nurseNum;
    private EditText inputSqlMsg;
    private ListView showDateListView;
    private List<Nurse> nurseList;
    private NurseListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse);

        setTitle("护士管理界面");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nurseDao = new NurseDao(this);
        initComponent();
        nurseList = new ArrayList<>();//...
        if (nurseDao.isDataExist()) {
            nurseList = nurseDao.getAllDate();
        }
        adapter = new NurseListAdapter(this, nurseList);
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
        Button btn_delete_trigger = (Button)findViewById(R.id.btn_delete_trigger);
        Button btn_index = (Button)findViewById(R.id.btn_index);

        if (HospitalContext.getFlag() == 0) {
            insertButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
            btn_delete_trigger.setVisibility(View.GONE);
            btn_index.setVisibility(View.GONE);
        }

        NurseActivity.SQLBtnOnclickListener onclickListener = new NurseActivity.SQLBtnOnclickListener();
        insertButton.setOnClickListener(onclickListener);
        deleteButton.setOnClickListener(onclickListener);
        updateButton.setOnClickListener(onclickListener);
        btn_delete_trigger.setOnClickListener(onclickListener);
        btn_index.setOnClickListener(onclickListener);

        tv_nurseNum = (TextView)findViewById(R.id.tv_nurseNum);
        showDateListView = (ListView)findViewById(R.id.showDateListView);
        showDateListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_nurse, null), null, false);

    }

    private void refreshOrderList(){

        if (nurseList != null) {
            nurseList.clear();
        }

        if (nurseDao.getAllDate() != null && nurseList != null) {
            nurseList.addAll(nurseDao.getAllDate());
        }

        adapter.notifyDataSetChanged();

        calcuateCount();
    }

    private void calcuateCount() {

        int count = nurseDao.getNurseCount();
        tv_nurseNum.setText("护士总数 ：" + String.valueOf(count));
    }

    public class SQLBtnOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.insertButton:

                    AlertDialog.Builder customizeDialog =
                            new AlertDialog.Builder(NurseActivity.this);
                    final View dialogView = LayoutInflater.from(NurseActivity.this)
                            .inflate(R.layout.dialog_nurse,null);
                    customizeDialog.setTitle("添加护士");
                    customizeDialog.setView(dialogView);
                    customizeDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_nurse_id = (EditText) dialogView.findViewById(R.id.et_nurse_id);
                                    EditText et_nurse_name = (EditText) dialogView.findViewById(R.id.et_nurse_name);
                                    EditText et_nurse_job = (EditText) dialogView.findViewById(R.id.et_nurse_job);

                                    nurseDao.insert(Integer.parseInt(et_nurse_id.getText().toString()), et_nurse_name.getText().toString(), et_nurse_job.getText().toString());
                                    refreshOrderList();
                                }
                            });
                    customizeDialog.show();

                    break;

                case R.id.deleteButton:

                    AlertDialog.Builder deleteDialog =
                            new AlertDialog.Builder(NurseActivity.this);
                    final View deleteView = LayoutInflater.from(NurseActivity.this)
                            .inflate(R.layout.dialog_nurse_delete,null);
                    deleteDialog.setTitle("删除护士");
                    deleteDialog.setView(deleteView);
                    deleteDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_nurse_delete = (EditText) deleteView.findViewById(R.id.et_nurse_delete);

                                    nurseDao.delete(Integer.parseInt(et_nurse_delete.getText().toString()));
                                    refreshOrderList();
                                }
                            });
                    deleteDialog.show();

                    break;

                case R.id.updateButton:

                    AlertDialog.Builder updateDialog =
                            new AlertDialog.Builder(NurseActivity.this);
                    final View updateView = LayoutInflater.from(NurseActivity.this)
                            .inflate(R.layout.dialog_nurse_update,null);
                    updateDialog.setTitle("更改护士");
                    updateDialog.setView(updateView);
                    updateDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_nurse_update_id = (EditText) updateView.findViewById(R.id.et_nurse_update_id);
                                    EditText et_nurse_update_name = (EditText) updateView.findViewById(R.id.et_nurse_update_name);
                                    EditText et_nurse_update_job = (EditText) updateView.findViewById(R.id.et_nurse_update_job);

                                    nurseDao.update(Integer.parseInt(et_nurse_update_id.getText().toString()), et_nurse_update_name.getText().toString(), et_nurse_update_job.getText().toString());
                                    refreshOrderList();
                                }
                            });
                    updateDialog.show();
                    break;

                case R.id.btn_delete_trigger:
                    nurseDao.addTrigger();
                    break;

                case R.id.btn_index:
                    nurseDao.addIndex();
                    break;

                default:
                    break;
            }
        }
    }
}

