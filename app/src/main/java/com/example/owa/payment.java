package com.example.owa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class payment extends AppCompatActivity {

    EditText et2;
    TextView et1;
    Button b1, b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        et1 = findViewById(R.id.et_useremail2);
        et2 = findViewById(R.id.et_card);
        b1 = findViewById(R.id.btnpay);
        b2 = findViewById(R.id.btncancel2);

        Bundle b = getIntent().getExtras();
        et1.setText(b.getString("user_email"));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
