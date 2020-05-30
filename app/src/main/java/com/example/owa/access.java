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

public class access extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        this.setTitle("User Access");

        Button news = findViewById(R.id.news);
        Button help = findViewById(R.id.helps);

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(access.this, news.class);
                startActivity(n);
            }
        });
        /*help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(access.this, helpGuide.class);
                startActivity(n);
            }
        });*/
    }

    /////////////////////////////MENU////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_b4_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Login:
                Intent gotologin = new Intent(access.this, login.class);
                startActivity(gotologin);
                return true;

            case R.id.register:
                Intent gotoregister = new Intent(access.this, registration.class);
                startActivity(gotoregister);
                return true;

            case R.id.AdminLogin:
                Intent admin_login = new Intent(access.this, adminLogin.class);
                startActivity(admin_login);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ////////////////////////////////////MENU////////////////////////////////////////////////////////
}
