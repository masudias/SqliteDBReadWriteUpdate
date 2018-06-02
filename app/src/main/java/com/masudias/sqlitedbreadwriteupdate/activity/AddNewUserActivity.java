package com.masudias.sqlitedbreadwriteupdate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.masudias.sqlitedbreadwriteupdate.R;
import com.masudias.sqlitedbreadwriteupdate.database.DataHelper;
import com.masudias.sqlitedbreadwriteupdate.domain.User;

public class AddNewUserActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private Button addNewUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        userNameEditText = (EditText) findViewById(R.id.user_name_edit_text);
        addNewUserButton = (Button) findViewById(R.id.add_new_button);
        setButtonAction();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setButtonAction() {
        addNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameEditText.getText().toString();
                if (userName != null && !userName.isEmpty()) {
                    try {
                        User user = new User(userName, System.currentTimeMillis());
                        DataHelper.getInstance(AddNewUserActivity.this).insertUser(user);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddNewUserActivity.this, R.string.add_new_failed, Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(AddNewUserActivity.this, R.string.please_enter_name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
