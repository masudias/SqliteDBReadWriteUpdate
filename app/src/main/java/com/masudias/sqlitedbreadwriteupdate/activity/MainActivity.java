package com.masudias.sqlitedbreadwriteupdate.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.masudias.sqlitedbreadwriteupdate.R;
import com.masudias.sqlitedbreadwriteupdate.adapter.DeleteUserActionListener;
import com.masudias.sqlitedbreadwriteupdate.adapter.UpdateUserActionListener;
import com.masudias.sqlitedbreadwriteupdate.adapter.UserListAdapter;
import com.masudias.sqlitedbreadwriteupdate.database.DBConstants;
import com.masudias.sqlitedbreadwriteupdate.database.DataHelper;
import com.masudias.sqlitedbreadwriteupdate.database.SQLiteCursorLoader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, UpdateUserActionListener, DeleteUserActionListener {

    private static final int GET_USERS_QUERY_LOADER = 0;

    private TextView emptyTextView;
    private RecyclerView userRecyclerView;
    private UserListAdapter userListAdapter;
    private LinearLayoutManager mLayoutManager;
    private FloatingActionButton addNewUserFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyTextView = (TextView) findViewById(R.id.user_list_empty);
        userRecyclerView = (RecyclerView) findViewById(R.id.user_list);
        addNewUserFab = (FloatingActionButton) findViewById(R.id.add_new_user_fab);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        userRecyclerView.setLayoutManager(mLayoutManager);

        setButtonAction();
        getSupportLoaderManager().initLoader(GET_USERS_QUERY_LOADER, null, this).forceLoad();
    }

    private void setButtonAction() {
        addNewUserFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewUserIntent = new Intent(MainActivity.this, AddNewUserActivity.class);
                startActivity(addNewUserIntent);
            }
        });
    }

    private void setupRecyclerViewWithAdapter(Cursor data) {
        userListAdapter = new UserListAdapter(data);
        userListAdapter.updateUserListener = this;
        userListAdapter.deleteUserListener = this;
        userRecyclerView.setAdapter(userListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(GET_USERS_QUERY_LOADER, null, MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(GET_USERS_QUERY_LOADER);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new SQLiteCursorLoader(this) {
            @Override
            public Cursor loadInBackground() {
                DataHelper dataHelper = DataHelper.getInstance(MainActivity.this);
                Cursor cursor;
                cursor = dataHelper.getAllUsers();

                if (cursor != null)
                    this.registerContentObserver(cursor, DBConstants.DB_TABLE_USER_URI);

                return cursor;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.getCount() > 0)
            emptyTextView.setVisibility(View.GONE);
        else emptyTextView.setVisibility(View.VISIBLE);

        setupRecyclerViewWithAdapter(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onDeleteUserActionReceived(int userId) {
        DataHelper.getInstance(MainActivity.this).deleteUser(userId);
    }

    @Override
    public void onUpdateUserActionReceived(int userId) {
        Intent updateUserIntent = new Intent(MainActivity.this, UpdateUserActivity.class);
        updateUserIntent.putExtra(UpdateUserActivity.USER_ID, userId);
        startActivity(updateUserIntent);
    }
}