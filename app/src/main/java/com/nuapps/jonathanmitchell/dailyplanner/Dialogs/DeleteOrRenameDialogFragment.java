package com.nuapps.jonathanmitchell.dailyplanner.Dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClassFactory;
import com.nuapps.jonathanmitchell.dailyplanner.R;

import java.util.UUID;

/**
 * Created by jmitch on 10/10/2015.
 */
public class DeleteOrRenameDialogFragment extends DialogFragment implements View.OnClickListener{

    private static final String TAG = "DEL_OR_RENAME_DFRAG";

    public static final int REQUEST_DELETE_OR_RENAME = 3;

    protected static final String INT_CODE_KEY = "INT_CODE";
    private static final String UUID_KEY = "UUID_KEY";
    private static final String ASSIGNMENT_NAME_KEY = "ASSIGNMENT_NAME_KEY";

    protected static final int SCHOOL_CLASS = 3;
    protected static final int ASSIGNMENT = 4;

    protected SchoolClass schoolClass;
    protected SchoolClass.Assignment assignment;

    public static DeleteOrRenameDialogFragment newInstance(SchoolClass schoolClass){
        DeleteOrRenameDialogFragment dFrag = new DeleteOrRenameDialogFragment();
        Bundle args = new Bundle();
        args.putString(UUID_KEY,schoolClass.getUniqueId().toString());
        args.putInt(INT_CODE_KEY, SCHOOL_CLASS);
        dFrag.setArguments(args);
        return dFrag;
    }

    public static DeleteOrRenameDialogFragment newInstance(SchoolClass.Assignment assignment){
        DeleteOrRenameDialogFragment dFrag = new DeleteOrRenameDialogFragment();
        Bundle args = new Bundle();
        args.putString(UUID_KEY,assignment.getParentUUID().toString());
        args.putInt(INT_CODE_KEY, ASSIGNMENT);
        args.putString(ASSIGNMENT_NAME_KEY, assignment.getAssignmentName());
        dFrag.setArguments(args);
        return dFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args.getInt(INT_CODE_KEY)!=SCHOOL_CLASS && args.getInt(INT_CODE_KEY)!=ASSIGNMENT){
            Log.e(TAG,"Error, must have valid integer code (SCHOOL_CLASS or ASSIGNMENT). Dismissing Dialog...");
            dismiss();
        }

        schoolClass = SchoolClassFactory.getFactory(getActivity()).getSchoolClassByUUID(UUID.fromString(args.getString(UUID_KEY)));

        if(schoolClass==null){
            Log.e(TAG,"NULL school class upon factory retrieval. Dismissing...");
            dismiss();
        }

        if(getArguments().getInt(INT_CODE_KEY) == ASSIGNMENT){
            assignment = schoolClass.getAssignmentFromName(getArguments().getString(ASSIGNMENT_NAME_KEY));
            if(assignment==null){
                Log.e(TAG,"Error, Could not retrieve the assignment. Dismissing...");
                dismiss();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_delete_or_rename, container, false);

        TextView textView = (TextView)v.findViewById(R.id.text_view_delete_or_rename);
        textView.setText(getTextForTextView());

        Button cancel = (Button)v.findViewById(R.id.button_cancel_class_edit);
        Button delete = (Button)v.findViewById(R.id.button_delete_class_edit);
        Button rename = (Button)v.findViewById(R.id.button_rename_class_edit);

        cancel.setOnClickListener(this);
        delete.setOnClickListener(this);
        rename.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_cancel_class_edit:
                dismiss();
                break;
            case R.id.button_delete_class_edit:
                dismiss();
                String classOrAssignment = getArguments().getInt(INT_CODE_KEY) == SCHOOL_CLASS ? "class "+schoolClass.getClassName()+" and ALL of it's assignments?" : "assignment "+assignment.getAssignmentName()+"?";
                new AlertDialog.Builder(getActivity())
                        .setTitle("Are you sure you want to delete this class?")
                        .setMessage("Are you sure you want to permanently remove "+classOrAssignment)
                        .setNegativeButton("No!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                remove();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.button_rename_class_edit:
                rename();
                break;
        }
    }

    private String getTextForTextView(){
        String basicText = "What do you want to do with ";
        switch (getArguments().getInt(INT_CODE_KEY)){
            case SCHOOL_CLASS:
                basicText+=" class \'"+schoolClass.getClassName()+"\' ?";
                return basicText;
            case ASSIGNMENT:
                basicText+=" assignment \'"+assignment.getAssignmentName()+"\' ?";
                return basicText;
            default:
                return "";
        }
    }

    private void remove(){
        switch (getArguments().getInt(INT_CODE_KEY)){
            case SCHOOL_CLASS:
                SchoolClassFactory.getFactory(getActivity()).removeSchoolClass(schoolClass);
                sendResult(REQUEST_DELETE_OR_RENAME);
                break;
            case ASSIGNMENT:
                schoolClass.removeAssignment(assignment);
                sendResult(REQUEST_DELETE_OR_RENAME);
                break;
        }
    }

    private void rename(){
        switch (getArguments().getInt(INT_CODE_KEY)){
            case SCHOOL_CLASS:
                dismiss();
                RenameDialogFragment dFrag = RenameDialogFragment.newInstance(this);
                dFrag.setTargetFragment(getTargetFragment(),REQUEST_DELETE_OR_RENAME);
                dFrag.show(getActivity().getSupportFragmentManager(),TAG);
                break;
            case ASSIGNMENT:
                dismiss();
                RenameDialogFragment dFrag2 = RenameDialogFragment.newInstance(this);
                dFrag2.setTargetFragment(getTargetFragment(),REQUEST_DELETE_OR_RENAME);
                dFrag2.show(getActivity().getSupportFragmentManager(),TAG);
                break;
        }
    }

    protected void sendResult(final int REQUEST_CODE){
        getTargetFragment().onActivityResult(REQUEST_CODE,Activity.RESULT_OK,null);
    }
}
