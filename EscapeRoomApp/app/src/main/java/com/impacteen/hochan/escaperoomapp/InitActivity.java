package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InitActivity extends AppCompatActivity {

    TextView textView;
    Button initBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        textView = (TextView) findViewById(R.id.initTextView);
        initBtn = (Button) findViewById(R.id.initNextBtn);
        initBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "타이핑 끝", Toast.LENGTH_SHORT).show();
            }
        });
    }
}