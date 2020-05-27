package com.example.owa;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register_workshop extends AppCompatActivity {

    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference, newRef, myRef;
    FirebaseDatabase fd;

    int Image_Request_Code = 7;
    //long maxid=000;//for autoincrement
    String WorkshopName, Workshopstrt, Workshopend, Workshopdate, WorkshopRoom, WorkshopFee, WorkshopAge, WorkshopSeats, WorkshopCategory;
    Button btnbrowse, btnupload;
    EditText txtdata, workName, workstrtTime, workendTime, workDate, workRoom, workFee, workAge, workSeats, workCat;
    ImageView imgview;
    workshopsView wv;
    //CalendarView ;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_workshop);

        fd = FirebaseDatabase.getInstance();
        myRef = fd.getReference("WorkShops Details");

//Calendaer


        storageReference = FirebaseStorage.getInstance().getReference("Images");
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Workshops Images").push();
        newRef = FirebaseDatabase.getInstance().getReference().child("Workshops Images").push();

        btnbrowse = findViewById(R.id.btnbrowse);
        btnupload = findViewById(R.id.btnupload);
        txtdata = findViewById(R.id.txtdata);
        imgview = findViewById(R.id.image_view);

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
        btnbrowse.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
                    }
                });
        btnupload.setOnClickListener(new View.OnClickListener() {
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

                wv.setWorkName(workName.getText().toString().trim());
                wv.setWorkCategory(workCat.getText().toString().trim());
                wv.setWstartTime(workstrtTime.getText().toString().trim());
                wv.setWendTime(workendTime.getText().toString().trim());
                wv.setWdate(workDate.getText().toString().trim());
                wv.setWroomNo(workRoom.getText().toString().trim());
                wv.setWfee(workFee.getText().toString().trim());
                wv.setWage(workAge.getText().toString().trim());
                wv.setwNoSeats(workSeats.getText().toString().trim());

                databaseReference = fd.getReference();
                newRef.setValue(wv);
                /*DatabaseReference newRef = ref.child("Person").push();
                newRef.setValue(person);*/

                UploadWorkInfo();
                UploadImage();

            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        workDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Register_workshop.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        workDate.setText(sdf.format(myCalendar.getTime()));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void UploadImage() {

        if (FilePathUri != null) {

            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String TempImageName = txtdata.getText().toString().trim();
                    Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                    @SuppressWarnings("VisibleForTests")
                    uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString());
                    String ImageUploadId = databaseReference.push().getKey();
                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                }
            });
        } else {

            Toast.makeText(Register_workshop.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
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
