package com.example.owa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Admin_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        this.setTitle("Admin Access");

        final Button workshop = findViewById(R.id.addworkshop);
        Button news = findViewById(R.id.add_news);
        Button ads = findViewById(R.id.addadvertisements);
        Button modify = findViewById(R.id.updateordelete);
        Button workList = findViewById(R.id.work_list);
        Button workRegisteredList = findViewById(R.id.registered_list);
        Button delparty = findViewById(R.id.deleteParticipant);
        TextView logout = findViewById(R.id.out);

        workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Admin_page.this, Register_workshop.class);
                startActivity(n);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Admin_page.this, add_News.class);
                startActivity(n);
            }
        });

        ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Admin_page.this, add_Advertisements.class);
                startActivity(n);
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Admin_page.this, Workshop.class);
                startActivity(n);
            }
        });

        delparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Admin_page.this, registred_workshops.class);
                startActivity(n);
            }
        });


        workList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Admin_page.this, PDF_workshops.class);
                startActivity(n);
            }
        });
        workRegisteredList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Admin_page.this, PDF_registered_workshops.class);
                startActivity(n);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Admin_page.this, access.class);
                startActivity(n);
            }
        });

    }
}
