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
import com.ir.app.Activities.BottomNav;
import com.ir.app.Activities.LikedActivity;
import com.ir.app.Presentors.wishlistPresentor;
import com.ir.app.R;
import com.ir.firebase.Helpers.WishlistHelper;
import com.ir.sqlite.models.Item;

import java.util.ArrayList;
import java.util.List;

public class LikedAdapter extends RecyclerView.Adapter<LikedAdapter.LikedViewHolder> {
    private List<Item> items;
    String usr;
    public LikedAdapter(List<Item> items, String usr) {
        this.items=items;
        this.usr = usr;
    }

    @NonNull
    @Override
    public LikedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.liked_rv_fill,parent,false);
        LikedAdapter.LikedViewHolder lvh= new LikedAdapter.LikedViewHolder(v);
        return lvh;
    }
    public void tes(){
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull LikedViewHolder holder, int position) {

        //Item current= items.get(position);
        holder.imageView2.setImageResource(0);

        final LikedAdapter.LikedViewHolder tmpHolder = holder;
        StorageReference gsReference = FirebaseStorage.getInstance().getReference("images/"+items.get(position).getImgUri());
        gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                tmpHolder.imageView2.setImageBitmap(bm);
                tmpHolder.imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        holder.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WishlistHelper.getInstance().removeItem(items.get(position), usr);
                //items.remove(position);
                //notifyDataSetChanged();
                //tes();



            }
        });

        holder.textView.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();    }

    public static class LikedViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView2;
        TextView textView;
        //, textView2;
        Button but;
        public LikedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView2 =itemView.findViewById(R.id.liked_imgs);
            textView=itemView.findViewById(R.id.liked_text_rv);
            but=itemView.findViewById(R.id.liked_but);

        }
    }
    {}
}
