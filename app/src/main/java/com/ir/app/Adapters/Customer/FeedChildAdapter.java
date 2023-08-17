package com.ir.app.Adapters.Customer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ir.app.Activities.BottomNav;
import com.ir.app.Adapters.Vendor.ManageItemAdapter;
import com.ir.app.R;
import com.ir.sqlite.models.Item;

import java.util.List;

public class FeedChildAdapter extends RecyclerView.Adapter<FeedChildAdapter.FeedChildViewHolder> {
    Context context;
    public FeedChildAdapter(List<Item> items, Context c) {
        this.items = items;
        this.context = c;
    }

    List<Item> items;
    @NonNull
    @Override
    public FeedChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_child_rv,parent, false);
        return new FeedChildViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FeedChildViewHolder holder, int position) {
        final Item tmpItem = items.get(position);
        holder.img.setImageResource(0);

        final FeedChildAdapter.FeedChildViewHolder tmpHolder = holder;
        StorageReference gsReference = FirebaseStorage.getInstance().getReference("images/"+items.get(position).getImgUri());
        gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                tmpHolder.img.setImageBitmap(bm);
                tmpHolder.img.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        holder.text.setText(items.get(position).getName());
        holder.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof BottomNav) {
                    ((BottomNav)context).showTempView(tmpItem);
                }
            }
        });
    }

    public static class FeedChildViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView text;
        Button but;
        public FeedChildViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.child_feed_img);
            text=itemView.findViewById(R.id.child_feed_txt);
            but=itemView.findViewById(R.id.but);
        }
    }
}
