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
import com.example.hospital.adapter.PacientListAdapter;
import com.example.hospital.dao.PacientDao;
import com.example.hospital.table.Pacient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wachsbeere on 2018/12/31.
 */

public class PacientActivity extends AppCompatActivity {

    private static final String TAG = "PacientActivity";
    private PacientDao pacientDao;
    private TextView tv_pacientNum;
    private ListView showDateListView;
    private List<Pacient> pacientList;
    private PacientListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacient);

        setTitle("病人管理界面");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pacientDao = new PacientDao(this);
        if (!pacientDao.isDataExist()) {
        }

        initComponent();

        pacientList = new ArrayList<>();//...
        if (pacientDao.isDataExist()) {
            pacientList = pacientDao.getAllDate();
        }

        adapter = new PacientListAdapter(this, pacientList);
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
        Button btn_queryAll = (Button)findViewById(R.id.btn_queryAll);
        Button btn_queryKeyWord = (Button)findViewById(R.id.btn_queryKeyWord);

        if (HospitalContext.getFlag() == 0) {
            insertButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
            btn_queryAll.setVisibility(View.GONE);
            btn_queryKeyWord.setVisibility(View.GONE);
        }


        PacientActivity.SQLBtnOnclickListener onclickListener = new PacientActivity.SQLBtnOnclickListener();
        insertButton.setOnClickListener(onclickListener);
        deleteButton.setOnClickListener(onclickListener);
        updateButton.setOnClickListener(onclickListener);
        btn_queryAll.setOnClickListener(onclickListener);
        btn_queryKeyWord.setOnClickListener(onclickListener);

        tv_pacientNum = (TextView)findViewById(R.id.tv_pacientNum);
        showDateListView = (ListView)findViewById(R.id.showDateListView);
        showDateListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_pacient, null), null, false);
    }

    private void refreshOrderList() {
        if (pacientList != null) {
            pacientList.clear();
        }

        if (pacientDao.getAllDate() != null && pacientList != null) {
            pacientList.addAll(pacientDao.getAllDate());
        }
        adapter.notifyDataSetChanged();
        calcuateCount();
    }

    private void calcuateCount() {

        int count = pacientDao.getPacientCount();
        tv_pacientNum.setText("病人总数 ：" + String.valueOf(count));
    }

    public class SQLBtnOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.insertButton:

                    AlertDialog.Builder customizeDialog =
                            new AlertDialog.Builder(PacientActivity.this);
                    final View dialogView = LayoutInflater.from(PacientActivity.this)
                            .inflate(R.layout.dialog_pacient,null);
                    customizeDialog.setTitle("添加病人");
                    customizeDialog.setView(dialogView);
                    customizeDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_pacient_hospitalizationNo = (EditText) dialogView.findViewById(R.id.et_pacient_hospitalizationNo);
                                    EditText et_pacient_name = (EditText) dialogView.findViewById(R.id.et_pacient_name);
                                    EditText et_pacient_sex = (EditText) dialogView.findViewById(R.id.et_pacient_sex);
                                    EditText et_pacient_admissionDate = (EditText) dialogView.findViewById(R.id.et_pacient_admissionDate);
                                    EditText et_pacient_dischargedDate = (EditText) dialogView.findViewById(R.id.et_pacient_dischargedDate);
                                    EditText et_pacient_roomNo = (EditText) dialogView.findViewById(R.id.et_pacient_roomNo);
                                    EditText et_pacient_dectorNo = (EditText) dialogView.findViewById(R.id.et_pacient_dectorNo);

                                    pacientDao.insert(Integer.parseInt(et_pacient_hospitalizationNo.getText().toString()), et_pacient_name.getText().toString(), et_pacient_sex.getText().toString(),
                                            et_pacient_admissionDate.getText().toString(), et_pacient_dischargedDate.getText().toString(), Integer.parseInt(et_pacient_roomNo.getText().toString()),
                                            Integer.parseInt(et_pacient_dectorNo.getText().toString()));
                                    refreshOrderList();
                                }
                            });
                    customizeDialog.show();

                    break;

                case R.id.deleteButton:

                    AlertDialog.Builder deleteDialog =
                            new AlertDialog.Builder(PacientActivity.this);
                    final View deleteView = LayoutInflater.from(PacientActivity.this)
                            .inflate(R.layout.dialog_pacient_delete,null);
                    deleteDialog.setTitle("删除病人");
                    deleteDialog.setView(deleteView);
                    deleteDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 获取EditView中的输入内容
                                    EditText et_pacient_delete = (EditText) deleteView.findViewById(R.id.et_pacient_delete);

                                    pacientDao.delete(Integer.parseInt(et_pacient_delete.getText().toString()));
                                    refreshOrderList();
                                }
                            });
                    deleteDialog.show();

                    break;

                case R.id.updateButton:

                    AlertDialog.Builder updateDialog =
                            new AlertDialog.Builder(PacientActivity.this);
                    final View updateView = LayoutInflater.from(PacientActivity.this)
                            .inflate(R.layout.dialog_pacient_update,null);
                    updateDialog.setTitle("更改病人信息");
                    updateDialog.setView(updateView);
                    updateDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    EditText et_pacient_update_hospitalizationNo = (EditText) updateView.findViewById(R.id.et_pacient_update_hospitalizationNo);
                                    EditText et_pacient_update_name = (EditText) updateView.findViewById(R.id.et_pacient_update_name);
                                    EditText et_pacient_update_sex = (EditText) updateView.findViewById(R.id.et_pacient_update_sex);
                                    EditText et_pacient_update_admissionDate = (EditText) updateView.findViewById(R.id.et_pacient_update_admissionDate);
                                    EditText et_pacient_update_dischargedDate = (EditText) updateView.findViewById(R.id.et_pacient_update_dischargedDate);
                                    EditText et_pacient_update_roomNo = (EditText) updateView.findViewById(R.id.et_pacient_update_roomNo);
                                    EditText et_pacient_update_dectorNo = (EditText) updateView.findViewById(R.id.et_pacient_update_dectorNo);

                                    pacientDao.update(Integer.parseInt(et_pacient_update_hospitalizationNo.getText().toString()), et_pacient_update_name.getText().toString(), et_pacient_update_sex.getText().toString()
                                            , et_pacient_update_admissionDate.getText().toString(), et_pacient_update_dischargedDate.getText().toString(), Integer.parseInt(et_pacient_update_roomNo.getText().toString()),
                                            Integer.parseInt(et_pacient_update_dectorNo.getText().toString()));
                                    refreshOrderList();
                                }
                            });
                    updateDialog.show();
                    break;

                case R.id.btn_queryAll:

                    refreshOrderList();
                    break;
                case R.id.btn_queryKeyWord:

                    AlertDialog.Builder queryKeyWordDialog =
                            new AlertDialog.Builder(PacientActivity.this);
                    final View queryKeyWordView = LayoutInflater.from(PacientActivity.this)
                            .inflate(R.layout.dialog_pacient_querykeyword,null);
                    queryKeyWordDialog.setTitle("查询关键字");
                    queryKeyWordDialog.setView(queryKeyWordView);
                    queryKeyWordDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    EditText et_pacient_keyword = (EditText) queryKeyWordView.findViewById(R.id.et_pacient_keyword);

                                    pacientList.clear();

                                    pacientList.addAll(pacientDao.getKeyWordQuery(et_pacient_keyword.getText().toString()));

                                    adapter.notifyDataSetChanged();
                                    tv_pacientNum.setText("符合查询条件的病人总数 ：" + pacientList.size());
                                }
                            });
                    queryKeyWordDialog.show();
                    break;

                default:

                    break;
            }
        }
    }
}
