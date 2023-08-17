package com.ir.app.Fragments.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ir.app.Activities.BottomNav;
import com.ir.app.Activities.LikedActivity;
import com.ir.app.Adapters.Customer.LikedAdapter;
import com.ir.app.Presentors.ItemPresentor;
import com.ir.app.Presentors.wishlistPresentor;
import com.ir.app.R;
import com.ir.firebase.Helpers.ItemHelper;
import com.ir.sqlite.models.Item;
import com.ir.sqlite.models.Session;

import java.util.ArrayList;
import java.util.List;


public class LikedFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_liked, container, false);
        //items = ItemHelper.getInstance().getCatagories().get(0).getItems();
        wishlistPresentor wp = new wishlistPresentor();
        Session s = ((BottomNav)getActivity()).getSession();
        recyclerView = v.findViewById(R.id.liked_rv);
        //wp.store(items, s.getUser().getUsername());
        adapter= new LikedAdapter(wp.getItems(), s.getUser().getUsername());
        wp.fetch(adapter, s.getUser().getUsername());
        layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
