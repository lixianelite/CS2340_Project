package cs2340.gatech.edu.m4.activity;

import android.content.Intent;
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

/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText mUserView;
    private EditText mPasswordView;

    private DataDatabaseHelper dataDatabaseHelper;
    private SQLiteDatabase db;


    UserDatabaseHelper helper = new UserDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);

        dataDatabaseHelper = new DataDatabaseHelper(this, "Data.db", null, 1);
        db = dataDatabaseHelper.getWritableDatabase();

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

                    DataDatabaseHelper.loadId(db);
                    readSDFile();
                    DataDatabaseHelper.readDatabase(db);

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
            while ((line = br.readLine()) != null && i < 50000) {
                Log.d("readSDFile", i + "");
                i++;
                String[] tokens = line.split(",");
                if (tokens.length != 51 || tokens[8].equals("")) {
                    continue;
                }
                int id = Integer.parseInt(tokens[0]);
                if (!model.containsId(id)) {
                    String date = transformDate(tokens[1]);
                    DataItem item = new DataItem(id, date, tokens[7], Integer.valueOf(tokens[8]), tokens[9], tokens[16], tokens[23], Float.valueOf(tokens[49]), Float.valueOf(tokens[50]));
                    DataDatabaseHelper.writeIntoDatabase(db, item);
                    model.addId(id);
                }
            }
            br.close();
        } catch (IOException e) {
            Log.e("MainActivity", "error reading assets", e);
        }
    }

    private String transformDate(String rawDate){
        String[] date = rawDate.split("/");
        String[] subDate = date[2].split(" ");
        int month = Integer.parseInt(date[0]);
        int day = Integer.parseInt(date[1]);
        if (day < 10) date[1] = "0" + date[1];
        if (month < 10) date[0] = "0" + date[0];
        return 20 + subDate[0] + "-" + date[0] + "-" + date[1];
    }

}

