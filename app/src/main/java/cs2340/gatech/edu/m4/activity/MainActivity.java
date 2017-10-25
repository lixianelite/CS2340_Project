package cs2340.gatech.edu.m4.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.model.DataDatabaseHelper;
import cs2340.gatech.edu.m4.model.DataItem;
import cs2340.gatech.edu.m4.model.DataItemAdapter;
import cs2340.gatech.edu.m4.model.SimpleModel;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MY_APP";
    private DataDatabaseHelper dataDatabaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataDatabaseHelper = new DataDatabaseHelper(this, "Data.db", null, 1);
        db = dataDatabaseHelper.getWritableDatabase();
        readDatabase(db);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        DataItemAdapter adapter = new DataItemAdapter(SimpleModel.INSTANCE.getItems());
        recyclerView.setAdapter(adapter);




        /*Button logoutButton = (Button) findViewById(R.id.logout_button);
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
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.report_button:
                Intent ReportIntent = new Intent(MainActivity.this, ReportActivity.class);
                MainActivity.this.startActivity(ReportIntent);
                break;
            case R.id.loaddata_button:
                //readSDFile();
                //Intent DataIntent = new Intent(MainActivity.this, DataListActivity.class);
                //MainActivity.this.startActivity(DataIntent);
                break;
            case R.id.logout_button:
                Intent logoutIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                MainActivity.this.startActivity(logoutIntent);
                finish();
                break;
            default:
        }
        return true;
    }

    private void readDatabase(SQLiteDatabase db){
        SimpleModel model = SimpleModel.INSTANCE;

        Cursor cursor = db.query("data", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String location_type = cursor.getString(cursor.getColumnIndex("location_type"));
                int zip = cursor.getInt(cursor.getColumnIndex("zip"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String city = cursor.getString(cursor.getColumnIndex("city"));
                String borough = cursor.getString(cursor.getColumnIndex("borough"));
                float latitude = cursor.getFloat(cursor.getColumnIndex("latitude"));
                float longitude = cursor.getFloat(cursor.getColumnIndex("longitude"));
                DataItem item = new DataItem(id, date, location_type, zip, address, city, borough, latitude, longitude);
                model.addItem(item);
            }while (cursor.moveToNext());
            cursor.close();
        }

    }

}