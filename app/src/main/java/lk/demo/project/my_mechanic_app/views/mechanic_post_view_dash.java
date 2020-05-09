package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class mechanic_post_view_dash extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ImageView advertise_image;
    private Button btn_edit_add,btn_get_call;
    private TextView add_title,add_description,add_price,add_condition,add_store,add_store_location,add_store_owner_name,add_store_owner_contact;

    private String owner_key;
    private String Item_Key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_post_view_dash);

        //UI value Assign
        Assign_value();

        owner_key=firebaseAuth.getUid();

        Item_Key =getIntent().getStringExtra("Item_Key");


        //go to edit
        btn_edit_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mechanic_post_view_dash.this,mechanic_post_view_dash_edit.class);
                intent.putExtra("AD_Number",Item_Key);
                startActivity(intent);
            }
        });


        databaseReference.child(Item_Key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String seller_id = dataSnapshot.child("Owner_UID").getValue().toString();
                    String post_title = dataSnapshot.child("Post_Title").getValue().toString();
                    String post_description = dataSnapshot.child("Post_Description").getValue().toString();
                    String post_price = dataSnapshot.child("Post_Cost").getValue().toString();
                    String post_condition = dataSnapshot.child("Post_Type").getValue().toString();
                    String post_store_name = dataSnapshot.child("Store_Name").getValue().toString();
                    String post_imageUri = dataSnapshot.child("ImageUri").getValue().toString();
                    String post_contact = dataSnapshot.child("Store_Contact").getValue().toString();
                    String post_owner_name = dataSnapshot.child("Post_Store_Owner_Name").getValue().toString();
                    String post_store_location = dataSnapshot.child("Post_Store_Location").getValue().toString();


                    if (seller_id.equals(owner_key))
                    {
                        btn_edit_add.setVisibility(View.VISIBLE);
                        btn_get_call.setVisibility(View.GONE);
                    }

                    add_title.setText(post_title);
                    add_description.setText(post_description);
                    add_price.setText("Rs "+post_price+".00");
                    add_condition.setText(post_condition);
                    add_store.setText(post_store_name);
                    add_store_owner_contact.setText(post_contact);
                    add_store_location.setText(post_store_location);
                    add_store_owner_name.setText(post_owner_name);

                    Picasso.get().load(post_imageUri).into(advertise_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void Assign_value()
    {
        //Image view
        advertise_image=findViewById(R.id.img_post_details_image);

        //Edit text
        add_title=findViewById(R.id.tv_post_details_title);
        add_description=findViewById(R.id.tv_post_details_description);
        add_price=findViewById(R.id.tv_post_details_price);
        add_condition=findViewById(R.id.tv_shop_details_condition);
        add_store=findViewById(R.id.tv_post_details_store_name);
        add_store_location=findViewById(R.id.tv_post_details_store_location);
        add_store_owner_name=findViewById(R.id.tv_post_details_owner_name);
        add_store_owner_contact=findViewById(R.id.tv_post_details_contact_number);

        //Buttons
        btn_edit_add=findViewById(R.id.btn_post_details_edit_add);
        btn_get_call = findViewById(R.id.btn_post_details_edit_call);

        //Firebase
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Mechanicians wall posts");

        //Arrange user display
        btn_edit_add.setVisibility(View.GONE);

    }
}
