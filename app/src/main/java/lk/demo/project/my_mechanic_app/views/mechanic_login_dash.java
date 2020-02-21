package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.MainActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_client_signup;
import lk.demo.project.my_mechanic_app.control.validation_provider_signup;
import lk.demo.project.my_mechanic_app.model.client_profile;
import lk.demo.project.my_mechanic_app.model.mechanic_profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mechanic_login_dash extends AppCompatActivity {

    private Button login, goto_forget_password,goto_signup;
    private EditText provider_mail,provider_password;
    private TextView wrong_details;
    private CheckBox password_show;

    private String mechanic_email,mechanic_password,user_type="mechanic";

    //firebase auth
    private FirebaseAuth firebaseAuth;

    //firebase Database
    private FirebaseDatabase firebaseDatabase;

    //progessdialog box
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_login);

        //call assign variable function
        Assign_variable();

        // find firebase user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //if any user login still now ?
        if(user != null)
        {
            finish();
            startActivity(new Intent(mechanic_login_dash.this,mechanic_dashboard.class));
        }

        //goto forget password
        goto_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_login_dash.this, mechanic_dashboard.class));
            }
        });

        //goto signup
        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_login_dash.this,mechanic_signup_dash.class));
            }
        });

        //password show button press
        password_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    provider_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    provider_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mechanic_email=provider_mail.getText().toString();
                mechanic_password=provider_password.getText().toString();

                validate(mechanic_email,mechanic_password);
            }
        });
    }

    void Assign_variable()
    {
        login=(Button)findViewById(R.id.btn_login_service);
        goto_forget_password=(Button)findViewById(R.id.btn_goforget_service_login);
        goto_signup=(Button)findViewById(R.id.btn_gosignin_service_login);
        provider_mail=(EditText)findViewById(R.id.et_serviceemail_login);
        provider_password=(EditText)findViewById(R.id.et_servicepassword_login);
        wrong_details=(TextView)findViewById(R.id.tv_wrong_service_details);

        password_show=(CheckBox)findViewById(R.id.cb_service_show_password_login);

        //firbase assign
        firebaseAuth = FirebaseAuth.getInstance();

        //firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();

        //progess dialog
        progressDialog = new ProgressDialog(this);
    }

    private void validate(String email,String password)
    {
        if (validation_client_signup.is_fill(email,password))
        {
            if(validation_client_signup.is_Validmail(email))
            {
                progressDialog.setMessage("Your Details in Processing Please waite..!");
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            //checkemail_verification();
                            checkUsertype();

                        }else {
                            progressDialog.dismiss();
                            wrong_details.setText("Login Failed Check your Details");
                        }
                    }
                });


            }else {
                wrong_details.setText("Please enter valid Email");
            }
        }else {
            wrong_details.setText("Please Fill All Details..!");
        }
    }

    private void checkemail_verification()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        boolean emailflage = user.isEmailVerified();

        if (emailflage)
        {
            finish();
            startActivity(new Intent(mechanic_login_dash.this, mechanic_dashboard.class));
        }else{
            wrong_details.setText("Verify Your Email First..!");
            firebaseAuth.signOut();
        }

    }

    private  void checkUsertype()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mechanic_profile mechanicProfile = dataSnapshot.getValue(mechanic_profile.class);

                if (user_type.equals(mechanicProfile.getUsertype().trim()))
                {
                    //state = true;
                    checkemail_verification();
                }else {
                    //state =false;
                    closeActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mechanic_login_dash.this,"You Can't Login this Movement",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void closeActivity()
    {
        firebaseAuth.signOut();
        Toast.makeText(mechanic_login_dash.this,"Chek Type",Toast.LENGTH_SHORT).show();
    }
}
