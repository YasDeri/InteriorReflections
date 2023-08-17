package com.ir.app.Fragments.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ir.app.Adapters.Admin.CustomersAdapter;
import com.ir.app.Adapters.Admin.VendorAdapter;
import com.ir.app.R;
import com.ir.firebase.Helpers.UserHelper;
import com.ir.sqlite.models.User;

import java.util.ArrayList;
import java.util.List;

public class AdminVendorFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> users = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        List<User> tmpUsers = UserHelper.getInstance().getUsers("Customer");
//        for (int i = 0; i < tmpUsers.size(); i++) {
//            users.add(tmpUsers.get(i));
//        }
        View v = inflater.inflate(R.layout.fragment_admin_vendor, container, false);
        recyclerView = v.findViewById(R.id.vendor_rv);

//        for(int i=0; i<ItemHelper.getInstance().getCatagories().size(); i++) {
//            List<Item> tmp = ItemHelper.getInstance().getCatagories().get(i).getItems();
//            for(int j=0; j<tmp.size(); j++) {
//                items.add(tmp.get(j));
//            }
//        }

        adapter = new VendorAdapter(UserHelper.getInstance().getUsers("Vendor"), getActivity());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return v;
    }
}
