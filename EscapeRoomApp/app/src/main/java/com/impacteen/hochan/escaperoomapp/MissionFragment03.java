package com.impacteen.hochan.escaperoomapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission03Binding;

public class MissionFragment03 extends Fragment {

    @NonNull FragmentMission03Binding binding;
    public static final int CURRENT_STAGE = 3;

    private Context mContext;
    private AnswerEventListener mListener;
    private String ANSWER = "0729";

    public MissionFragment03() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        binding = FragmentMission03Binding.inflate(inflater, container, false);
        binding.imageView3.setOnClickListener(view -> {
        });
        binding.answerBox3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                String inputAnswer = textView.getText().toString();
                textView.setText("");

                if(inputAnswer.equals(ANSWER)||inputAnswer.equalsIgnoreCase(MyConfig.TEST_COMMAND)){
                    mListener.event(CURRENT_STAGE, MyConfig.CORRECT_ANSWER);
                }else{
                    mListener.event(CURRENT_STAGE, MyConfig.WRONG_ANSWER);

                }
                binding.answerBox3.clearFocus();
                MainActivity.inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                return true;

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void registerListener(AnswerEventListener listener) {
        mListener = listener;
    }
    public Integer getFragmentIdx() {
        return CURRENT_STAGE;
    }

}