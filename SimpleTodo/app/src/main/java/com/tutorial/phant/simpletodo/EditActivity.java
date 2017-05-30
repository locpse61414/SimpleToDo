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

import butterknife.BindView;
import butterknife.ButterKnife;
import model.User;

public class EditActivity extends AppCompatActivity {
    @BindView(R.id.toolbarEdit)
    Toolbar toolbar;
    @BindView(R.id.etTaskNameEdit)
    EditText mTaskName;
    @BindView(R.id.etTaskNoteEdit)
    EditText mTaskNote;
    @BindView(R.id.spinnerStatusEdit)
    Spinner mSatusSpinner;
    @BindView(R.id.spinnerPriorityEdit)
    Spinner mPrioritySpinner;
    @BindView(R.id.datePickerEdit)
    DatePicker mDatePicker;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        mUser = getIntent().getExtras().getParcelable("todo");
        setUpViews();
    }

    private void setUpViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_todo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpinner.setAdapter(priorityAdapter);
        if (mUser.getPriority().equalsIgnoreCase("high")) {
            mPrioritySpinner.setSelection(0);
        } else if (mUser.getPriority().equalsIgnoreCase("medium")) {
            mPrioritySpinner.setSelection(1);
        } else {
            mPrioritySpinner.setSelection(2);
        }

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSatusSpinner.setAdapter(statusAdapter);
        if (mUser.getStatus().equalsIgnoreCase("DONE")) {
            mSatusSpinner.setSelection(0);
        } else {
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
