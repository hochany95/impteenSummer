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
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission04Binding;

public class MissionFragment04 extends Fragment {

    public static final int CURRENT_STAGE = 4;
    FragmentMission04Binding binding;

    private Context mContext;
    private AnswerEventListener mListener;
    private String ANSWER = "1225";
    public MissionFragment04() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        binding = FragmentMission04Binding.inflate(inflater, container, false);
        binding.imageView4.setOnClickListener(view -> {
        });

        binding.answerBox4.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String inputAnswer = textView.getText().toString();
                textView.setText("");

                if(inputAnswer.equals(ANSWER)||inputAnswer.equalsIgnoreCase(MyConfig.TEST_COMMAND)){
                    mListener.event(CURRENT_STAGE, MyConfig.CORRECT_ANSWER);
                }else{
                    mListener.event(CURRENT_STAGE, MyConfig.WRONG_ANSWER);

                }
                binding.answerBox4.clearFocus();
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