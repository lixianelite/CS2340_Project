package cs2340.gatech.edu.m4.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tianyunan on 9/29/17.
 */

public class UserDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERTYPE = "usertype";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "CREATE TABLE users" + "(" + "id" + " INTEGER PRIMARY KEY NOT NULL,"
            + "username TEXT NOT NULL," + "password TEXT NOT NULL," + "email TEXT NOT NULL," + "usertype TEXT NOT NULL" + ");";


    public UserDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public boolean CheckIfDataExists( String uname) {
        db = this.getReadableDatabase();
        String query = "SELECT * FROM" + " " + TABLE_NAME + " WHERE  username  = '" + uname + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public long insertUser(User u){

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        String query = "SELECT * FROM" + " " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID,count);
        values.put(COLUMN_USERNAME, u.getUsername());
        values.put(COLUMN_EMAIL, u.getEmail());
        values.put(COLUMN_PASSWORD, u.getPassword());
        values.put(COLUMN_USERTYPE, u.getUsertype());

        long success_insert = db.insert(TABLE_NAME, null, values);

        db.close();
        return success_insert;

    }

    public String searchPassword(String uname){
        db = this.getReadableDatabase();
        String query = "SELECT username, password FROM" + " " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String un, pw;
        pw = "not found";
        if(cursor.moveToFirst()){
            do {
                un = cursor.getString(0);
                if(un.equals(uname)){
                    pw = cursor.getString(1);
                    break;
                }

            }
            while(cursor.moveToNext());
        }
        return pw;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS" + " " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

}
