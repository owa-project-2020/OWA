package com.example.owa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class services extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        this.setTitle("Services");
        Button viewadv = findViewById(R.id.btn_viewadv);
        Button workshop = findViewById(R.id.btn_workshop);


        viewadv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(services.this, Viewadv.class);
                startActivity(n);
            }
        });
        workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(services.this, user_view_workshop.class);
                startActivity(n);
            }
        });
    }
}
