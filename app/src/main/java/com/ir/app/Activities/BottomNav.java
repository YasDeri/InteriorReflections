package com.ir.app.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.ir.app.Fragments.Customer.CartFragment;
import com.ir.app.Fragments.Customer.FeedFragment;
import com.ir.app.Fragments.Customer.ItemViewFragment;
import com.ir.app.Fragments.Customer.LikedFragment;
import com.ir.app.Fragments.Vendors.ManageItemFragment;
import com.ir.app.Fragments.Customer.OrdersFragment;
import com.ir.app.R;
import com.ir.app.Fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ir.notification.Notify;
import com.ir.sqlite.models.Item;
import com.ir.sqlite.models.Session;

public class BottomNav extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private NavigationView drawer;
    private DrawerLayout drawerLayout;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_nav);
        drawerLayout = findViewById(R.id.drawer);
        drawer = findViewById(R.id.drawer_view);
        Gson gson = new Gson();
        session = gson.fromJson(getIntent().getStringExtra("session"), Session.class);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        View hd = drawer.getHeaderView(0);
        TextView uinfo = hd.findViewById(R.id.userinfo);
        uinfo.setText(session.getUser().getType() + "\n" + session.getUser().getFname() + " " + session.getUser().getLname());

        drawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Intent x;
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.drawer_logout:
                        x = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(x);
                        break;
                    case R.id.drawer_setting:
                        Bundle bundle = new Bundle();
                        bundle.putString("usrn", session.getUser().getUsername());
                        fragment = new SettingsFragment();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, fragment);
                        fragmentTransaction.commit();
                        drawerLayout.closeDrawers();
                        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
                        break;
                }
                return false;
            }
        });
        Fragment fragment = null;
        fragment = new FeedFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_feed:
                        fragment = new FeedFragment();
                        break;
                    case R.id.nav_liked:
                        fragment=new LikedFragment();
                        break;
                    case R.id.nav_orders:
                        fragment=new OrdersFragment();
                        break;
                    case R.id.nav_cart:
                        fragment = new CartFragment();
                        break;
                }
                bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.commit();
                return true;
            }


        });
    }


    public void forceRefreshWishlist() {
        Fragment fragment=new LikedFragment();
        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    public void showTempView(Item item) {
        Bundle bundle = new Bundle();
        Gson gson = new Gson();

        bundle.putString("item", gson.toJson(item));
        Fragment fragment = new ItemViewFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
//        drawerLayout.closeDrawers();
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
    }

    public void addToCart(Item item) {
        session.getCart().add(item);
    }

    public Session getSession() {
        return this.session;
    }

    public void mt() {
        Toast.makeText(getBaseContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void gotofeed() {
        Fragment fragment = null;
        fragment = new FeedFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public void showNotification(String title, String message){
//        Notify x = new Notify();
//        Log.i("yuhu","showNotification");
//        x.chck(this);
//    }
}