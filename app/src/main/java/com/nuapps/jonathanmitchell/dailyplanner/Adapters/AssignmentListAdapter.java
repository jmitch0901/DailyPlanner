package com.nuapps.jonathanmitchell.dailyplanner.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.R;

import java.util.List;

/**
 * Created by jonathanmitchell on 10/7/15.
 */
public class AssignmentListAdapter extends ArrayAdapter<SchoolClass.Assignment> {

    public AssignmentListAdapter(Context context, int resource, List<SchoolClass.Assignment> assignments) {
        super(context, resource,assignments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.view_assignment,parent,false);
        }

        TextView assignmentName = (TextView)v.findViewById(R.id.text_view_assignment_name);
        TextView dateAdded = (TextView)v.findViewById(R.id.text_view_date_added);
        TextView dateDue = (TextView)v.findViewById(R.id.text_view_due_date);

        LinearLayout reminderSet = (LinearLayout)v.findViewById(R.id.linear_layout_reminder_set);
        LinearLayout reminderNotSet = (LinearLayout)v.findViewById(R.id.linear_layout_reminder_not_set);

        SchoolClass.Assignment assignment = getItem(position);

        if(assignment.hasReminder()){
            reminderSet.setVisibility(View.VISIBLE);
            reminderNotSet.setVisibility(View.GONE);
        } else {
            reminderSet.setVisibility(View.GONE);
            reminderNotSet.setVisibility(View.VISIBLE);
        }

        assignmentName.setText(assignment.getAssignmentName());
        dateAdded.setText(assignment.getDateAddedForView());
        dateDue.setText(assignment.getDueDateForView());

        return v;
    }
}
