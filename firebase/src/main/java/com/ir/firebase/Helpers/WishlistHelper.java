package com.ir.firebase.Helpers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ir.sqlite.models.Item;
import com.ir.sqlite.models.ItemCategory;
import com.ir.sqlite.models.ItemList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistHelper {

    List<Item> allItems = new ArrayList<Item>();

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private static WishlistHelper obj = null;

    private WishlistHelper() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Wishlists");
    }

    public static WishlistHelper getInstance()
    {
        if(obj == null) {
            obj = new WishlistHelper();
        }
        return obj;
    }

    public void readItems(final List<Item> items, final RecyclerView.Adapter adapter, final String usr) {
        ref.child(usr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                items.clear();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Item i = keyNode.getValue(Item.class);
                    items.add(i);

                }
                adapter.notifyDataSetChanged();
                //dataStatus.DataIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


//    public List<Item> getItems(final String usr) {
//        final List<Item> tmp = new ArrayList<Item>();
//        ref.child(usr).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<String> keys = new ArrayList<>();
//                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
//                    Item i = keyNode.getValue(Item.class);
//                    allItems.add(i);
//
//                }
//                return allItems();
//                //dataStatus.DataIsLoaded(users, keys);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
    public void writeItem(List<Item> x, String name) {
        ItemList xx = new ItemList(x);
        DatabaseReference usersRef = ref;
        Map<String, ItemList> t = new HashMap<>();
        for(int i=0; i<x.size(); i++) {
            t.put(name, xx);
            usersRef.setValue(t);
        }
    }

    public void writeItem(Item x, String name) {
        DatabaseReference usersRef = ref;
        usersRef.child(name).push().setValue(x);
    }

    public void removeItem(final Item x, String usr) {
        final String uri = x.getImgUri();
        final String fusr = usr;
        ref.child(usr).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Item i = keyNode.getValue(Item.class);
                    if(i.getImgUri().equals(uri) && i.getName().equals(x.getName()) && i.getCategory().equals(x.getCategory())) {
                        ref.child(fusr).child(keyNode.getKey()).removeValue();

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
}
