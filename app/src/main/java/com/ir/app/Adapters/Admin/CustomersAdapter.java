package com.ir.app.Adapters.Admin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ir.app.R;
import com.ir.firebase.Helpers.UserHelper;
import com.ir.sqlite.models.Item;
import com.ir.sqlite.models.User;

import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.CustomersViewHolder> {
    private List<User> customers;
    Context c;


    public CustomersAdapter(List<User> customers, Context c) {
        this.customers = customers;
        this.c = c;
    }

    @NonNull
    @Override
    public CustomersAdapter.CustomersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_customer_rv,parent,false);
        CustomersAdapter.CustomersViewHolder lvh= new CustomersAdapter.CustomersViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomersAdapter.CustomersViewHolder holder, int position) {
        holder.cusName.setText(customers.get(position).getUsername());
        holder.cusDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserHelper.getInstance().removeuser(customers.get(position));
                customers.remove(position);
                //customers.clear();
                //customers.addAll(UserHelper.getInstance().getUsers("Customers"));
                Toast.makeText(c, "Successfully deleted the customer.", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        holder.mkV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserHelper.getInstance().changeType(customers.get(position), "Vendor");
                customers.remove(position);
                Toast.makeText(c, "Successfully promoted to Vendor", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        holder.mkA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserHelper.getInstance().changeType(customers.get(position), "Admin");
                customers.remove(position);
                Toast.makeText(c, "Successfully promoted to Admin", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();    }

    public static class CustomersViewHolder extends RecyclerView.ViewHolder{
        TextView cusName;
        Button cusDel, mkV, mkA;

        public CustomersViewHolder(@NonNull View itemView) {
            super(itemView);
            cusName =itemView.findViewById(R.id.customer_name);
            cusDel = itemView.findViewById(R.id.delete_but);
            mkV = itemView.findViewById(R.id.make_vendor);
            mkA = itemView.findViewById(R.id.make_admin);
        }
    }
    {}
}
