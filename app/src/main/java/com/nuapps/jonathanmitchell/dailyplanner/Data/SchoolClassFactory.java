package com.nuapps.jonathanmitchell.dailyplanner.Data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmitch on 10/5/2015.
 */
public class SchoolClassFactory {

    private List<SchoolClass> schoolClasses;

    private static final String TAG = "SCHOOL_CLASS_FACT";
    private static SchoolClassFactory myFactory;

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

    public void parseFromJSON(){

    }

    public void outputToJSON(){}

}
