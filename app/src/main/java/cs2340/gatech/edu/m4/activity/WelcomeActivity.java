package cs2340.gatech.edu.m4.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataDatabaseHelper;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

public class WelcomeActivity extends AppCompatActivity {

    private DataDatabaseHelper dataDatabaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        dataDatabaseHelper = new DataDatabaseHelper(this, "Data.db", null, 1);
        db = dataDatabaseHelper.getWritableDatabase();


        Button loginButton2 = (Button) findViewById(R.id.login_button2);
        loginButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataDatabaseHelper.loadId(db);
                readSDFile();
                DataDatabaseHelper.readDatabase(db);
                Intent loginIntent = new Intent (WelcomeActivity.this, LoginActivity.class);
                WelcomeActivity.this.startActivity(loginIntent);
                finish();
            }
        });

        Button registerButton2 = (Button) findViewById(R.id.register_button2);
        registerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent (WelcomeActivity.this, RegisterActivity.class);
                WelcomeActivity.this.startActivity(registerIntent);
                finish();
            }
        });

        Button ExitButton = (Button) findViewById((R.id.exit_button));
        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(WelcomeActivity.this);
                alertbuilder.setMessage("Do you want to exit?");
                alertbuilder.setCancelable(true);
                alertbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = alertbuilder.create();
                alert.show();
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
            while ((line = br.readLine()) != null && i < 10 ) {
                i++;
                Log.d(MainActivity.TAG, line);
                String[] tokens = line.split(",");
                if (tokens.length != 51) {
                    continue;
                }
                int id = Integer.parseInt(tokens[0]);
                if (!model.containsId(id)) {
                    DataItem item = new DataItem(id, tokens[1], tokens[7], Integer.valueOf(tokens[8]), tokens[9], tokens[16], tokens[23], Float.valueOf(tokens[49]), Float.valueOf(tokens[50]));
                    DataDatabaseHelper.writeIntoDatabase(db, item);
                    model.addId(id);
                }
            }
            br.close();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "error reading assets", e);
        }
    }
}
