package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;
import com.impacteen.hochan.escaperoomapp.databinding.ActivityMainBinding;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AnswerEventListener {

    public Map<Integer, Fragment> fragmentMap = new LinkedHashMap<>();
    public Map<Integer, Boolean> isOpen = new LinkedHashMap<>();
    public MissionFragment01 missionFragment01;
    public MissionFragment02 missionFragment02;
    public MissionFragment03 missionFragment03;
    public  MissionFragment04 missionFragment04;
    public MissionFragment05 missionFragment05;
    public  MissionFragment06 missionFragment06;
    public  MissionFragment07 missionFragment07;
    public  MissionFragment08 missionFragment08;
    public  MissionFragment09 missionFragment09;
    public  MissionFragment10 missionFragment10;

    public  ActivityMainBinding binding;

    public static InputMethodManager inputManager;

    public MediaPlayer player;
    public static Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initFragment();

        //진동 매니저
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            VibratorManager vibManager = (VibratorManager) getSystemService(VIBRATOR_MANAGER_SERVICE);
            vibrator = vibManager.getDefaultVibrator();
        }else{
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }

        //키보드 매니저
        inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        //BGM
        player = MediaPlayer.create(this, R.raw.sample_main_music);
        player.setLooping(true);

        //이전 문제로 가는 버튼
        binding.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movePrevFragment();
            }
        });

        binding.helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "call help - To-Do", Toast.LENGTH_SHORT).show();
            }
        });

        //테스트 모드의 경우에 활성화
        if(MyConfig.isTestMode()){
            binding.nextBtn.setVisibility(View.VISIBLE);
        }else{
            binding.nextBtn.setVisibility(View.GONE);
        }
        binding.helpBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("init");
                builder.setMessage("셋팅을 초기화 하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initFragment();
                        MyConfig.TEST_MODE = false;
                        MyConfig.LAST_OPEN = 1;
                        MyConfig.CurrentStage = 0;
                        Toast.makeText(getApplicationContext(), "모든 값을 초기화 합니다.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                return true;
            };
        });

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyConfig.CurrentStage == MyConfig.LAST_STAGE){
                    Toast.makeText(getApplicationContext(), "you can't move next", Toast.LENGTH_SHORT).show();
                }else if(isOpen.get(MyConfig.CurrentStage)||MyConfig.isTestMode()){
                    changeFragment(MyConfig.CurrentStage+1);
                }else{
                    Toast.makeText(getApplicationContext(), "Not opened yet", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onResume() {
        player.start();
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

    @Override
    protected void onDestroy() {
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

    public void changeFragment(Integer idx) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragmentMap.get(idx));
        ft.commit();
        MyConfig.CurrentStage = idx;
    }

    private void moveNextFragment(int idx){
        if(idx < MyConfig.LAST_STAGE){
            isOpen.put(idx+1, true);
            changeFragment(idx+1);
        }else{
            MoveLastActivity();
        }
    }
    private void movePrevFragment(){
        if(MyConfig.CurrentStage == MissionFragment01.CURRENT_STAGE){
            onBackPressed();
        }else{
            changeFragment(MyConfig.CurrentStage-1);
        }
    }
    private void MoveLastActivity() {
        //To-Do
        Toast.makeText(getApplicationContext(), "아직 마지막 페이지가 준비 되지 않았습니다.", Toast.LENGTH_SHORT).show();
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
        missionFragment10 = new MissionFragment10();
        missionFragment10.registerListener(this);

        fragmentMap.put(missionFragment01.getFragmentIdx(), missionFragment01);
        fragmentMap.put(missionFragment02.getFragmentIdx(), missionFragment02);
        fragmentMap.put(missionFragment03.getFragmentIdx(), missionFragment03);
        fragmentMap.put(missionFragment04.getFragmentIdx(), missionFragment04);
        fragmentMap.put(missionFragment05.getFragmentIdx(), missionFragment05);
        fragmentMap.put(missionFragment06.getFragmentIdx(), missionFragment06);
        fragmentMap.put(missionFragment07.getFragmentIdx(), missionFragment07);
        fragmentMap.put(missionFragment08.getFragmentIdx(), missionFragment08);
        fragmentMap.put(missionFragment09.getFragmentIdx(), missionFragment09);
        fragmentMap.put(missionFragment10.getFragmentIdx(), missionFragment10);
        isOpen.put(missionFragment01.getFragmentIdx(), true);
        isOpen.put(missionFragment02.getFragmentIdx(), false);
        isOpen.put(missionFragment03.getFragmentIdx(), false);
        isOpen.put(missionFragment04.getFragmentIdx(), false);
        isOpen.put(missionFragment05.getFragmentIdx(), false);
        isOpen.put(missionFragment06.getFragmentIdx(), false);
        isOpen.put(missionFragment07.getFragmentIdx(), false);
        isOpen.put(missionFragment08.getFragmentIdx(), false);
        isOpen.put(missionFragment09.getFragmentIdx(), false);
        isOpen.put(missionFragment10.getFragmentIdx(), false);

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

            case MyConfig.REPLAY_MUSIC:
                player.start();
                break;

            default:
                Toast.makeText(getApplicationContext(), "get unexpected message from"+idx, Toast.LENGTH_SHORT).show();
                break;
        }
    }


}