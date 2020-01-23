package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.MainActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_client_signup;
import lk.demo.project.my_mechanic_app.model.client_profile;

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

public class client_login_dash extends AppCompatActivity {

    private Button login,goto_signup,goto_forget;
    private TextView wrong_details;
    private EditText user_email,user_password;
    private CheckBox password_show;

    private String userEmail,userPassword,user_type,login_type="client";

    //firebase Auth
    private FirebaseAuth firebaseAuth;

    //firebase Database
    private FirebaseDatabase firebaseDatabase;

    //progreedialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        //Assign Variable
        Assign_variable();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user!=null)
        {
            finish();
            startActivity(new Intent(client_login_dash.this,MainActivity.class));
        }

        //readUsertype();

        //go to forget password dashboard
        goto_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(client_login_dash.this, forget_password_client_dash.class));
            }
        });

        //go to register dashboard
        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(client_login_dash.this,client_signup_dash.class));
            }
        });

        //press login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail=user_email.getText().toString().trim();
                userPassword=user_password.getText().toString().trim();

                //call validation function
                Validate_details(userEmail,userPassword);
            }
        });

        //password show button press
        password_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    user_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    user_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    void Validate_details(String user_email,String user_password)
    {
       if (validation_client_signup.is_fill(userEmail,userPassword))
       {
           if(validation_client_signup.is_Validmail(user_email))
           {
               progressDialog.setMessage("Your Details in Processing Please waite..!");
               progressDialog.show();

               firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if (task.isSuccessful())
                       {
                           progressDialog.dismiss();
//                         Toast.makeText(client_login_dash.this,"Login Sucessfully",Toast.LENGTH_SHORT).show();
//                         startActivity(new Intent(client_login_dash.this,MainActivity.class));
                           checkEmailVerification();
                       }else{
                           progressDialog.dismiss();
                           wrong_details.setText("Login Failed Check your Details");
                       }
                   }
               });
           }else {
               wrong_details.setText("Please Enter Valid Email");
           }
       }else{
           wrong_details.setText("Please Fill All Details");
       }
    }

    void Assign_variable()
    {
        login=(Button)findViewById(R.id.btn_login_client);
        goto_forget=(Button)findViewById(R.id.btn_goforget_client_login);
        goto_signup=(Button)findViewById(R.id.btn_gosignin_client_login);
        wrong_details=(TextView)findViewById(R.id.tv_wrongdetails_client_login);
        user_email=(EditText)findViewById(R.id.et_clientemail_login);
        user_password=(EditText)findViewById(R.id.et_clientpassword_login);
        password_show=(CheckBox)findViewById(R.id.cb_show_password_client_login);

        //firebase
        firebaseAuth=FirebaseAuth.getInstance();

        //firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance();

        //progress Dialog
        progressDialog= new ProgressDialog(this);
    }

    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag)
        {
            finish();
            startActivity(new Intent(client_login_dash.this,MainActivity.class));
        }else {
            //Toast.makeText(client_login_dash.this,"Verify Your Email First..!!",Toast.LENGTH_SHORT).show();
            wrong_details.setText("Verify Your Email First..!");
            firebaseAuth.signOut();
        }
    }

    private  void readUsertype()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                client_profile clientProfile = dataSnapshot.getValue(client_profile.class);
                user_type = clientProfile.usertype;
                System.out.println(user_type);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
