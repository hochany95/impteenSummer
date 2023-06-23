package com.impacteen.hochan.escaperoomapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.databinding.FragmentInitBinding;

public class MissionFragment_001 extends Fragment {

    FragmentInitBinding binding;
    public final int CURRENT_STAGE = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = FragmentInitBinding.inflate(inflater, container, false);
         binding.button4.setOnClickListener(view -> {
             Toast.makeText(container.getContext(), "next script", Toast.LENGTH_SHORT).show();
         });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public int getCurrentState() {
        return this.CURRENT_STAGE;
    }


}