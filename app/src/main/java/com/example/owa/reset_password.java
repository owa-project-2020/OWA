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

public class reset_password extends AppCompatActivity {

    int flag;
    FirebaseDatabase fd;
    DatabaseReference myRef;
    String un, ukey, new_password, umail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Button renew = findViewById(R.id.btn_renew);
        final EditText etold = findViewById(R.id.recent_pwd);
        final EditText etnew = findViewById(R.id.new_pwd);
        final EditText userMail = findViewById(R.id.usermail);

        fd = FirebaseDatabase.getInstance();
        myRef = fd.getReference("user");
        //String ff = getIntent().getStringExtra("email");

        umail = GlobalA.abc;

        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                final String umail = userMail.getText().toString();
                String oldpass = etold.getText().toString();
                String newpass = etnew.getText().toString();

                if (oldpass.isEmpty() && newpass.isEmpty()) {
                    Toast.makeText(reset_password.this, "Please fill all feilds.", Toast.LENGTH_LONG).show();

                } else if (newpass.isEmpty()) {
                    Toast.makeText(reset_password.this, "Please Enter new password.", Toast.LENGTH_LONG).show();
                } else if (oldpass.isEmpty()) {
                    Toast.makeText(reset_password.this, "Please  Enter old password.", Toast.LENGTH_LONG).show();
                } else if (umail.isEmpty()) {
                    Toast.makeText(reset_password.this, "Please Enter your registered email.", Toast.LENGTH_LONG).show();
                }

                // else if (!PASSWORD_PATTERN.matcher(ed_npass.getText().toString()).matches()) {
                //  Toast.makeText(changepasswordA.this, "Password should combines letters and numbers and length not less than 7 digits.", Toast.LENGTH_SHORT).show();

                //  }
                else {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String encpass = etold.getText().toString();
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                MyUser u = d.getValue(MyUser.class);
                                String pass = "";
                                try {
                                    pass = decrypt_encrypt.decrypt(u.password);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (etold.getText().toString().equalsIgnoreCase(pass) && u.email.equalsIgnoreCase(umail)) {
                                    String key = d.getKey();
                                    ukey = key;
                                    flag = 1;
                                    break;
                                }
                            }
                            if (flag == 1) {
                                if (flag == 1) {
                                    try {
                                        new_password = decrypt_encrypt.encrypt(etnew.getText().toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    myRef.child(ukey).child("password").setValue(new_password);
                                    Intent gg1 = new Intent(reset_password.this, login.class);
                                    startActivity(gg1);
                                    Toast.makeText(reset_password.this, "Password Changed.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(reset_password.this, "Invalid Current Password", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });
    }
}