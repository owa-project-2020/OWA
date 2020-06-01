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

public class add_News extends AppCompatActivity {

    Uri FilePathUri;
    StorageReference storageReference;
    FirebaseDatabase fd;
    DatabaseReference databaseReference, newRef;
    int Image_Request_Code = 7;
    //long maxid = 000;//for autoincrement
    String article, Ndate, Ntitle, Ncategory;
    Button btnbrowse, btnupload;
    EditText category, newsdate, newsTitle, news;
    ImageView imgview;
    //CalendarView ;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__news);

        fd = FirebaseDatabase.getInstance();
        //newRef = fd.getReference("News Details");

        storageReference = FirebaseStorage.getInstance().getReference("news Images");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("News").push();

        btnbrowse = findViewById(R.id.btnbrowse);
        btnupload = findViewById(R.id.btnupload);
        newsdate = findViewById(R.id.et_Ndate);
        newsTitle = findViewById(R.id.txtTitle);
        news = findViewById(R.id.et_newdata);
        category = findViewById(R.id.txtCategory);
        imgview = findViewById(R.id.image_view);

        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select News Image"), Image_Request_Code);

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newsView nv = new newsView();

                Ndate = newsdate.getText().toString().trim();
                Ntitle = newsTitle.getText().toString().trim();
                article = news.getText().toString().trim();
                Ncategory = category.getText().toString().trim();

                nv.setDate(newsdate.getText().toString().trim());
                nv.setTitle(newsTitle.getText().toString().trim());
                nv.setArticle(news.getText().toString().trim());
                nv.setCategory(category.getText().toString().trim());

                UploadImage();
                UploadNews();

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
        newsdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(add_News.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void UploadNews() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int flag = 0;
                newsView nv = new newsView();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    nv = ds.getValue(newsView.class);

                    Toast.makeText(add_News.this, "News Successfully added", Toast.LENGTH_SHORT).show();
                    flag = 1;
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        newsdate.setText(sdf.format(myCalendar.getTime()));
    }

    //THIS IS OUTSIDE ONCREEATE..
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

                    String TempImageName = category.getText().toString().trim();

                    Toast.makeText(getApplicationContext(), "News Uploaded Successfully ", Toast.LENGTH_LONG).show();
                    @SuppressWarnings("VisibleForTests")
                    uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString());
                    String ImageUploadId = databaseReference.push().getKey();
                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                }
            });
        } else {

            Toast.makeText(add_News.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    /////////////////////////////MENU////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_news_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adminMain:
                Intent wo1 = new Intent(add_News.this, Admin_page.class);
                startActivity(wo1);
                return true;

            case R.id.addworkshps:
                Intent wo2 = new Intent(add_News.this, Register_workshop.class);
                startActivity(wo2);
                return true;

            case R.id.addadvertisement:
                Intent wo3 = new Intent(add_News.this, add_Advertisements.class);
                startActivity(wo3);
                return true;

            case R.id.modify:
                Intent wo4 = new Intent(add_News.this, update_workshop.class);
                startActivity(wo4);
                return true;

            case R.id.del_participant:
                Intent wo6 = new Intent(add_News.this, admin_del_registered_user.class);
                startActivity(wo6);
                return true;

            case R.id.exit:
                Intent wo5 = new Intent(add_News.this, adminLogin.class);
                startActivity(wo5);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ////////////////////////////////////MENU////////////////////////////////////////////////////////
}
