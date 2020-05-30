package com.example.owa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class update_workshop extends AppCompatActivity {

    FirebaseDatabase fd;
    DatabaseReference databaseReference;
    Calendar myCalendar = Calendar.getInstance();
    String workshopName, newStrtTime, newEndTime, newDate;
    EditText workshopStrtTime, workshopEndTime, workshopDate;
    TextView workname;
    int flag;

    ArrayList<workshopsView> alist;

    //ArrayList<String>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_workshop);

        workshopStrtTime = findViewById(R.id.et_wstrtTime);
        workshopEndTime = findViewById(R.id.et_wendTime);
        workshopDate = findViewById(R.id.et_wdate);
        workname = findViewById(R.id.wname);
        Button update = findViewById(R.id.btnUpdate);
        Button delete = findViewById(R.id.btnDelete);


        fd = FirebaseDatabase.getInstance();
        databaseReference = fd.getReference("WorkShops Details");

        Bundle b = getIntent().getExtras();

        workname.setText(b.getString("workName"));
        workshopDate.setText(b.getString("wdate"));
        workshopStrtTime.setText(b.getString("wstartTime"));
        workshopEndTime.setText(b.getString("wendTime"));
        alist = new ArrayList<workshopsView>();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot d : dataSnapshot.getChildren()) {

                            workshopsView wv = d.getValue(workshopsView.class);

                            if (wv.getWorkName().equalsIgnoreCase(wv.getWorkName())) {

                                String workname = d.getKey();
                                workshopName = workname;
                                flag = 1;
                                break;
                            }
                        }

                        if (flag == 1) {

                            if (!workshopStrtTime.getText().toString().equals("")) {
                                newStrtTime = workshopStrtTime.getText().toString();
                                databaseReference.child(workshopName).child("wstartTime").setValue(newStrtTime);
                            }
                            if (!workshopEndTime.getText().toString().equals("")) {
                                newEndTime = workshopEndTime.getText().toString();
                                databaseReference.child(workshopName).child("wendTime").setValue(newEndTime);
                            }

                            if (!workshopDate.getText().toString().equals("")) {
                                newDate = workshopDate.getText().toString();
                                databaseReference.child(workshopName).child("wdate").setValue(newDate);
                            }

                            Toast.makeText(update_workshop.this, workname.getText().toString() + " is Updated.", Toast.LENGTH_LONG).show();

                            Intent gotoWorkshopList = new Intent(update_workshop.this, Workshop.class);
                            startActivity(gotoWorkshopList);
                        } else {
                            Toast.makeText(update_workshop.this, "Invalid Workshop Name ", Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {

                            workshopsView wv = d.getValue(workshopsView.class);

                            if (wv.getWorkName().equalsIgnoreCase(wv.getWorkName())) {

                                String workname = d.getKey();
                                workshopName = workname;
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 1) {
                            databaseReference.child(workshopName).removeValue();
                            Toast.makeText(update_workshop.this, "this Test ID " + workname.getText().toString() + " been deleted", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        workshopDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(update_workshop.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        workshopDate.setText(sdf.format(myCalendar.getTime()));
    }

    /////////////////////////////MENU////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_workshop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adminMain:
                Intent wo1 = new Intent(update_workshop.this, Admin_page.class);
                startActivity(wo1);
                return true;

            case R.id.addworkshps:
                Intent wo2 = new Intent(update_workshop.this, Register_workshop.class);
                startActivity(wo2);
                return true;

            case R.id.addadvertisement:
                Intent wo3 = new Intent(update_workshop.this, add_Advertisements.class);
                startActivity(wo3);
                return true;

            case R.id.modify:
                Intent wo4 = new Intent(update_workshop.this, add_News.class);
                startActivity(wo4);
                return true;

            case R.id.del_participant:
                Intent wo6 = new Intent(update_workshop.this, admin_del_registered_user.class);
                startActivity(wo6);
                return true;

            case R.id.exit:
                Intent wo5 = new Intent(update_workshop.this, adminLogin.class);
                startActivity(wo5);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ////////////////////////////////////MENU////////////////////////////////////////////////////////

}

