package com.nuapps.jonathanmitchell.dailyplanner.IO;

import android.content.Context;
import android.util.Log;

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClassFactory;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by jmitch on 10/5/2015.
 */
public class JSONSerializer {

    private static final String TAG = "JSONSerial.";


    private Context context;
    private String fileName;

    public JSONSerializer(Context context, String fileName){
        this.context=context;
        this.fileName=fileName;
    }

    public List<SchoolClass> loadSchoolClasses(){

        return null;
    }

    public void saveSchoolClasses(SchoolClassFactory factory){

        try {
            Log.d(TAG, factory.toJSON().toString());
        } catch (JSONException e){
            Log.e(TAG,"JSON Exception while saving: "+e.toString());
        }


    }

}
