package com.example.hospital.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hospital.R;
import com.example.hospital.dao.LoginDao;

/**
 * Created by Wachsbeere on 2019/1/6.
 */

public class RegisterActivity extends AppCompatActivity {

//    private ImageButton mBack;
    private EditText mAccount;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private Button mConfirm;
    private LoginDao loginDao;
    private RadioGroup radioGroup;
    private int isManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginDao = new LoginDao(this);
        initView();
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

    private void initView() {
//        mBack = (ImageButton) findViewById(R.id.btn_back);
        mAccount = (EditText) findViewById(R.id.et_name);
        mPassword = (EditText) findViewById(R.id.et_password);
        mPasswordAgain = (EditText) findViewById(R.id.et_passwordAgain);
        mConfirm = (Button) findViewById(R.id.btn_confirm);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        Intent intent = getIntent();

//        mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPassword() != null && getPassword().equals(getPasswordAgain())) {
                    if (loginDao.register(getAccount(), getPassword(), isManager)) {
                        jumpToLogin();
                    }
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                //获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(radioButtonId);

                if (rb.getText().toString().equals("管理员")) {
                    isManager = 1;
                } else {
                    isManager = 0;
                }
            }
        });

    }

    public String getAccount() {
        return mAccount.getText().toString();
    }

    public String getPassword() {
        return mPassword.getText().toString();
    }

    public String getPasswordAgain() {
        return mPasswordAgain.getText().toString();
    }

    public void jumpToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void showWrongPasswordInfo() {
        Toast.makeText(RegisterActivity.this, "两次密码输入不同，请重新输入。", Toast.LENGTH_SHORT).show();
    }

    public void showEmptyPasswordInfo() {
        Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
    }

    public void showWrongInputInfo() {
        Toast.makeText(RegisterActivity.this, "姓名不能为空！", Toast.LENGTH_SHORT).show();
    }

    public void showHasRegisted() {
        Toast.makeText(RegisterActivity.this, "该号码已经注册,请直接登录。", Toast.LENGTH_SHORT).show();
    }

    public void showFailed(String reason) {
        Toast.makeText(RegisterActivity.this, "错误，" + reason, Toast.LENGTH_SHORT).show();
    }
}
