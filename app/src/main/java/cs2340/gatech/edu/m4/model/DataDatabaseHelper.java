package cs2340.gatech.edu.m4.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
        Toast.makeText(mContext, "Create succeeded!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
