package com.ir.app.Adapters.Vendor;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ir.app.R;
import com.ir.firebase.Helpers.ItemHelper;
import com.ir.sqlite.models.Item;

import java.util.ArrayList;
import java.util.List;


public class VendorItemAdapter extends RecyclerView.Adapter<VendorItemAdapter.VendorItemViewHolder> {

    List<Item> itemList;
    Context c;

    public VendorItemAdapter(List<Item> items, Context c) {
        this.itemList = new ArrayList(items);
        this.c = c;
    }

    @NonNull
    @Override
    public VendorItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_list_rv, parent,false);
        return new VendorItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorItemViewHolder holder, int position) {

        Item current = itemList.get(position);
        holder.txt.setText(current.getName());


        holder.img.setImageResource(0);

        final VendorItemAdapter.VendorItemViewHolder tmpHolder = holder;
        StorageReference gsReference = FirebaseStorage.getInstance().getReference("images/"+itemList.get(position).getImgUri());
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

        holder.dbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemHelper x = ItemHelper.getInstance();
                x.removeItem(current);
                itemList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class VendorItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        ImageView img;
        Button ebut, dbut;
        public VendorItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.itemName);
            img = itemView.findViewById(R.id.itemImg);
            //ebut = itemView.findViewById(R.id.vendor_edit);
            dbut = itemView.findViewById(R.id.vendor_delete);
            //but = itemView.findViewById(R.id.deleteItem);
        }
    }
}
