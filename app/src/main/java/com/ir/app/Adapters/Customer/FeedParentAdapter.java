package com.ir.app.Adapters.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ir.app.R;
import com.ir.sqlite.models.ItemCategory;

import java.util.ArrayList;
import java.util.List;


public class FeedParentAdapter extends RecyclerView.Adapter<FeedParentAdapter.FeedParentViewHolder>{
    List<ItemCategory> interior;
    Context context;

    ArrayList<String> text=new ArrayList<>();
    ArrayList<Integer> imgs= new ArrayList<>();

    public FeedParentAdapter(List<ItemCategory> interior, Context context) {
        this.interior = interior;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_fragment_rv, parent,false);
        return new FeedParentViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedParentViewHolder holder, int position) {
        holder.textView.setText(interior.get(position).getName());
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        holder.r1.setLayoutManager(layoutManager);
        holder.r1.setHasFixedSize(true);
        FeedChildAdapter feedChildAdapter=new FeedChildAdapter(interior.get(position).getItems(), context);
        holder.r1.setAdapter(feedChildAdapter);
        feedChildAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return interior.size();
    }

    public static class FeedParentViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        RecyclerView r1;
        public FeedParentViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView= itemView.findViewById(R.id.text_rv_feeds);
            r1=itemView.findViewById(R.id.feeds_rv);
        }
    }
}
