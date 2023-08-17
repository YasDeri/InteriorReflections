package com.ir.firebase.Helpers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ir.sqlite.models.Item;
import com.ir.sqlite.models.ItemList;
import com.ir.sqlite.models.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHelper {
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private static OrderHelper obj = null;

    private OrderHelper() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Orders");
    }

    public static OrderHelper getInstance()
    {
        if(obj == null) {
            obj = new OrderHelper();
        }
        return obj;
    }

    public void readItems(final List<Order> orders, final RecyclerView.Adapter adapter, final String usr) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Order tmp = keyNode.getValue(Order.class);
                    if(tmp.getUsr().equals(usr)) {
                        orders.add(tmp);
                    }

                }
                adapter.notifyDataSetChanged();
                //dataStatus.DataIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readVOrders(final List<Order> orders,  final RecyclerView.Adapter adapter, final String v) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Order tmp = keyNode.getValue(Order.class);

                    for(int i=0; i<tmp.getItems().size(); i++) {
                        if(tmp.getItems().get(i).getVendor().equals(v)) {
                            boolean flag = false;
                            for(int x=0; x<orders.size(); x++) {
                                if(orders.get(x).getOrderNo().equals(tmp.getOrderNo())) {
                                    flag = true;
                                }
                            }
                            if(flag == false) {
                                orders.add(tmp);
                            }

                        }
                    }
                }
                adapter.notifyDataSetChanged();
                //dataStatus.DataIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void changeStatus(final Order x, final String status) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Order tmp = keyNode.getValue(Order.class);

                    for(int i=0; i<tmp.getItems().size(); i++) {
                        if(tmp.getOrderNo().equals(x.getOrderNo())) {
                            ref.child(keyNode.getKey()).child("status").setValue(status);
                        }
                    }
                }
                //adapter.notifyDataSetChanged();
                //dataStatus.DataIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void writeItem(Order order) {
        DatabaseReference usersRef = ref;
        Map<String, Order> t = new HashMap<>();
        t.put(order.getOrderNo(), order);
        usersRef.child(order.getOrderNo()).setValue(order);
    }
}
