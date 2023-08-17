package com.ir.app.Adapters.Vendor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ir.app.R;
import com.ir.sqlite.models.Item;

import java.util.List;

public class ManageItemAdapter extends RecyclerView.Adapter<ManageItemAdapter.ManageItemViewHolder> {
    private List<Item> items;

    public ManageItemAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ManageItemAdapter.ManageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_item_rv,parent,false);
        ManageItemAdapter.ManageItemViewHolder lvh= new ManageItemAdapter.ManageItemViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ManageItemAdapter.ManageItemViewHolder holder, int position) {
        holder.imageView2.setImageResource(0);
        final ManageItemAdapter.ManageItemViewHolder tmpHolder = holder;
        StorageReference gsReference = FirebaseStorage.getInstance().getReference("images/"+items.get(position).getImgUri());
        gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                tmpHolder.imageView2.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        holder.textView.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();    }

    public static class ManageItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView2;
        TextView textView, textView2;
        public ManageItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView2 =itemView.findViewById(R.id.liked_imgs);
            textView=itemView.findViewById(R.id.liked_text_rv);
            textView2=itemView.findViewById(R.id.liked_but);
        }
    }
    {}
}
