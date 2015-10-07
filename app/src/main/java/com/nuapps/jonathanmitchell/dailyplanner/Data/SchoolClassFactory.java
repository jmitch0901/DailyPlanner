package com.nuapps.jonathanmitchell.dailyplanner.Data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by jmitch on 10/5/2015.
 */
public class SchoolClassFactory {



    private static final String TAG = "SCHOOL_CLASS_FACT";

    private static final String CLASSES = "classes";


    private static SchoolClassFactory myFactory;

    private List<SchoolClass> schoolClasses;
    private Context context;

    private SchoolClassFactory(Context context){
        this.context = context;
        schoolClasses = new ArrayList<>();
    }

    public static SchoolClassFactory getFactory(Context context){
        if(myFactory==null){
            myFactory=new SchoolClassFactory(context);
        }

        return myFactory;
    }

    public void addClassAndSort(SchoolClass schoolClass){
        schoolClasses.add(schoolClass);
        Collections.sort(schoolClasses);
    }

    public void addClass(SchoolClass schoolClass){
        schoolClasses.add(schoolClass);
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        for(SchoolClass sc : schoolClasses){
            ja.put(sc.toJSON());
        }

        jo.put(CLASSES,ja);

        return jo;
    }
}
