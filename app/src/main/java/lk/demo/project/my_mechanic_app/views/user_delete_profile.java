package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user_delete_profile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private LinearLayout password_check,delete_profile;

    private Button re_Authpass,delete_account;
    private EditText auth_password,display_email;
    private CheckBox show_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete_profile);

        Assign_varible();

        delete_profile.setVisibility(View.GONE);
        password_check.setVisibility(View.VISIBLE);

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
                        }else {
                            Toast.makeText(user_delete_profile.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

        re_Authpass=findViewById(R.id.btn_reAuth_pass_dp);
        delete_account=findViewById(R.id.btn_delete_dp);

        auth_password=findViewById(R.id.et_reAuth_pass_dp);
        display_email=findViewById(R.id.et_email_display_dp);

        show_pass=findViewById(R.id.cb_show_password_client_dp);

        password_check=findViewById(R.id.layoutPassword_dp);
        delete_profile=findViewById(R.id.layoutDeleteProfile_dp);

    }
}
