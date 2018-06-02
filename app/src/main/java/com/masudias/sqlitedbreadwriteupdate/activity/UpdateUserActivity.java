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

public class UpdateUserActivity extends AppCompatActivity {

    public static final String USER_ID = "user_id";

    private EditText userNameEditText;
    private Button updateUserButton;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        userNameEditText = (EditText) findViewById(R.id.user_name_edit_text);
        updateUserButton = (Button) findViewById(R.id.update_button);
        userId = getIntent().getIntExtra(USER_ID, -1);

        setButtonAction();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setButtonAction() {
        updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameEditText.getText().toString();
                if (userName != null && !userName.isEmpty()) {
                    try {
                        DataHelper.getInstance(UpdateUserActivity.this).updateUserName(userId, userName);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(UpdateUserActivity.this, R.string.failed_user_update, Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(UpdateUserActivity.this, R.string.please_enter_name, Toast.LENGTH_SHORT).show();
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
