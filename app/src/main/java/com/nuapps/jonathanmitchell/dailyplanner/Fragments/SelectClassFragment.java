package com.nuapps.jonathanmitchell.dailyplanner.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nuapps.jonathanmitchell.dailyplanner.Adapters.ClassListAdapter;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClassFactory;
import com.nuapps.jonathanmitchell.dailyplanner.Dialogs.AddClassDialogFragment;
import com.nuapps.jonathanmitchell.dailyplanner.R;

/**
 * Created by jmitch on 10/5/2015.
 */
public class SelectClassFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "SLCT_CLASS_FRAG";

    private FloatingActionButton addButton;
    private ClassListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_class, container, false);


        addButton = (FloatingActionButton)v.findViewById(R.id.fab_add);
        addButton.setOnClickListener(this);

        if(adapter==null){
            SchoolClassFactory factory = SchoolClassFactory.getFactory(getActivity());
            adapter = new ClassListAdapter(getActivity(),android.R.layout.simple_list_item_1,factory.getSchoolClasses());
        }

        ListView classesView = (ListView)v.findViewById(R.id.list_view_school_classes);
        classesView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.fab_add:
                AddClassDialogFragment dFrag = new AddClassDialogFragment();
                dFrag.setTargetFragment(SelectClassFragment.this,AddClassDialogFragment.REQUEST_EDIT_TEXT_DATA);
                dFrag.show(getActivity().getSupportFragmentManager(),TAG);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode==AddClassDialogFragment.REQUEST_EDIT_TEXT_DATA){
                String className = data.getStringExtra(AddClassDialogFragment.CLASS_KEY);
                String teachName = data.getStringExtra(AddClassDialogFragment.TEACHER_KEY);
                SchoolClassFactory.getFactory(getActivity()).addClass(new SchoolClass(className,teachName));
                adapter.notifyDataSetChanged();
            } else {
                Log.e(TAG,"Didn't get dialog data; bad REQUEST code.");
            }
        } else {
            Log.e(TAG,"Didn't get dialog data; bad RESULT code");
        }
    }
}
