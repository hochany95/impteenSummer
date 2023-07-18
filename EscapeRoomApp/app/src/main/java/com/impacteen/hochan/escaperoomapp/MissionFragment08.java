package com.impacteen.hochan.escaperoomapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission08Binding;

public class MissionFragment08 extends Fragment {
    public static final int CURRENT_STAGE = 8;
    @NonNull FragmentMission08Binding binding;


    private Context mContext;
    private AnswerEventListener mListener;
    private String ANSWER = "1234";
    public MissionFragment08() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        binding = FragmentMission08Binding.inflate(inflater, container, false);
        binding.imageView8.setOnClickListener(view -> {
        });

        binding.answerBox8.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String inputAnswer = textView.getText().toString();
                textView.setText("");

                if(inputAnswer.equals(ANSWER)||inputAnswer.equalsIgnoreCase(MyConfig.TEST_ANSWER)){
                    mListener.event(CURRENT_STAGE, MyConfig.CORRECT_ANSWER);
                }else{
                    mListener.event(CURRENT_STAGE, MyConfig.WRONG_ANSWER);

                }
                binding.answerBox8.clearFocus();
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