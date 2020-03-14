package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Button delete_button;

    //Service key
    private String Service_Key;

    //Firebase
    private DatabaseReference DataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_view_service_details);

        //Service Key
        Service_Key = getIntent().getStringExtra("Service_Key");

        //Assign variable
        Assign_Variable();

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
        delete_button = findViewById(R.id.btn_service_details_delete);
        delete_button.setVisibility(View.GONE);

        //database
        DataRef = FirebaseDatabase.getInstance().getReference().child("Mechanic Upload Service Package").child(Service_Key);
    }
}
