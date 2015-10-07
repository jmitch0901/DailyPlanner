package com.nuapps.jonathanmitchell.dailyplanner.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClass;
import com.nuapps.jonathanmitchell.dailyplanner.Data.SchoolClassFactory;
import com.nuapps.jonathanmitchell.dailyplanner.R;

import java.util.UUID;

/**
 * Created by jmitch on 10/7/2015.
 */
public class AddAssignmentDialogFragment extends DialogFragment {

    private static final String TAG = "ADD_ASSGN_D_FRAG";

    private static final String XTRA_UUID="XTRA_UUID";

    private SchoolClass schoolClass;

    public static AddAssignmentDialogFragment newInstance(UUID uuid){
        AddAssignmentDialogFragment dFrag = new AddAssignmentDialogFragment();
        Bundle args = new Bundle();
        args.putString(XTRA_UUID,uuid.toString());
        dFrag.setArguments(args);
        return dFrag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_add_assignment,container,false);

        UUID uuid=UUID.fromString(getArguments().getString(XTRA_UUID));
        schoolClass= SchoolClassFactory.getFactory(getActivity()).getSchoolClassByUUID(uuid);


        return v;
    }
}
