package com.ir.app.Activities.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ir.app.Activities.MainActivity;
import com.ir.app.Fragments.Admin.AdminAdminFragment;
import com.ir.app.Fragments.Admin.AdminCustomerFragment;
import com.ir.app.Fragments.Admin.AdminVendorFragment;
import com.ir.app.Fragments.SettingsFragment;
import com.ir.app.Fragments.Vendors.NewItemFragment;
import com.ir.app.Fragments.Vendors.VendorList;
import com.ir.app.R;
import com.ir.sqlite.models.Session;

public class AdminNav extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private NavigationView drawer;
    private DrawerLayout drawerLayout;
    Session session;

    public Session getSession() {
        return this.session;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nav);
        drawerLayout = findViewById(R.id.drawer);
        drawer = findViewById(R.id.drawer_view);
        bottomNavigationView = findViewById(R.id.bottom_nav);


        Gson gson = new Gson();
        session = gson.fromJson(getIntent().getStringExtra("session"), Session.class);

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
                        break;
                }
                return false;
            }
        });
        Fragment fragment = null;
        fragment = new AdminCustomerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
//                    case R.id.nav_items:
//                        fragment = new ManageItemFragment();
//                        break;
                    case R.id.nav_customers:
                        fragment=new AdminCustomerFragment();
                        break;
                    case R.id.nav_vendors:
                        fragment=new AdminVendorFragment();
                        break;
                    case R.id.nav_admins:
                        fragment=new AdminAdminFragment();
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
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
