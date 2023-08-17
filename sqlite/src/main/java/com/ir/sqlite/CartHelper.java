package com.ir.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.ir.sqlite.models.Cart;
import com.ir.sqlite.models.Item;

import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_1;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_2;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_3;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_4;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.DATABASE_NAME;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.TABLE_NAME;


public class CartHelper extends SQLiteOpenHelper {
//    public static final String COL_1 = "item_name";
//    public static final String COL_2 = "item_price";
//    public static final String COL_3 = "item_category";
//    public static final String COL_4 = "";
//    public static final String COL_5 = "password";
//    public static final String COL_6 = "type";

    public CartHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("+COL_1 +" TEXT primary key, "+COL_2+" INT, "+COL_3+" TEXT, " + COL_4 + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertCart(Item y) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1,y.getName());
        values.put(COL_2, y.getPrice());
        values.put(COL_3,y.getCategory());
        values.put(COL_4, y.getVendor());
        //values.put(COL_4);
        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1) return false;
        return true;
//
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }
}
