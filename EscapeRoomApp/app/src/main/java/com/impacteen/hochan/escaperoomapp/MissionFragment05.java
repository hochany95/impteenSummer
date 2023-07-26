package com.impacteen.hochan.escaperoomapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission05Binding;

public class MissionFragment05 extends Fragment {

    public static final int CURRENT_STAGE = 5;
    FragmentMission05Binding binding;

    private boolean isFirstBell = true;
    private boolean BGMPaused = false;
    public MediaPlayer player;
    private Context mContext;
    private AnswerEventListener mListener;
    //미정
    private String ANSWER = "1225";
    public MissionFragment05() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        binding = FragmentMission05Binding.inflate(inflater, container, false);
        binding.imageView5.setOnClickListener(view -> {
        });

        binding.answerBox5.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String inputAnswer = textView.getText().toString();
                textView.setText("");

                if(inputAnswer.equals(ANSWER)||inputAnswer.equalsIgnoreCase(MyConfig.TEST_ANSWER)){
                    mListener.event(CURRENT_STAGE, MyConfig.CORRECT_ANSWER);
                }else{
                    mListener.event(CURRENT_STAGE, MyConfig.WRONG_ANSWER);

                }
                binding.answerBox5.clearFocus();
                MainActivity.inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                return true;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(isFirstBell){
            mListener.event(CURRENT_STAGE, MyConfig.PAUSE_MUSIC);
            player = MediaPlayer.create(mContext, R.raw.bell1);
            player.setLooping(false);

            player.start();
            MainActivity.vibrator.vibrate(2000);
            isFirstBell = false;
            BGMPaused = true;
        }
    }

    @Override
    public void onPause() {
        //다시 음악 시작 - 2번재 음악으로 변경
        if(BGMPaused){
            mListener.event(R.raw.flower, MyConfig.MUSIC_CHANGE);
        }

        super.onPause();
    }

    public void registerListener(AnswerEventListener listener) {
        mListener = listener;
    }

    public Integer getFragmentIdx() {
        return CURRENT_STAGE;
    }
}