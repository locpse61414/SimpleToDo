package com.tutorial.phant.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import adapter.UserAdapter;
import fragment.AddDialogFragment;
import model.User;

public class MainActivity extends AppCompatActivity implements AddDialogFragment.AddDialogListener {

    private ArrayList<User> users;
    private RecyclerView rvUsers;
    private UserAdapter userAdapter;
    private String value;
    private Toolbar toolbar;
    private final int REQUEST_CODE = 20;
    private long idEditItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users = new ArrayList<>();
        loadDataFromDB();
        setUpViews();

        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(View itemView, int position) {
                idEditItem = users.get(position).getId();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putParcelableArrayListExtra("list", users);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        userAdapter.notifyDataSetChanged();
    }

    private void loadDataFromDB() {
        users = (ArrayList<User>) User.listAll(User.class);
    }


    //setUpViews
    private void setUpViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_todo);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        rvUsers = (RecyclerView) findViewById(R.id.rvUsers);


        userAdapter = new UserAdapter(users);
        rvUsers.setAdapter(userAdapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
    }


    //Show Dialog Fragment
    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddDialogFragment editNameDialogFragment = AddDialogFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd:
                showEditDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            int postion = data.getExtras().getInt("positionRemoved", -1);
            if (postion != -1) {
                User user = users.get(postion);
                user.delete();
                users.remove(postion);
            }
            int postion2 = data.getExtras().getInt("itemEditedPosition", -1);
            if (postion2 != -1) {

                User user = data.getExtras().getParcelable("itemEdited");
                User newUser = User.findById(User.class, idEditItem);
                newUser.setTaskname(user.getTaskname());
                newUser.setStatus(user.getStatus());
                newUser.setPriority(user.getPriority());
                newUser.setTaskNote(user.getTaskNote());
                newUser.setDueDate(user.getDueDate());
                newUser.save();

                users.set(postion2, newUser);
            }
        }
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFinishAddDialog(User user) {
        users.add(user);
        user.save();
        userAdapter.notifyDataSetChanged();
    }

}
