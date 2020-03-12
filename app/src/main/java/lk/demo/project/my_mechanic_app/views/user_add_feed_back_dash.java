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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class user_add_feed_back_dash extends AppCompatActivity {

    //UI Component
    private static final int RESULT_CODE_REQUEST = 101;
    private EditText feeback_description;
    private TextView store_name,user_name;
    private ImageView feedback_img;
    private LinearLayout upload_state_layout;
    private Button btn_submit_data;

    //Loarding
    private ProgressBar loarding_bar;
    private TextView loarding_state;

    //Sprinner
    private Spinner satisfaction_type;

    //Seller key
    private String Seller_Key,Seller_Store,User_Name;

    //image part
    private Uri imageuri;
    private boolean Isimage_Added = false;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_feed_back_dash);

        //Get Seller Key
        Seller_Key=getIntent().getStringExtra("Seller_Key");
        Seller_Store=getIntent().getStringExtra("Seller_Store");
        User_Name=getIntent().getStringExtra("User_Name");


        //Assign
        Assign_UI();

        //start
        upload_state_layout.setVisibility(View.GONE);
        store_name.setText(Seller_Store);
        user_name.setText(User_Name);


        //load feedback image
        feedback_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,RESULT_CODE_REQUEST);
            }
        });

        btn_submit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String seller = store_name.getText().toString();
                final String user = user_name.getText().toString();
                final String description = feeback_description.getText().toString();
                final String user_satify = satisfaction_type.getSelectedItem().toString();

                if (Isimage_Added != false)
                {
                    if (user_satify!="-Select Answer-")
                    {
                        if (description!=null)
                        {
                            //Upload Details
                            Upload_Details(seller,user,description,user_satify);
                        }else{
                            Toast.makeText(getApplicationContext(),"Please Fill Description",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Please Select Satisfaction",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please Select Image",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Upload_Details(final String seller, final String user,final String description,final String user_satify) {
        upload_state_layout.setVisibility(View.VISIBLE);

        //final String Key = databaseReference.push().getKey();
        final String Key = firebaseUser.getUid();

        storageReference.child(Key+"jpg").putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference.child(Key+"jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        HashMap hashMap = new HashMap();
                        hashMap.put("Seller_ID",seller);
                        hashMap.put("User_ID",user);
                        hashMap.put("User_Description",description);
                        hashMap.put("User_Satisfaction",user_satify);
                        hashMap.put("Image_Uri",uri.toString());


                        databaseReference.child(Key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(getApplicationContext(),"Upload Complete",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(user_add_feed_back_dash.this,show_all_type_of_feedback.class));
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
                loarding_state.setText(progress+"%");
                loarding_bar.setProgress((int) progress);
            }
        });

    }

    private void Assign_UI() {

        //upload state hide
        upload_state_layout = findViewById(R.id.ll_feedback_upload_state);

        //ui variable
        feeback_description = findViewById(R.id.et_feedback_details);
        store_name = findViewById(R.id.tv_feedback_store_name);
        user_name = findViewById(R.id.tv_feedback_user_name);
        feedback_img = findViewById(R.id.img_feedback_upload_add);

        //Button
        btn_submit_data = findViewById(R.id.btn_feedback_upload);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User Feedback").child(Seller_Key);
        storageReference = FirebaseStorage.getInstance().getReference().child("User Feedback").child(Seller_Key);


        //Progressbar
        loarding_bar=findViewById(R.id.feedback_upload_progess_bar);
        loarding_state = findViewById(R.id.tv_feedback_upload_progress_presantage);


        //Sprinner Part
        satisfaction_type = findViewById(R.id.spn_feedback_type);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(user_add_feed_back_dash.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.satify_typr));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        satisfaction_type.setAdapter(myAdapter);
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
                feedback_img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
