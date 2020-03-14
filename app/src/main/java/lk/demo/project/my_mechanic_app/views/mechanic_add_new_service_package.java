package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.mechanic_profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class mechanic_add_new_service_package extends AppCompatActivity {

    private static final int RESULT_CODE_REQUEST = 101;
    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    //Edite Text
    private EditText service_title,service_description,service_price;

    //ImageView
    private ImageView service_image;

    //Layout
    private LinearLayout upload_state_layout;

    //Textview
    private TextView tv_store_name,tv_upload_state;

    //Progressbar
    private ProgressBar upload_state_bar;

    //Button
    private Button submit_details;

    //image part
    private Uri imageuri;
    private boolean Isimage_Added = false;

    //private variable
    private String seller_id,seller_store_name,seller_store_location,seller_contact;
    private String service_name,service_cost,service_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_add_new_service_package);

        //Assign Variables
        Assign_Variable();

        //Data Read
        Read_value();

        //set Store Name
        //tv_store_name.setText(seller_store_name);

        //get image
        service_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,RESULT_CODE_REQUEST);
            }
        });

        //submit button
        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                seller_id = firebaseAuth.getUid();
                service_name = service_title.getText().toString();
                service_cost = service_price.getText().toString();
                service_info = service_description.getText().toString();

                if (Isimage_Added)
                {
                    if (service_name.isEmpty() || service_cost.isEmpty() || service_info.isEmpty())
                    {
                        Toast.makeText(mechanic_add_new_service_package.this,"Please Fill All Details",Toast.LENGTH_SHORT).show();
                    }else {
                        //upload Data
                        Upload_Data(seller_id,service_name,service_info,service_cost);
                    }
                }else {
                    Toast.makeText(mechanic_add_new_service_package.this,"Please Add Image",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Upload_Data(final String seller_UID,final  String service_TITLE,final  String servic_INFO,final String service_COST) {

        //upload layout
        upload_state_layout.setVisibility(View.VISIBLE);

        final String key = databaseReference.push().getKey();

        storageReference.child(key+"jpg").putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference.child(key+"jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        HashMap hashMap = new HashMap();
                        hashMap.put("Seller_Uid",seller_UID);
                        hashMap.put("Service_Title",service_TITLE);
                        hashMap.put("Service_Description",servic_INFO);
                        hashMap.put("Service_Cost",service_COST);
                        hashMap.put("Seller_Store_Name",seller_store_name);
                        hashMap.put("Seller_Store_Location",seller_store_location);
                        hashMap.put("Seller_Contact",seller_contact);

                        hashMap.put("Image_Uri",uri.toString());


                        databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mechanic_add_new_service_package.this,"Upload Complete",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(mechanic_add_new_service_package.this,mechanic_dashboard.class));
                                finish();
                            }
                        });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                tv_upload_state.setText(progress+"%");
                upload_state_bar.setProgress((int) progress);
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
                service_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Assign_Variable() {

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("Mechanic Upload Service Package");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Mechanic Upload Service Package");

        //UI part
        service_title = findViewById(R.id.et_add_new_service_name);
        service_description = findViewById(R.id.et_add_new_service_details);
        service_price = findViewById(R.id.et_add_new_service_price);

        tv_store_name = findViewById(R.id.tv_add_new_service_store_name);
        tv_upload_state = findViewById(R.id.tv_add_new_service_progress_presantage);

        upload_state_bar = findViewById(R.id.add_new_service_upload_progess_bar);

        service_image = findViewById(R.id.img_add_new_Service_img);

        submit_details = findViewById(R.id.btn_add_new_service_upload);

        //Linear Layout
        upload_state_layout = findViewById(R.id.ll_add_new_service_upload_state);
        upload_state_layout.setVisibility(View.GONE);
    }

    private void Read_value()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mechanic_profile mechanicProfile = dataSnapshot.getValue(mechanic_profile.class);

                tv_store_name.setText(mechanicProfile.getShop_name());
                seller_contact = mechanicProfile.getShop_contact();
                seller_store_location = mechanicProfile.getOwner_city();

                //Toast.makeText(mechanic_add_new_service_package.this,seller_store_location,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mechanic_add_new_service_package.this,"Can't Connect Database now..!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
