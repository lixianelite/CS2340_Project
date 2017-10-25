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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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


    private DataDatabaseHelper dataDatabaseHelper;
    private SQLiteDatabase db;
    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;
    //private View mProgressView;
    //private View mLoginFormView;

    UserDatabaseHelper helper = new UserDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.username);
        dataDatabaseHelper = new DataDatabaseHelper(this, "Data.db", null, 1);

        db = dataDatabaseHelper.getWritableDatabase();

        loadId(db);

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
                    readSDFile();
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

    private void readSDFile() {
        SimpleModel model = SimpleModel.INSTANCE;

        try {
            InputStream is = getResources().openRawResource(R.raw.rat_sightings);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            br.readLine(); //get rid of header line
            int i = 1;
            //Log.d(MainActivity.TAG, i + "");
            while ((line = br.readLine()) != null && i < 21 ) {
                i++;
                Log.d(MainActivity.TAG, line);
                String[] tokens = line.split(",");
                if (tokens.length != 51) {
                    continue;
                }
                int id = Integer.parseInt(tokens[0]);
                if (!model.containsId(id)) {
                    DataItem item = new DataItem(id, tokens[1], tokens[7], Integer.valueOf(tokens[8]), tokens[9], tokens[16], tokens[23], Float.valueOf(tokens[49]), Float.valueOf(tokens[50]));
                    DataDatabaseHelper.readIntoDatabase(db, item);
                }
            }
            br.close();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "error reading assets", e);
        }
    }


    /*private void readIntoDatabase(SQLiteDatabase db, DataItem item){
        ContentValues values = new ContentValues();
        values.put("id", item.getId());
        values.put("date", item.getCreatedDate());
        values.put("location_type", item.getLocationType());
        values.put("zip", item.getZip());
        values.put("address", item.getAddress());
        values.put("city", item.getCity());
        values.put("borough", item.getBorough());
        values.put("latitude", item.getLatitude());
        values.put("longitude", item.getLongitude());
        long judge = db.insert("data", null, values);
        if (judge == -1){
            Log.d("LoginActivity", "insert error!");
        }
    }*/

    private void loadId(SQLiteDatabase db){
        SimpleModel model = SimpleModel.INSTANCE;
        Cursor cursor = db.query("data", new String[]{"id"}, null, null, null, null, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Log.d("LoginActivity_id", id + "");
                model.addId(id);
            }while (cursor.moveToNext());
            cursor.close();
        }
    }

    /*
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mUserView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }*/

    /**
     * Callback received when a permissions request has been completed.

    @Override*/
    /*
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }
    */

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    /*
    private void attemptLogin() {

        // Reset errors.


        // Store values at the time of the login attempt.
        String username = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        //View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            //focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUserView.setError(getString(R.string.error_field_required));
            //focusView = mUserView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }*/

    /*private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }*/

    /**
     * Shows the progress UI and hides the login form.
     */
    /*
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> usernames = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            usernames.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addUsernamesToAutoComplete(usernames);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addUsernamesToAutoComplete(List<String> usernameAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, usernameAddressCollection);

        mUserView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
    */
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    /*
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mUsername)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            //mAuthTask = null;
            //showProgress(false);

            if (success) {
                finish();
                Intent loginIntent = new Intent (LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(loginIntent);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }*/
}

