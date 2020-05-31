package com.example.owa;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Register_workshop extends AppCompatActivity {

    //Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference, newRef, myRef;
    FirebaseDatabase fd;

    int Image_Request_Code = 7;
    //long maxid=000;//for autoincrement
    String WorkshopName, Workshopstrt, Workshopend, Workshopdate, WorkshopRoom, WorkshopFee, WorkshopAge, WorkshopSeats, WorkshopCategory;
    //TIME VIEW...
    public TimePicker timePicker1;
    EditText workName, workstrtTime, workendTime, workDate, workRoom, workFee, workAge, workSeats, workCat;
    //ImageView imgview;
    workshopsView wv;
    Button upload, cancel;
    //CalendarView ;
    Calendar myCalendar = Calendar.getInstance();
    String wname = "[a-zA-Z]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_workshop);

        fd = FirebaseDatabase.getInstance();
        //myRef = fd.getReference("WorkShops Details");

//Calendaer


        //storageReference = FirebaseStorage.getInstance().getReference("Images");
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Workshops Images").push();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("WorkShops Details").push();

        upload = findViewById(R.id.upload);
        cancel = findViewById(R.id.CANCEL);
        //txtdata = findViewById(R.id.txtdata);
        //imgview = findViewById(R.id.image_view);

        workName = findViewById(R.id.et_wname);
        workCat = findViewById(R.id.et_category);
        workstrtTime = findViewById(R.id.et_wstrtTime);
        workendTime = findViewById(R.id.et_wendTime);
        workDate = findViewById(R.id.et_wdate);
        workRoom = findViewById(R.id.et_wRoomNo);
        workFee = findViewById(R.id.et_wFee);
        workAge = findViewById(R.id.et_wAge);
        workSeats = findViewById(R.id.et_wSeats);
        wv = new workshopsView();

        cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Register_workshop.this, Admin_page.class);
                        startActivity(intent);
                    }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WorkshopName = workName.getText().toString().trim();
                WorkshopCategory = workCat.getText().toString().trim();
                Workshopstrt = workstrtTime.getText().toString().trim();
                Workshopend = workendTime.getText().toString().trim();
                Workshopdate = workDate.getText().toString().trim();
                WorkshopRoom = workRoom.getText().toString().trim();
                WorkshopFee = workFee.getText().toString().trim();
                WorkshopAge = workAge.getText().toString().trim();
                WorkshopSeats = workSeats.getText().toString().trim();

                if (WorkshopName.isEmpty())
                    Toast.makeText(Register_workshop.this, "Write workshop Name", Toast.LENGTH_SHORT).show();

                else if (WorkshopCategory.isEmpty())
                    Toast.makeText(Register_workshop.this, "Write workshop Category", Toast.LENGTH_SHORT).show();
                else if (!(WorkshopCategory.matches(wname)))
                    Toast.makeText(Register_workshop.this, "Workshop Categoty should be contains letters only", Toast.LENGTH_SHORT).show();

                else if (Workshopstrt.isEmpty())
                    Toast.makeText(Register_workshop.this, "Plese select start time", Toast.LENGTH_SHORT).show();

                else if (Workshopend.isEmpty())
                    Toast.makeText(Register_workshop.this, "Plese select end time", Toast.LENGTH_SHORT).show();
                else if (workstrtTime.equals(workendTime))
                    Toast.makeText(Register_workshop.this, "Start time can't equals end time!!!", Toast.LENGTH_SHORT).show();

                else if (Workshopdate.isEmpty())
                    Toast.makeText(Register_workshop.this, "Plese select a Date", Toast.LENGTH_SHORT).show();


                else if (WorkshopRoom.isEmpty() || WorkshopRoom.equals("0"))
                    Toast.makeText(Register_workshop.this, "Write workshop Room Number and shouldn't be 0", Toast.LENGTH_SHORT).show();

                else if (WorkshopFee.isEmpty() && !WorkshopFee.contains("OMR"))
                    Toast.makeText(Register_workshop.this, "Determine the workshop Fee with OMR currency", Toast.LENGTH_SHORT).show();

                else if (WorkshopAge.isEmpty())
                    Toast.makeText(Register_workshop.this, "Determine the workshop Age", Toast.LENGTH_SHORT).show();

                else if (WorkshopSeats.isEmpty() || WorkshopSeats.equals("0"))
                    Toast.makeText(Register_workshop.this, "Determine Number of Seats", Toast.LENGTH_SHORT).show();

                else {
                    wv.setWorkName(workName.getText().toString().trim());
                    wv.setWorkCategory(workCat.getText().toString().trim());
                    wv.setWstartTime(workstrtTime.getText().toString().trim());
                    wv.setWendTime(workendTime.getText().toString().trim());
                    wv.setWdate(workDate.getText().toString().trim());
                    wv.setWroomNo(workRoom.getText().toString().trim());
                    wv.setWfee(workFee.getText().toString().trim());
                    wv.setWage(workAge.getText().toString().trim());
                    wv.setwNoSeats(workSeats.getText().toString().trim());

                    newRef = fd.getReference();
                    databaseReference.setValue(wv);

                    UploadWorkInfo();
                    updateTime();
                }
            }
        });
        /*final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };*/
        workDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(Register_workshop.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date1 = null;
                        try {
                            date1 = sdf.parse(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            Date date2 = sdf.parse(mYear + "/" + (mMonth + 1) + "/" + mDay);
                            if (date1.compareTo(date2) >= 0) {
                                workDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                                Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                                String dayOfWeek = simpledateformat.format(date);

                            } else if (date1.compareTo(date2) < 0) {
                                Toast.makeText(Register_workshop.this, "Correct the Date  ", Toast.LENGTH_LONG).show();
                                //   workDate.setText("");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
                d.show();
            }
        });
        //TO SHOW TIME PICKER DIALOG IN WORKSHOP START TIME EDITTEXT....
        workstrtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Register_workshop.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String amPm;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        workstrtTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                        if (hourOfDay != 8 || hourOfDay != 9 || hourOfDay != 10) {
                            Toast.makeText(Register_workshop.this, "Workshop End Time must from 10,12,2 pm..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });

        //TO SHOW TIME PICKER DIALOG IN WORKSHOP END TIME EDITTEXT....
        workendTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Register_workshop.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String amPm;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        workendTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                        if (hourOfDay != 10 || hourOfDay != 12 || hourOfDay != 2) {
                            Toast.makeText(Register_workshop.this, "Workshop End Time must from 10,12,2 pm..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        workDate.setText(sdf.format(myCalendar.getTime()));

    }


    public void updateTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date intime = null;
        try {
            intime = sdf.parse(Workshopstrt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date outTime = null;
        try {
            outTime = sdf.parse(Workshopend);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (intime.before(outTime)) {
            Toast.makeText(Register_workshop.this, "correct Time Selection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Register_workshop.this, "Check selected Time", Toast.LENGTH_SHORT).show();
        }


    }


    private void UploadWorkInfo() {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int flag = 0;
                workshopsView wv = new workshopsView();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    wv = ds.getValue(workshopsView.class);

                    if (wv.getWorkName().equals(WorkshopName)) {
                        Toast.makeText(Register_workshop.this, "This Workshop is Already Exists!", Toast.LENGTH_SHORT).show();
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
                Intent wo1 = new Intent(Register_workshop.this, Admin_page.class);
                startActivity(wo1);
                return true;

            case R.id.add:
                Intent wo2 = new Intent(Register_workshop.this, add_Advertisements.class);
                startActivity(wo2);
                return true;

            case R.id.addNews:
                Intent wo3 = new Intent(Register_workshop.this, add_News.class);
                startActivity(wo3);
                return true;

            case R.id.modify:
                Intent wo4 = new Intent(Register_workshop.this, update_workshop.class);
                startActivity(wo4);
                return true;

            case R.id.exit:
                finish();
                System.exit(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ////////////////////////////////////MENU////////////////////////////////////////////////////////
}
