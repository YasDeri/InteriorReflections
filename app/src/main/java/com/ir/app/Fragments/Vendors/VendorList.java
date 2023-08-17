package com.ir.app.Fragments.Vendors;


import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ir.app.Activities.Vendor.VendorNav;
import com.ir.app.Adapters.Customer.FeedParentAdapter;

import com.ir.app.Adapters.Vendor.VendorItemAdapter;
import com.ir.app.R;
import com.ir.firebase.Helpers.ItemHelper;

import com.ir.sqlite.models.Item;
import com.ir.sqlite.models.ItemCategory;

import java.util.ArrayList;
import java.util.List;


public class VendorList extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;




    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ItemHelper h = ItemHelper.getInstance();

        List<Item> its = h.getUserItems(((VendorNav)getActivity()).getSession().getUser().getUsername());

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.vendor_list_items, container, false);


        recyclerView = v.findViewById(R.id.vendor_items_rv);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter= new VendorItemAdapter(its, getContext());
        recyclerView.setAdapter(adapter);
        return v;
    }
}
