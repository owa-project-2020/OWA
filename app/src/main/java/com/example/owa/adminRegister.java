package com.example.owa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminRegister extends AppCompatActivity {

    String adminName, adminPassword, adminMail;
    FirebaseDatabase fd;
    DatabaseReference myRef;

    String encrypted = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        this.setTitle("Admin Registration");

        final EditText adname = findViewById(R.id.et_adname);
        final EditText adpwd = findViewById(R.id.et_adpass);
        final EditText admail = findViewById(R.id.et_admail);
        Button register = findViewById(R.id.btn_adregister);

        fd = FirebaseDatabase.getInstance();
        myRef = fd.getReference("AdminOWA");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminName = adname.getText().toString().trim();
                adminPassword = adpwd.getText().toString().trim();
                adminMail = admail.getText().toString().trim();

                if (adminName.isEmpty())
                    Toast.makeText(adminRegister.this, "Enter User name!", Toast.LENGTH_SHORT).show();

                   /* else if (!(username.matches(usernamePattern)))
                        Toast.makeText(adminRegister.this, "Invalid user name", Toast.LENGTH_SHORT).show();
*/
                else if (adminPassword.isEmpty())
                    Toast.makeText(adminRegister.this, "Enter password!", Toast.LENGTH_SHORT).show();

                else if (adminPassword.length() < 5)
                    Toast.makeText(adminRegister.this, "Password should be 5 digits!", Toast.LENGTH_SHORT).show();

                else if (adminMail.equalsIgnoreCase(""))
                    Toast.makeText(adminRegister.this, "Enter Email!", Toast.LENGTH_SHORT).show();

               /* else if(!adminMail.matches(emailPattern))
                    Toast.makeText(adminRegister.this, "Invalid email address", Toast.LENGTH_SHORT).show();*/

                else {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int flag = 0;
                            admin ad = new admin();
                            for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                ad = ds1.getValue(admin.class);

                               /* if (ad.adname.equalsIgnoreCase(adminName)) {
                                    Toast.makeText(adminRegister.this, "User Already Exists!", Toast.LENGTH_SHORT).show();
                                    flag = 1;
                                    break;
                                }*/
                            }
                            // START ENCRYPTION CODE
                            try {
                                if (flag == 0) {
                                    encrypted = decrypt_encrypt.encrypt(adminPassword);
                                    admin r = new admin(adminName, adminMail, encrypted);
                                    String key = myRef.push().getKey();
                                    myRef.child(key).setValue(r);

                                    Toast.makeText(adminRegister.this, "Registered!", Toast.LENGTH_SHORT).show();
                                    Intent gotologin = new Intent(adminRegister.this, login.class);
                                    startActivity(gotologin);
                                } else {
                                    Toast.makeText(adminRegister.this, "Fail  Register!", Toast.LENGTH_SHORT).show();
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

    }
}
