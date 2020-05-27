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

public class login extends AppCompatActivity {

    FirebaseDatabase fd;
    DatabaseReference myRef;
    String un, pass;

    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        fd = FirebaseDatabase.getInstance();

        this.setTitle("Login");

        final EditText user_name = findViewById(R.id.et_user_name);
        final EditText password = findViewById(R.id.et_pass);
        Button btnSignIn = findViewById(R.id.btn_login);
        Button forgot = findViewById(R.id.txt_4getp);
        Button reset = findViewById(R.id.txt_reset);
        TextView tvregister = findViewById(R.id.registertxt);
        final TextView adminLog = findViewById(R.id.adminLogintxt);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String un = user_name.getText().toString();
                final String pass = password.getText().toString();

                if (un.isEmpty()) {
                    user_name.setError("Please enter username");
                    user_name.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else {

                    myRef = fd.getReference("user");
                    //signIn(un,pass);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            flag = 0;
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                MyUser value = d.getValue(MyUser.class);

                                if (value.username.equalsIgnoreCase(un)) {
                                    SignIn(value, un, pass);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoforget = new Intent(login.this, Forget_Password.class);
                startActivity(gotoforget);
            }
        });
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregister = new Intent(login.this, registration.class);
                startActivity(gotoregister);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoreset = new Intent(login.this, reset_password.class);
                startActivity(gotoreset);
            }
        });
        adminLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAdminLogin = new Intent(login.this, adminLogin.class);
                startActivity(gotoAdminLogin);
            }
        });

    }

    private void SignIn(MyUser value, String un, String pass) {
        // START DECRYPTION CODE
        try {
            String decrypt = decrypt_encrypt.decrypt(value.getPassword());

            if (pass.equals(decrypt)) {
                flag = 1;

                Toast.makeText(login.this, "Succefful Login", Toast.LENGTH_SHORT).show();
                Intent gotoaccess = new Intent(login.this, access_login.class);
                startActivity(gotoaccess);

            } else {

                Toast.makeText(login.this, "Wrong Info..", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //ENDS DECRYPTION
    }
}
