package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.databinding.ActivityInitBinding;
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission01Binding;

public class InitActivity extends AppCompatActivity {
    public AlertDialog initDialog;
    public ActivityInitBinding binding;
    public MediaPlayer player;

    public Vibrator vibrator;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createPasswordDialog();

        // 시계 뷰 선택 -> 정답 다이얼로그 표시
        binding.initScreenClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordDialog();
            }
        });


        //시작하기 버튼 -> 문제 화면 표출
        binding.initStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isVisible = binding.initQuestion.getVisibility();
                if(isVisible == View.VISIBLE){
                    binding.initQuestion.setVisibility(View.INVISIBLE);
                }else{
                    binding.initQuestion.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.initBookBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!MyConfig.isTestMode()){
                    MyConfig.TEST_MODE = true;
                    binding.testModeBtn.setVisibility(View.VISIBLE);

                    Toast.makeText(getApplicationContext(), "테스트 모드 진입", Toast.LENGTH_SHORT).show();
                }else{
                    MyConfig.TEST_MODE = false;
                    binding.testModeBtn.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "테스트 모드 해제", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        binding.testModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyConfig.isTestMode()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                    MyConfig.CurrentStage = MissionFragment01.CURRENT_STAGE;
                    startActivity(intent);

                }
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            VibratorManager vibManager = (VibratorManager) getSystemService(VIBRATOR_MANAGER_SERVICE);
            vibrator = vibManager.getDefaultVibrator();
        }else{
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }

        player = MediaPlayer.create(this, R.raw.sample_music);
        player.setLooping(true);
    }

    @Override
    protected void onDestroy() {
        player.stop();
        player.release();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();
        if(MyConfig.isTestMode()){
            binding.testModeBtn.setVisibility(View.VISIBLE);
        } else{
            binding.testModeBtn.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    protected void onPause() {
        player.pause();
        super.onPause();
    }

    @Override
    protected void onUserLeaveHint() {
        player.stop();
        player.release();
        super.onUserLeaveHint();
    }

    private void createPasswordDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout dialogLayout = (LinearLayout) inflater.inflate(R.layout.init_password_dialog, null);

        EditText inputEditText = (EditText) dialogLayout.findViewById(R.id.inputBox);
        ImageView dialogAnswerBtn = (ImageView) dialogLayout.findViewById(R.id.dialogAnswerBtn);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogLayout);

        dialogAnswerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String inputString = inputEditText.getText().toString();
                inputEditText.setText("");
                if(inputString.equalsIgnoreCase("three")
                        ||inputString.equalsIgnoreCase("0915")){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);

                    MyConfig.CurrentStage =  MissionFragment01.CURRENT_STAGE;
                    startActivity(intent);
                    initDialog.dismiss();

                }
                // test를 위한 암호 - 제거 필요
               else{
                   vibrator.vibrate(500);
                    Toast.makeText(getApplicationContext(), "우리 다시 생각해볼까요?", Toast.LENGTH_SHORT).show();
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
    }
}