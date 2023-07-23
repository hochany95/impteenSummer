package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.HintManager;
import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.conf.PrefManager;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;
import com.impacteen.hochan.escaperoomapp.databinding.ActivityMainBinding;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AnswerEventListener {

    public Map<Integer, Fragment> fragmentMap = new LinkedHashMap<>();
    public MissionFragment01 missionFragment01;
    public MissionFragment02 missionFragment02;
    public MissionFragment03 missionFragment03;
    public MissionFragment04 missionFragment04;
    public MissionFragment05 missionFragment05;
    public MissionFragment06 missionFragment06;
    public MissionFragment07 missionFragment07;
    public MissionFragment08 missionFragment08;
    public MissionFragment09 missionFragment09;

    public ActivityMainBinding binding;

    public static InputMethodManager inputManager;

    public MediaPlayer player;
    public static Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("testApp", "onCreate");
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initFragment();


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            VibratorManager vibManager = (VibratorManager) getSystemService(VIBRATOR_MANAGER_SERVICE);
            vibrator = vibManager.getDefaultVibrator();
        }else{
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }
        inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        binding.prevBtn.setOnClickListener(view -> movePrevFragment());

        binding.helpBtn.setOnClickListener(view -> showWarningDialog());

        if(MyConfig.isTestMode()){
            binding.nextBtn.setVisibility(View.VISIBLE);
        }else{
            binding.nextBtn.setVisibility(View.GONE);
        }


        binding.nextBtn.setOnClickListener(view -> {
            if(MyConfig.CurrentStage == MyConfig.LAST_STAGE){
                MoveLastActivity();
            }else {
                changeFragment(MyConfig.CurrentStage+1);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.d("testApp", "onResume");

        if(PrefManager.getGameState(getApplication()) != PrefManager.GAME_INIT){
            MyConfig.START_TIME = PrefManager.getTime(getApplication());
            binding.stopwatchMain.setBase(MyConfig.START_TIME);
        }

        if(PrefManager.getGameState(getApplication())!=PrefManager.GAME_FINISHED){

            binding.stopwatchMain.start();
        }

        player = MediaPlayer.create(this, R.raw.yiruma_indigo);
        player.setLooping(true);

        player.start();
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

    @Override
    protected void onDestroy() {
        Log.d("testApp", "onDestroy");
        player.stop();
        player.release();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (MyConfig.CurrentStage == MissionFragment01.CURRENT_STAGE) {
            super.onBackPressed();
        }else{
            changeFragment(MyConfig.CurrentStage - 1);
        }
    }

    public void changeFragment(Integer currentStage) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragmentMap.get(currentStage));
        ft.commit();
        MyConfig.CurrentStage = currentStage;
    }

    private void moveNextFragment(int idx){
        if(idx < MyConfig.LAST_STAGE){
            changeFragment(idx+1);
        }else{
            MoveLastActivity();
        }
    }
    private void MoveLastActivity() {
        binding.stopwatchMain.stop();
        Intent intent = new Intent(getApplicationContext(), LastActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        startActivity(intent);
    }
    private void movePrevFragment(){
        if(MyConfig.CurrentStage == MissionFragment01.CURRENT_STAGE){
            onBackPressed();
        }else{
            changeFragment(MyConfig.CurrentStage-1);
        }
    }


    public void initFragment() {
        missionFragment01 = new MissionFragment01();
        missionFragment01.registerListener(this);
        missionFragment02 = new MissionFragment02();
        missionFragment02.registerListener(this);
        missionFragment03 = new MissionFragment03();
        missionFragment03.registerListener(this);
        missionFragment04 = new MissionFragment04();
        missionFragment04.registerListener(this);
        missionFragment05 = new MissionFragment05();
        missionFragment05.registerListener(this);
        missionFragment06 = new MissionFragment06();
        missionFragment06.registerListener(this);
        missionFragment07 = new MissionFragment07();
        missionFragment07.registerListener(this);
        missionFragment08 = new MissionFragment08();
        missionFragment08.registerListener(this);
        missionFragment09 = new MissionFragment09();
        missionFragment09.registerListener(this);

        fragmentMap.put(missionFragment01.getFragmentIdx(), missionFragment01);
        fragmentMap.put(missionFragment02.getFragmentIdx(), missionFragment02);
        fragmentMap.put(missionFragment03.getFragmentIdx(), missionFragment03);
        fragmentMap.put(missionFragment04.getFragmentIdx(), missionFragment04);
        fragmentMap.put(missionFragment05.getFragmentIdx(), missionFragment05);
        fragmentMap.put(missionFragment06.getFragmentIdx(), missionFragment06);
        fragmentMap.put(missionFragment07.getFragmentIdx(), missionFragment07);
        fragmentMap.put(missionFragment08.getFragmentIdx(), missionFragment08);
        fragmentMap.put(missionFragment09.getFragmentIdx(), missionFragment09);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fragmentContainer, missionFragment01);
        ft.commit();

        MyConfig.CurrentStage = missionFragment01.getFragmentIdx();
    }


    @Override
    public void event(int idx, int Event) {
        switch (Event) {
            case MyConfig.CORRECT_ANSWER:
            case MyConfig.GO_NEXT:
                moveNextFragment(idx);
                break;

            case MyConfig.WRONG_ANSWER:
                vibrator.vibrate(500);
                Toast.makeText(getApplicationContext(), "정답이 아닌 것 같다.", Toast.LENGTH_SHORT).show();
                break;

            case MyConfig.PAUSE_MUSIC:
                player.pause();
                break;

            case MyConfig.RESUME_MUSIC:
                player.start();
                break;
            case MyConfig.GAME_CLEAR:
                MoveLastActivity();
                break;

            case MyConfig.MUSIC_CHANGE:
                player.stop();
                player.release();

                player = MediaPlayer.create(this, idx);
                player.setLooping(true);
                player.start();

            default:
                Toast.makeText(getApplicationContext(), "get unexpected message from"+idx, Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void showWarningDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("힌트를 보시겠습니까?")
                .setMessage(R.string.hintWarning)
                .setPositiveButton("힌트사용", (dialogInterface, i) -> {
                    if(MyConfig.LAST_HINT_USED < 1 || MyConfig.TEST_MODE || HintManager.opened.get(MyConfig.CurrentStage) ||
                            SystemClock.elapsedRealtime() - MyConfig.LAST_HINT_USED > MyConfig.COOL_TIME){
                        showHintDialog();
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
        if(!HintManager.opened.get(MyConfig.CurrentStage)){
            HintManager.opened.set(MyConfig.CurrentStage, true);
            MyConfig.START_TIME -= MyConfig.PENALTY_TIME;
            binding.stopwatchMain.setBase(MyConfig.START_TIME);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("힌트입니다");
        switch (MyConfig.CurrentStage){

            case 1:
                builder.setMessage(R.string.step1hint);
                break;
            case 2:
                builder.setMessage(R.string.step2hint);
                break;
            case 3:
                builder.setMessage(R.string.step3hint);
                break;
            case 4:
                builder.setMessage(R.string.step4hint);
                break;
            case 5:
                builder.setMessage(R.string.step5hint);
                break;
            case 6:
                builder.setMessage(R.string.step6hint);
                break;
            case 7:
                builder.setMessage(R.string.step7hint);
                break;
            case 8:
                builder.setMessage(R.string.step8hint);
                break;
            case 9:
                builder.setMessage(R.string.step9hint);
                break;
        }

        AlertDialog dialog;
        dialog = builder.create();
        dialog.show();
        MyConfig.LAST_HINT_USED = SystemClock.elapsedRealtime();
    }


}