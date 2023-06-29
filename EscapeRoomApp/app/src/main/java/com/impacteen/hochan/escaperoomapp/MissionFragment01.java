package com.impacteen.hochan.escaperoomapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission01Binding;

import java.util.zip.Inflater;

public class MissionFragment01 extends Fragment {
    /*
    * 줌으로 나온 스크립트가 보인다.
    * 앞 줄의 첫 단어 - 숫자
     */

    FragmentMission01Binding binding;
    public static final int CURRENT_STAGE = 1;
    private Context mContext;
    private AnswerEventListener mListener;
    private boolean opened = true;
    private String answer = "test1";
    private LayoutInflater mInflater;
    public MissionFragment01(Context applicationContext) {
        mContext = applicationContext;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        binding = FragmentMission01Binding.inflate(inflater, container, false);
        //하단 버튼을 사용할 경우에 필요
//        binding.answer1.setOnClickListener(view -> {
//
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
    private void createPasswordDialog() {
//        LayoutInflater inflater = (LayoutInflater)  mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout dialogLayout = (LinearLayout) mInflater.inflate(R.layout.init_password_dialog, null);

        EditText inputEditText = (EditText) dialogLayout.findViewById(R.id.editTextText);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogLayout);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                String inputString = inputEditText.getText().toString();
//                inputEditText.setText("");
//                if(inputString.equalsIgnoreCase(answer)){
//
//                }else if(inputString.equalsIgnoreCase(TEST_COMMAND)){
//                    if (MyConfig.isTestMode()) {
//                        MyConfig.setTestMode(false);
//                        testButton.setVisibility(View.INVISIBLE);
//                        Toast.makeText(getApplicationContext(), "change to user mode", Toast.LENGTH_SHORT).show();
//                    } else {
//                        MyConfig.setTestMode(true);
//                        testButton.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplicationContext(), "change to Test mode", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    inputEditText.setHint("input here..");
//                    Toast.makeText(getApplicationContext(), "Wrong, try again", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        builder.setNegativeButton("test", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                if (MyConfig.isTestMode()) {
//                    MyConfig.setTestMode(false);
//                    testButton.setVisibility(View.INVISIBLE);
//                    Toast.makeText(getApplicationContext(), "change to user mode", Toast.LENGTH_SHORT).show();
//                } else {
//                    MyConfig.setTestMode(true);
//                    testButton.setVisibility(View.VISIBLE);
//                    Toast.makeText(getApplicationContext(), "change to Test mode", Toast.LENGTH_SHORT).show();
//                }
            }
        });
//        initDialog = builder.create();

    }

    private void showPasswordDialog(){
//        initDialog.show();
    }
    public boolean getOpened() {
        return opened;
    }

    public void setOpened() {
        opened = true;
    }
}