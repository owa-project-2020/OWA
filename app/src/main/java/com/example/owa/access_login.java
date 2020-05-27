package com.example.owa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class access_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_login);

        this.setTitle("User Access");

        Button news = findViewById(R.id.news);
        Button photo = findViewById(R.id.gallery);
        Button services = findViewById(R.id.services);
        Button help = findViewById(R.id.help);

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(access_login.this, news.class);
                startActivity(n);
            }
        });
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(access_login.this, services.class);
                startActivity(n);
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(access_login.this, gallery.class);
                startActivity(n);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(access_login.this, helpGuide.class);
                startActivity(n);
            }
        });

    }

    /////////////////////////////MENU////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_after_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                Intent go2reset = new Intent(access_login.this, reset_password.class);
                startActivity(go2reset);
                return true;

            case R.id.logout:
                logout();/*
                Intent logut=new Intent(access_login.this,access.class);
                startActivity(logut);*/
                return true;

            case R.id.feed:
                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{"courseproject22020@gmail.com"});
                it.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                it.setType("message/rfc822");
                startActivity(Intent.createChooser(it, "Choose Mail App"));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ////////////////////////////////////MENU////////////////////////////////////////////////////////
    private void logout() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(access_login.this, access.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
