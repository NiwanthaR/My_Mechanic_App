package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_client_signup;
import lk.demo.project.my_mechanic_app.model.client_profile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;

public class client_signup_dash extends AppCompatActivity {

    private EditText user_fname,user_lname,user_nic,user_dob,user_gender,user_address,user_city,user_contact,user_email,user_password,user_repassword;
    private Button register_btn,goback_btn;
    private CheckBox show_password_cb;
    private TextView worng_details;
    private ImageView user_profile_pic;

    private String fname,lname,nic,dob,gender,address,city,contact,email,password,repassword,user_type="client";

    //firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    //datepicker
    Calendar calendar;
    DatePickerDialog datePickerDialog;

    //gender read
    private RadioGroup gender_group;
    private RadioButton select_gender;

    private static int PICK_IMG=123;
    private Uri image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_signup_dash);

        //assign variable
        Assign_variable();



        user_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Profile Picture"),PICK_IMG);
            }
        });


        //press go back button code
        goback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(client_signup_dash.this,client_login_dash.class));
            }
        });

        //date of birth select dialog
        user_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar= Calendar.getInstance();
                int date=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);
                datePickerDialog=new DatePickerDialog(client_signup_dash.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        user_dob.setText(year +"/"+ (month+1) +"/"+ dayOfMonth);
                    }
                },year,month,date);
                datePickerDialog.show();
            }
        });

        //if password show checkbox click
        show_password_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    user_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    user_repassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    user_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    user_repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        //press the register butoon
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname=user_fname.getText().toString();
                lname=user_lname.getText().toString();
                nic=user_nic.getText().toString();
                dob=user_dob.getText().toString();
                //read gender
                read_gender();

                address=user_address.getText().toString();
                city=user_city.getText().toString();
                contact=user_contact.getText().toString();
                email=user_email.getText().toString();
                password=user_password.getText().toString();
                repassword=user_repassword.getText().toString();

                if (all_right())
                {
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
//                                Toast.makeText(client_signup_dash.this,"Registration Successfully",Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(client_signup_dash.this,client_login_dash.class));
                                  sendEmaiVerification();
                            }else{
                                Toast.makeText(client_signup_dash.this,"Something was Wrong",Toast.LENGTH_SHORT).show();
                                //worng_details.setText("Something Wrong");
                            }
                        }
                    });
                }

            }
        });
    }

    //load Image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMG && resultCode == RESULT_OK && data.getData() != null)
        {
            image_path = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),image_path);
                user_profile_pic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //read gender type
    private void read_gender()
    {
        // get selected radio button from radioGroup
        int selectedId = gender_group.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        select_gender = (RadioButton) findViewById(selectedId);

        gender= String.valueOf(select_gender.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    void Assign_variable()
    {
        user_fname=(EditText)findViewById(R.id.et_fname_client_signup);
        user_lname=(EditText)findViewById(R.id.et_lname_client_signup);
        user_nic=(EditText)findViewById(R.id.et_nic_client_signup);
        user_dob=(EditText)findViewById(R.id.et_dob_client_signup);
        user_address=(EditText)findViewById(R.id.et_address_client_signup);
        user_city=(EditText)findViewById(R.id.et_city_client_signup);
        user_contact=(EditText)findViewById(R.id.et_contact_client_signup);
        user_email=(EditText)findViewById(R.id.et_email_client_signup);
        user_password=(EditText)findViewById(R.id.et_password_client_signup);
        user_repassword=(EditText)findViewById(R.id.et_repassword_client_signup);

        register_btn=(Button)findViewById(R.id.btn_registernow_client_signup);
        goback_btn=(Button)findViewById(R.id.btn_alredysign_client_signup);
        show_password_cb=(CheckBox)findViewById(R.id.cb_show_password_client_signup);

        user_profile_pic=findViewById(R.id.img_profilepic_signup);

        worng_details=(TextView)findViewById(R.id.tv_wrong_client_signup);

        gender_group=(RadioGroup)findViewById(R.id.radio_gender_client_signup);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

    }

    //validation user data
    private boolean all_right()
    {
        if (validation_client_signup.is_fill(fname,lname,nic,dob,address,city,contact,email,password))
        {
            if (validation_client_signup.is_password_same(password,repassword))
            {
                if (validation_client_signup.is_ValidPassword(password))
                {
                    if (validation_client_signup.is_Validmail(email))
                    {
                        if (validation_client_signup.is_ValidNic(nic))
                        {
                            if (validation_client_signup.is_contact(contact))
                            {
                                if (validation_client_signup.is_image_ok(image_path))
                                {
                                    return true;
                                }else {
                                    worng_details.setText("Please Select Image");
                                }
                            }else{
                                worng_details.setText("Please Enter Valid Contact");
                            }
                        }else {
                            worng_details.setText("Please Enter Valid NIC");
                        }
                    }else {
                        worng_details.setText("Your Email is not Valid");
                    }
                }else {
                    worng_details.setText("Your Password not Valid");
                }
            }else {
                worng_details.setText("Your Password isn't Same..!!");
            }
        } else {
            worng_details.setText("Please Fill All Details...!!");
        }
        return false;
    }

    private void sendEmaiVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(client_signup_dash.this,"Verification Mail Has Been Send..!!",Toast.LENGTH_SHORT).show();
                        sendUserdata();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(client_signup_dash.this,client_login_dash.class));
                    }else {
                        Toast.makeText(client_signup_dash.this,"Verification Mail Hasn't Been Send..!!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserdata()
    {
        DatabaseReference myref = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());
        client_profile clientprofile = new client_profile(fname,lname,nic,dob,gender,address,city,contact,user_type);
        myref.setValue(clientprofile);

        StorageReference myReference = storageReference.child("Profile Picture").child(firebaseAuth.getUid());
        UploadTask uploadTask = myReference.putFile(image_path);

         uploadTask.addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(client_signup_dash.this,"Image Upload Failed",Toast.LENGTH_SHORT).show();
             }
         }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 Toast.makeText(client_signup_dash.this,"Image Upload Successfully",Toast.LENGTH_SHORT).show();
             }
         });
    }
}
