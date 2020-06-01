package com.example.owa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class userRegiseterToWorkshop extends AppCompatActivity {


    //CalendarView ;
    Calendar myCalendar = Calendar.getInstance();
    Calendar today = Calendar.getInstance();

    FirebaseDatabase fd, db;
    DatabaseReference myRef, workReference, newRef;

    EditText e3, e4, e5;
    TextView e1, e2, e6;
    Button b1, b2;
    String name, workshopname, dob, usermail, worksgopdate, wprice;

    userRegisterWorkshop ur;
    workshopsView wv;

    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregisetertoworkshop);

        fd = FirebaseDatabase.getInstance();
        newRef = FirebaseDatabase.getInstance().getReference().child("registered workshops").push();
        db = FirebaseDatabase.getInstance();
        workReference = db.getReference("Workshops Images");

        e1 = findViewById(R.id.et_wname);
        e2 = findViewById(R.id.et_wdate);
        e3 = findViewById(R.id.et_uname);
        e4 = findViewById(R.id.et_birthDate);
        e5 = findViewById(R.id.et_umaile);
        e6 = findViewById(R.id.et_wprice);

        b1 = findViewById(R.id.cancel);
        b2 = findViewById(R.id.submit);

        Bundle b = getIntent().getExtras();
        e1.setText(b.getString("workName"));
        e2.setText(b.getString("wdate"));
        e6.setText(b.getString("wfee") + " OMR");

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userRgisterWorkshop Class
                ur = new userRegisterWorkshop();
                //workshopsView Class
                wv = new workshopsView();

                workshopname = e1.getText().toString().trim();
                worksgopdate = e2.getText().toString().trim();
                name = e3.getText().toString().trim();
                dob = e4.getText().toString().trim();
                usermail = e5.getText().toString().trim();
                wprice = e6.getText().toString().trim();

                ur.setWorkshop_name(e1.getText().toString().trim());
                ur.setWorkshop_date(e2.getText().toString().trim());
                ur.setUser_Name(e3.getText().toString().trim());
                ur.setUser_dob(e4.getText().toString().trim());
                ur.setUser_email(e5.getText().toString().trim());

                workReference = fd.getReference();
                newRef.setValue(ur);
                calculateAge();


            }
        });

////////**************************C*A*N*C*E*L*B*U*T*T*O*N***************************************////////////////
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewWorks = new Intent(userRegiseterToWorkshop.this, user_view_workshop.class);
                startActivity(viewWorks);
            }
        });
////////**************************C*A*N*C*E*L*B*U*T*T*O*N***************************************////////////////

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };
        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(userRegiseterToWorkshop.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }

    private void calculateAge() {
        //Formulla to caculate age
        int yearOfBirth = Integer.parseInt(e4.getText().toString().substring(6, 10));

        Calendar c = Calendar.getInstance();
        int age = c.get(Calendar.YEAR) - yearOfBirth;
        // Toast.makeText(userRegiseterToWorkshop.this, c.get(Calendar.YEAR) + " - " + yearOfBirth + " = " + age, Toast.LENGTH_SHORT).show();
        if (age < 15) {
            Toast.makeText(userRegiseterToWorkshop.this, "Your Age must be grater 15", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(userRegiseterToWorkshop.this, payment.class);
            i.putExtra("user_email", e5.getText().toString());
            startActivity(i);
            uploadUserRegister();
        }
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        e4.setText(sdf.format(myCalendar.getTime()));
    }

    private void uploadUserRegister() {


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                flag = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ur = ds.getValue(userRegisterWorkshop.class);
                    if (ur.user_email.isEmpty()) {
                        Toast.makeText(userRegiseterToWorkshop.this, "Please Enter your e-mail !!", Toast.LENGTH_SHORT).show();
                        flag = 1;
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
