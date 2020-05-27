package com.example.owa;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class user_view_workshop extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseDatabase fdb;
    RecyclerView rv;
    ArrayList<workshopsView> list;
    userworkshopAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_workshop);

        rv = findViewById(R.id.rv1);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fdb = FirebaseDatabase.getInstance();
        reference = fdb.getReference("Workshops Images");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<workshopsView>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    workshopsView p = dataSnapshot1.getValue(workshopsView.class);
                    list.add(p);
                }
                adp = new userworkshopAdapter(list, user_view_workshop.this);
                rv.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(user_view_workshop.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
