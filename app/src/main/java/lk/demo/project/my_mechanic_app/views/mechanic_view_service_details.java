package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class mechanic_view_service_details extends AppCompatActivity {

    //Textview
    private TextView tv_title,tv_details,tv_price,tv_contact,tv_store_name,tv_store_location;

    //Imageview
    private ImageView imageView;

    //Button
    private Button edit_button,call_button;

    //Service key
    private String Service_Key;

    //Linear_layout
    private LinearLayout delete_panel,call_panel;

    //Firebase
    private DatabaseReference DataRef;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_view_service_details);

        //Service Key
        Service_Key = getIntent().getStringExtra("Service_Key");

        //Assign variable
        Assign_Variable();


        //owner key
        final String Owner_Key = firebaseAuth.getUid();

        //go to edite
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mechanic_view_service_details.this,mechanic_view_service_details_edit.class);
                intent.putExtra("Service_Key",Service_Key);
                startActivity(intent);
            }
        });

        //get call button
        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number =  tv_contact.getText().toString().trim();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });



        //Read values
        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    String title = dataSnapshot.child("Service_Title").getValue().toString();
                    String description = dataSnapshot.child("Service_Description").getValue().toString();
                    String price = dataSnapshot.child("Service_Cost").getValue().toString();
                    String contact = dataSnapshot.child("Seller_Contact").getValue().toString();
                    String store = dataSnapshot.child("Seller_Store_Name").getValue().toString();
                    String store_location = dataSnapshot.child("Seller_Store_Location").getValue().toString();
                    String imageuri = dataSnapshot.child("Image_Uri").getValue().toString();

                    if ((dataSnapshot.child("Seller_Uid").getValue().toString()).equals(Owner_Key))
                    {
                        delete_panel.setVisibility(View.VISIBLE);
                        call_panel.setVisibility(View.GONE);
                    }

                    Picasso.get().load(imageuri).into(imageView);
                    tv_title.setText(title);
                    tv_details.setText(description);
                    tv_price.setText(price);
                    tv_contact.setText(contact);
                    tv_store_name.setText(store);
                    tv_store_location.setText(store_location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void Assign_Variable() {

        //tv value
        tv_title = findViewById(R.id.tv_service_details_title);
        tv_details = findViewById(R.id.tv_service_details_description);
        tv_price = findViewById(R.id.tv_service_details_price);
        tv_contact = findViewById(R.id.tv_service_details_contact);
        tv_store_name = findViewById(R.id.tv_service_details_store_name);
        tv_store_location = findViewById(R.id.tv_service_details_location);

        //imageview
        imageView = findViewById(R.id.img_service_details_image);

        //delete_btn
        edit_button = findViewById(R.id.btn_service_details_edit_details);
        call_button = findViewById(R.id.btn_service_details_call);

        //database
        DataRef = FirebaseDatabase.getInstance().getReference().child("Mechanic Upload Service Package").child(Service_Key);
        firebaseAuth = FirebaseAuth.getInstance();

        //layout
        delete_panel = findViewById(R.id.ll_service_details_delete_panel);
        call_panel = findViewById(R.id.ll_servie_details_call_panel);

        //layout hide
        delete_panel.setVisibility(View.GONE);
    }
}
