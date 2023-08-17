package com.ir.app.Adapters.Vendor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ir.app.Adapters.Customer.RecyclerAdapter;
import com.ir.app.R;
import com.ir.firebase.Helpers.OrderHelper;
import com.ir.firebase.Helpers.UserHelper;
import com.ir.sqlite.models.Order;

import java.util.List;

public class VendorOrderAdapter extends RecyclerView.Adapter<VendorOrderAdapter.VendorOrderViewHolder> {
    private List<Order> orders;
    private String uname;
    @NonNull
    @Override
    public VendorOrderAdapter.VendorOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_order_rv,parent,false);
        VendorOrderAdapter.VendorOrderViewHolder ovh= new VendorOrderAdapter.VendorOrderViewHolder(v);
        return ovh;
    }

    @Override
    public void onBindViewHolder(@NonNull VendorOrderAdapter.VendorOrderViewHolder holder, int position) {
        Order current= orders.get(position);
        //holder.imageView.setImageResource(current.g());
        holder.textView.setText("Order #"+current.getOrderNo());


        holder.textView1.setText("Address: "+UserHelper.getInstance().getUser(current.getUsr()).getAddress());
        holder.textView2.setText(current.getStatus());


        String st = "";
        for(int i=0; i<current.getItems().size(); i++) {
            if(current.getItems().get(i).getVendor().equals(uname)) {
                st += current.getItems().get(i).getName() + "\n";
            }
        }

        holder.its.setText(st);

        holder.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setStatus("Dispatched");
                OrderHelper.getInstance().changeStatus(current, "Dispatched");
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public VendorOrderAdapter(List<Order> orders, String uname){
        this.orders = orders;
        this.uname = uname;
    }
    public static class VendorOrderViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView, textView1, textView2, its;
        public VendorOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imgView_orders);
            textView=itemView.findViewById(R.id.name_order);
            textView1=itemView.findViewById(R.id.description);
            textView2=itemView.findViewById(R.id.status);
            its = itemView.findViewById(R.id.vits);
        }
    }
}
