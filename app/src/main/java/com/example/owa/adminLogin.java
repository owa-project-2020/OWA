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

public class adminLogin extends AppCompatActivity {

    FirebaseDatabase fd;
    DatabaseReference myRef;
    String adun, adpass;

    int flag;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        fd = FirebaseDatabase.getInstance();
        this.setTitle("Admin Login");

        final EditText admin_name = findViewById(R.id.et_Admin_name);
        final EditText admin_password = findViewById(R.id.et_adminpass);
        Button btnlogin = findViewById(R.id.btn_adminlogin);
        Button forgot = findViewById(R.id.txt_admin_4getpass);
        //Button adminreg = findViewById(R.id.btn_adminRegister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String adun = admin_name.getText().toString();
                final String adpass = admin_password.getText().toString();

                if (adun.isEmpty()) {
                    admin_name.setError("Please enter your name");
                    admin_name.requestFocus();
                } else if (adpass.isEmpty()) {
                    admin_password.setError("Please enter your password");
                    admin_password.requestFocus();
                } else {

                    myRef = fd.getReference("AdminOWA");
                    //signIn(un,pass);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            flag = 0;
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                admin value = d.getValue(admin.class);

                                if (value.adname.equalsIgnoreCase(adun)) {
                                    LoginAdmin(value, adun, adpass);
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
        /*adminreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminReg = new Intent(adminLogin.this, adminRegister.class);
                startActivity(adminReg);
            }
        });*/

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(adminLogin.this, Forget_Password.class);
                startActivity(f);
            }
        });
    }

    private void LoginAdmin(admin value, String adun, String adpass) {
        // START DECRYPTION CODE
        try {
            String decrypt = decrypt_encrypt.decrypt(value.getAdpass());

            if (adpass.equals(decrypt)) {
                flag = 1;

                Toast.makeText(adminLogin.this, "Succefful Login", Toast.LENGTH_SHORT).show();
                Intent gotoaadminpage = new Intent(adminLogin.this, Admin_page.class);
                startActivity(gotoaadminpage);

            } else {

                Toast.makeText(adminLogin.this, "Wrong Info..", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //ENDS DECRYPTION
    }
}

