package com.nuapps.jonathanmitchell.dailyplanner.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClassFactory;
import com.nuapps.jonathanmitchell.dailyplanner.R;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by jmitch on 10/7/2015.
 */
public class AddAssignmentDialogFragment extends DialogFragment
        implements View.OnClickListener,
        DatePicker.OnDateChangedListener,
        TimePicker.OnTimeChangedListener
{

    private static final String TAG = "ADD_ASSGN_D_FRAG";
    private static final String XTRA_UUID="XTRA_UUID";

    private static final String SIS_ASSIGNMENT_NAME = "SIS_ASSIGNMENT_NAME";

    private SchoolClass schoolClass;

    private EditText assignmentEditText;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView confirmTextView;
    private Button setTime;
    private Button setDate;
    private Button confirm;
    private Button cancel;

    private LinearLayout setTimeLayout;
    private LinearLayout setDateLayout;


    private Calendar cal;

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
        datePicker=(DatePicker)v.findViewById(R.id.date_picker_add_assignment);
        timePicker=(TimePicker)v.findViewById(R.id.time_picker_add_assignment);
        //confirmTextView=(TextView)v.findViewById(R.id.text_view_confirm_assignment);
        setTime=(Button)v.findViewById(R.id.button_set_time);
        setDate=(Button)v.findViewById(R.id.button_set_date);
        confirm=(Button)v.findViewById(R.id.button_confirm_new_assignment);
        cancel=(Button)v.findViewById(R.id.button_cancel_new_assignment);

        setTimeLayout=(LinearLayout)v.findViewById(R.id.linear_layout_time_picker);
        setDateLayout=(LinearLayout)v.findViewById(R.id.linear_layout_date_picker);

        if(savedInstanceState!=null){
            assignmentEditText.setText(savedInstanceState.getString(SIS_ASSIGNMENT_NAME));
        }

        setTime.setOnClickListener(this);
        setDate.setOnClickListener(this);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this);
        datePicker.setMinDate(cal.getTimeInMillis());
        timePicker.setOnTimeChangedListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_set_time:
                setTimeLayout.setVisibility(View.VISIBLE);
                setDateLayout.setVisibility(View.GONE);
                break;
            case R.id.button_set_date:
                setTimeLayout.setVisibility(View.GONE);
                setDateLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.button_confirm_new_assignment:
                break;
            case R.id.button_cancel_new_assignment:
                dismiss();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SIS_ASSIGNMENT_NAME,assignmentEditText.getText().toString());
    }



    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {

    }

}
