package com.ir.app.Fragments.Customer;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ir.app.Activities.BottomNav;
import com.ir.app.Adapters.Customer.CartAdapter;
import com.ir.app.Adapters.Vendor.ManageItemAdapter;
import com.ir.app.R;
import com.ir.firebase.Helpers.ItemHelper;
import com.ir.firebase.Helpers.OrderHelper;
import com.ir.sqlite.CartHelper;
import com.ir.sqlite.models.Cart;
import com.ir.sqlite.models.Item;
import com.ir.sqlite.models.Order;
import com.ir.sqlite.models.Session;


import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_1;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_2;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_3;
import static com.ir.sqlite.models.CartTable.FavoritesEntry.COL_4;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Item> items = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Gson gson = new Gson();
        final Session s = ((BottomNav)getActivity()).getSession();
        items = s.getCart().getItems();
        CartHelper ch = new CartHelper(((BottomNav)getActivity()));
//        if(items.size() == 0) {
//            Cursor cf = ch.getAllData();
//            while(cf.moveToNext()) {
//                items.add(new Item(
//                        cf.getString(cf.getColumnIndex(COL_1)),
//                        cf.getInt(cf.getColumnIndex(COL_2)),
//                        cf.getString(cf.getColumnIndex(COL_3)),
//                        cf.getString(cf.getColumnIndex(COL_4))
//                ));
//
//            }
//        }

        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = v.findViewById(R.id.manage_item_rv);
        TextView bill = v.findViewById(R.id.bill);
        adapter = new CartAdapter(items, s.getUser().getUsername(), bill);
        layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        int amount = 0;
        for(int i=0; i<items.size(); i++) {
            amount += items.get(i).getPrice();
        }

        bill.setText("Total Bill: "+amount+" Rs.");
        Button placeOrder = v.findViewById(R.id.placeOrderButton);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s.getCart().getItems().size() != 0) {
                    OrderHelper.getInstance().writeItem(new Order(s.getUser().getUsername(), items));
                    s.getCart().getItems().removeAll(s.getCart().getItems());
                    ((BottomNav) getActivity()).gotofeed();
                }
                else {
                    Toast.makeText(getActivity(), "Cart is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
}