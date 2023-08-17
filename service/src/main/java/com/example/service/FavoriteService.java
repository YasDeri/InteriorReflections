package com.example.service;

import android.app.Service;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ir.sqlite.CartHelper;
import com.ir.sqlite.models.CartTable;
import com.ir.sqlite.models.Item;
import com.ir.sqlite.models.User;

import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_1;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_2;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_3;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.TABLE_NAME;


public class FavoriteService extends Service {

    Item item=null;


    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private SQLiteDatabase mDatabase;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void addinFirebase()
    {
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("LikedMovies").push();
//        FirebaseAuth mAuth= FirebaseAuth.getInstance();
//        FirebaseUser user =  mAuth.getCurrentUser();
//        assert user != null;
//        String uid = user.getUid();
        //String title=movie;
        Log.i("getdata","Yo");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
       Bundle b= intent.getExtras();
        assert b != null;
        Item y = (Item) intent.getSerializableExtra("item");
        CartHelper c=new CartHelper(getApplicationContext());
        mDatabase = c.getWritableDatabase();
        c.insertCart(y);
       //addinFirebase();
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
       // onDestroy();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}