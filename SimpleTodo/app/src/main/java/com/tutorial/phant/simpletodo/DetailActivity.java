package com.tutorial.phant.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.User;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbarDetail)
    Toolbar toolbar;
    @BindView(R.id.tvTaskDetail)
    TextView mTaskname;
    @BindView(R.id.tvNoteDetail)
    TextView mTaskNote;
    @BindView(R.id.tvStatusDetail)
    TextView mSatus;
    @BindView(R.id.tvPriorityDetail)
    TextView mPriority;
    @BindView(R.id.tvDateDetail)
    TextView mDatePicker;
    private User mUser;
    private ArrayList mUsers;
    private int mPosition = -1;
    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mUsers = (ArrayList) getIntent().getParcelableArrayListExtra("list");
        mPosition = (int) getIntent().getSerializableExtra("position");

        setUpViews(mUsers, mPosition);

    }

    private void setUpViews(ArrayList<User> users, int position) {
        mUser = users.get(position);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_todo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mTaskname.setText(mUser.getTaskname());
        mTaskNote.setText(mUser.getTaskNote());
        mSatus.setText(mUser.getStatus());
        mPriority.setText(mUser.getPriority());
        mDatePicker.setText(mUser.getDueDate());
    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        return super.getSupportParentActivityIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemDelete:
                removeItem();
                return true;
            case R.id.itemEdit:
                editItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            User user = data.getExtras().getParcelable("user");
            //update object in array
            mUsers.set(mPosition, user);

            Intent data2 = new Intent();
            // Pass relevant data back as a result
            data2.putExtra("itemEdited", user);
            data2.putExtra("itemEditedPosition", mPosition);
            // Activity finished ok, return the data
            setResult(RESULT_OK, data2); // set result code and bundle data for response
            finish();
            startActivity(getIntent());
            // Toast the name to display temporarily on screen
        }
    }

    private void editItem() {
        Intent i = new Intent(DetailActivity.this, EditActivity.class);
        i.putExtra("todo", (Parcelable) mUsers.get(mPosition)); // pass arbitrary data to launched activity
        startActivityForResult(i, REQUEST_CODE);
    }

    private void removeItem() {
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("positionRemoved", mPosition);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        this.finish();
    }
}
