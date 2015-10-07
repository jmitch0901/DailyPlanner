package com.nuapps.jonathanmitchell.dailyplanner.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.Dialogs.AddClassDialogFragment;
import com.nuapps.jonathanmitchell.dailyplanner.R;

/**
 * Created by jmitch on 10/5/2015.
 */
public class SelectClassFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "SLCT_CLASS_FRAG";

    private FloatingActionButton addButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_class,container,false);

        addButton = (FloatingActionButton)v.findViewById(R.id.fab_add);
        addButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.fab_add:
                new AddClassDialogFragment().show(getActivity().getSupportFragmentManager(),"TAG");
                break;
        }

    }
}
