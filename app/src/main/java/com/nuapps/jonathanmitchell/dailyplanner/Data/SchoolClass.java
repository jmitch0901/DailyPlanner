package com.nuapps.jonathanmitchell.dailyplanner.Data;


import android.util.Log;

import com.nuapps.jonathanmitchell.dailyplanner.IO.JSONSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jmitch on 10/5/2015.
 */
public class SchoolClass implements Comparable<SchoolClass>{

    private static final String TAG = "SCHOOL_CLASS";

    public static final SimpleDateFormat DFORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mma");

    private static final String JSON_UUID= "uuid";
    private static final String CLASS_NAME = "class_name";
    private static final String TEACHER_NAME = "teacher_name";
    private static final String DATE_ADDED = "date_added";
    private static final String ASSIGNMENTS = "assignments";

    private UUID uniqueId;
    private String className;
    private String teacherName;
    private Date dateAdded;
    private List<Assignment> assignments;


    public SchoolClass(String className, String teacherName){
        this.className=className;
        this.teacherName=teacherName;
        this.dateAdded=Calendar.getInstance().getTime();
        uniqueId = UUID.randomUUID();
        assignments = new ArrayList<>();
        Log.i("Class Add Date",dateAdded.toString());
    }

    public SchoolClass(JSONObject jo) throws JSONException{
        assignments = new ArrayList<>();
        uniqueId=UUID.fromString(jo.getString(JSON_UUID));
        className=jo.getString(CLASS_NAME);
        teacherName=jo.getString(TEACHER_NAME);
        try {
            dateAdded = DFORMAT.parse(jo.getString(DATE_ADDED));
        } catch (ParseException e){
            Log.e(TAG,"Error parsing the date for "+className+", "+uniqueId+": "+e.toString());
        }

        try {
            JSONArray ja = jo.getJSONArray(ASSIGNMENTS);
            for (int i = 0; i < ja.length(); i++) {
                assignments.add(new Assignment(ja.getJSONObject(i)));
            }
        } catch (JSONException e){
            Log.e(TAG,"Error parsing assignments: "+e.toString());
            throw e;
        }
    }

    public String getAssignmentNotice(){
        return assignments.size()==1 ? "You have "+assignments.size()+" assignment coming up" : "You have "+assignments.size()+" assignments coming up";
    }

    public void addAssignmentAndSort(String assignmentDescription, Date dueDate){
        assignments.add(new Assignment(assignmentDescription,dueDate));
        Collections.sort(assignments);
    }

    public void addAssignment(String assignmentDescription, Date dueDate){
        assignments.add(new Assignment(assignmentDescription, dueDate));
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getClassName() {
        return className;
    }

    public String getDateAdded() {
        return DFORMAT.format(dateAdded);
    }

    public String getDateForView(){
        String s = DFORMAT.format(dateAdded);
        //s =  s.substring(0,10)+"\n"+s.substring(11,s.length());

        if(s.charAt(11) == '0'){
            char[] chars = s.toCharArray();
            chars[11] = ' ';
            s = new String(chars);
        }

        return s;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public int compareTo(SchoolClass schoolClass) {
        return schoolClass.dateAdded.compareTo(dateAdded);
    }

    public JSONObject toJSON() throws JSONException{

        JSONObject jo = new JSONObject();
        JSONArray jAssignments = new JSONArray();

        jo.put(JSON_UUID,uniqueId.toString());
        jo.put(CLASS_NAME,className);
        jo.put(TEACHER_NAME,teacherName);
        jo.put(DATE_ADDED,DFORMAT.format(dateAdded));

        Log.i(TAG,"Date JSON: "+DFORMAT.format(dateAdded));

        for(Assignment a : assignments){
            jAssignments.put(a.toJSON());
        }


        jo.put(ASSIGNMENTS,jAssignments);

        return jo;

    }

    public int getAssignmentCount(){
        return assignments.size();
    }

    public class Assignment implements Comparable<Assignment>{

        private static final String ASSIGNMENT_NAME = "date";
        private static final String DATE_ADDED = "date_added";
        private static final String DATE_DUE = "date_due";

        private Date dateAdded;
        private Date dueDate;
        private String assignmentName;

        public Assignment(String assignmentName, Date dueDate){
            Calendar calendar = Calendar.getInstance();
            dateAdded = calendar.getTime();
            this.dueDate=dueDate;
            this.assignmentName=assignmentName;

            Log.i("DATE",dateAdded.toString());
        }

        public Assignment(JSONObject jo) throws JSONException{
            try{
                dateAdded = DFORMAT.parse(jo.getString(DATE_ADDED));
                dueDate = DFORMAT.parse(jo.getString(DATE_DUE));
                assignmentName = jo.getString(ASSIGNMENT_NAME);
            } catch (ParseException e){
                Log.e(TAG,"Error parsing the date in assignments: "+e.toString());
            }
        }

        public String getAssignmentName() {
            return assignmentName;
        }

        public Date getDateAdded() {
            return dateAdded;
        }

        public Date getDueDate() {
            return dueDate;
        }

        public JSONObject toJSON() throws JSONException{
            JSONObject jo = new JSONObject();
            jo.put(ASSIGNMENT_NAME,assignmentName);
            jo.put(DATE_ADDED,DFORMAT.format(dateAdded));
            jo.put(DATE_DUE, DFORMAT.format(dueDate));
            return jo;
        }

        @Override
        public int compareTo(Assignment assignment) {
            return assignment.dateAdded.compareTo(dateAdded);
        }
    }
}
