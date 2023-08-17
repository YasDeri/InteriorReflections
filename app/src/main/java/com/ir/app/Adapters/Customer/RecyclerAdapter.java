package com.ir.app.Adapters.Customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ir.app.Activities.OrdersActivity;
import com.ir.app.R;
import com.ir.sqlite.models.Order;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.OrdersViewHolder> {
    private List<Order> orders;
    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_recycle_fill,parent,false);
        OrdersViewHolder ovh= new OrdersViewHolder(v);
        return ovh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Order current= orders.get(position);
        //holder.imageView.setImageResource(current.g());
        holder.textView.setText("Order #"+current.getOrderNo());
        holder.textView1.setText("Bill: "+current.getBill());
        holder.textView2.setText(current.getStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public RecyclerAdapter(List<Order> orders){
        this.orders = orders;
    }
    public static class OrdersViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView, textView1, textView2;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imgView_orders);
            textView=itemView.findViewById(R.id.name_order);
            textView1=itemView.findViewById(R.id.description);
            textView2=itemView.findViewById(R.id.status);
        }
    }
}
