package com.ir.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ir.sqlite.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "cart.db";
    public static final String TABLE_NAME = "userstable";
    public static final String COL_1 = "username";
    public static final String COL_2 = "fname";
    public static final String COL_3 = "lname";
    public static final String COL_4 = "addr";
    public static final String COL_5 = "password";
    public static final String COL_6 = "type";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DATABASE_NAME + " ("+COL_1+" TEXT primary key, "+COL_2+" TEXT, "+COL_3+" TEXT, "+COL_4+" TEXT, "+COL_5+" TEXT, "+COL_6+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(User x) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, x.getUsername());
        contentValues.put(COL_2, x.getFname());
        contentValues.put(COL_3,  x.getLname());
        contentValues.put(COL_4, x.getAddress());
        contentValues.put(COL_5, x.getPassword());
        contentValues.put(COL_6, x.getType());
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }
}
