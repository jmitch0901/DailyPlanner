package com.nuapps.jonathanmitchell.dailyplanner.IO;

import android.content.Context;
import android.util.Log;

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClassFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmitch on 10/5/2015.
 */
public class JSONSerializer {

    public static final String FILENAME = "class_list.json";
    private static final String TAG = "JSONSerial.";


    private Context context;

    public JSONSerializer(Context context){
        this.context=context;
    }

    public List<SchoolClass> loadSchoolClasses(final String CLASSES_KEY){
        Log.i(TAG,"LOADING SCHOOL CLASSES");

        BufferedReader reader = null;
        List<SchoolClass> schoolClasses = new ArrayList<>();

        try {
            InputStream in = context.openFileInput(FILENAME);
            reader = new BufferedReader(new InputStreamReader(in));

            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while((line=reader.readLine()) != null){
                jsonString.append(line);
            }

            JSONObject jo = (JSONObject) new JSONTokener(jsonString.toString()).nextValue();

            try{
                JSONArray ja = jo.getJSONArray(CLASSES_KEY);
                for(int i = 0; i < ja.length(); i++){
                    schoolClasses.add(new SchoolClass(ja.getJSONObject(i)));
                }

                return schoolClasses;

            } catch (JSONException e){
                Log.e(TAG,"Error loading the json object into the factory: "+e.toString());
            }


        } catch (JSONException e) {
            Log.e(TAG, "JSON Exception while loading: " + e.toString());
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException reading classes: " + e.toString());
        } catch (IOException e){
            Log.e(TAG, "IOException in reading classes: " + e.toString());
        }

        return new ArrayList<>();

    }

    public void saveSchoolClasses(SchoolClassFactory factory){
        Log.i(TAG,"SAVING SCHOOL CLASSES");
        Writer writer = null;

        try {
            JSONObject jo = factory.toJSON();

            OutputStream os = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(os);
            writer.write(jo.toString());
            //Log.d(TAG, factory.toJSON().toString());
        } catch (JSONException e){
            Log.e(TAG,"JSON Exception while saving: "+e.toString());
        } catch (FileNotFoundException e) {
            Log.e(TAG,"FileNotFoundException saving classes: "+e.toString());
        } catch (IOException e) {
            Log.e(TAG,"IOException in saving classes: "+e.toString());
        } finally {
            if(writer!=null){
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e){
                    Log.e(TAG,"Error closing the writer: "+e.toString());
                }
            }
        }


    }

}
