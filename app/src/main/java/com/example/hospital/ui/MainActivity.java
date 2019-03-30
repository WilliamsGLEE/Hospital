package com.example.hospital.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hospital.application.HospitalContext;
import com.example.hospital.R;

/**
 * Created by Wachsbeere on 2019/1/1.
 */

public class MainActivity extends AppCompatActivity {

    private Button doctor;
    private Button nurse;
    private Button bed;
    private Button pacient;
    private TextView tv_welcome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        doctor = (Button)findViewById(R.id.doctor);
        nurse = (Button)findViewById(R.id.nurse);
        bed = (Button)findViewById(R.id.bed);
        pacient = (Button)findViewById(R.id.pacient);
        tv_welcome = (TextView) findViewById(R.id.tv_welcome);

        if (HospitalContext.getFlag() == 1) {
            tv_welcome.setText("  欢迎，管理员。你目前账号名是：" + HospitalContext.getUsername());
        } else {
            tv_welcome.setText("  欢迎，游客。你目前账号名是：" + HospitalContext.getUsername());
        }


        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DoctorActivity.class);
                startActivity(intent);
            }
        });

        nurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NurseActivity.class);
                startActivity(intent);
            }
        });

        bed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BedActivity.class);
                startActivity(intent);
            }
        });

        pacient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PacientActivity.class);
                startActivity(intent);
            }
        });
    }
}
