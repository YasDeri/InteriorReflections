package com.ir.app.Adapters.Customer;

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
import com.ir.app.Adapters.Vendor.ManageItemAdapter;
import com.ir.app.Presentors.wishlistPresentor;
import com.ir.app.R;
import com.ir.sqlite.models.Item;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Item> items;
    String usr;
    TextView bill;
    public CartAdapter(List<Item> items, String usr, TextView bill) {
        this.items = items; this.usr = usr;
        this.bill = bill;
    }

    void updateBill() {
        int amount = 0;
        for(int i=0; i<items.size(); i++) {
            amount += items.get(i).getPrice();
        }
        bill.setText("Total Bill: "+amount+" Rs.");
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_rv,parent,false);
        CartAdapter.CartViewHolder lvh= new CartAdapter.CartViewHolder(v);


        return lvh;
    }

    public void tes(){
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        holder.imageView2.setImageResource(items.get(position).getImg());
        holder.textView.setText(items.get(position).getName());
        final int pos = position;

        StorageReference gsReference = FirebaseStorage.getInstance().getReference("images/"+items.get(position).getImgUri());
        gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imageView2.setImageBitmap(bm);
                holder.imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        holder.x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(pos);
                updateBill();
                tes();
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView2;
        TextView textView, textView2;
        Button x;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView2 =itemView.findViewById(R.id.liked_imgs);
            textView=itemView.findViewById(R.id.liked_text_rv);
            textView2=itemView.findViewById(R.id.delete_but);
            x = itemView.findViewById(R.id.delete_but);
        }
    }
    {}
}