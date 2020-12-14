package com.example.mhealthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// SQLite Database Code
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG="DatabaseHelper";
    private static final String TABLE_NAME="medicine_name";

    private static final String COL1="ID";
    private static final String COL2="name";
    private static final String COL3="dose";
    private static final String COL4="food";
    private static final String COL5="time1";
    private static final String COL6="time2";
    private static final String COL7="time3";

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 10685);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name  TEXT, dose TEXT, food TEXT, time1 TEXT, time2 TEXT, time3 TEXT)";
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE iF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String item1,String item2,String item3,String item4,String item5 , String item6){
        long res = 0 ;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,item1);
        contentValues.put(COL3,item2);
        contentValues.put(COL4,item3);
        contentValues.put(COL5,item4);
        contentValues.put(COL6,item5);
        contentValues.put(COL7,item6);
        res = db.insert(TABLE_NAME , null ,contentValues);

        if(res==-1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;

    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }
}
