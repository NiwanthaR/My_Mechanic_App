package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_client_signup;
import lk.demo.project.my_mechanic_app.model.client_profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class client_profile_edite extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;

    private TextView pe_email,pe_nic,pe_gender,pe_dob,pe_profilename;
    private EditText pe_fname,pe_sname,pe_contact,pe_address,pe_city;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile_edite);

        Assign_value();

        Read_data();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Fname=pe_fname.getText().toString().trim();
                final String Lname=pe_sname.getText().toString().trim();
                final String Contact=pe_contact.getText().toString().trim();
                final String Address=pe_address.getText().toString().trim();
                final String City=pe_city.getText().toString().trim();
                final String Useer="client";
                final String Dob=pe_dob.getText().toString().trim();
                final String Nic=pe_nic.getText().toString().trim();
                final String Gender=pe_gender.getText().toString().trim();

                client_profile clientProfile = new client_profile(Fname,Lname,Nic,Dob,Gender,Address,City,Contact,Useer);
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

                if (validation_client_signup.is_fill_update(Fname,Lname,Address,City,Contact))
                {
                    if (validation_client_signup.is_contact(Contact))
                    {
                        databaseReference.setValue(clientProfile);
                        Toast.makeText(client_profile_edite.this,"Details Submitted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(client_profile_edite.this,client_profile_dashboard.class));
                        finish();
                    }else{
                        Toast.makeText(client_profile_edite.this,"Invalid Contact No",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(client_profile_edite.this,"Please Fill All Details..!!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void Assign_value()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        pe_profilename=findViewById(R.id.client_pe_profilename);
        pe_email=findViewById(R.id.client_pe_emailname);
        pe_fname=findViewById(R.id.client_pe_fname_edit);
        pe_sname=findViewById(R.id.client_pe_sname_edit);
        pe_nic=findViewById(R.id.client_pe_nicnum);
        pe_gender=findViewById(R.id.client_pe_gendertype);
        pe_dob=findViewById(R.id.client_pe_dob);
        pe_contact=findViewById(R.id.client_pe_contact_no_edit);
        pe_address=findViewById(R.id.client_pe_addresss_edit);
        pe_city=findViewById(R.id.client_pe_cityname);

        btn_submit=findViewById(R.id.btn_submitedite_client_profile);
    }

     void Read_data()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                client_profile clientProfile = dataSnapshot.getValue(client_profile.class);

                pe_profilename.setText(clientProfile.getFname()+" "+clientProfile.getLname());
                pe_email.setText(firebaseUser.getEmail());
                pe_fname.setText(clientProfile.getFname());
                pe_sname.setText(clientProfile.getLname());
                pe_nic.setText(clientProfile.getNic());
                pe_gender.setText(clientProfile.getGender());
                pe_dob.setText(clientProfile.getDob());
                pe_contact.setText(clientProfile.getContact());
                pe_address.setText(clientProfile.getAddress());
                pe_city.setText(clientProfile.getCity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(client_profile_edite.this,"Can't Connect Database now..!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
