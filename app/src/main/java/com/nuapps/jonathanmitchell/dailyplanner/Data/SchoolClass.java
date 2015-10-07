package com.nuapps.jonathanmitchell.dailyplanner.Data;


import android.util.Log;

import com.nuapps.jonathanmitchell.dailyplanner.IO.JSONSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
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

    private static final String JSON_UUID= "uuid";
    private static final String CLASS_NAME = "class_name";
    private static final String TEACHER_NAME = "teacher_name";
    private static final String DATE_ADDED = "date_added";
    private static final String CREDIT_HOURS = "credit_hours";
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
        Log.i("Class Add Date",dateAdded.toString());
    }

    public SchoolClass(JSONObject jo) throws JSONException{

    }

    public void addAssignment(String assignmentDescription, Date dueDate){
        assignments.add(new Assignment(assignmentDescription,dueDate));
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
        jo.put(DATE_ADDED,null);

        Collections.sort(assignments);
        for(Assignment a : assignments){
            jAssignments.put(a.toJSON());
        }


        jo.put(ASSIGNMENTS,jAssignments);

        return jo;

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


            return jo;
        }

        @Override
        public int compareTo(Assignment assignment) {
            return assignment.dateAdded.compareTo(dateAdded);
        }
    }
}
