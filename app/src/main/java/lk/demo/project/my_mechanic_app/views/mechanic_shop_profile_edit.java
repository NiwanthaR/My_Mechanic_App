package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.mechanic_profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mechanic_shop_profile_edit extends AppCompatActivity {

    //Variable
    private TextView shop_name,shop_name2;
    private TextView shop_begin,shop_regno;
    private Button go_edit;
    private EditText shop_contact,shop_address,shop_city,shop_postcode,shop_email,shop_wesite;
    private EditText shop_open,shop_close,shop_poya,shop_holiday,shop_breakdown,shop_serviceinfo;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_shop_profile_edit);

        //Equal Variable
        Assign_value();

        //Read Value
        Read_data();
    }

    private void Assign_value()
    {
        go_edit=findViewById(R.id.btn_submit_se_shop_edite);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        shop_name = findViewById(R.id.tv_mechanic_se_shop_name);
        shop_name2 = findViewById(R.id.tv_mechanic_se_shop_name2);
        shop_contact = findViewById(R.id.et_mechanic_se_shop_contact);
        shop_address = findViewById(R.id.et_mechanic_se_shop_address);
        shop_city = findViewById(R.id.et_mechanic_se_shop_city);
        shop_postcode = findViewById(R.id.et_mechanic_se_shop_postcode);
        shop_email = findViewById(R.id.et_mechanic_se_shop_email);
        shop_wesite = findViewById(R.id.et_mechanic_se_shop_web);

        shop_open = findViewById(R.id.et_mechanic_se_shop_open);
        shop_close = findViewById(R.id.et_mechanic_se_shop_close);
        shop_poya = findViewById(R.id.et_mechanic_se_shop_poya);
        shop_holiday = findViewById(R.id.et_mechanic_se_shop_holiday);
        shop_breakdown = findViewById(R.id.et_mechanic_se_shop_visit);
        shop_serviceinfo = findViewById(R.id.et_mechanic_se_shop_service);
        shop_begin = findViewById(R.id.tv_mechanic_se_shop_start);
        shop_regno = findViewById(R.id.tv_mechanic_se_shop_regno);
    }

    private void Read_data()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

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
                Toast.makeText(mechanic_shop_profile_edit.this,"Can't Connect Database now..!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
