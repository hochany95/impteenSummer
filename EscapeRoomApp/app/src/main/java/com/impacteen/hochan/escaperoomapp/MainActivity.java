package com.impacteen.hochan.escaperoomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;

public class MainActivity extends AppCompatActivity implements AnswerEventListener {

    MissionFragment01 missionFragment001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        missionFragment001 = new MissionFragment01(getApplicationContext());
        missionFragment001.registerListener(this);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fragmentContainer, missionFragment001);
        ft.commit();
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