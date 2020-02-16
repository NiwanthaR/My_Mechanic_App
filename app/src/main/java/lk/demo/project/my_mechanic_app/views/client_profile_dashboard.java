package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.client_profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class client_profile_dashboard extends AppCompatActivity {

    private Button go_profile_edit;
    private TextView p_profilename,p_email,p_gender,p_nic,p_dob,p_contact,p_address,p_city;
    private ImageView profile_pic;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile_dashboard);

        Assign_varible();

        Read_data();

        go_profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(client_profile_dashboard.this,client_profile_edite.class));
            }
        });


        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Profile Picture").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profile_pic);
            }
        });





    }

    private void Assign_varible()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        go_profile_edit=findViewById(R.id.btn_goedite_client_profile);

        p_profilename=findViewById(R.id.client_p_profilename);
        p_email=findViewById(R.id.client_p_emailname);
        p_nic=findViewById(R.id.client_p_nicnum);
        p_gender=findViewById(R.id.client_p_gendertype);
        p_dob=findViewById(R.id.client_p_dob);
        p_contact=findViewById(R.id.client_p_contact_no);
        p_address=findViewById(R.id.client_p_addresss);
        p_city=findViewById(R.id.client_p_cityname);
        profile_pic=findViewById(R.id.client_pr_profilepic);
    }

    private void Read_data()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                client_profile clientProfile = dataSnapshot.getValue(client_profile.class);

                p_profilename.setText(clientProfile.getFname()+" "+clientProfile.getLname());
                p_email.setText(firebaseUser.getEmail());
                p_nic.setText(clientProfile.getNic());
                p_gender.setText(clientProfile.getGender());
                p_dob.setText(clientProfile.getDob());
                p_contact.setText(clientProfile.getContact());
                p_address.setText(clientProfile.getAddress());
                p_city.setText(clientProfile.getCity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(client_profile_dashboard.this,"Can't Connect Database now..!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
