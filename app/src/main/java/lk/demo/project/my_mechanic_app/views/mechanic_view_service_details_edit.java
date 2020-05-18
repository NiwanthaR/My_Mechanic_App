package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_client_signup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class mechanic_view_service_details_edit extends AppCompatActivity {

    //key
    private String Service_Key;
    //Buttons
    private Button btn_update,btn_delete;
    //Edit text
    private EditText service_title,service_description,service_price,service_contact;
    //Text view
    private TextView service_store_name,service_store_location,wrong_details,presantage;
    //Progressbar
    private ProgressBar upload_prestage;
    //Imageview
    private ImageView service_image;
    //Layout
    private LinearLayout button_layout,state_layout;

    //Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;

    //String Value
    private String seller_id,title,description,price,contact,store,location,image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_view_service_details_edit);

        //get key
        Service_Key = getIntent().getStringExtra("Service_Key");

        //Assign Value
        UI_Declare();

        //Read Value
        databaseReference.child(Service_Key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    seller_id = dataSnapshot.child("Seller_Uid").getValue().toString();
                    title = dataSnapshot.child("Service_Title").getValue().toString();
                    description = dataSnapshot.child("Service_Description").getValue().toString();
                    price = dataSnapshot.child("Service_Cost").getValue().toString();
                    contact = dataSnapshot.child("Seller_Contact").getValue().toString();
                    store = dataSnapshot.child("Seller_Store_Name").getValue().toString();
                    location = dataSnapshot.child("Seller_Store_Location").getValue().toString();
                    image_uri = dataSnapshot.child("Image_Uri").getValue().toString();

                    Picasso.get().load(image_uri).into(service_image);
                    service_title.setText(title);
                    service_description.setText(description);
                    service_price.setText(price);
                    service_contact.setText(contact);
                    service_store_name.setText(store);
                    service_store_location.setText(location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Update
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = service_title.getText().toString().trim();
                description = service_description.getText().toString().trim();
                price = service_price.getText().toString().trim();
                contact = service_contact.getText().toString().trim();

                if (title.isEmpty() || description.isEmpty() || price.isEmpty() || contact.isEmpty())
                {
                    //Toast.makeText(getApplicationContext(),"Fill Details",Toast.LENGTH_SHORT).show();
                    wrong_details.setText("Fill Details");
                }else {
                    if (validation_client_signup.is_contact(contact))
                    {
                        //Upload Database
                         Upload_Details();
                    }else{
                        //Toast.makeText(getApplicationContext(),"Fill Details",Toast.LENGTH_SHORT).show();
                        wrong_details.setText("Please Check Your Contact Number");
                    }
                }
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get service details
                DatabaseReference service_data = firebaseDatabase.getReference().child("Mechanic Upload Service Package").child(Service_Key);

                //delete data
                service_data.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        //get service image
                        StorageReference service_img_remove = firebaseStorage.getReference().child("Mechanic Upload Service Package").child(Service_Key+"jpg");

                        service_img_remove.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(mechanic_view_service_details_edit.this,"AD Remove Succesfully",Toast.LENGTH_SHORT).show();

                                //go back
                                startActivity(new Intent(mechanic_view_service_details_edit.this,mechanic_edit_service_details.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                //go back
                                Toast.makeText(mechanic_view_service_details_edit.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                            }
                        });

                        //go back
                        //Toast.makeText(mechanic_view_service_details_edit.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mechanic_view_service_details_edit.this,"Can't remove this movement",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void UI_Declare() {

        //Layouts
        button_layout = findViewById(R.id.ll_service_details_edit_buttons);
        state_layout = findViewById(R.id.ll_et_service_details_edit_upload_state);

        //Hide
        state_layout.setVisibility(View.GONE);

        //Buttons
        btn_delete = findViewById(R.id.btn_et_service_details_edit_delete);
        btn_update = findViewById(R.id.btn_et_service_details_edit_update);

        //Edit Text
        service_title = findViewById(R.id.et_service_details_edit_title);
        service_description = findViewById(R.id.et_service_details_edit_details);
        service_contact = findViewById(R.id.et_service_details_edit_contact);
        service_price = findViewById(R.id.et_service_details_edit_price);

        //Text view
        service_store_name = findViewById(R.id.tv_service_details_edit_store_name);
        service_store_location = findViewById(R.id.tv_service_details_edit_location);
        wrong_details = findViewById(R.id.tv_service_details_edit_wrong_details);
        presantage = findViewById(R.id.et_service_details_edit__progress_presantage);
        //Progressbar
        upload_prestage = findViewById(R.id.et_service_details_edit_upload_progess_bar);
        //Imageview
        service_image = findViewById(R.id.img_service_details_edit_img);

        //firbase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Mechanic Upload Service Package");
        firebaseStorage = FirebaseStorage.getInstance();
    }


    private void Upload_Details() {


                        HashMap hashMap = new HashMap();
                        hashMap.put("Seller_Uid",seller_id);
                        hashMap.put("Service_Title",title);
                        hashMap.put("Service_Description",description);
                        hashMap.put("Service_Cost",price);
                        hashMap.put("Seller_Store_Name",store);
                        hashMap.put("Seller_Store_Location",location);
                        hashMap.put("Seller_Contact",contact);

                        hashMap.put("Image_Uri",image_uri);


                        databaseReference.child(Service_Key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mechanic_view_service_details_edit.this,"Upload Complete",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mechanic_view_service_details_edit.this,mechanic_view_service_details.class);
                                intent.putExtra("Service_Key",Service_Key);
                                startActivity(intent);
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Upload Failed",Toast.LENGTH_SHORT).show();

                            }
                        });

    }
}
