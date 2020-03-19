package com.example.integratecheck;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper1 extends SQLiteOpenHelper {
    public static final String Database_Name = "user.db";
    public static final String Table_Name= "user_reg";
    public static final String dbUID = "UID";
    public static final String dbName = "Name";
    public static final String dbBT_no = "BT_no";
    public static final String dbVehicle = "Vehicle";

    public DataBaseHelper1(@Nullable Context context) {
        super(context, Database_Name, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Table_Name+" ( UID integer PRIMARY KEY , Name text , BT_no text , Vehicle text )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
