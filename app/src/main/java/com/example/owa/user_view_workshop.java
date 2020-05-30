package com.example.owa;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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
    EditText find;
    ArrayList<workshopsView> list;
    userworkshopAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_workshop);

        find = findViewById(R.id.find);
        rv = findViewById(R.id.rv1);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fdb = FirebaseDatabase.getInstance();
        reference = fdb.getReference("WorkShops Details");

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
                adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(user_view_workshop.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        adp = new userworkshopAdapter(list, R.layout.workshop_layout);
        rv.setAdapter(adp);

        find.addTextChangedListener(new TextWatcher() {
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
                    adp.update(list);
                }
            }
        });

    }

    private void search(String s) {

        ArrayList<workshopsView> temp = new ArrayList<>();
        for (workshopsView u : list) {
            if (u.getWorkName().toLowerCase().contains(s.toLowerCase())) {
                temp.add(u);
            }
        }
        adp.update(temp);

    }
}
