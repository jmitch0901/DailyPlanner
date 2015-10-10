package com.nuapps.jonathanmitchell.dailyplanner.Activities;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.nuapps.jonathanmitchell.dailyplanner.Fragments.ClassShownFragment;

import java.util.UUID;

/**
 * Created by jmitch on 10/7/2015.
 */
public class SchoolClassActivity extends SingleFragmentActivity {

    @Override
    public Fragment getFragment() {
        UUID uuid = (UUID) getIntent().getExtras().get(ClassShownFragment.UUID_KEY);
        return ClassShownFragment.newInstance(uuid);
    }

    @Override
    public boolean isHomeAsUpEnabled() {
        return true;
    }
}
