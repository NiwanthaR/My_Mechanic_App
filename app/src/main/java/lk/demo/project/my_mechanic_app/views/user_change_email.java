package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_client_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user_change_email extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private LinearLayout password_layout,new_email_layout;
    private EditText password_check, newemail_submit;
    private Button password_submit,email_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_email);

        //----------------------------Assign Variable-----------------------------------------------
        Assign_value();

        password_layout.setVisibility(View.VISIBLE);
        new_email_layout.setVisibility(View.GONE);

        password_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=firebaseUser.getEmail();
                String password=password_check.getText().toString().trim();

                AuthCredential credential = EmailAuthProvider.getCredential(email,password);

                firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            password_layout.setVisibility(View.GONE);
                            new_email_layout.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(user_change_email.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//
            }
        });

        email_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = newemail_submit.getText().toString().trim();

                if (email.isEmpty())
                {
                    Toast.makeText(user_change_email.this,"Enter Your Email",Toast.LENGTH_SHORT).show();
                }else {
                    if (validation_client_signup.is_Validmail(email))
                    {
                        firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(user_change_email.this,"Email Update Successfully",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(user_change_email.this,settings_panel.class));
                                    finish();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(user_change_email.this,"Invalid Email Type",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void Assign_value()
    {
        password_layout=findViewById(R.id.layoutPassword_ce);
        new_email_layout=findViewById(R.id.layoutUpdateEmail_ce);

        password_check=findViewById(R.id.et_passAuth_ce);
        newemail_submit=findViewById(R.id.et_updateEmai_ce);

        password_submit=findViewById(R.id.btn_passAuth_ce);
        email_submit=findViewById(R.id.btn_updateemail_ce);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
    }
}
