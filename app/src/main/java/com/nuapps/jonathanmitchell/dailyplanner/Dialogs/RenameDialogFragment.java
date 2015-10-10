package com.nuapps.jonathanmitchell.dailyplanner.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClassFactory;
import com.nuapps.jonathanmitchell.dailyplanner.R;

/**
 * Created by jmitch on 10/10/2015.
 */
public class RenameDialogFragment extends DeleteOrRenameDialogFragment implements TextWatcher,View.OnClickListener{

    private static final String XTRA_EDIT_TEXT_TYPED = "EXTRA_EDIT_TEXT_TYPED";

    private TextView renameTextView;
    private EditText renameEditText;


    public static RenameDialogFragment newInstance(DeleteOrRenameDialogFragment deleteOrRenameDialogFragment){
        RenameDialogFragment renameDialogFragment = new RenameDialogFragment();
        renameDialogFragment.schoolClass=deleteOrRenameDialogFragment.schoolClass;
        renameDialogFragment.assignment=deleteOrRenameDialogFragment.assignment;
        renameDialogFragment.setArguments(deleteOrRenameDialogFragment.getArguments());

        return renameDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_rename,container,false);


        renameTextView = (TextView)v.findViewById(R.id.text_view_dialog_new_name);
        renameEditText = (EditText)v.findViewById(R.id.edit_text_dialog_new_name);
        renameEditText.addTextChangedListener(this);

        switch (super.getArguments().getInt(INT_CODE_KEY)) {
            case SCHOOL_CLASS:
                renameEditText.setText(savedInstanceState==null ? schoolClass.getClassName() : savedInstanceState.getString(XTRA_EDIT_TEXT_TYPED));
                break;
            case ASSIGNMENT:
                renameEditText.setText(savedInstanceState==null ? assignment.getAssignmentName() : savedInstanceState.getString(XTRA_EDIT_TEXT_TYPED));
                break;
        }


        Button cancel = (Button)v.findViewById(R.id.button_cancel_rename);
        Button confirm = (Button)v.findViewById(R.id.button_confirm_rename);

        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);


        return v;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.toString()==null || charSequence.toString().isEmpty()){
            renameTextView.setText(getTextViewText("\'?\'"));
        } else {
            renameTextView.setText(getTextViewText(charSequence.toString()));
        }

    }

    private String getTextViewText(String newName){
        switch (super.getArguments().getInt(INT_CODE_KEY)){
            case SCHOOL_CLASS:
                return "Class \'"+schoolClass.getClassName()+"\' will be renamed to: \'"+newName+"\'.";
            case ASSIGNMENT:
                return "Assignment \'"+assignment.getAssignmentName()+"\' will be renamed to: \'"+newName+"\'.";
            default:
                return "";
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button_cancel_rename:
                dismiss();
                break;
            case R.id.button_confirm_rename:
                if(renameEditText.getText().toString()==null || renameEditText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Cannot give empty name!",Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (super.getArguments().getInt(super.INT_CODE_KEY)){
                    case SCHOOL_CLASS:
                        if(SchoolClassFactory.getFactory(getActivity()).hasClassName(renameEditText.getText().toString())
                                && !renameEditText.getText().toString().equalsIgnoreCase(schoolClass.getClassName())
                                ){
                            Toast.makeText(getActivity(),"Class name \'"+renameEditText.getText().toString()
                                    +"\' already exists. Choose another name.",Toast.LENGTH_LONG).show();
                            return;
                        }
                        dismiss();
                        schoolClass.setNewName(renameEditText.getText().toString());
                        sendResult(REQUEST_DELETE_OR_RENAME);
                        break;
                    case ASSIGNMENT:
                        if(schoolClass.assignmentExists(renameEditText.getText().toString())
                                && !renameEditText.getText().toString().equalsIgnoreCase(assignment.getAssignmentName())
                                ){
                            Toast.makeText(getActivity(),"Assignment name \'"+renameEditText.getText().toString()
                                    +"\' already exists for class \'"+schoolClass.getClassName()+"\'. Choose another name.",Toast.LENGTH_LONG).show();
                            return;
                        }
                        dismiss();
                        assignment.setNewName(renameEditText.getText().toString());
                        sendResult(REQUEST_DELETE_OR_RENAME);
                        break;

                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(XTRA_EDIT_TEXT_TYPED,renameEditText.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {}
}
