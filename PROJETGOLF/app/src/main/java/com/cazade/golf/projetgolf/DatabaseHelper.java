package com.cazade.golf.projetgolf;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MaximeCazade on 24/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    public static final String DATABASE_NAME = "Golf.db";

    public static final String TABLE_NAME_1 = "user";
    public static final String COL_1_1 = "ID";
    public static final String COL_1_2 = "EMAIL";
    public static final String COL_1_3 = "PASSWORD";
    public static final String COL_1_4 = "PSEUDO";
    public static final String COL_1_5 = "HANDICAP";
    public static final String COL_1_6 = "PHOTO";

    public static final String TABLE_NAME_2 = "hole";
    public static final String COL_2_1 = "ID";
    public static final String COL_2_2 = "MAP";
    public static final String COL_2_3 = "PAR";
    public static final String COL_2_4 = "DISTANCE";
    public static final String COL_2_5 = "RESULT";

    public static final String TABLE_NAME_3 = "parcours";
    public static final String COL_3_1 = "ID";
    public static final String COL_3_2 = "HOLE_1";
    public static final String COL_3_3 = "HOLE_2";
    public static final String COL_3_4 = "HOLE_3";
    public static final String COL_3_5 = "HOLE_4";
    public static final String COL_3_6 = "HOLE_5";
    public static final String COL_3_7 = "HOLE_6";
    public static final String COL_3_8 = "HOLE_7";
    public static final String COL_3_9 = "HOLE_8";
    public static final String COL_3_10 = "HOLE_9";
    public static final String COL_3_11 = "HANDICAP";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, PASSWORD TEXT, PSEUDO TEXT, HANDICAP TEXT, PHOTO TEXT)");
        db.execSQL("create table " + TABLE_NAME_2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, MAP TEXT, PAR TEXT, DISTANCE TEXT, RESULT TEXT)");
        db.execSQL("create table " + TABLE_NAME_3 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, HOLE_1 TEXT, HOLE_2 TEXT, HOLE_3 TEXT, HOLE_4 TEXT, HOLE_5 TEXT, HOLE_6 TEXT, HOLE_7 TEXT, HOLE_8 TEXT, HOLE_9 TEXT, HANDICAP TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME_3);
        onCreate(db);

    }

    public void insertUser(User u){
        db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(COL_1_2, u.getEmail());
        values.put(COL_1_2, u.getEmail());
        values.put(COL_1_2, u.getEmail());
    }
}
