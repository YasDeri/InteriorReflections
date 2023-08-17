package com.ir.app.Fragments.Vendors;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ir.app.Activities.Vendor.VendorNav;
import com.ir.app.R;
import com.ir.firebase.Helpers.ItemHelper;
import com.ir.firebase.Helpers.StorageHelper;
import com.ir.sqlite.models.Item;
import com.ir.sqlite.models.ItemCategory;
import com.ir.sqlite.models.Session;

import java.util.Random;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class NewItemFragment extends Fragment {
    boolean flag = false;
    Button uploadImg, createItem, uploadModel;
    String uri = random();
    String modelUri = random();
    private final int PICK_IMAGE_REQUEST = 71, PICK_MODEL_REQUEST = 77;
    private ImageView imageView;
    private Uri filePath;

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = 15;
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(26) + 65);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void chooseModel() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select 3D Object"), PICK_MODEL_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            if(filePath != null)
            {
                //final ProgressDialog progressDialog = new ProgressDialog(context);
                //progressDialog.setTitle("Uploading...");
                //progressDialog.show();
                StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/"+this.uri);
                final ImageView x = imageView;
                final String tu = this.uri;
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //progressDialog.dismiss();

                                StorageReference gsReference = FirebaseStorage.getInstance().getReference("images/"+tu);
                                gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        x.setImageBitmap(bm);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                    }
                                });
                                Toast.makeText(((VendorNav)getActivity()), "Uploaded", Toast.LENGTH_SHORT).show();
                                flag = true;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //progressDialog.dismiss();
                                //Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                //progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }
//            Glide.with(getApplicationContext()).load(filePath).into(imageView);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
        }
        else if(requestCode == PICK_MODEL_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            filePath = data.getData();
            if(filePath != null){
                StorageReference ref = FirebaseStorage.getInstance().getReference().child("3d_models/"+this.modelUri);
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getActivity(), "3D Object Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_item, container, false);
//        final ImageView x = v.findViewById(R.id.preview);
//        StorageReference gsReference = FirebaseStorage.getInstance().getReference("images/cf4e21ed-a12c-4442-816e-f8e8a6b883cb");
//        gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                x.setImageBitmap(bm);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });

        uploadImg = v.findViewById(R.id.browse_img);
        uploadModel = v.findViewById(R.id.browse_3d_model);
        createItem = v.findViewById(R.id.create_item);
        imageView = v.findViewById(R.id.preview);
        imageView.setImageResource(0);
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        uploadModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseModel();
            }
        });
        final String tmpUri = this.uri;
        Session s = ((VendorNav)getActivity()).getSession();
        final EditText a = v.findViewById(R.id.edit_name_item),
                b = v.findViewById(R.id.edit_price_item),
                c = v.findViewById(R.id.edit_category_item);
        final Session ts = s;
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == false) {
                    Toast.makeText(((VendorNav)getActivity()), "Wait until file is uploaded!", Toast.LENGTH_SHORT).show();
                }
                else {

                    //ItemCategory t = new ItemCategory(c.getText().toString());
                    Item tItem = new Item(a.getText().toString(), Integer.valueOf(b.getText().toString()), c.getText().toString(), ts.getUser().getUsername(),ts.getUser().getUsername());
                    tItem.setImgUri(tmpUri);
                    tItem.setModelUri(modelUri);
                    ItemHelper.getInstance().writeItem(tItem);
                    Toast.makeText(((VendorNav)getActivity()), "Successfully added!", Toast.LENGTH_SHORT).show();
                    modelUri = random();
                }
            }
        });
        return v;
    }

}
