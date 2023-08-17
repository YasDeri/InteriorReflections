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
import com.ir.sqlite.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHelper {
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private static List<Item> items = new ArrayList<>();

    private static ItemHelper obj = null;

    private ItemHelper() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Items");
    }

    public static ItemHelper getInstance()
    {
        if(obj == null) {
            obj = new ItemHelper();
        }
        return obj;
    }

    public void readItems() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Item i = keyNode.getValue(Item.class);
                    items.add(i);
                }
                //dataStatus.DataIsLoaded(users, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void writeItem(List<ItemCategory> x) {
        DatabaseReference usersRef = ref;
        Map<String, ItemCategory> catagories = new HashMap<>();
        for(int i=0; i<x.size(); i++) {
            catagories.put(x.get(i).getName(), x.get(i));
            usersRef.setValue(catagories);
        }
    }

    public void writeItem(ItemCategory x) {
        DatabaseReference usersRef = ref;
        Map<String, ItemCategory> catagories = new HashMap<>();
        catagories.put(x.getName(), x);
        usersRef.setValue(catagories);
    }

    public void writeItem(Item x) {
        DatabaseReference usersRef = ref;
        usersRef.push().setValue(x);
    }
    public List<Item> getItems() {
        return items;
    }

    public List<Item> getUserItems(String vendor) {
        List uItems = new ArrayList<Item>();
        for(int i=0; i<items.size(); i++) {
            if(items.get(i).getVendor() != null && items.get(i).getVendor().equals(vendor) ) {
                uItems.add(items.get(i));
            }
        }
        return uItems;
    }

    public void removeItem(final Item x) {
        final String uri = x.getImgUri();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Item i = keyNode.getValue(Item.class);
                    if(i.getImgUri().equals(uri) && i.getName().equals(x.getName()) && i.getCategory().equals(x.getCategory())) {
                        ref.child(keyNode.getKey()).removeValue();
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
