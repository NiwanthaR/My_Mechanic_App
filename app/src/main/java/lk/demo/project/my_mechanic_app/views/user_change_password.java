package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_client_signup;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class user_change_password extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;

    private LinearLayout pass_conferm,pass_update;
    private Button re_Auth_pass,update_pass_submit;
    private EditText pass_re_Auth,pass_new_pass,pass_new_re_pass;
    private CheckBox auth_pass_show,new_pass_show;
    private ImageView user_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password);

        //-------------------------------used variable----------------------------------------------
        Assing_variable();

        pass_conferm.setVisibility(View.VISIBLE);
        pass_update.setVisibility(View.GONE);


        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Profile Picture").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(user_img);
            }
        });


        re_Auth_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password=pass_re_Auth.getText().toString().trim();
                String email=firebaseUser.getEmail();

                AuthCredential cridential = EmailAuthProvider.getCredential(email,password);

                firebaseUser.reauthenticate(cridential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            pass_conferm.setVisibility(View.GONE);
                            pass_update.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(user_change_password.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        update_pass_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String new_password = pass_new_pass.getText().toString().trim();
                String re_new_password = pass_new_re_pass.getText().toString().trim();

                if (validation_client_signup.is_password_same(new_password,re_new_password))
                {
                    if (validation_client_signup.is_ValidPassword(new_password))
                    {
                        firebaseUser.updatePassword(new_password).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(user_change_password.this,"Password Update..!!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(user_change_password.this,settings_panel.class));
                                    finish();
                                }else {
                                    Toast.makeText(user_change_password.this,"Password update Failed..!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(user_change_password.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(user_change_password.this,"Please Fill Details",Toast.LENGTH_SHORT).show();
                }
            }
        });

        auth_pass_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    pass_re_Auth.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    pass_re_Auth.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        new_pass_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    pass_new_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pass_new_re_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    pass_new_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass_new_re_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private void Assing_variable()
    {
        re_Auth_pass=findViewById(R.id.btn_reAuth_pass_cp);
        update_pass_submit=findViewById(R.id.btn_update_pass_cp);

        pass_re_Auth=findViewById(R.id.et_reAuth_pass_cp);
        pass_new_pass=findViewById(R.id.et_cp_new_pass);
        pass_new_re_pass=findViewById(R.id.et_cp_new_pass_re);

        pass_conferm=findViewById(R.id.layoutPassword_cp);
        pass_update=findViewById(R.id.layoutUpdatePassword_cp);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseStorage=FirebaseStorage.getInstance();
        user_img=findViewById(R.id.change_pass_img);

        auth_pass_show=findViewById(R.id.cb_show_password_client_cp1);
        new_pass_show=findViewById(R.id.cb_show_password_client_cp2);
    }
}
