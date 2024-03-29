package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.MainActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.mechanic_profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class mechanic_shop_profile_dashboard extends AppCompatActivity {

    //Variable
    private TextView shop_name,shop_name2,shop_contact,shop_address,shop_city,shop_postcode,shop_email,shop_wesite;
    private TextView shop_open,shop_close,shop_poya,shop_holiday,shop_breakdown,shop_serviceinfo,shop_begin,shop_regno;
    private Button go_edit,go_feedback;
    private ImageView mechanic_logo;

    //String
    String Seller_Key,User_Key,Seller_Store,User_Name;

    //Firebase
    private  FirebaseAuth firebaseAuth;
    private  FirebaseUser firebaseUser;
    private  FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_shop_dashboard);

        //Assign Variable
        Assign_value();

        //Seller Details
        Seller_Key =getIntent().getStringExtra("Seller_Key");
        Seller_Store = getIntent().getStringExtra("Seller_Store");
        User_Name = getIntent().getStringExtra("User_Name");

        //Get Login User ID
        User_Key = firebaseUser.getUid();

        //check user id eqal or not to login id
        if (User_Key.equals(Seller_Key))
        {
            go_edit.setVisibility(View.VISIBLE);
        }else{
            go_edit.setVisibility(View.GONE);
            go_feedback.setVisibility(View.VISIBLE);
        }

        //Read variable value
        Read_value();

        //go to give feedback page
        go_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mechanic_shop_profile_dashboard.this,show_all_type_of_feedback.class);
                intent.putExtra("Seller_Key",Seller_Key);
                intent.putExtra("Seller_Store",Seller_Store);
                intent.putExtra("User_Name",User_Name);
                startActivity(intent);
            }
        });


        //go to shop details edit panel
        go_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_shop_profile_dashboard.this, mechanic_shop_profile_edit.class));
            }
        });

        //load shop logo
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Profile Picture").child(Seller_Key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(mechanic_logo);
            }
        });
    }

    private void Assign_value()
    {
        go_edit=findViewById(R.id.btn_goedite_mechanic_shop_profile);
        go_feedback=findViewById(R.id.btn_feeback_mechanic_shop_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();

        shop_name = findViewById(R.id.tv_mechanic_s_shop_name);
        shop_name2 = findViewById(R.id.tv_mechanic_s_shop_name2);
        shop_contact = findViewById(R.id.tv_mechanic_s_shop_contact);
        shop_address = findViewById(R.id.tv_mechanic_s_shop_address);
        shop_city = findViewById(R.id.tv_mechanic_s_shop_city);
        shop_postcode = findViewById(R.id.tv_mechanic_s_shop_postalcode);
        shop_email = findViewById(R.id.tv_mechanic_s_shop_email);
        shop_wesite = findViewById(R.id.tv_mechanic_s_shop_website);

        mechanic_logo=findViewById(R.id.mechanic_s_shop_logo);

        shop_open = findViewById(R.id.tv_mechanic_s_shop_open);
        shop_close = findViewById(R.id.tv_mechanic_s_shop_close);
        shop_poya = findViewById(R.id.tv_mechanic_s_shop_state_poya);
        shop_holiday = findViewById(R.id.tv_mechanic_s_shop_holidays);
        shop_breakdown = findViewById(R.id.tv_mechanic_s_shop_visit_service);
        shop_serviceinfo = findViewById(R.id.tv_mechanic_s_shop_service_package);
        shop_begin = findViewById(R.id.tv_mechanic_s_shop_start_day);
        shop_regno = findViewById(R.id.tv_mechanic_s_shop_regno);

        go_edit.setVisibility(View.GONE);
        go_feedback.setVisibility(View.GONE);
    }

    private void Read_value()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(Seller_Key);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mechanic_profile mechanicProfile = dataSnapshot.getValue(mechanic_profile.class);

                shop_name.setText(mechanicProfile.getShop_name());
                shop_name2.setText(mechanicProfile.getShop_name());
                shop_contact.setText(mechanicProfile.getShop_contact());
                shop_address.setText(mechanicProfile.getShop_address());
                shop_city.setText(mechanicProfile.getShop_city());
                shop_postcode.setText(mechanicProfile.getShop_postal_code());
                shop_email.setText(mechanicProfile.getShop_email());
                shop_wesite.setText(mechanicProfile.getShop_web());

                shop_open.setText(mechanicProfile.getShop_open());
                shop_close.setText(mechanicProfile.getShop_close());
                shop_poya.setText(mechanicProfile.getShop_poya_state());
                shop_holiday.setText(mechanicProfile.getShop_special_holiday());
                shop_breakdown.setText(mechanicProfile.getShop_visite_service());
                shop_serviceinfo.setText(mechanicProfile.getShop_sevice());
                shop_begin.setText(mechanicProfile.getShop_start_date());
                shop_regno.setText(mechanicProfile.getShop_regno());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mechanic_shop_profile_dashboard.this,"Can't Connect Database now..!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
