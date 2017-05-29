package com.tutorial.phant.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import model.User;

import static com.tutorial.phant.simpletodo.R.id.datePickerEdit;

public class EditActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText mTaskName;
    private EditText mTaskNote;
    private Spinner mSatusSpinner;
    private Spinner mPrioritySpinner;
    private DatePicker mDatePicker;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mUser = getIntent().getExtras().getParcelable("todo");
        setUpViews();
    }

    private void setUpViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarEdit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_todo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mTaskName = (EditText) findViewById(R.id.etTaskNameEdit);
        mTaskNote = (EditText) findViewById(R.id.etTaskNoteEdit);
        mSatusSpinner = (Spinner) findViewById(R.id.spinnerStatusEdit);
        mPrioritySpinner = (Spinner) findViewById(R.id.spinnerPriorityEdit);
        mDatePicker = (DatePicker) findViewById(datePickerEdit);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpinner.setAdapter(priorityAdapter);
        if(mUser.getPriority().equalsIgnoreCase("high")){
            mPrioritySpinner.setSelection(0);
        }else if(mUser.getPriority().equalsIgnoreCase("medium")){
            mPrioritySpinner.setSelection(1);
        }else{
            mPrioritySpinner.setSelection(2);
        }

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSatusSpinner.setAdapter(statusAdapter);
        if(mUser.getStatus().equalsIgnoreCase("DONE")){
            mSatusSpinner.setSelection(0);
        }else{
            mSatusSpinner.setSelection(1);
        }

        mTaskName.setText(mUser.getTaskname());
        mTaskNote.setText(mUser.getTaskNote());

        String[] date = mUser.getDueDate().split(" ");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]) - 1;
        int year = Integer.parseInt(date[2]);

        mDatePicker.updateDate(year, month, day);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSave:
                saveItem();
                return true;
            case R.id.itemClose:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveItem() {
        String taskName = mTaskName.getText().toString();
        String taskNote = mTaskNote.getText().toString();

        String day = String.valueOf(mDatePicker.getDayOfMonth());
        String month = String.valueOf(mDatePicker.getMonth() + 1);
        String year = String.valueOf(mDatePicker.getYear());

        String date = day + " " + month + " " + year;
        String status = mSatusSpinner.getSelectedItem().toString();
        String priority = mPrioritySpinner.getSelectedItem().toString();

        if (mTaskName.length() > 0) {
            mUser.setTaskname(taskName);
            mUser.setTaskNote(taskNote);
            mUser.setDueDate(date);
            mUser.setStatus(status);
            mUser.setPriority(priority);
        }

        Intent data = new Intent();
        data.putExtra("user", mUser);
        setResult(RESULT_OK, data);
        this.finish();
    }
}
