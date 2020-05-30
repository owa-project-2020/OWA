package com.example.owa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class payment extends AppCompatActivity {

    EditText et2;
    TextView et1;
    Button b1, b2;
    FirebaseDatabase fd;
    DatabaseReference myref;
    Boolean found = false;
    workshopsView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        myref = FirebaseDatabase.getInstance().getReference().child("registered workshops");

        et1 = findViewById(R.id.et_useremail2);
        et2 = findViewById(R.id.et_card);
        b1 = findViewById(R.id.btnpay);
        b2 = findViewById(R.id.btncancel2);

        Bundle b = getIntent().getExtras();
        et1.setText(b.getString("user_email"));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (found.equals(true)) {
                            final String u_mail = et1.getText().toString();

                            //mail
                            //Getting content for email
                            String email = u_mail;
                            String subject = "Proof of payment";
                            String message = " Dear user..\n"
                                    + "You have payed for a workshop " + wv.getWorkName() + " Successfully \n"
                                    + "Please show your civil ID card in time of need\n" +
                                    "Have a good time";

                            //Creating SendMail object
                            GMailSender gsm = new GMailSender(payment.this, email, subject, message);
                            gsm.execute();
                            //end

                        } else {

                            Toast.makeText(payment.this, "Email Not Found", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(payment.this, user_view_workshop.class);
            }
        });
    }
}
