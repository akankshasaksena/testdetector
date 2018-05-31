package com.example.akankshasaksena.spydetector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelpder extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "SpyDetector.db";
    public static final String TABLE_NAME = "spyhistory";
    public static final String COLUMN_TEXT = "message";
    public static final String COLUMN_INCIDENTDATE = "incident_date";
    private HashMap hp;

    public DBHelpder(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + TABLE_NAME +
                        "(id integer primary key autoincrement, message text, incident_date date)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS spyhistory");
        onCreate(db);
    }

    public boolean insertSpyHistory (String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEXT, text);
        contentValues.put(COLUMN_INCIDENTDATE, dateFormat.format(date));
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<String> getAllSpyHistories() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" order by "+COLUMN_INCIDENTDATE, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_TEXT)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }
}
