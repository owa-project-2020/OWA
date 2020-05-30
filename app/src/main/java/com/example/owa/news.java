package com.example.owa;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class news extends AppCompatActivity {

    RecyclerView adver_rv;
    NewsAdapter NAdapter;

    List<uploadinfo> mUploads;

    DatabaseReference reference;
    //FirebaseDatabase fdb;
    StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news2);

        adver_rv = findViewById(R.id.rv3);
        adver_rv.setHasFixedSize(true);
        adver_rv.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance().getReference("News_Images");
        reference = FirebaseDatabase.getInstance().getReference("News Details");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    uploadinfo upload = postSnapshot.getValue(uploadinfo.class);
                    mUploads.add(upload);
                }
                NAdapter = new NewsAdapter(mUploads, news.this);
                adver_rv.setAdapter(NAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(news.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
