package com.example.owa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class registred_workshops extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseDatabase fdb;
    RecyclerView rv;
    ArrayList<userRegisterWorkshop> registered_list;
    registredworkshopsAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registred_workshops);

        rv = findViewById(R.id.rv2);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fdb = FirebaseDatabase.getInstance();
        reference = fdb.getReference("registered workshops");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                registered_list = new ArrayList<userRegisterWorkshop>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    userRegisterWorkshop p = dataSnapshot1.getValue(userRegisterWorkshop.class);
                    registered_list.add(p);
                }
                adp = new registredworkshopsAdapter(registered_list, registred_workshops.this);
                rv.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(registred_workshops.this, "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /////////////////////////////MENU////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.del_participant2workshop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adminMain:
                Intent wo1 = new Intent(registred_workshops.this, Admin_page.class);
                startActivity(wo1);
                return true;

            case R.id.addworkshps:
                Intent wo2 = new Intent(registred_workshops.this, Register_workshop.class);
                startActivity(wo2);
                return true;

            case R.id.addadvertisement:
                Intent wo3 = new Intent(registred_workshops.this, add_Advertisements.class);
                startActivity(wo3);
                return true;

            case R.id.addNews:
                Intent wo4 = new Intent(registred_workshops.this, add_News.class);
                startActivity(wo4);
                return true;

            case R.id.modify:
                Intent wo5 = new Intent(registred_workshops.this, update_workshop.class);
                startActivity(wo5);
                return true;

            case R.id.exit:
                finish();
                System.exit(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ////////////////////////////////////MENU////////////////////////////////////////////////////////

}
