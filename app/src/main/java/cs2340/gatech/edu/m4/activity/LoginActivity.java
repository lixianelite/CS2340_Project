package cs2340.gatech.edu.m4.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataDatabaseHelper;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;
import cs2340.gatech.edu.m4.model.UserDatabaseHelper;
import cs2340.gatech.edu.m4.model.User;
import cs2340.gatech.edu.m4.model.UserDatabaseHelper;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mUserView;
    private EditText mPasswordView;

    UserDatabaseHelper helper = new UserDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mUsernameSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mUsernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUserView.getText().toString();
                String password =  mPasswordView.getText().toString();
                int count = 0;

                String searchpassword = helper.searchPassword(username);

                if (TextUtils.isEmpty(username)) {
                    mUserView.setError(getString(R.string.error_field_required));
                }


                if (searchpassword.equals(password)) {
                    finish();
                    Intent loginIntent = new Intent (LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(loginIntent);
                }
                else{
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                }
            }
        });

        Button CancelButton = (Button) findViewById((R.id.cancel_button));

        CancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent (LoginActivity.this, WelcomeActivity.class);
                LoginActivity.this.startActivity(cancelIntent);
                finish();
            }

        });

    }


}

