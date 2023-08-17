package com.ir.app.Fragments.Vendors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ir.app.Activities.BottomNav;
import com.ir.app.Activities.Vendor.VendorNav;
import com.ir.app.Adapters.Customer.RecyclerAdapter;
import com.ir.app.Adapters.Vendor.VendorOrderAdapter;
import com.ir.app.R;
import com.ir.firebase.Helpers.OrderHelper;
import com.ir.sqlite.models.Order;
import com.ir.sqlite.models.Session;

import java.util.ArrayList;
import java.util.List;

public class VendorOrders extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_vendor_orders, container, false);
        List<Order> orders = new ArrayList<>();

        Session s = ((VendorNav) getActivity()).getSession();

        recyclerView = v.findViewById(R.id.recycle_orders);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new VendorOrderAdapter(orders, s.getUser().getUsername());
        OrderHelper.getInstance().readVOrders(orders, adapter, s.getUser().getUsername());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
