package com.ir.app.Fragments.Customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ir.app.Activities.BottomNav;
import com.ir.app.Activities.OrdersActivity;
import com.ir.app.Adapters.Customer.RecyclerAdapter;
import com.ir.app.R;
import com.ir.firebase.Helpers.OrderHelper;
import com.ir.sqlite.models.Order;
import com.ir.sqlite.models.Session;

import java.util.ArrayList;
import java.util.List;


public class OrdersFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        List<Order> orders = new ArrayList<>();

        Session s = ((BottomNav)getActivity()).getSession();

        recyclerView = v.findViewById(R.id.recycle_orders);
        layoutManager= new LinearLayoutManager(getContext());
        adapter = new RecyclerAdapter(orders);
        OrderHelper.getInstance().readItems(orders, adapter, s.getUser().getUsername());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return v;
    }

}
