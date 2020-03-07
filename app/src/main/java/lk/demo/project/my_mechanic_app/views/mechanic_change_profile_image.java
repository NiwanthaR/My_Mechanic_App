package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class mechanic_change_profile_image extends AppCompatActivity {

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;

    //ImageView
    private ImageView uplosad_new_img;
    private ImageView previous_image;

    //Upload Image
    private Button upload_data;

    //progress dialog
    private ProgressDialog progressDialog;

    //Image upload part
    private static int PICK_IMG=123;
    private Uri image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_change_profile_image);

        //Assign UI
        Assign_Values();

        //load current profile image
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Mechanicians Profile Image").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(previous_image);
            }
        });

        uplosad_new_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Profile Picture"),PICK_IMG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMG && resultCode == RESULT_OK && data.getData() != null)
        {
            image_path = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),image_path);
                uplosad_new_img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Assign_Values() {

        //Firebase
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        storageReference=firebaseStorage.getReference();

        uplosad_new_img=findViewById(R.id.img_mechanic_upload_new_image);
        previous_image=findViewById(R.id.img_mechanic_change_image);

        //progress Dialog
        progressDialog= new ProgressDialog(this);
    }
}
