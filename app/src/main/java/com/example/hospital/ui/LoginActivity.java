package com.example.hospital.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospital.R;
import com.example.hospital.dao.LoginDao;

/**
 * Created by Wachsbeere on 2019/1/6.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText mAccount;
    private EditText mPassword;
    private TextView mRegister;
    private Button mLogin;
    private Context context;
    private LoginDao loginDao;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        initView();
        loginDao = new LoginDao(this);
    }

    private void initView() {
        mAccount = (EditText) findViewById(R.id.et_account);
        mPassword = (EditText) findViewById(R.id.et_password);
        mRegister = (TextView) findViewById(R.id.tv_register);
        mLogin = (Button) findViewById(R.id.btn_login);


        //跳转到注册
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToRegister();

            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(getAccount())) {
                    Toast.makeText(LoginActivity.this, "请输入账号名称", Toast.LENGTH_SHORT).show();
                }

                if (!TextUtils.isEmpty(getAccount()) && TextUtils.isEmpty(getPassword())) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }

                if (!TextUtils.isEmpty(getAccount()) && !TextUtils.isEmpty(getPassword())) {
                    if (loginDao.login(getAccount(), getPassword())) {
                        jumpToHomePage();
                    }
                }
            }
        });
    }

    public String getPassword() {
        return mPassword.getText().toString();
    }

    public String getAccount() {
        return mAccount.getText().toString();
    }

    public void jumpToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void jumpToHomePage() {
        Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showFailed(String reason) {
        Toast.makeText(LoginActivity.this, "抱歉，遇到了一个意料之外的错误！", Toast.LENGTH_SHORT).show();
    }

}
