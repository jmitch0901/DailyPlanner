package com.nuapps.jonathanmitchell.dailyplanner.Dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClassFactory;
import com.nuapps.jonathanmitchell.dailyplanner.R;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by jmitch on 10/7/2015.
 */
public class AddAssignmentDialogFragment extends DialogFragment
        implements View.OnClickListener,
        DatePicker.OnDateChangedListener,
        TextWatcher
{

    public static final int REQUEST_DUE_DATE = 2;

    private static final String TAG = "ADD_ASSGN_D_FRAG";
    private static final String XTRA_UUID="XTRA_UUID";

    private static final String SIS_ASSIGNMENT_NAME = "SIS_ASSIGNMENT_NAME";

    private SchoolClass schoolClass;
    private EditText assignmentEditText;
    private TextView confirmTextView;
    private Calendar cal;
    private Date selectedDate;

    private String assignmentName;

    public static AddAssignmentDialogFragment newInstance(UUID uuid){
        AddAssignmentDialogFragment dFrag = new AddAssignmentDialogFragment();
        Bundle args = new Bundle();
        args.putString(XTRA_UUID, uuid.toString());
        dFrag.setArguments(args);
        return dFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cal = Calendar.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_add_assignment,container,false);

        UUID uuid=UUID.fromString(getArguments().getString(XTRA_UUID));
        schoolClass=SchoolClassFactory.getFactory(getActivity()).getSchoolClassByUUID(uuid);

        assignmentEditText=(EditText)v.findViewById(R.id.edit_text_assignment_name);
        DatePicker datePicker=(DatePicker)v.findViewById(R.id.date_picker_add_assignment);
        confirmTextView=(TextView)v.findViewById(R.id.text_view_confirm_assignment);
        Button confirm=(Button)v.findViewById(R.id.button_confirm_new_assignment);
        Button cancel=(Button)v.findViewById(R.id.button_cancel_new_assignment);

        if(savedInstanceState!=null){
            assignmentEditText.setText(savedInstanceState.getString(SIS_ASSIGNMENT_NAME));
        }

        assignmentEditText.addTextChangedListener(this);

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this);
        datePicker.setMinDate(cal.getTimeInMillis());

        initializeConfirmTextView();

        return v;
    }

    private void initializeConfirmTextView(){
        selectedDate=new Date();
        assignmentName="";
        updateTextView();
    }

    private String getFormattedDate(){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(selectedDate);
    }

    private String getWeekDay(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        return format.format(selectedDate);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_confirm_new_assignment:
                if(inputIsValid()) {
                    if(!schoolClass.assignmentExists(assignmentName)) {
                        sendResult(REQUEST_DUE_DATE);
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(),"Assignment \'"+assignmentName+"\' already exists. Please remove old assignment, " +
                                "or rename current.",Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(getActivity(),"Please input an Assignment Name",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_cancel_new_assignment:
                dismiss();
                break;
        }
    }

    private boolean inputIsValid(){
        return assignmentEditText.getText().toString()!=null && !assignmentEditText.getText().toString().isEmpty();
    }

    private void sendResult(final int REQUEST_CODE){
        schoolClass.addAssignmentAndSort(assignmentName,selectedDate); //POINTING to the place in factory, ADD that assignment.
        getTargetFragment().onActivityResult(REQUEST_CODE, Activity.RESULT_OK,null);
    }

    private void updateTextView(){
        assignmentName=assignmentName==null||assignmentName.isEmpty() ? "?" : assignmentName;
        String text = "Assignment \'"+assignmentName+"\' will be due "+getWeekDay()+", "
                +getFormattedDate()+" for class \'"+schoolClass.getClassName()+"\'.";
        confirmTextView.setText(text);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SIS_ASSIGNMENT_NAME,assignmentEditText.getText().toString());
    }


    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        selectedDate = new Date(datePicker.getCalendarView().getDate());
        updateTextView();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        assignmentName=charSequence.toString();
        updateTextView();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {}
}
