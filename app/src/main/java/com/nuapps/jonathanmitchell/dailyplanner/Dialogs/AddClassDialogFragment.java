package com.nuapps.jonathanmitchell.dailyplanner.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.nuapps.jonathanmitchell.dailyplanner.R;

import java.util.Calendar;

/**
 * Created by jmitch on 10/5/2015.
 */
public class AddClassDialogFragment extends DialogFragment {

    private EditText className;
    private EditText teacherName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_add_class, container, false);


        return v;
    }
}