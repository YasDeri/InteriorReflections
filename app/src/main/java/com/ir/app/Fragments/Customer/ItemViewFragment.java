package com.ir.app.Fragments.Customer;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.service.FavoriteService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.ir.app.Activities.ArActivity;
import com.ir.app.Activities.BottomNav;
import com.ir.app.Adapters.Customer.FeedChildAdapter;
import com.ir.app.Presentors.wishlistPresentor;
import com.ir.app.R;
import com.ir.firebase.Helpers.WishlistHelper;
import com.ir.notification.Broadcast;
import com.ir.notification.Notify;
import com.ir.sqlite.models.Item;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class ItemViewFragment extends Fragment {


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_item_view, container, false);
        Gson gson = new Gson();
        final Item y = gson.fromJson(getArguments().getString("item"), Item.class);
        TextView x = v.findViewById(R.id.itemNameTxt);
        x.setText(y.getName());
        final ImageView img = v.findViewById(R.id.imgitem);
        final TextView priceTextView = v.findViewById(R.id.itemPriceTxt);
        img.setImageResource(0);
        priceTextView.setText("Price: " + y.getPrice()+ " RS");

        StorageReference gsReference = FirebaseStorage.getInstance().getReference("images/"+y.getImgUri());
        gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img.setImageBitmap(bm);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        Button arBut = v.findViewById(R.id.view3D);
        arBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AR
                Intent i = new Intent(getActivity(), ArActivity.class);
                i.putExtra("model_uri", y.getModelUri());
                startActivity(i);
            }
        });
        Button bt = v.findViewById(R.id.addToCart);
        final Context c = ((BottomNav)getActivity());

        bt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                ((BottomNav)getActivity()).addToCart(y);
                Toast.makeText(getActivity(), "Successfully added to cart", Toast.LENGTH_SHORT).show();

//                Intent i=new Intent(c, FavoriteService.class);
//                Log.i("extrainn","rdasasasfd");
//
//                i.putExtra("item",y);
//                Log.i("extran","rdasasasfd");
//                c.startService(i);
//
//                Log.i("localdb","rdasasasfd");

                //showNotification("Notification","Cart Updated");
                //   ((BottomNav)getActivity()).showNotification("asf","asf");
            }
        });
        bt = v.findViewById(R.id.addToWishlist);
        final wishlistPresentor wp = new wishlistPresentor();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wp.storeOne(y, ((BottomNav)getActivity()).getSession().getUser().getUsername());
                Toast.makeText(getActivity(), "Successfully added to Wishlist", Toast.LENGTH_SHORT).show();
            }
        });

//        bt = v.findViewById(R.id.placeOrderButton);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return v;
    }
    private void showNotification(String title, String message){
        Notify x = new Notify();
        x.showNotification(title,message, getActivity());
    }
}
