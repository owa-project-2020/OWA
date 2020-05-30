package com.example.owa;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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

public class view_Ads extends AppCompatActivity {

    RecyclerView adver_rv;
    advertisementsAdapter Adapter;
    EditText search;
    int flag;
    ArrayList<uploadinfo> mUploads;

    DatabaseReference reference;
    //FirebaseDatabase fdb;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__ads);

        adver_rv = findViewById(R.id.rv1);
        adver_rv.setHasFixedSize(true);
        adver_rv.setLayoutManager(new LinearLayoutManager(this));
        search = findViewById(R.id.find);

        mUploads = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance().getReference("Advertisements Images");
        reference = FirebaseDatabase.getInstance().getReference("Ads");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    uploadinfo upload = postSnapshot.getValue(uploadinfo.class);
                    mUploads.add(upload);
                }
                Adapter = new advertisementsAdapter(mUploads, view_Ads.this);
                adver_rv.setAdapter(Adapter);
                Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(view_Ads.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {
                    search(s.toString());
                } else {
                    Adapter.update(mUploads);
                }
            }
        });
    }

    private void search(String s) {


        ArrayList<uploadinfo> temp = new ArrayList<>();
        for (uploadinfo u : mUploads) {
            if (u.getImageName().toLowerCase().contains(s.toLowerCase())) {
                temp.add(u);
            }
        }
        Adapter.update(temp);


    }
}
