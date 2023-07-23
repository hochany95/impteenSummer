package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceGroup;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.HintManager;
import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.conf.PrefManager;
import com.impacteen.hochan.escaperoomapp.databinding.ActivityInitBinding;
public class InitActivity extends AppCompatActivity {
    public AlertDialog initDialog;
    public ActivityInitBinding binding;
    public MediaPlayer player;
    public Vibrator vibrator;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("testApp", "oncreate");
        super.onCreate(savedInstanceState);
        binding = ActivityInitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 기본 셋팅==================
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            VibratorManager vibManager = (VibratorManager) getSystemService(VIBRATOR_MANAGER_SERVICE);
            vibrator = vibManager.getDefaultVibrator();
        }else{
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }
        player = MediaPlayer.create(this, R.raw.suzume_piano);
        player.setLooping(true);
        createPasswordDialog();

        //=====================================================
        //초기 룰 설명
        if(PrefManager.getGameState(this) == PrefManager.GAME_INIT){
            binding.ruleScreen.setVisibility(View.VISIBLE);
            binding.ruleStartBtn.setVisibility(View.VISIBLE);
            binding.initRuleBackground.setVisibility(View.VISIBLE);
        }else{
            binding.ruleScreen.setVisibility(View.INVISIBLE);
            binding.ruleStartBtn.setVisibility(View.INVISIBLE);
            binding.initRuleBackground.setVisibility(View.INVISIBLE);
        }

        //시작버튼과 함께 게임 시작
        binding.ruleStartBtn.setOnClickListener(view -> {
            PrefManager.setGameStarted(getApplicationContext());
            //게임 시작 초기화
            MyConfig.START_TIME = SystemClock.elapsedRealtime();
            PrefManager.setTime(getApplicationContext(), MyConfig.START_TIME);
            binding.stopwatchInit.setBase(MyConfig.START_TIME);
            binding.stopwatchInit.start();

            binding.ruleScreen.setVisibility(View.GONE);
            binding.ruleStartBtn.setVisibility(View.GONE);
            binding.initRuleBackground.setVisibility(View.GONE);
        });

        // 배경 시계 버튼
        binding.initScreenClock.setOnClickListener(view -> {
            showPasswordDialog();
        });

        //help 버튼
        binding.helpBtnInit.setOnClickListener(view -> {
            showWarningDialog();
        });
        binding.helpBtnInit.setOnLongClickListener(view -> {
            if(MyConfig.INIT_COUNT >=2){
                initAllSetting();
                player.stop();
                player.release();
                Toast.makeText(getApplicationContext(), "설정을 초기화 합니다. 앱을 다시 실행해주세요", Toast.LENGTH_SHORT).show();

            }else{
                MyConfig.INIT_COUNT++;
                Toast.makeText(getApplicationContext(), String.format("설정 초기화 (%d/3)", MyConfig.INIT_COUNT), Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        //초기 문제 화면
        binding.initQuestion.setOnClickListener(view -> {
            if(binding.initQuestion.getVisibility() == View.VISIBLE){
                binding.initQuestion.setVisibility(View.INVISIBLE);
            }
        });

        //책 이미지 버튼
        binding.initBookBtn.setOnClickListener(view -> binding.initQuestion.setVisibility(View.VISIBLE));

        //테스트 모드 집입을 위한 동작 - will be removed
        binding.initBookBtn.setOnLongClickListener(view -> {
            if(!MyConfig.isTestMode()){
                AlertDialog.Builder testDialogBuilder = new AlertDialog.Builder(this)
                        .setTitle("테스트 모드 진입하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyConfig.setTestMode(true);
                                binding.testModeBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "테스트 모드 진입", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", null);
                testDialogBuilder.show();

            }else{
                AlertDialog.Builder testDialogBuilder = new AlertDialog.Builder(this)
                        .setTitle("일반 모드로 진입하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyConfig.setTestMode(false);
                                binding.testModeBtn.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "테스트 모드 해제", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("취소", null);
                testDialogBuilder.show();

            }
            return true;
        });
        binding.testModeBtn.setOnClickListener(v -> {
            if (MyConfig.isTestMode()) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                MyConfig.CurrentStage = MissionFragment01.CURRENT_STAGE;
                startActivity(intent);
                overridePendingTransition(R.anim.none, R.anim.horizon_exit);
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
        Log.d("testApp", "onResume");


        player = MediaPlayer.create(this, R.raw.suzume_piano);
        player.setLooping(true);
        player.start();
        if(MyConfig.isTestMode()){
            binding.testModeBtn.setVisibility(View.VISIBLE);
        } else{
            binding.testModeBtn.setVisibility(View.INVISIBLE);
        }

        //시작전 종료시
        if(PrefManager.getGameState(getApplication()) == PrefManager.GAME_INIT){
            HintManager.init();
        }
        // 비정상 종료시
        if(PrefManager.getGameState(getApplication()) != PrefManager.GAME_INIT){
            MyConfig.START_TIME = PrefManager.getTime(getApplication());
            binding.stopwatchInit.setBase(MyConfig.START_TIME);
            MyConfig.GAME_START = true;
        }
        if (PrefManager.getGameState(getApplication()) == PrefManager.GAME_STARTED) {
            binding.stopwatchInit.start();
        }

        //이미 게임이 끝나고 앱이 종료
        if (PrefManager.getGameState(this) == PrefManager.GAME_FINISHED) {
            MyConfig.GAME_FINISH = true;
            Intent intent = new Intent(getApplicationContext(), LastActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            startActivity(intent);
        }
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.d("testApp", "onPause");
        if (PrefManager.getGameState(getApplication()) == PrefManager.GAME_STARTED) {
            PrefManager.setTime(getApplication(), MyConfig.START_TIME);
        }
        try{
            player.pause();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "앱이 비정상 종료되었습니다.\n앱을 다시 시작해주세요 \n:" + e, Toast.LENGTH_SHORT).show();
        }
        if(PrefManager.getNeedHintInit(getApplication())){
            HintManager.init();
            PrefManager.setNeedHintInit(getApplication(), false);
        }

        super.onPause();
    }

    @Override
    protected void onUserLeaveHint() {
        Log.d("testApp", "onUserLeaveHint");
        if (PrefManager.getGameState(getApplication()) == PrefManager.GAME_STARTED) {
            PrefManager.setTime(getApplication(), MyConfig.START_TIME);
        }
        player.stop();
        player.release();
        PrefManager.setNeedHintInit(getApplication(), true);

        super.onUserLeaveHint();
    }

    private void createPasswordDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout dialogLayout = (LinearLayout) inflater.inflate(R.layout.init_password_dialog, null);

        EditText inputEditText = (EditText) dialogLayout.findViewById(R.id.inputBox);
        ImageView dialogAnswerBtn = (ImageView) dialogLayout.findViewById(R.id.dialogAnswerBtn);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogLayout);

        dialogAnswerBtn.setOnClickListener(view -> {
            String inputString = inputEditText.getText().toString();
            inputEditText.setText("");
            if(inputString.equalsIgnoreCase("three")
                    ||inputString.equalsIgnoreCase(MyConfig.TEST_ANSWER)){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                MyConfig.CurrentStage =  MissionFragment01.CURRENT_STAGE;
                startActivity(intent);
                overridePendingTransition(R.anim.none, R.anim.horizon_exit);
                initDialog.dismiss();
            }
            // test를 위한 암호 - 제거 필요
           else{
               vibrator.vibrate(500);
                Toast.makeText(getApplicationContext(), "우리 다시 생각해볼까요?", Toast.LENGTH_SHORT).show();
            }
        });
        initDialog = builder.create();
    }



    private void showPasswordDialog(){
        initDialog.show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "앱이 꺼지지 않도록 주의 하자", Toast.LENGTH_SHORT).show();
    }



    private void showWarningDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("힌트 사용 주의")
                .setMessage(R.string.hintWarning)
                .setPositiveButton("힌트 사용", (dialogInterface, i) -> {
                    if(MyConfig.LAST_HINT_USED < 1 || MyConfig.TEST_MODE || HintManager.opened.get(0) ||
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
        if(!HintManager.opened.get(0)){
            HintManager.opened.set(0, true);
            MyConfig.START_TIME -= MyConfig.PENALTY_TIME;
            binding.stopwatchInit.setBase(MyConfig.START_TIME);
        }


        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("힌트입니다")
                .setMessage(R.string.step1hint)
                .create();
        dialog.show();
    }

    private void initAllSetting() {
        MyConfig.INIT_COUNT = 0;
        PrefManager.resetTime(getApplication());
        PrefManager.setGameReset(getApplication());
        MyConfig.START_TIME = SystemClock.elapsedRealtime();
        MyConfig.GAME_START = false;
        MyConfig.GAME_FINISH = false;
        MyConfig.setTestMode(false);
        MyConfig.LAST_HINT_USED  = 0;

        PrefManager.setTime(getApplicationContext(), MyConfig.START_TIME);
        binding.stopwatchInit.setBase(MyConfig.START_TIME);

    }

}