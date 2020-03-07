package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class mechanic_profile_dashboard extends AppCompatActivity {

    //firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;

    //Image view
    private ImageView owner_image;

    //textview
    private TextView owner_name,owner_email,owner_nic,owner_gender,owner_dob,owner_contact,owner_address,owner_city;
    //Button
    private Button go_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_profile_dashboard);

        //Assign All value
        Value_assign();

        //Load Value
        Load_value();


        go_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_profile_dashboard.this,mechanic_profile_dashboard_edit.class));
            }
        });


        //load current profile image
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Mechanicians Profile Image").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(owner_image);
            }
        });
    }
    private void Value_assign()
    {
        //firbase value set
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        storageReference=firebaseStorage.getReference();


        //textview value
        owner_name=findViewById(R.id.tv_mechanic_u_profile_name1);
        owner_email=findViewById(R.id.tv_mechanic_u_profile_email);
        owner_nic=findViewById(R.id.tv_mechanic_u_profile_nic);
        owner_dob=findViewById(R.id.tv_mechanic_u_profile_dob);
        owner_gender=findViewById(R.id.tv_mechanic_u_profile_gender);
        owner_contact=findViewById(R.id.tv_mechanic_u_profile_contact);
        owner_address=findViewById(R.id.tv_mechanic_u_profile_address);
        owner_city=findViewById(R.id.tv_mechanic_u_profile_city);

        //button value
        go_edit=findViewById(R.id.btn_goedite_mechanic_owner_profile);

        //Image view
        owner_image = findViewById(R.id.mechanic_u_profile_img);
    }

    private void Load_value()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mechanic_profile mechanicProfile = dataSnapshot.getValue(mechanic_profile.class);

                owner_name.setText(mechanicProfile.getOwner_fname()+" "+mechanicProfile.getOwner_sname());
                owner_email.setText(firebaseUser.getEmail());
                owner_nic.setText(mechanicProfile.getOwner_nic());
                owner_gender.setText(mechanicProfile.getOwner_gender());
                owner_dob.setText(mechanicProfile.getOwner_dob());
                owner_contact.setText(mechanicProfile.getOwner_contact());
                owner_address.setText(mechanicProfile.getOwner_address());
                owner_city.setText(mechanicProfile.getOwner_city());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mechanic_profile_dashboard.this,"Can't Connect Database now..!!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
