package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_client_signup;

import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.IOException;
import java.util.HashMap;

public class mechanic_post_view_dash_edit extends AppCompatActivity {

    //key Declare
    private String AD_Number;
    //Layout
    private LinearLayout state_layout,buttons_layout;
    //Edittext
    private EditText post_title,post_description,post_price,post_contact;
    //Textview
    private TextView post_condition,post_store,post_store_location,post_owner,wrong_details,presantage;
    //Progressbar
    private ProgressBar upload_state;
    //Imageview
    private ImageView post_image;
    //Buttons
    private Button btn_update,btn_delete;


    //Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;

    //String
    private String adseller_id,adpost_title,adpost_description,adpost_price,adpost_condition,adpost_store_name,adpost_imageUri,adpost_contact,adpost_owner_name,adpost_store_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_post_view_dash_edit);

        //String Key
        AD_Number = getIntent().getStringExtra("AD_Number");

        //Ui Declare
        UI_Declare();


        //Loard Data
        databaseReference.child(AD_Number).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    adseller_id = dataSnapshot.child("Owner_UID").getValue().toString();
                    adpost_title = dataSnapshot.child("Post_Title").getValue().toString();
                    adpost_description = dataSnapshot.child("Post_Description").getValue().toString();
                    adpost_price = dataSnapshot.child("Post_Cost").getValue().toString();
                    adpost_condition = dataSnapshot.child("Post_Type").getValue().toString();
                    adpost_store_name = dataSnapshot.child("Store_Name").getValue().toString();
                    adpost_imageUri = dataSnapshot.child("ImageUri").getValue().toString();
                    adpost_contact = dataSnapshot.child("Store_Contact").getValue().toString();
                    adpost_owner_name = dataSnapshot.child("Post_Store_Owner_Name").getValue().toString();
                    adpost_store_location = dataSnapshot.child("Post_Store_Location").getValue().toString();


                    post_title.setText(adpost_title);
                    post_description.setText(adpost_description);
                    post_price.setText(adpost_price);
                    post_condition.setText(adpost_condition);
                    post_store.setText(adpost_store_name);
                    post_store_location.setText(adpost_store_location);
                    post_owner.setText(adpost_owner_name);
                    post_contact.setText(adpost_contact);


                    Picasso.get().load(adpost_imageUri).into(post_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //Read Update Data
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adpost_title = post_title.getText().toString().trim();
                adpost_description = post_description.getText().toString().trim();
                adpost_price = post_price.getText().toString().trim();
                adpost_contact = post_contact.getText().toString().trim();

                    if (adpost_title.isEmpty() || adpost_description.isEmpty() || adpost_price.isEmpty() || adpost_contact.isEmpty())
                    {
                        //Toast.makeText(getApplicationContext(),"Fill Details",Toast.LENGTH_SHORT).show();
                        wrong_details.setText("Fill Details");
                    }else {

                        if (validation_client_signup.is_contact(adpost_contact))
                        {
                            //Upload Database
                            Upload_Details();
                            //wrong_details.setText("Details Ok");
                        }else
                        {
                            //Toast.makeText(getApplicationContext(),"Fill Details",Toast.LENGTH_SHORT).show();
                            wrong_details.setText("Please Check Your Contact Number");
                        }
                    }
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mechanic_post_view_dash_edit.this);
                builder.setTitle("Are you sure...??");
                builder.setMessage("Delete this AD will result in completely removing your" +
                        "account from the system and you wan't be able to access this AD again.");
                builder.setPositiveButton("Delete Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //get service details
                        DatabaseReference add_data = firebaseDatabase.getReference().child("Mechanicians wall posts").child(AD_Number);

                        //delete data
                        add_data.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                //get service image
                                StorageReference add_img_remove = firebaseStorage.getReference().child("Mechanicians wall posts").child(AD_Number+"jpg");

                                add_img_remove.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(mechanic_post_view_dash_edit.this,"AD Remove Successfully",Toast.LENGTH_SHORT).show();

                                        //go back
                                        startActivity(new Intent(mechanic_post_view_dash_edit.this,mechanic_add_wall.class));
                                        finish();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        //go back
                                        Toast.makeText(mechanic_post_view_dash_edit.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                //go back
                                //Toast.makeText(mechanic_view_service_details_edit.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mechanic_post_view_dash_edit.this,"Can't remove this movement",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


    }

    private void UI_Declare() {
        //layouts
        state_layout = findViewById(R.id.ll_mechanic_post_details_edit_upload_state);
        buttons_layout = findViewById(R.id.li_mechanic_post_details_edit_Buttons);

        state_layout.setVisibility(View.GONE);

        //Buttons
        btn_delete = findViewById(R.id.btn_mechanic_post_details_edit_delete_add);
        btn_update = findViewById(R.id.btn_mechanic_post_details_edit_edit_add);

        //Textview
        post_condition = findViewById(R.id.tv_mechanic_post_details_edit_condition);
        post_store = findViewById(R.id.tv_mechanic_post_details_edit_store_name);
        post_store_location = findViewById(R.id.tv_mechanic_post_details_edit_store_location);
        post_owner = findViewById(R.id.tv_mechanic_post_details_edit_owner_name);
        wrong_details = findViewById(R.id.tv_mechanic_post_details_edit_wrong_details);
        presantage = findViewById(R.id.et_mechanic_post_details_edit_progress_presantage);

        //Editetext
        post_title = findViewById(R.id.et_mechanic_post_details_edit_title);
        post_description = findViewById(R.id.et_mechanic_post_details_edit__description);
        post_price = findViewById(R.id.et_mechanic_post_details_edit_price);
        post_contact = findViewById(R.id.tv_mechanic_post_details_edit_contact_number);

        //Progressbar
        upload_state = findViewById(R.id.et_mechanic_post_details_edit_upload_progess_bar);
        //Image
        post_image = findViewById(R.id.img_mechanic_post_details_edit_image);

        //firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Mechanicians wall posts");
        firebaseStorage = FirebaseStorage.getInstance();

    }


    //Update Data
    private void Upload_Details(){

                        HashMap hashMap = new HashMap();
                        hashMap.put("Owner_UID",adseller_id);
                        hashMap.put("Post_Title",adpost_title);
                        hashMap.put("Post_Description",adpost_description);
                        hashMap.put("Post_Cost",adpost_price);
                        hashMap.put("Post_Type",adpost_condition);
                        hashMap.put("Store_Name",adpost_store_name);
                        hashMap.put("Store_Contact",adpost_contact);
                        hashMap.put("Post_Store_Owner_Name",adpost_owner_name);
                        hashMap.put("ImageUri",adpost_imageUri);
                        hashMap.put("Post_Store_Location",adpost_store_location);

                        databaseReference.child(AD_Number).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Upload Complete",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),mechanic_post_view_dash.class);
                                intent.putExtra("Item_Key",AD_Number);
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
