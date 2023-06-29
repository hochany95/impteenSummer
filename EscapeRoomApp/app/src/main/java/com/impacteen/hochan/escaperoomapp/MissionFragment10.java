package com.impacteen.hochan.escaperoomapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission04Binding;
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission10Binding;

public class MissionFragment10 extends Fragment {
    public static final int CURRENT_STAGE = 10;
    @NonNull FragmentMission10Binding binding;


    private Context mContext;
    private AnswerEventListener mListener;

    public MissionFragment10() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        binding = FragmentMission10Binding.inflate(inflater, container, false);
        binding.answer10.setOnClickListener(view -> {
            mListener.event(CURRENT_STAGE, MyConfig.CORRECT_ANSWER);
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