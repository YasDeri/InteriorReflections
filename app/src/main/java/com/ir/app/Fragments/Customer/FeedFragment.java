package com.ir.app.Fragments.Customer;


import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ir.app.Adapters.Customer.FeedParentAdapter;

import com.ir.app.R;
import com.ir.firebase.Helpers.ItemHelper;
import com.ir.sqlite.models.Item;
import com.ir.sqlite.models.ItemCategory;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    SearchView sv;
    List<ItemCategory> catagories= new ArrayList<>();
    List<ItemCategory> tcatagories= new ArrayList<>();

    void addItem(Item x){
        int flag = -1;
        for(int i=0; i<catagories.size(); i++) {
            if(catagories.get(i).getName().equals(x.getCategory())) {
                flag = i;
                break;
            }
        }

        if(flag != -1) {
            catagories.get(flag).addItem(x);
        }
        else {
            ItemCategory tc = new ItemCategory(x.getCategory());
            tc.addItem(x);
            catagories.add(tc);
        }
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ItemHelper h = ItemHelper.getInstance();

        List<Item> its = h.getItems();
        for(int i=0; i<its.size(); i++) {
            addItem(its.get(i));
        }
        tcatagories.addAll(catagories);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        sv = v.findViewById(R.id.search_bar);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ItemCategory tmpCat = new ItemCategory("Search Result");
                for(int i=0; i<tcatagories.size(); i++) {
                    if(tcatagories.get(i).getName().contains(query)) {
                        tmpCat.addItems(tcatagories.get(i).getItems());
                    }
                    else {
                        for(int x=0; x<tcatagories.get(i).getItems().size(); x++) {
                            if(tcatagories.get(i).getItems().get(x).getName().contains(query)) {
                                tmpCat.addItem(tcatagories.get(i).getItems().get(x));
                            }
                        }
                    }
                }
                catagories.removeAll(catagories);
                catagories.add(tmpCat);
                adapter.notifyDataSetChanged();

                //((BottomNav)getActivity()).mt();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        recyclerView = v.findViewById(R.id.feeds_rv_parent);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter= new FeedParentAdapter(catagories, getContext());
        recyclerView.setAdapter(adapter);
        /*catagories.add(new ItemCategory("Beds"));
        catagories.add(new ItemCategory("Tables"));
        catagories.add(new ItemCategory("Wallpapers"));

        catagories.get(0).addItem(new Item("Wooden", 1200, R.drawable.bed1, "Beds"));
        catagories.get(0).addItem(new Item("Old Jeans", 1200, R.drawable.bed2, "Beds"));
        catagories.get(0).addItem(new Item("Dagwood", 1200, R.drawable.bed3, "Beds"));
        catagories.get(0).addItem(new Item("Comfy", 1200, R.drawable.bed4, "Beds"));

        catagories.get(1).addItem(new Item("Side Stand", 100, R.drawable.tab1, "Tables"));
        catagories.get(1).addItem(new Item("Wood", 100, R.drawable.tab2, "Tables"));
        catagories.get(1).addItem(new Item("Centre of Attention", 100, R.drawable.tab3, "Tables"));
        catagories.get(1).addItem(new Item("Dining", 100, R.drawable.tab4, "Tables"));

        catagories.get(2).addItem(new Item("Brown", 100, R.drawable.wall1, "Wallpaper"));
        catagories.get(2).addItem(new Item("Leaves", 100, R.drawable.wall2, "Wallpaper"));
        catagories.get(2).addItem(new Item("Coloured", 100, R.drawable.wall3, "Wallpaper"));*/


        //adapter.notifyDataSetChanged();

        //ItemHelper.getInstance().writeItem(catagories);
        return v;
    }
}
