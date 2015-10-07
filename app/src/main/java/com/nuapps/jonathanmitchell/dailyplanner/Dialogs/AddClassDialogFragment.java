package com.nuapps.jonathanmitchell.dailyplanner.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.nuapps.jonathanmitchell.dailyplanner.R;

import java.util.Calendar;

/**
 * Created by jmitch on 10/5/2015.
 */
public class AddClassDialogFragment extends DialogFragment implements View.OnClickListener{

    private static final String TAG = "ADD_CLASS_D_FRAG";

    public static final String CLASS_KEY = "SIS_CLASS";
    public static final String TEACHER_KEY = "SIS_TEACHER";

    public static final int REQUEST_EDIT_TEXT_DATA = 1;

    private EditText className;
    private EditText teacherName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_add_class, container, false);

        className=(EditText)v.findViewById(R.id.edit_text_class_name);
        teacherName=(EditText)v.findViewById(R.id.edit_text_teacher_name);

        if(savedInstanceState!=null){
            className.setText(savedInstanceState.getString(CLASS_KEY));
            teacherName.setText(savedInstanceState.getString(TEACHER_KEY));
        }

        Button addButton = (Button)v.findViewById(R.id.button_add_class);
        Button cancelButton = (Button)v.findViewById(R.id.button_cancel_class);

        addButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);


        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CLASS_KEY, className.getText().toString());
        outState.putString(TEACHER_KEY, teacherName.getText().toString());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_add_class:
                if(inputIsValid())
                    sendResult(REQUEST_EDIT_TEXT_DATA);
                break;
            case R.id.button_cancel_class:
                dismiss();
                break;
        }
    }

    private boolean inputIsValid(){
        String classNameText = className.getText().toString();
        StringBuilder builder = new StringBuilder();

        boolean classBad, teacherBad = true;

        if(classBad = (classNameText==null || classNameText.isEmpty())){
            builder.append("Need Class Name");
        }

        String teachText = teacherName.getText().toString();
        if(teacherBad = (teachText==null || teachText.isEmpty())){
            if(classBad){
                builder.append("\n");
            }
            builder.append("Need Teacher Name");
        }

        if(teacherBad || classBad){
            Toast.makeText(getActivity(),builder.toString(),Toast.LENGTH_SHORT).show();
        }

        return !teacherBad && !classBad;
    }

    private void sendResult(final int REQUEST_CODE){
        Log.i(TAG,"Sending result to activity");
        Intent i = new Intent();
        i.putExtra(CLASS_KEY,className.getText().toString());
        i.putExtra(TEACHER_KEY,teacherName.getText().toString());
        getTargetFragment()
                .onActivityResult(REQUEST_CODE, Activity.RESULT_OK, i);
        dismiss();
    }
}