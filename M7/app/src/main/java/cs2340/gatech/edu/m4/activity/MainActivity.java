package cs2340.gatech.edu.m4.activity;

import android.content.Intent;
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
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.SimpleModel;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MY_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                MainActivity.this.startActivity(logoutIntent);
                finish();
            }
        });

        Button loaddataButton = (Button) findViewById(R.id.loaddata_button);
        loaddataButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                readSDFile();
                Intent DataIntent = new Intent(MainActivity.this, DataListActivity.class);
                MainActivity.this.startActivity(DataIntent);
            }
        });

        Button reportButton = (Button) findViewById(R.id.report_button);
        reportButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent ReportIntent = new Intent(MainActivity.this, ReportActivity.class);
                MainActivity.this.startActivity(ReportIntent);
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
            Log.d(MainActivity.TAG, i + "");
            while ((line = br.readLine()) != null && i < 21 ) {

                i++;
                Log.d(MainActivity.TAG, line);
                String[] tokens = line.split(",");
                if (tokens.length != 51) {
                    continue;


                }

                String id = tokens[0];
                Log.d("MainActivity Debug", tokens[1]);
                Log.d("MainActivity Debug", tokens[7]);
                Log.d("MainActivity Debug", tokens[8]);
                Log.d("MainActivity Debug", tokens[9]);
                Log.d("MainActivity Debug", tokens[16]);
                Log.d("MainActivity Debug", tokens[23]);
                Log.d("MainActivity Debug", String.valueOf(tokens[49]));
                Log.d("MainActivity Debug", String.valueOf(tokens[50]));
                Log.d("MainActivity Debug", i + "");
                model.addItem(new DataItem(id, tokens[1], tokens[7], tokens[8], tokens[9], tokens[16], tokens[23], Float.valueOf(tokens[49]), Float.valueOf(tokens[50])));
            }
            br.close();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "error reading assets", e);


        }
    }

}