package com.ir.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.ir.admin.MainAdmin;
import com.ir.app.Activities.Admin.AdminNav;
import com.ir.app.Activities.Vendor.VendorNav;
import com.ir.app.R;
import com.ir.firebase.Helpers.ItemHelper;
import com.ir.firebase.Helpers.StorageHelper;
import com.ir.firebase.Helpers.UserHelper;
import com.ir.sqlite.models.Session;
import com.ir.sqlite.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserHelper.getInstance().readUsers();
        ItemHelper.getInstance().readItems();
//
//        Intent i= null;
//        try { i = new Intent(this,Class.forName("com.example.retrofit.ShowInstance"));
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        startActivity(i);

        Stetho.initializeWithDefaults(this);

    }

    public void signin(View view) {
        //Intent x = new Intent(getApplicationContext(), MainAdmin.class);
        //startActivity(x);
        //chooseImage();
        EditText user = findViewById(R.id.editText1), pass = findViewById(R.id.editText2);
        if(!user.getText().toString().matches("") && !pass.getText().toString().matches("")) {
            if (UserHelper.getInstance().validateUser(user.getText().toString(), pass.getText().toString())) {
                User tmpUser = UserHelper.getInstance().getUser(user.getText().toString(), pass.getText().toString());
                Gson gson = new Gson();
                Session session = new Session();
                session.setUser(tmpUser);
                String myJson = gson.toJson(session);
                if(tmpUser.getType().equals("Customer")) {
                    Intent i = new Intent(getApplicationContext(), BottomNav.class);
                    i.putExtra("session", myJson);
                    startActivity(i);
                }
                else if(tmpUser.getType().equals("Admin")) {
                    Intent i = new Intent(getApplicationContext(), AdminNav.class);
                    i.putExtra("session", myJson);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(getApplicationContext(), VendorNav.class);
                    i.putExtra("session", myJson);
                    startActivity(i);
                }
            } else {
                Toast.makeText(getBaseContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getBaseContext(), "You must enter username and password!", Toast.LENGTH_SHORT).show();
        }
    }

    public void signup(View view) {
        Intent i = new Intent(getApplicationContext(), Signup.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
