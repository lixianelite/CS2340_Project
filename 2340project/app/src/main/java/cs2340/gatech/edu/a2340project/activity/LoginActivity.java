package cs2340.gatech.edu.a2340project.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cs2340.gatech.edu.a2340project.R;
import cs2340.gatech.edu.a2340project.model.DatabaseHelper;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "123@gmail.com:123456", "bar@example.com:world", "user:password"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.username);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mUsernameSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mUsernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUserView.getText().toString();
                String password = mPasswordView.getText().toString();
                int count = 0;

                String searchpassword = helper.searchPassword(username);

                if (TextUtils.isEmpty(username)) {
                    mUserView.setError(getString(R.string.error_field_required));
                }
                //if (!TextUtils.isEmpty(username)&&password.length() < 4){
                //mPasswordView.setError(getString(R.string.error_invalid_password));
                //}

                if (searchpassword.equals(password)) {
                    finish();
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(loginIntent);
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                }
            }
        });

        Button CancelButton = (Button) findViewById((R.id.cancel_button));
        CancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
                LoginActivity.this.startActivity(cancelIntent);
                finish();
            }

        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
}