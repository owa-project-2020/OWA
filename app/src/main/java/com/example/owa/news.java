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

public class news extends AppCompatActivity {

    RecyclerView news_rv;
    NewsAdapter NAdapter;
    int flag;
    ArrayList<uploadinfo> mUploads;
    DatabaseReference reference;
    //FirebaseDatabase fdb;
    StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        news_rv = findViewById(R.id.rv3);
        news_rv.setHasFixedSize(true);
        news_rv.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance().getReference("News_Images");
        reference = FirebaseDatabase.getInstance().getReference("News");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    uploadinfo upload = postSnapshot.getValue(uploadinfo.class);
                    mUploads.add(upload);
                }
                NAdapter = new NewsAdapter(mUploads);
                news_rv.setAdapter(NAdapter);
                NAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(news.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
