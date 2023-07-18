package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.databinding.ActivityLastBinding;

public class LastActivity extends AppCompatActivity {

    public ActivityLastBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.stopwatchLast.setBase(MyConfig.START_TIME);

        MyConfig.GAME_FINISH = true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "게임이 끝났습니다.", Toast.LENGTH_SHORT).show();
    }
}