package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_provider_signup;
import lk.demo.project.my_mechanic_app.model.mechanic_profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mechanic_profile_dashboard_edit extends AppCompatActivity {

    //Text box and edit text
    private TextView owner_name_up,owner_email,owner_nic,owner_gender,owner_dob;
    private EditText owner_fname,owner_sname,owner_contact,owner_address,owner_city;

    //Button
    private Button submit_data;

    //Equal Variable
    private String email,fname,sname,nic,gender,dob,contact,address,city;
    private String shop_name,shop_regno,shop_startdate,shop_address,shop_city,shop_postcode,shop_contact,shop_email,shop_web,shop_open,shop_close,shop_poya,shop_holiday,shop_breakdown,shop_service,usertype;

    //get Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;

    //progreedialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_profile_dashboard_edit);

        //Assign Variable
        Value_Assign();

        //Read Data
        Load_value();

        submit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=owner_email.getText().toString().trim();
                fname=owner_fname.getText().toString().trim();
                sname=owner_sname.getText().toString().trim();
                nic=owner_nic.getText().toString().trim();
                dob=owner_dob.getText().toString().trim();
                gender=owner_gender.getText().toString().trim();
                contact=owner_contact.getText().toString().trim();
                address=owner_address.getText().toString().trim();
                city=owner_city.getText().toString().trim();

                if(validation_provider_signup.mechanic_profile_update(fname,sname,contact,address,city))
                {
                    if (validation_provider_signup.is_valide_contact(contact))
                    {
                        Toast.makeText(mechanic_profile_dashboard_edit.this,"All right",Toast.LENGTH_SHORT).show();
                        progressDialog.setMessage("Your Details in Processing Please waite..!");
                        progressDialog.show();

                        //upload data
                        Data_upload();

                    }else {
                        Toast.makeText(mechanic_profile_dashboard_edit.this,"Please enter valid contact....!!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(mechanic_profile_dashboard_edit.this,"Please fill all details....!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Value_Assign()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        submit_data = findViewById(R.id.btn_mechanic_pe_profile_submit);


        owner_name_up = findViewById(R.id.tv_mechanic_pe_profile_name);
        owner_email = findViewById(R.id.tv_mechanic_pe_profile_email);
        owner_nic = findViewById(R.id.tv_mechanic_pe_profile_nic);
        owner_gender = findViewById(R.id.tv_mechanic_pe_profile_gender);
        owner_dob = findViewById(R.id.tv_mechanic_pe_profile_dob);

        owner_fname = findViewById(R.id.et_mechanic_pe_profile_fname);
        owner_sname = findViewById(R.id.et_mechanic_pe_profile_sname);
        owner_contact = findViewById(R.id.et_mechanic_pe_profile_contact);
        owner_address = findViewById(R.id.et_mechanic_pe_profile_address);
        owner_city = findViewById(R.id.et_mechanic_pe_profile_city);

        //progress Dialog
        progressDialog= new ProgressDialog(this);
    }

    private void Load_value()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mechanic_profile mechanicProfile = dataSnapshot.getValue(mechanic_profile.class);

                owner_name_up.setText(mechanicProfile.getOwner_fname()+" "+mechanicProfile.getOwner_sname());
                owner_fname.setText(mechanicProfile.getOwner_fname());
                owner_sname.setText(mechanicProfile.getOwner_sname());
                owner_email.setText(firebaseUser.getEmail());
                owner_nic.setText(mechanicProfile.getOwner_nic());
                owner_gender.setText(mechanicProfile.getOwner_gender());
                owner_dob.setText(mechanicProfile.getOwner_dob());
                owner_contact.setText(mechanicProfile.getOwner_contact());
                owner_address.setText(mechanicProfile.getOwner_address());
                owner_city.setText(mechanicProfile.getOwner_city());

                shop_name=mechanicProfile.getShop_name();
                shop_regno=mechanicProfile.getShop_regno();
                shop_startdate=mechanicProfile.getShop_start_date();
                shop_address=mechanicProfile.getShop_address();
                shop_city=mechanicProfile.getShop_city();
                shop_postcode=mechanicProfile.getShop_postal_code();
                shop_contact=mechanicProfile.getShop_contact();
                shop_email=mechanicProfile.getShop_email();
                shop_web=mechanicProfile.getShop_web();
                shop_open=mechanicProfile.getShop_open();
                shop_close=mechanicProfile.getShop_close();
                shop_poya=mechanicProfile.getShop_poya_state();
                shop_holiday=mechanicProfile.getShop_special_holiday();
                shop_breakdown=mechanicProfile.getShop_visite_service();
                shop_service=mechanicProfile.getShop_sevice();
                usertype=mechanicProfile.getUsertype();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mechanic_profile_dashboard_edit.this,"Can't Connect Database now..!!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Data_upload()
    {
        mechanic_profile mechanicProfile = new mechanic_profile(shop_name,shop_regno,shop_startdate,shop_address,shop_city,shop_postcode,shop_contact,shop_email,shop_web,shop_open,shop_close,shop_poya,shop_holiday,shop_breakdown,shop_service,fname,sname,nic,dob,gender,address,city,contact,usertype);
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

        databaseReference.setValue(mechanicProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(mechanic_profile_dashboard_edit.this,"Details Updated....!!",Toast.LENGTH_SHORT).show();
                }else {
                     Toast.makeText(mechanic_profile_dashboard_edit.this,"Details Updated....!!",Toast.LENGTH_SHORT).show();
                 }
            }
        });

    }
}
