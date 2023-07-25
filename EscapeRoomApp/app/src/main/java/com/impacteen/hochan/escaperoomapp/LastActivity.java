package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.conf.PrefManager;
import com.impacteen.hochan.escaperoomapp.databinding.ActivityLastBinding;

public class LastActivity extends AppCompatActivity {

    public ActivityLastBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PrefManager.setGameFinished(getApplication());

        if(PrefManager.getGameState(getApplication()) == PrefManager.GAME_STARTED){
            //정상 과정으로 마지막 단계 도착
            binding.stopwatchLast.setBase(MyConfig.START_TIME);
            PrefManager.setTime(getApplication(), MyConfig.START_TIME);
        }else{
            // 비정상 앱 종료
            binding.stopwatchLast.setBase(PrefManager.getTime(getApplication()));
        }



        MyConfig.GAME_FINISH = true;



    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "게임이 끝났습니다. \n 안내에 따라 강당으로 이동 바랍니다.", Toast.LENGTH_SHORT).show();
    }
}