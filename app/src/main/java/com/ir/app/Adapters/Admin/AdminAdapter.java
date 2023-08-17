package com.ir.app.Adapters.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ir.app.R;
import com.ir.firebase.Helpers.UserHelper;
import com.ir.sqlite.models.User;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    private List<User> customers;
    Context c;

    public AdminAdapter(List<User> customers, Context c) {
        this.customers = customers;
        this.c = c;
    }

    @NonNull
    @Override
    public AdminAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_admin_rv,parent,false);
        AdminAdapter.AdminViewHolder lvh= new AdminAdapter.AdminViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.AdminViewHolder holder, int position) {
        holder.cusName.setText(customers.get(position).getUsername());
        holder.cusDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserHelper.getInstance().removeuser(customers.get(position));
                customers.remove(position);
                Toast.makeText(c, "Successfully deleted the Admin", Toast.LENGTH_SHORT).show();
                //customers.clear();
                //customers.addAll(UserHelper.getInstance().getUsers("Customers"));
                notifyDataSetChanged();
            }
        });
        holder.mkV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserHelper.getInstance().changeType(customers.get(position), "Customer");
                customers.remove(position);
                Toast.makeText(c, "Successfully changed to Customer", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        holder.mkA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserHelper.getInstance().changeType(customers.get(position), "Vendor");
                customers.remove(position);
                Toast.makeText(c, "Successfully changed to Vendor", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder{
        TextView cusName;
        Button cusDel, mkV, mkA;

            public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            cusName =itemView.findViewById(R.id.admin_name);
            cusDel = itemView.findViewById(R.id.delete_but);
            mkV = itemView.findViewById(R.id.make_customer);
            mkA = itemView.findViewById(R.id.make_vendor);
        }
    }
    {}
}
