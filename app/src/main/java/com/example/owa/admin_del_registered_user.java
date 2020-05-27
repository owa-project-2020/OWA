package com.example.owa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_del_registered_user extends AppCompatActivity {

    TextView rn, rm, rw;
    Button b1;
    FirebaseDatabase fd;
    DatabaseReference databaseReference;
    String registered_Name, registered_email, registered_workshop;
    Boolean found = false;
    int flag = 0;
    ArrayList<userRegisterWorkshop> registered_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_del_registered_user);

        rn = findViewById(R.id.registeredName);
        rm = findViewById(R.id.registeredEmail);
        rw = findViewById(R.id.registeredWorkshop);
        b1 = findViewById(R.id.userDelete);

        fd = FirebaseDatabase.getInstance();
        databaseReference = fd.getReference("registered workshops");

        Bundle b = getIntent().getExtras();

        rn.setText(b.getString("user_Name"));
        rm.setText(b.getString("user_email"));
        rw.setText(b.getString("workshop_name"));
        registered_list = new ArrayList<userRegisterWorkshop>();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            userRegisterWorkshop ur = d.getValue(userRegisterWorkshop.class);
                            if (ur.getUser_Name().equals(rn.getText().toString()) && ur.getUser_email().equals(rm.getText().toString())) {
                                String partyName = d.getKey();
                                registered_Name = partyName;
                                flag = 1;
                                break;
                            }
                            if (flag == 1) {
                                databaseReference.child(registered_Name).removeValue();
                                if (found.equals(true)) {
                                    final String partyMail = rm.getText().toString();

                                    //mail
                                    //Getting content for email
                                    String email = partyMail;
                                    String subject = "Your Participant to workshop is deleted";
                                    String message = " Dear user...Your Participant to " + registered_workshop + "has been deleted, Please contact us to turn back your payed money";

                                    //Creating SendMail object
                                    GMailSender gsm = new GMailSender(admin_del_registered_user.this, email, subject, message);
                                    gsm.execute();
                                    //end

                                } else {

                                    Toast.makeText(admin_del_registered_user.this, "Email Not Found", Toast.LENGTH_SHORT).show();

                                }
                                Toast.makeText(admin_del_registered_user.this, "deleted", Toast.LENGTH_SHORT).show();
                                Intent n = new Intent(admin_del_registered_user.this, registred_workshops.class);
                                startActivity(n);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    /////////////////////////////MENU////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.del_participant2workshop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adminMain:
                Intent wo1 = new Intent(admin_del_registered_user.this, Admin_page.class);
                startActivity(wo1);
                return true;

            case R.id.addworkshps:
                Intent wo2 = new Intent(admin_del_registered_user.this, Register_workshop.class);
                startActivity(wo2);
                return true;

            case R.id.addadvertisement:
                Intent wo3 = new Intent(admin_del_registered_user.this, add_Advertisements.class);
                startActivity(wo3);
                return true;

            case R.id.addNews:
                Intent wo4 = new Intent(admin_del_registered_user.this, add_News.class);
                startActivity(wo4);
                return true;

            case R.id.modify:
                Intent wo5 = new Intent(admin_del_registered_user.this, update_workshop.class);
                startActivity(wo5);
                return true;

            case R.id.exit:
                Intent wo6 = new Intent(admin_del_registered_user.this, adminLogin.class);
                startActivity(wo6);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ////////////////////////////////////MENU////////////////////////////////////////////////////////
}
