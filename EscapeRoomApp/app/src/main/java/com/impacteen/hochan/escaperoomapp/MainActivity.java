package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AnswerEventListener {

    public Map<Integer, Fragment> fragmentMap = new LinkedHashMap<>();
    MissionFragment01 missionFragment01;
    MissionFragment02 missionFragment02;
    MissionFragment03 missionFragment03;
    MissionFragment04 missionFragment04;
    MissionFragment05 missionFragment05;
    MissionFragment06 missionFragment06;
    MissionFragment07 missionFragment07;
    MissionFragment08 missionFragment08;
    MissionFragment09 missionFragment09;
    MissionFragment10 missionFragment10;

    int currentStage = 0;

    ImageView prevBtn, helpBtn, nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

        prevBtn = (ImageView) findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이전 버튼
                if(currentStage == MissionFragment01.CURRENT_STAGE){
                    onBackPressed();
                }else{
//                    Toast.makeText(getApplicationContext(), "prev pressed", Toast.LENGTH_SHORT).show();
                    changeFragment(currentStage-1);
                }

            }
        });

        helpBtn = (ImageView) findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        nextBtn = (ImageView) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentStage == MyConfig.LAST_STAGE){
                    Toast.makeText(getApplicationContext(), "you can't move next", Toast.LENGTH_SHORT).show();
                }else{
//                    Toast.makeText(getApplicationContext(), "next pressed", Toast.LENGTH_SHORT).show();
                    changeFragment(currentStage+1);
                }
            }
        });

    }


    public void changeFragment(Integer idx) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragmentMap.get(idx));
        ft.commit();
        currentStage = idx;
    }
    public void initFragment() {
        missionFragment01 = new MissionFragment01(getApplicationContext());
        missionFragment01.registerListener(this);
        missionFragment02 = new MissionFragment02(getApplicationContext());
        missionFragment02.registerListener(this);
        missionFragment03 = new MissionFragment03(getApplicationContext());
        missionFragment03.registerListener(this);
        missionFragment04 = new MissionFragment04(getApplicationContext());
        missionFragment04.registerListener(this);
        missionFragment05 = new MissionFragment05(getApplicationContext());
        missionFragment05.registerListener(this);
        missionFragment06 = new MissionFragment06(getApplicationContext());
        missionFragment06.registerListener(this);
        missionFragment07 = new MissionFragment07(getApplicationContext());
        missionFragment07.registerListener(this);
        missionFragment08 = new MissionFragment08(getApplicationContext());
        missionFragment08.registerListener(this);
        missionFragment09 = new MissionFragment09(getApplicationContext());
        missionFragment09.registerListener(this);
        missionFragment10 = new MissionFragment10(getApplicationContext());
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

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fragmentContainer, missionFragment01);
        ft.commit();

        currentStage = missionFragment01.getFragmentIdx();
    }
    @Override
    public void event(int idx, int Event) {
        switch (Event) {
            case MyConfig.CORRECT_ANSWER:
                Toast.makeText(getApplicationContext(), "get answer from"+idx, Toast.LENGTH_SHORT).show();
                break;
            case MyConfig.WRONG_ANSWER:
                Toast.makeText(getApplicationContext(), "get wrong from"+idx, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "get unexpected message from"+idx, Toast.LENGTH_SHORT).show();
                break;
        }
    }


}