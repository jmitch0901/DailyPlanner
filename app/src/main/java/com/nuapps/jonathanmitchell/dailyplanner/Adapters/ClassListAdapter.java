package com.nuapps.jonathanmitchell.dailyplanner.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.R;

import java.util.List;

/**
 * Created by jmitch on 10/6/2015.
 */
public class ClassListAdapter extends ArrayAdapter<SchoolClass> {

    public ClassListAdapter(Context context, int resource, List<SchoolClass> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.view_school_class,parent,false);
        }

        SchoolClass schoolClass = getItem(position);

        TextView className = (TextView)v.findViewById(R.id.text_view_class_name);
        TextView teacherName = (TextView)v.findViewById(R.id.text_view_teacher_name);
        TextView assignmentCount = (TextView)v.findViewById(R.id.text_view_n_assignments);
        TextView dateAdded = (TextView)v.findViewById(R.id.text_view_date_added);

        className.setText(schoolClass.getClassName());
        teacherName.setText(schoolClass.getTeacherName());
        assignmentCount.setText(schoolClass.getAssignmentCount()+" assignments");
        dateAdded.setText("created "+schoolClass.getDateForView());

        return v;
    }
}
