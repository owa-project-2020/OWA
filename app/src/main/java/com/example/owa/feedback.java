package com.example.owa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class feedback extends AppCompatActivity {

    EditText et1, et2, et3;
    Button b1, b2;
    String fbname, mail, msg;
    FirebaseDatabase fdb;
    DatabaseReference dr, databaseReference, newRef;

    //feedbackClass fbc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        et1 = findViewById(R.id.fb_Name);
        et2 = findViewById(R.id.fb_mail);
        et3 = findViewById(R.id.fb_msg);
        b1 = findViewById(R.id.fbBtn);
        b2 = findViewById(R.id.fbBtn2);


        fdb = FirebaseDatabase.getInstance();
        dr = fdb.getReference().child("Feedback").push();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fbname = et1.getText().toString().trim();
                mail = et2.getText().toString().trim();
                msg = et3.getText().toString().trim();

                dr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (fbname.isEmpty()) {
                            et1.setError("This is required field");
                            et1.requestFocus();
                        } else if (mail.isEmpty()) {
                            et2.setError("This is required field");
                            et2.requestFocus();
                        } else if (msg.isEmpty()) {
                            et3.setError("This is required field");
                            et3.requestFocus();
                        } else {
                           /* fbc =new feedbackClass();
                            fbc.setName(et1.getText().toString());
                            fbc.setMail(et2.getText().toString());
                            fbc.setMsg(et3.getText().toString());
                            databaseReference = fdb.getReference();
                            dr.setValue(fbc);*/
                            Toast.makeText(feedback.this, "Data is uploaded.", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(feedback.this, access_login.class);
                startActivity(i);
            }
        });
    }
}
