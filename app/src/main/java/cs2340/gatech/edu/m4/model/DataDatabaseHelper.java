package cs2340.gatech.edu.m4.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;


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

    public static void readDatabase(SQLiteDatabase db, String startDate, String endDate){
        SimpleModel model = SimpleModel.INSTANCE;
        String formatStartDate = dateTransform(startDate);
        String formatEndDate = dateTransform(endDate);
        Cursor cursor = db.query("data", null, "date >= ? and date <= ?", new String[]{formatStartDate, formatEndDate}, null, null, null);
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

    public static DataItem QueryData(SQLiteDatabase db, int DataId){
        Cursor cursor = db.query("data", null, "id = ?", new String[]{String.valueOf(DataId)}, null, null, null);
        DataItem item = new DataItem();
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
                item = new DataItem(id, date, location_type, zip, address, city, borough, latitude, longitude);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return item;
    }

    public static void loadId(SQLiteDatabase db){
        SimpleModel model = SimpleModel.INSTANCE;
        Cursor cursor = db.query("data", new String[]{"id"}, "id >= ?", new String[]{"50000000"}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                Log.d("LoginActivity_id", id + "");
                model.addId(id);
            }while (cursor.moveToNext());
            cursor.close();
        }
    }

    public static void FilterData(SQLiteDatabase db, String startDate, String endDate, List<DataItem> list){
        list.clear();
        startDate = dateTransform(startDate);
        endDate = dateTransform(endDate);
        Cursor cursor = db.query("data", null, "date >= ? and date <= ?", new String[]{startDate, endDate}, null, null, null);
        Log.d("DataBasehelper", "startDate: " + startDate + " endData:" + endDate);
        if (cursor.moveToFirst()){
            do {
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
                list.add(item);
            }while (cursor.moveToNext());
            cursor.close();
        }
    }

    public static String dateTransform(String rawDate){
        String[] date = rawDate.split("-");
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        if (month < 10) date[1] = "0" + date[1];
        if (day < 10) date[2] = "0" + date[2];
        return date[0] + "-" + date[1] + "-" + date[2];
    }

    public static boolean isDbPresent(){
        boolean checkFlag = true;
        SQLiteDatabase testDb;
        String testPath = "data/data/cs2340.gatech.edu.m4/databases/Data.db";
        try {
            testDb = SQLiteDatabase.openDatabase(testPath, null, SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLiteException sqlException){
            checkFlag = false;
        }
        return checkFlag;
    }

}
