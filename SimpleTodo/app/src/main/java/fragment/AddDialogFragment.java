package fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.tutorial.phant.simpletodo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.User;

/**
 * Created by phant on 24-May-17.
 */

public class AddDialogFragment extends DialogFragment {

    @BindView(R.id.etTaskName)
    EditText mTaskName;
    @BindView(R.id.etTaskNote)
    EditText mTaskNote;
    @BindView(R.id.spinnerStatus)
    Spinner mSatusSpinner;
    @BindView(R.id.spinnerPriority)
    Spinner mPrioritySpinner;
    @BindView(R.id.datePicker)
    DatePicker mDatePicker;
    private User mUser;

    public AddDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddDialogFragment newInstance(String title) {

        Bundle args = new Bundle();

        AddDialogFragment fragment = new AddDialogFragment();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public interface AddDialogListener {
        void onFinishAddDialog(User user);
    }

    private AddDialogListener listener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_name, container);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater i = getActivity().getLayoutInflater();

        View view = i.inflate(R.layout.fragment_edit_name, null);
        ButterKnife.bind(this, view);
        setUpViews();

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setTitle("Simple To Do")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // do something...

                                //get data input from field
                                String taskName = mTaskName.getText().toString();
                                String taskNote = mTaskNote.getText().toString();

                                String day = String.valueOf(mDatePicker.getDayOfMonth());
                                String month = String.valueOf(mDatePicker.getMonth() + 1);
                                String year = String.valueOf(mDatePicker.getYear());

                                String date = day + " " + month + " " + year;
                                String status = mSatusSpinner.getSelectedItem().toString();
                                String priority = mPrioritySpinner.getSelectedItem().toString();

                                if (mTaskName.length() > 0) {
                                    User user = new User(taskName, taskNote, priority, status, date);
                                    listener = (AddDialogListener) getActivity();
                                    listener.onFinishAddDialog(user);
                                    dismiss();
                                }
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );
        b.setView(view);
        return b.create();
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        // Get field from view
//        mTaskName = (EditText) view.findViewById(R.id.etTaskName);
//        mTaskNote = (EditText) view.findViewById(R.id.etTaskNote);
//        mSatusSpinner = (Spinner) view.findViewById(R.id.spinnerStatus);
//        mPrioritySpinner = (Spinner) view.findViewById(R.id.spinnerPriority);
//        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
//
//        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.priority_array, android.R.layout.simple_spinner_item);
//        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mPrioritySpinner.setAdapter(priorityAdapter);
//
//        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.status_array, android.R.layout.simple_spinner_item);
//        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSatusSpinner.setAdapter(statusAdapter);
//
//
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//
//    }

    private void setUpViews() {
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpinner.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSatusSpinner.setAdapter(statusAdapter);
    }

//    @Override
//    public void onResume() {
//        // Get existing layout params for the window
//        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
//        // Assign window properties to fill the parent
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.MATCH_PARENT;
//        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//        // Call super onResume after sizing
//
//        super.onResume();
//    }
}
