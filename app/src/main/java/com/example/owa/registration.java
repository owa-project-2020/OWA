package com.example.owa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registration extends AppCompatActivity {

    String username, password, email, phone;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String usernamePattern = "[a-zA-Z]";

    FirebaseDatabase fd;
    DatabaseReference myRef;

    String encrypted = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        this.setTitle("Registration");

        final EditText uname = findViewById(R.id.et_name);
        final EditText pwd = findViewById(R.id.et_pass);
        final EditText uphone = findViewById(R.id.et_phone);
        final EditText mail = findViewById(R.id.et_mail);
        final TextView loginTv = findViewById(R.id.logtxt);

        Button reg = findViewById(R.id.btn_submit);
        Button cancel = findViewById(R.id.btn_cancel);

        fd = FirebaseDatabase.getInstance();
        myRef = fd.getReference("user");

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = uname.getText().toString().trim();
                password = pwd.getText().toString().trim();
                email = mail.getText().toString().trim();
                phone = uphone.getText().toString().trim();

                if (username.isEmpty())
                    Toast.makeText(registration.this, "Enter User name!", Toast.LENGTH_SHORT).show();

                   /* else if (!(username.matches(usernamePattern)))
                        Toast.makeText(registration.this, "Invalid user name", Toast.LENGTH_SHORT).show();
*/
                else if (password.isEmpty())
                    Toast.makeText(registration.this, "Enter password!", Toast.LENGTH_SHORT).show();

                else if (password.length() < 5)
                    Toast.makeText(registration.this, "Password should be 5 digits!", Toast.LENGTH_SHORT).show();

                else if (email.equalsIgnoreCase(""))
                    Toast.makeText(registration.this, "Enter Email!", Toast.LENGTH_SHORT).show();

                else if (!email.matches(emailPattern))
                    Toast.makeText(registration.this, "Invalid email address", Toast.LENGTH_SHORT).show();

                else if (phone.equalsIgnoreCase(""))
                    Toast.makeText(registration.this, "Enter phone number!", Toast.LENGTH_SHORT).show();

                else if (phone.length() < 8)
                    Toast.makeText(registration.this, "Phone No should be 8 digits!", Toast.LENGTH_SHORT).show();

                else if (!phone.startsWith("7") && !phone.startsWith("9"))
                    Toast.makeText(registration.this, "Phone no must start with 7 or 9!", Toast.LENGTH_SHORT).show();

                else {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int flag = 0;
                            MyUser u1 = new MyUser();
                            for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                u1 = ds1.getValue(MyUser.class);


                                if (u1.username.equalsIgnoreCase(username)) {
                                    Toast.makeText(registration.this, "User Already Exists!", Toast.LENGTH_SHORT).show();
                                    flag = 1;
                                    break;
                                }
                            }
                            // START ENCRYPTION CODE
                            try {
                                if (flag == 0) {
                                    encrypted = decrypt_encrypt.encrypt(password);
                                    MyUser r = new MyUser(username, encrypted, email, phone);
                                    String key = myRef.push().getKey();
                                    myRef.child(key).setValue(r);

                                    Toast.makeText(registration.this, "Registered!", Toast.LENGTH_SHORT).show();
                                    Intent gotologin = new Intent(registration.this, login.class);
                                    startActivity(gotologin);

                                } else {
                                    Toast.makeText(registration.this, "Fail  Register!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //ENDS ENCRYPTION

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }

                    });

                }

            }
        });

//  CLEAR ALL FIELDS...
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                uname.setText("");
                pwd.setText("");
                uphone.setText("");
                mail.setText("");
            }
        });
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoLogin = new Intent(registration.this, login.class);
                startActivity(gotoLogin);
            }
        });


    }

}




