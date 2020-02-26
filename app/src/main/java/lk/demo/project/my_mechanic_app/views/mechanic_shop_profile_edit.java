package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_provider_signup;
import lk.demo.project.my_mechanic_app.model.mechanic_profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mechanic_shop_profile_edit extends AppCompatActivity {

    //Variable
    private TextView shop_name,shop_name2;
    private TextView shop_begin,shop_regno,display_wrong;
    private Button btn_submit;
    private EditText shop_contact,shop_address,shop_city,shop_postcode,shop_email,shop_wesite;
    private EditText shop_open,shop_close,shop_poya,shop_holiday,shop_breakdown,shop_serviceinfo;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    //variable
    private String name,regno,start_date,contact,address,city,post_code,email,website,open,close,poya,holiday,breakdown,serviceinfo;
    private String owner_fname,owner_sname,owner_nic,owner_dob,owner_gender,owner_address,owner_city,owner_contact,usertype;

    //progreedialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_shop_profile_edit);

        //Equal Variable
        Assign_value();

        //Read Value
        Read_data();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=shop_name.getText().toString().trim();
                contact=shop_contact.getText().toString().trim();
                address=shop_address.getText().toString().trim();
                city=shop_city.getText().toString().trim();
                post_code=shop_postcode.getText().toString().trim();
                email=shop_email.getText().toString().trim();
                website=shop_wesite.getText().toString().trim();

                open=shop_open.getText().toString().trim();
                close=shop_close.getText().toString().trim();
                poya=shop_poya.getText().toString().trim();
                holiday=shop_holiday.getText().toString().trim();
                breakdown=shop_breakdown.getText().toString().trim();
                serviceinfo=shop_serviceinfo.getText().toString().trim();

                regno=shop_regno.getText().toString().trim();
                start_date=shop_begin.getText().toString().trim();

                if(validation_provider_signup.mechanic_shop_update(contact,address,city,post_code,email,open,close,breakdown))
                {
                    if (validation_provider_signup.is_Validmail(email))
                    {
                        if (validation_provider_signup.is_valide_contact(contact))
                        {
                                //Toast.makeText(mechanic_shop_profile_edit.this,"Allright",Toast.LENGTH_SHORT).show();
                            update_data();
                        }else {
                            display_wrong.setText("Please enter valid contact");
                        }
                    }else {
                        display_wrong.setText("Your email is Wrong");
                    }
                }else {
                    display_wrong.setText("Please Fill All Details");
                }
            }
        });
    }

    private void Assign_value()
    {
        btn_submit=findViewById(R.id.btn_submit_se_shop_edite);

        //progress Dialog
        progressDialog= new ProgressDialog(this);

        //firebase
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
        display_wrong = findViewById(R.id.tv_mechanic_se_wrong);
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

                owner_fname=mechanicProfile.getOwner_fname();
                owner_sname=mechanicProfile.getOwner_sname();
                owner_nic=mechanicProfile.getOwner_nic();
                owner_dob=mechanicProfile.getOwner_dob();
                owner_gender=mechanicProfile.getOwner_gender();
                owner_address=mechanicProfile.getOwner_address();
                owner_city=mechanicProfile.getOwner_city();
                owner_contact=mechanicProfile.getOwner_contact();
                usertype=mechanicProfile.getUsertype();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mechanic_shop_profile_edit.this,"Can't Connect Database now..!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update_data()
    {

        mechanic_profile mechanicProfile = new mechanic_profile(name,regno,start_date,address,city,post_code,contact,email,website,open,close,poya,holiday,breakdown,serviceinfo,owner_fname,owner_sname,owner_nic,owner_dob,owner_gender,owner_address,owner_city,owner_contact,usertype);
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

        progressDialog.setMessage("Your Details in Processing Please waite..!");
        progressDialog.show();

        databaseReference.setValue(mechanicProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(mechanic_shop_profile_edit.this,"Details Update",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mechanic_shop_profile_edit.this,mechanic_shop_profile_dashboard.class));
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(mechanic_shop_profile_edit.this,"Can't Update this movement",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
