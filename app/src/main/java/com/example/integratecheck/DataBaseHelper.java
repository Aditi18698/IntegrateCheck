package com.example.integratecheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String Database_Name = "vehicle.db";
    public static final String Table_Name= "trip";

    public static final String dbdist_travelled = "dist_travelled";
    public static final String dbfuel_cosumed = "fuel_cosumed";
    public static final String dbavg_trip = "avg_trip";


    public DataBaseHelper(Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Table_Name+" ( dist_travelled float , fuel_cosumed float , avg_trip float )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        onCreate(db);

    }

    public boolean addData(String dist_travelled , String fuel_cosumed , String avg_trip){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contantValues= new ContentValues();
        contantValues.put(dbdist_travelled,dist_travelled);
        contantValues.put(dbfuel_cosumed,fuel_cosumed);
        contantValues.put(dbavg_trip,avg_trip);
        Log.d("ADebugTag", "Value DB: " + avg_trip);
        long result = db.insert(Table_Name,null,contantValues);

        if (result==-1)
            return false;
        else
            return true;
    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+Table_Name,null);
        return res;
    }

    public Cursor reset(String Table_Name){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("DELETE FROM "+Table_Name, null);
        return res;
    }

}
