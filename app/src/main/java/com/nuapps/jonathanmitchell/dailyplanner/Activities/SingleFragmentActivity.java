package com.nuapps.jonathanmitchell.dailyplanner.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nuapps.jonathanmitchell.dailyplanner.R;

/**
 * Created by jmitch on 10/5/2015.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);

        if(getSupportFragmentManager().findFragmentById(R.id.frame_layout_fragment_container)==null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_layout_fragment_container, getFragment())
                    .commit();
        }
    }
}
