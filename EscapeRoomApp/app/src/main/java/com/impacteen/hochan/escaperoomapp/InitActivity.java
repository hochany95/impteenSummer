package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.Hint;
import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.databinding.ActivityInitBinding;
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            VibratorManager vibManager = (VibratorManager) getSystemService(VIBRATOR_MANAGER_SERVICE);
            vibrator = vibManager.getDefaultVibrator();
        }else{
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }
        player = MediaPlayer.create(this, R.raw.suzume_instrumental);
        player.setLooping(true);

        if(!MyConfig.GAME_START){
            Hint.init();
        }
        createPasswordDialog();

        binding.initScreenClock.setOnClickListener(view -> {
            if(!MyConfig.GAME_START&& !MyConfig.GAME_FINISH){
                MyConfig.START_TIME = SystemClock.elapsedRealtime();
                binding.stopwatchInit.setBase(MyConfig.START_TIME);
                binding.stopwatchInit.start();
                MyConfig.GAME_START = true;

                showRuleDialog();
            }else{
                showPasswordDialog();
            }
        });

        binding.helpBtnInit.setOnClickListener(view -> {
            if(!MyConfig.GAME_START && !MyConfig.GAME_FINISH){
                MyConfig.START_TIME = SystemClock.elapsedRealtime();
                binding.stopwatchInit.setBase(MyConfig.START_TIME);
                binding.stopwatchInit.start();
                MyConfig.GAME_START = true;
                showRuleDialog();
            }else{
                showWarningDialog();
            }
        });

        binding.initStartBtn.setOnClickListener(view -> {
            if(!MyConfig.GAME_START && !MyConfig.GAME_FINISH){
                MyConfig.GAME_START = true;
                MyConfig.START_TIME = SystemClock.elapsedRealtime();
                binding.stopwatchInit.setBase(MyConfig.START_TIME);
                binding.stopwatchInit.start();
                showRuleDialog();
            }else{
                if(binding.initQuestion.getVisibility() == View.VISIBLE){
                    binding.initQuestion.setVisibility(View.INVISIBLE);
                }else{
                    binding.initQuestion.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.initQuestion.setOnClickListener(view -> {
            if(binding.initQuestion.getVisibility() == View.VISIBLE){
                binding.initQuestion.setVisibility(View.INVISIBLE);
            }
        });
        binding.initBookBtn.setOnLongClickListener(view -> {
            if(!MyConfig.isTestMode()){
                MyConfig.setTestMode(true);
                binding.testModeBtn.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "테스트 모드 진입", Toast.LENGTH_SHORT).show();
            }else{
                MyConfig.setTestMode(false);
                binding.testModeBtn.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "테스트 모드 해제", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        binding.testModeBtn.setOnClickListener(v -> {
            if (MyConfig.isTestMode()) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                MyConfig.CurrentStage = MissionFragment01.CURRENT_STAGE;
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        player.stop();
        player.release();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        player.start();
        if(MyConfig.isTestMode()){
            binding.testModeBtn.setVisibility(View.VISIBLE);
        } else{
            binding.testModeBtn.setVisibility(View.INVISIBLE);
        }
        super.onResume();
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
                        ||inputString.equalsIgnoreCase(MyConfig.TEST_ANSWER)){
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


    private void showRuleDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
        .setTitle("주의 사항")
                .setMessage(R.string.rules)
                .create();
        dialog.show();
    }

    private void showWarningDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("힌트를 보시겠습니까?")
                .setMessage(R.string.hintWarning)
                .setPositiveButton("힌트 사용", (dialogInterface, i) -> {
                    if(MyConfig.LAST_HINT_USED < 1 || MyConfig.TEST_MODE || Hint.opened.get(0) ||
                            SystemClock.elapsedRealtime() - MyConfig.LAST_HINT_USED > MyConfig.COOL_TIME){
                        showHintDialog();
                        MyConfig.LAST_HINT_USED = SystemClock.elapsedRealtime();
                    }
                    else{
                        long restTime = MyConfig.COOL_TIME - (SystemClock.elapsedRealtime() - MyConfig.LAST_HINT_USED);
                        Toast.makeText(getApplicationContext(), restTime / 1000 + "초 뒤에 사용 가능합니다", Toast.LENGTH_SHORT).show();
                    }

                })
                .create();
        dialog.show();


    }
    private void showHintDialog(){
        if(!Hint.opened.get(0)){
            Hint.opened.set(0, true);
            MyConfig.START_TIME -= MyConfig.PENALTY_TIME;
            binding.stopwatchInit.setBase(MyConfig.START_TIME);
        }


        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("힌트입니다")
                .setMessage(Hint.hintInit)
                .create();
        dialog.show();
    }
}