package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class mechanic_add_post_dash extends AppCompatActivity {

    private static final int RESULT_CODE_REQUEST = 101;
    private Button submit_data;
    private EditText post_title,post_discrption;
    private ImageView upload_img_view;
    private TextView upload_state;
    private ProgressBar upload_bar;

    //image
    private Uri imageuri;
    private boolean Isimage_Added = false;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_add_post_dash);

        //Load UI
        Assign_Data();

        upload_img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,RESULT_CODE_REQUEST);
            }
        });

        submit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = post_title.getText().toString().trim();
                final String description = post_discrption.getText().toString().trim();

                if (Isimage_Added!=false)
                {
                    if (title.isEmpty() || description.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Fill Details",Toast.LENGTH_SHORT).show();
                    }else {

                        //Upload Database
                        Upload_Details(title,description);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Add Image",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==RESULT_CODE_REQUEST && data!=null)
        {
            imageuri=data.getData();
            Isimage_Added=true;

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                upload_img_view.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Assign_Data() {

        submit_data=findViewById(R.id.btn_upload_post_mechanic);
        upload_img_view=findViewById(R.id.mechanic_upload_image_post);
        post_title=findViewById(R.id.mechanic_post_upload_title);
        post_discrption=findViewById(R.id.mechanic_post_upload_discription);
        upload_state=findViewById(R.id.mechanic_upload_post_progessdisplay);
        upload_bar=findViewById(R.id.mechanic_upload_progess);

        upload_state.setVisibility(View.GONE);
        upload_bar.setVisibility(View.GONE);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Mechanicians wall posts");
        storageReference= FirebaseStorage.getInstance().getReference().child("Mechanicians wall posts");
    }

    private void Upload_Details(final String title_post, final String description){

        upload_state.setVisibility(View.VISIBLE);
        upload_bar.setVisibility(View.VISIBLE);

        final String key=databaseReference.push().getKey();
        storageReference.child(key+"jpg").putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference.child(key+"jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        HashMap hashMap = new HashMap();
                        hashMap.put("Owner_ID",firebaseUser.getEmail());
                        hashMap.put("Owner_UID",firebaseAuth.getUid());
                        hashMap.put("Post_Title",title_post);
                        hashMap.put("Post_Description",description);
                        hashMap.put("ImageUri",uri.toString());

                        databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Upload Complete",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),mechanic_add_wall.class));
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Upload Failed",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                upload_state.setText(progress+"%");
                upload_bar.setProgress((int) progress);
            }
        });
    }
}
