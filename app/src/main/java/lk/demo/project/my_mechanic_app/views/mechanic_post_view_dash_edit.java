package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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
    }
}
