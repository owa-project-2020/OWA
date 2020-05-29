package com.example.owa;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class add_Advertisements extends AppCompatActivity {

    Uri FilePathUri;
    StorageReference storageReference;
    FirebaseDatabase fd;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    String Adcategory;
    Button btnbrowse, btnupload;
    EditText category;
    ImageView imgview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__advertisements);

        fd = FirebaseDatabase.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference("Advertisementds Images");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ADS").push();

        btnbrowse = findViewById(R.id.btnbrowse);
        btnupload = findViewById(R.id.btnupload);
        category = findViewById(R.id.txtCategory);
        imgview = findViewById(R.id.image_view);

        btnbrowse.setOnClickListener(new View.OnClickListener() {
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
                UploadImage();
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

                    String TempImageName = category.getText().toString().trim();

                    Toast.makeText(getApplicationContext(), "Advertisements Uploaded Successfully ", Toast.LENGTH_LONG).show();
                    @SuppressWarnings("VisibleForTests")
                    uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString());
                    String ImageUploadId = databaseReference.push().getKey();
                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                }
            });
        } else {

            Toast.makeText(add_Advertisements.this, "Please Select Image or Add Image Ads", Toast.LENGTH_LONG).show();

        }
    }

    /////////////////////////////MENU////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_advertisements_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adminMain:
                Intent wo1 = new Intent(add_Advertisements.this, Admin_page.class);
                startActivity(wo1);
                return true;

            case R.id.addworkshps:
                Intent wo2 = new Intent(add_Advertisements.this, Register_workshop.class);
                startActivity(wo2);
                return true;

            case R.id.addNews:
                Intent wo3 = new Intent(add_Advertisements.this, add_News.class);
                startActivity(wo3);
                return true;

            case R.id.modify:
                Intent wo4 = new Intent(add_Advertisements.this, update_workshop.class);
                startActivity(wo4);
                return true;

            case R.id.del_participant:
                Intent wo6 = new Intent(add_Advertisements.this, admin_del_registered_user.class);
                startActivity(wo6);
                return true;

            case R.id.exit:
                Intent wo5 = new Intent(add_Advertisements.this, adminLogin.class);
                startActivity(wo5);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ////////////////////////////////////MENU////////////////////////////////////////////////////////
}
