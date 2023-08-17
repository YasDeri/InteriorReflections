package com.ir.app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ir.app.R;

import java.io.File;
import java.io.IOException;

public class ArActivity extends AppCompatActivity {

    private ArFragment arFragment;
    boolean flag = false;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        FirebaseApp.initializeApp(this);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef = storage.getReference().child("3d_models/"+getIntent().getStringExtra("model_uri"));
        try {
            File f = File.createTempFile("temp", "glb");
            modelRef.getFile(f).addOnSuccessListener(taskSnapshot ->{
                file = f;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            if(!flag) {
                flag = true;
                Anchor anchor = hitResult.createAnchor();
                ModelRenderable
                        .builder()
                        .setSource(
                                this,
                                RenderableSource
                                        .builder()
                                        .setSource(this, Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
                                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                                        .build()
                        )
                        .setRegistryId(file.getPath())
                        .build()
                        .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable));
            }
        });
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }
}
