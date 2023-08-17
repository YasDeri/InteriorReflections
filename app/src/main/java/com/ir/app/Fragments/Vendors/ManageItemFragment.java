package com.ir.app.Fragments.Vendors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ir.app.Adapters.Vendor.ManageItemAdapter;
import com.ir.app.R;
import com.ir.firebase.Helpers.ItemHelper;
import com.ir.sqlite.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ManageItemFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Item> items = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        for(int i=0; i< ItemHelper.getInstance().getItems().size(); i++) {
                items.add(ItemHelper.getInstance().getItems().get(i));
        }
        View v = inflater.inflate(R.layout.fragment_manage_item, container, false);
        recyclerView = v.findViewById(R.id.manage_item_rv);

//        for(int i=0; i<ItemHelper.getInstance().getCatagories().size(); i++) {
//            List<Item> tmp = ItemHelper.getInstance().getCatagories().get(i).getItems();
//            for(int j=0; j<tmp.size(); j++) {
//                items.add(tmp.get(j));
//            }
//        }

        adapter = new ManageItemAdapter(items);
        layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
