package com.nuapps.jonathanmitchell.dailyplanner.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.nuapps.jonathanmitchell.dailyplanner.Adapters.AssignmentListAdapter;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClassFactory;
import com.nuapps.jonathanmitchell.dailyplanner.Dialogs.AddAssignmentDialogFragment;
import com.nuapps.jonathanmitchell.dailyplanner.R;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by jmitch on 10/7/2015.
 */
public class ClassShownFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "CLASS_SWN_FRAG";
    public static final String UUID_KEY = "XTRA_UUID";

    private TextView className;
    private TextView teacherName;
    private TextView assignmentNotice;
    private ListView assignmentListView;
    private FloatingActionButton addAssignmentButton;


    private SchoolClass schoolClass;
    private AssignmentListAdapter adapter;


    public static ClassShownFragment newInstance(UUID schoolClassUUID){
        ClassShownFragment frag = new ClassShownFragment();
        Bundle b = new Bundle();
        b.putString(UUID_KEY,schoolClassUUID.toString());
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID uuid = UUID.fromString(getArguments().getString(UUID_KEY));
        schoolClass = SchoolClassFactory.getFactory(getActivity()).getSchoolClassByUUID(uuid);
        if(schoolClass==null){
            Log.e(TAG,"Could not find the school class by UUID. Exiting...");
            getActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_school_class,container,false);

        className=(TextView)v.findViewById(R.id.text_view_class_name);
        teacherName=(TextView)v.findViewById(R.id.text_view_teacher_name);
        assignmentNotice=(TextView)v.findViewById(R.id.text_view_upcoming_assignments);
        assignmentListView=(ListView)v.findViewById(R.id.list_view_assignments);
        addAssignmentButton=(FloatingActionButton)v.findViewById(R.id.button_add_new_assignment);
        addDataToViews();

        if(adapter==null){
            adapter = new AssignmentListAdapter(getContext(),android.R.layout.simple_list_item_1,schoolClass.getAssignments());
        }
        assignmentListView.setAdapter(adapter);

        addAssignmentButton.setOnClickListener(this);

        return v;
    }

    private void addDataToViews(){
        className.setText(schoolClass.getClassName());
        teacherName.setText(schoolClass.getTeacherName());
        assignmentNotice.setText(schoolClass.getAssignmentNotice());

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_add_new_assignment:
                AddAssignmentDialogFragment dFrag = AddAssignmentDialogFragment.newInstance(schoolClass.getUniqueId());
                dFrag.setTargetFragment(ClassShownFragment.this,AddAssignmentDialogFragment.REQUEST_DUE_DATE);
                dFrag.show(getActivity().getSupportFragmentManager(), TAG);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==AddAssignmentDialogFragment.REQUEST_DUE_DATE){
                SchoolClassFactory.getFactory(getActivity()).saveClasses();
                assignmentNotice.setText(schoolClass.getAssignmentNotice());
                adapter.notifyDataSetChanged();
            } else {
                Log.e(TAG,"Bad REQUEST code for ADD ASSIGNMENT D FRAG");
            }
        } else {
            Log.e(TAG,"Bad RESULT code for ADD ASSIGNMENT D FRAG");
        }
    }
}
