package com.nuapps.jonathanmitchell.dailyplanner.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nuapps.jonathanmitchell.dailyplanner.Fragments.SelectClassFragment;
import com.nuapps.jonathanmitchell.dailyplanner.R;

public class MainActivity extends SingleFragmentActivity {


    @Override
    public Fragment getFragment() {
        return new SelectClassFragment();
    }
}
