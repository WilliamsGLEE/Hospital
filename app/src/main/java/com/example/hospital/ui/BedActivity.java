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
import com.example.hospital.adapter.BedListAdapter;
import com.example.hospital.dao.BedDao;
import com.example.hospital.table.Bed;
import com.example.hospital.table.BedAndNurse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wachsbeere on 2019/1/1.
 */

public class BedActivity extends AppCompatActivity {

    private static final String TAG = "BedActivity";
    private BedDao bedDao;
    private TextView tv_roomNum;
    private ListView showDateListView;
    private List<Bed> bedList;
    private List<BedAndNurse> bedAndNurseList;
    private BedListAdapter adapter;
    private View headerView1;
    private View headerView2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed);

        setTitle("病房管理界面");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        headerView1 = LayoutInflater.from(this).inflate(R.layout.item_bed, null);
        headerView2 = LayoutInflater.from(this).inflate(R.layout.item_bedandnurse, null);


        bedDao = new BedDao(this);
        initComponent();
        bedList = new ArrayList<>();//...
        bedAndNurseList = new ArrayList<>();
        if (bedDao.isDataExist()) {
            bedList = bedDao.getAllDate();
        }
        adapter = new BedListAdapter(this, bedList, false);
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
        Button btn_queryOutKey = (Button)findViewById(R.id.btn_queryOutKey);

        BedActivity.SQLBtnOnclickListener onclickListener = new BedActivity.SQLBtnOnclickListener();
        insertButton.setOnClickListener(onclickListener);
        deleteButton.setOnClickListener(onclickListener);
        updateButton.setOnClickListener(onclickListener);
        btn_query.setOnClickListener(onclickListener);
        btn_queryOutKey.setOnClickListener(onclickListener);

        showDateListView = (ListView)findViewById(R.id.showDateListView);
        tv_roomNum = (TextView)findViewById(R.id.tv_roomNum);

        if (HospitalContext.getFlag() == 0) {
            insertButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
            btn_query.setVisibility(View.GONE);
            btn_queryOutKey.setVisibility(View.GONE);
            showDateListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_bed, null), null, false);
        }
    }

    private void calcuateCount() {

        int count = bedDao.getBedCount();
        tv_roomNum.setText("病房总数 ：" + String.valueOf(count));
    }

    private void refreshOrderList(){

        if (bedList != null) {
            bedList.clear();
        }

        if (bedDao.getAllDate() != null && bedList != null) {
            bedList.addAll(bedDao.getAllDate());
        }

        //....
        adapter = new BedListAdapter(this, bedList, false);
        showDateListView.setAdapter(adapter);
        showDateListView.removeHeaderView(headerView2);
        showDateListView.addHeaderView(headerView1, null, false);

        adapter.notifyDataSetChanged();
        calcuateCount();
    }

    private void refreshLinkList(){

        if (bedAndNurseList != null) {
            bedAndNurseList.clear();
        }

        if (bedDao.getAllDate() != null && bedAndNurseList != null) {
            bedAndNurseList.addAll(bedDao.getAllLinkQuery());
        }

        adapter = new BedListAdapter(this, bedAndNurseList, true);
        showDateListView.setAdapter(adapter);

        showDateListView.removeHeaderView(headerView1);
        showDateListView.addHeaderView(headerView2, null, false);

        adapter.notifyDataSetChanged();
        calcuateCount();
    }

    public class SQLBtnOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.insertButton:

                    AlertDialog.Builder customizeDialog =
                            new AlertDialog.Builder(BedActivity.this);
                    final View dialogView = LayoutInflater.from(BedActivity.this)
                            .inflate(R.layout.dialog_bed,null);
                    customizeDialog.setTitle("添加病房");
                    customizeDialog.setView(dialogView);
                    customizeDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_bed_roomNo = (EditText) dialogView.findViewById(R.id.et_bed_roomNo);
                                    EditText et_bed_bedNo = (EditText) dialogView.findViewById(R.id.et_bed_bedNo);
                                    EditText et_bed_nurseNo = (EditText) dialogView.findViewById(R.id.et_bed_nurseNo);

                                    bedDao.insert(Integer.parseInt(et_bed_roomNo.getText().toString()), Integer.parseInt(et_bed_bedNo.getText().toString()), Integer.parseInt(et_bed_nurseNo.getText().toString()));
                                    refreshOrderList();
                                }
                            });
                    customizeDialog.show();

                    break;

                case R.id.deleteButton:

                    AlertDialog.Builder deleteDialog =
                            new AlertDialog.Builder(BedActivity.this);
                    final View deleteView = LayoutInflater.from(BedActivity.this)
                            .inflate(R.layout.dialog_bed_delete,null);
                    deleteDialog.setTitle("删除病房");
                    deleteDialog.setView(deleteView);
                    deleteDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_bed_delete = (EditText) deleteView.findViewById(R.id.et_bed_delete);

                                    bedDao.delete(Integer.parseInt(et_bed_delete.getText().toString()));
                                    refreshOrderList();
                                }
                            });
                    deleteDialog.show();

                    break;

                case R.id.updateButton:

                    AlertDialog.Builder updateDialog =
                            new AlertDialog.Builder(BedActivity.this);
                    final View updateView = LayoutInflater.from(BedActivity.this)
                            .inflate(R.layout.dialog_bed_update,null);
                    updateDialog.setTitle("更改病房");
                    updateDialog.setView(updateView);
                    updateDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_bed_update_roomNo = (EditText) updateView.findViewById(R.id.et_bed_update_roomNo);
                                    EditText et_bed_update_bed = (EditText) updateView.findViewById(R.id.et_bed_update_bed);
                                    EditText et_bed_update_nurseNo = (EditText) updateView.findViewById(R.id.et_bed_update_nurseNo);

                                    bedDao.update(Integer.parseInt(et_bed_update_roomNo.getText().toString()), Integer.parseInt(et_bed_update_bed.getText().toString()), Integer.parseInt(et_bed_update_nurseNo.getText().toString()));
                                    refreshOrderList();
                                }
                            });
                    updateDialog.show();
                    break;

                case R.id.btn_query:
                    refreshOrderList();
                    break;
                case R.id.btn_queryOutKey:
                    refreshLinkList();
                    break;

                default:

                    break;
            }
        }
    }
}

