package com.impacteen.hochan.escaperoomapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission03Binding;

public class MissionFragment03 extends Fragment {

    @NonNull FragmentMission03Binding binding;
    public static final int CURRENT_STAGE = 3;

    private Context mContext;
    private AnswerEventListener mListener;

    public MissionFragment03() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        binding = FragmentMission03Binding.inflate(inflater, container, false);
//        binding.answer3.setOnClickListener(view -> {
//            mListener.event(CURRENT_STAGE, MyConfig.CORRECT_ANSWER);
//        });
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