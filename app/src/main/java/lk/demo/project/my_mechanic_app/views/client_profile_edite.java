package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.client_profile;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile_edite);

        Assign_value();

        Read_data();

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
    }

    private void Read_data()
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
