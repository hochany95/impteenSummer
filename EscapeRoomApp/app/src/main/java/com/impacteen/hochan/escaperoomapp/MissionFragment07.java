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
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission07Binding;

import java.util.function.BiFunction;

public class MissionFragment07 extends Fragment {
    public static final int CURRENT_STAGE = 7;
    @NonNull FragmentMission07Binding binding;
    private boolean BGMPaused = false;
    public MediaPlayer player;
    private Context mContext;
    private AnswerEventListener mListener;
    private String ANSWER = "1234";

    public MissionFragment07() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        binding = FragmentMission07Binding.inflate(inflater, container, false);
        binding.imageView7.setOnClickListener(view -> {
        });

        mListener.event(CURRENT_STAGE, MyConfig.PAUSE_MUSIC);
        BGMPaused = true;


        binding.answerBox7.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String inputAnswer = textView.getText().toString();
                textView.setText("");

                if(inputAnswer.equals(ANSWER)||inputAnswer.equalsIgnoreCase(MyConfig.TEST_ANSWER)){
                    mListener.event(CURRENT_STAGE, MyConfig.CORRECT_ANSWER);
                }else{
                    mListener.event(CURRENT_STAGE, MyConfig.WRONG_ANSWER);

                }
                binding.answerBox7.clearFocus();
                MainActivity.inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                return true;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        player = MediaPlayer.create(mContext, R.raw.love_never_fails_pitch_shift);
        player.setLooping(true);
        player.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        player.stop();
        player.release();
        if(BGMPaused){
            mListener.event(CURRENT_STAGE, MyConfig.RESUME_MUSIC);
        }
    }

    public void registerListener(AnswerEventListener listener) {
        mListener = listener;
    }

    public Integer getFragmentIdx() {
        return CURRENT_STAGE;
    }
}