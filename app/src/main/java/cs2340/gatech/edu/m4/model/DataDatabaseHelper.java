package cs2340.gatech.edu.m4.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cs2340.gatech.edu.m4.R;
import cs2340.gatech.edu.m4.activity.MainActivity;

/**
 * Created by bravado on 10/24/17.
 */

public class DataDatabaseHelper extends SQLiteOpenHelper{

    public static final String CREATE_DATA = "create table data ("
            + "id integer primary key, "
            + "date text not null, "
            + "location_type text, "
            + "zip integer, "
            + "address text, "
            + "city text, "
            + "borough text, "
            + "latitude real, "
            + "longitude real)";

    private Context mContext;

    public DataDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void writeIntoDatabase(SQLiteDatabase db, DataItem item){
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
    }

    public static void readDatabase(SQLiteDatabase db){
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

    public static void SynchronizeData(SQLiteDatabase db){
        SimpleModel model = SimpleModel.INSTANCE;
        Cursor cursor = db.query("data", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                if (model.containsId(id)) continue;
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
                model.addId(id);
            }while (cursor.moveToNext());
            cursor.close();
        }
    }


    public static void loadId(SQLiteDatabase db){
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

}
