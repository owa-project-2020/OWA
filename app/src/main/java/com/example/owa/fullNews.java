package com.example.owa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class fullNews extends AppCompatActivity {

    TextView t1, t2;
    Button b1;
    FirebaseDatabase fdb;
    DatabaseReference databaseReference;
    ArrayList<newsView> newViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        b1 = findViewById(R.id.btnBack);

        fdb = FirebaseDatabase.getInstance();
        databaseReference = fdb.getReference("News");

        Bundle b = getIntent().getExtras();

        t1.setText(b.getString("title"));
        t2.setText(b.getString("article"));
        newViewList = new ArrayList<newsView>();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(fullNews.this, news.class);
                startActivity(back);
            }
        });

    }
}
