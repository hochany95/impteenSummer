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

public class InitActivity extends AppCompatActivity {

    TextView backgroundView;
    TextClock textClock;
    Button testButton;
    AlertDialog initDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        backgroundView = (TextView) findViewById(R.id.initTextView);
        textClock = (TextClock) findViewById(R.id.textClock);
        textClock.setAlpha((float) 0.4);
        testButton = (Button) findViewById(R.id.initNextBtn);
        if(MyConfig.isTestMode()){
            testButton.setVisibility(View.VISIBLE);
        }
        createPasswordDialog();
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "어따쓰지", Toast.LENGTH_SHORT).show();
            }
        });
        textClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordDialog();
            }
        });
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyConfig.isTestMode()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
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
                }else if(inputString.equalsIgnoreCase(MyConfig.TEST_COMMAND)){
                    if (MyConfig.isTestMode()) {
                        MyConfig.setTestMode(false);
                        testButton.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "change to user mode", Toast.LENGTH_SHORT).show();
                    } else {
                        MyConfig.setTestMode(true);
                        testButton.setVisibility(View.VISIBLE);
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
                    testButton.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "change to user mode", Toast.LENGTH_SHORT).show();
                } else {
                    MyConfig.setTestMode(true);
                    testButton.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "change to Test mode", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initDialog = builder.create();
    }

    private void showPasswordDialog(){
        initDialog.show();
    }
}