package com.nuapps.jonathanmitchell.dailyplanner.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nuapps.jonathanmitchell.dailyplanner.R;

/**
 * Created by jmitch on 10/5/2015.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public abstract Fragment getFragment();

    public abstract boolean isHomeAsUpEnabled();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);

        toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUpEnabled());


        if(getSupportFragmentManager().findFragmentById(R.id.frame_layout_fragment_container)==null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_layout_fragment_container, getFragment())
                    .commit();
        }
    }
}
