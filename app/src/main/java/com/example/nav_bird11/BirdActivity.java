package com.example.nav_bird11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class BirdActivity extends AppCompatActivity {

    ArrayList<String> imagelist;
    RecyclerView recyclerView;
    StorageReference root;
    ProgressBar progressBar;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird);

        imagelist=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        adapter=new ImageAdapter(imagelist,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        StorageReference listRef = FirebaseStorage.getInstance().getReference().child("images");
        listRef.listAll().addOnSuccessListener( listResult -> {
            for(StorageReference file:listResult.getItems()){
                file.getDownloadUrl()
                        .addOnSuccessListener (uri -> {
                    imagelist.add(uri.toString());
                    Log.e("Itemvalue",uri.toString());
                }).
                        addOnSuccessListener( uri -> {
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                });
            }
        });
    }
}