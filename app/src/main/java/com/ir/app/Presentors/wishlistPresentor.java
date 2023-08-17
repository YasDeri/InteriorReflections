package com.ir.app.Presentors;

import android.widget.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.ir.firebase.Helpers.WishlistHelper;
import com.ir.sqlite.models.Item;

import java.util.ArrayList;
import java.util.List;

public class wishlistPresentor {
    List<Item> items;
    RecyclerView.Adapter adapter;

    public wishlistPresentor(){
        items = new ArrayList<>();
    }

    public void fetch(RecyclerView.Adapter adapter, String usr) {
        this.adapter = adapter;

        WishlistHelper.getInstance().readItems(items, adapter, usr);
    }

    public void store(List<Item> tmp, String user) {
        //WishlistHelper.getInstance().writeItem(tmp, user);
    }

    public void storeOne(Item tmp, String user) {
        WishlistHelper.getInstance().writeItem(tmp, user);
        //items.add(tmp);
        //store(items, user);
        //adapter.notifyDataSetChanged();
    }

    public void deleteItem(Item tmp, String usr) {
        WishlistHelper.getInstance().removeItem(tmp, usr);
    }

    public List<Item> getItems() {
        return items;
    }
}
