package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.databinding.ActivityInitBinding;

public class InitActivity extends AppCompatActivity {
    AlertDialog initDialog;
    ActivityInitBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        createPasswordDialog();
        binding.initScreenClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordDialog();
            }
        });

        //test를 위한 버튼, 제거 필요
        binding.testModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyConfig.isTestMode()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MyConfig.isTestMode()){
            binding.testModeBtn.setVisibility(View.VISIBLE);
        } else{
            binding.testModeBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void createPasswordDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout dialogLayout = (LinearLayout) inflater.inflate(R.layout.init_password_dialog, null);

        EditText inputEditText = (EditText) dialogLayout.findViewById(R.id.inputBox);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogLayout);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String inputString = inputEditText.getText().toString();
                inputEditText.setText("");
                if(inputString.equalsIgnoreCase("three")){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                // test를 위한 암호 - 제거 필요
                else if(inputString.equalsIgnoreCase(MyConfig.TEST_COMMAND)){
                    if (MyConfig.isTestMode()) {
                        MyConfig.setTestMode(false);
                        binding.testModeBtn.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "change to user mode", Toast.LENGTH_SHORT).show();
                    } else {
                        MyConfig.setTestMode(true);
                        binding.testModeBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "change to Test mode", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    inputEditText.setHint("input here..");
                    Toast.makeText(getApplicationContext(), "Wrong, try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("test", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (MyConfig.isTestMode()) {
                    MyConfig.setTestMode(false);
                    binding.testModeBtn.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "TEST MODE OFF", Toast.LENGTH_SHORT).show();
                } else {
                    MyConfig.setTestMode(true);
                    binding.testModeBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "TEST MODE ON", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initDialog = builder.create();
    }

    private void showPasswordDialog(){
        initDialog.show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "앱이 꺼지지 않도록 하자", Toast.LENGTH_SHORT).show();
//
//        super.onBackPressed();
    }
}