package com.example.owa;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Forget_Password extends AppCompatActivity {

    EditText userEmail;
    Button submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+.com";
    String decryptionPass;
    FirebaseDatabase fd;
    DatabaseReference myref;
    Boolean found = false;
    FirebaseAuth firebaseAuth;
    int flag;
    String umail, upass, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);

        userEmail = findViewById(R.id.et_mail);
        submit = findViewById(R.id.btn_submit);

        firebaseAuth = FirebaseAuth.getInstance();
        //fd=FirebaseDatabase.getInstance();


//        final String email=userEmail.getText().toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = userEmail.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {
                    Toast.makeText(Forget_Password.this, "Please Enter your Email", Toast.LENGTH_SHORT).show();
                    userEmail.requestFocus();
                    if (mail.equalsIgnoreCase(mail)) {
                        if (mail.matches(emailPattern)) {
                            Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    myref = FirebaseDatabase.getInstance().getReference().child("user");
                    myref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // pass="";
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                MyUser mu = d.getValue(MyUser.class);
                                if (mu.getEmail().equals(userEmail.getText().toString())) {
                                    found = true;
                                    try {
                                        pass = mu.getEmail();
                                        decryptionPass = decrypt_encrypt.decrypt(mu.getPassword());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    break;
                                }
                            }
                            if (found.equals(true)) {
                                final String u_mail = userEmail.getText().toString();

                                //mail
                                //Getting content for email
                                String email = u_mail;
                                String subject = " Forget password ";
                                String message = " Your Password is ( " + decryptionPass + " ) ";

                                //Creating SendMail object
                                GMailSender gsm = new GMailSender(Forget_Password.this, email, subject, message);
                                gsm.execute();
                                //end

                            } else {

                                Toast.makeText(Forget_Password.this, "Email Not Found", Toast.LENGTH_SHORT).show();

                            }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                /*pass="";
                                try {
                                    pass = decrypt_encrypt.decrypt(mu.password);

                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (mu.email.equals(userEmail.getText().toString()))
                                {
                                    flag = 1;
                                    break;
                                }
                            }
                            if (flag == 1)
                            {
                                sendMessage(userEmail.getText().toString(),pass);
                                Intent i = new Intent(Forget_Password.this,login.class);
                                startActivity(i);
                            } else {

                                Toast.makeText(Forget_Password.this, "Email Not Existing, Try with valid email", Toast.LENGTH_SHORT).show();
                            }*/
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                /*try {
                    GMailSender sender = new GMailSender("courseproject22020@gmail.com","course*2020");

                    sender.sendMail("Omani Women Association",
                            "Your Password is: "+upass,
                            "courseproject22020@gmail.com",umail);
                    Toast.makeText(getApplicationContext(),"Check your email...",Toast.LENGTH_LONG).show();

                }

                catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();

                }*/

            }
        });
    }

   /* private void sendMessage(final String email, final String pass) {

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("courseproject22020@gmail.com","course*2020");

                    sender.sendMail("Omani Women Association",
                            "Your Password is: "+pass,
                            "courseproject22020@gmail.com",email);
                    Toast.makeText(getApplicationContext(),"Check your email...",Toast.LENGTH_LONG).show();

                }

                catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                }
            }
        });
        sender.start();
    }*/
}

