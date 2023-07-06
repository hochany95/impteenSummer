package com.impacteen.hochan.escaperoomapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.impacteen.hochan.escaperoomapp.conf.MyConfig;
import com.impacteen.hochan.escaperoomapp.control.AnswerEventListener;
import com.impacteen.hochan.escaperoomapp.databinding.FragmentMission02Binding;

public class MissionFragment02 extends Fragment {

    FragmentMission02Binding binding;
    public static final int CURRENT_STAGE = 2;

    private Context mContext;
    private AnswerEventListener mListener;
    private AlertDialog currentDialog;
    private TextView dialogTextView;
    private String answer = "test2";
    public MissionFragment02() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        createPasswordDialog();
        binding = FragmentMission02Binding.inflate(inflater, container, false);
        binding.imageView2.setOnClickListener(view -> {
            showPasswordDialog();
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
    private void createPasswordDialog() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout dialogLayout = (LinearLayout) inflater.inflate(R.layout.init_password_dialog, null);

        EditText inputPW = (EditText) dialogLayout.findViewById(R.id.inputBox);
        dialogTextView = dialogLayout.findViewById(R.id.dialogTextView);
        dialogTextView.setText("두 번째 문제입니다.");
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogLayout);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String inputString = inputPW.getText().toString();

                if(inputString.equalsIgnoreCase(answer)||inputString.equalsIgnoreCase(MyConfig.TEST_COMMAND)){
                    mListener.event(CURRENT_STAGE, MyConfig.CORRECT_ANSWER);
                    Toast.makeText(mContext, "다음 페이지가 열립니다", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "Wrong, try again", Toast.LENGTH_SHORT).show();
                }
                inputPW.setText("");
                inputPW.setHint("input here..");
            }
        });
        builder.setNegativeButton("test", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.event(CURRENT_STAGE, MyConfig.CORRECT_ANSWER);
                Toast.makeText(mContext, "test mode.", Toast.LENGTH_SHORT).show();
            }
        });
        currentDialog = builder.create();

    }

    private void showPasswordDialog(){
        currentDialog.show();
    }

}