package com.nuapps.jonathanmitchell.dailyplanner.IO;

import android.content.Context;

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;

import java.util.List;

/**
 * Created by jmitch on 10/5/2015.
 */
public class JSONSerializer {

    private Context context;
    private String fileName;

    public JSONSerializer(Context context, String fileName){
        this.context=context;
        this.fileName=fileName;
    }

    public List<SchoolClass> loadSchoolClasses(){

        return null;
    }

    public void saveSchoolClasses(List<SchoolClass> classes){

    }

}
