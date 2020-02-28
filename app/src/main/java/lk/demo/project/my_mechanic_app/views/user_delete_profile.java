package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class user_delete_profile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    private LinearLayout password_check,delete_profile;

    private Button re_Authpass,delete_account;
    private TextView display_email;
    private EditText auth_password;
    private CheckBox show_pass;
    private ImageView user_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete_profile);

        Assign_varible();

        delete_profile.setVisibility(View.GONE);
        password_check.setVisibility(View.VISIBLE);


        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Profile Picture").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(user_img);
            }
        });

        re_Authpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = firebaseUser.getEmail();
                String password = auth_password.getText().toString().trim();

                AuthCredential authCredential = EmailAuthProvider.getCredential(email,password);

                firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            delete_profile.setVisibility(View.VISIBLE);
                            password_check.setVisibility(View.GONE);
                            display_email.setText(firebaseUser.getEmail());
                        }else {
                            Toast.makeText(user_delete_profile.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(user_delete_profile.this);
                builder.setTitle("Are you sure...??");
                builder.setMessage("Delete this account will result in completely removing your" +
                        "account from the system and you wan't be able to access this app.");
                builder.setPositiveButton("Delete Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        delete_user_data();

                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(user_delete_profile.this,"Profile Delete Successful..!!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(user_delete_profile.this,select_login_type_dash.class));
                                    finish();
                                }else {
                                    Toast.makeText(user_delete_profile.this,"Profile Delete Failed..!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    auth_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    auth_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }

    private void Assign_varible()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();

        re_Authpass=findViewById(R.id.btn_reAuth_pass_dp);
        delete_account=findViewById(R.id.btn_delete_dp);

        auth_password=findViewById(R.id.et_reAuth_pass_dp);
        display_email=findViewById(R.id.et_email_display_dp);

        show_pass=findViewById(R.id.cb_show_password_client_dp);

        password_check=findViewById(R.id.layoutPassword_dp);
        delete_profile=findViewById(R.id.layoutDeleteProfile_dp);

        user_img=findViewById(R.id.delete_account_img);

    }

    private void delete_user_data()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());
        databaseReference.removeValue();
    }
}
