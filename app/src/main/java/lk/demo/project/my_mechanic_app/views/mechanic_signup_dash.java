package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_client_signup;
import lk.demo.project.my_mechanic_app.control.validation_provider_signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class mechanic_signup_dash extends AppCompatActivity {

    private EditText owner_fname,owner_lname,owner_nic,owner_dob,owner_address,owner_city,owner_contact,owner_email,owner_password,owner_repassword;
    private EditText shop_name,shop_regno,shop_start_date,shop_address,shop_contact,shop_email,shop_web,shop_open,shop_close,special_holiday,special_service;

    private String fname,lname,nic,dob,gender,address,city,contact,email,password,repassword;
    private String sname,sregno,sstartday,saddress,scontact,semail,sweb,sopen,sclose,poya_day,sspecial_holiday,visite_service,sspecial_service;

    private Button go_back,register_now;
    private CheckBox show_password_cb;
    private TextView worng_details;

    //gender read
    private RadioGroup gender_group;
    private RadioButton select_gender;

    //gender read
    private RadioGroup poya_group;
    private RadioButton poya_state;

    //gender read
    private RadioGroup visite_service_group;
    private RadioButton service_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_signup_dash);

        //assign variable
        variable_assign();

        //go back login dashboard
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_signup_dash.this,mechanic_login_dash.class));
            }
        });

        //if password show checkbox click
        show_password_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    owner_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    owner_repassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    owner_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    owner_repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        //click register button
        register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname=owner_fname.getText().toString().trim();
                lname=owner_lname.getText().toString().trim();
                nic=owner_nic.getText().toString().trim();
                dob=owner_dob.getText().toString().trim();
                //read gender value
                read_gender();
                address=owner_address.getText().toString().trim();
                city=owner_city.getText().toString().trim();
                contact=owner_contact.getText().toString().trim();
                email=owner_email.getText().toString().trim();
                password=owner_password.getText().toString().trim();
                repassword=owner_repassword.getText().toString().trim();

                sname=shop_name.getText().toString().trim();
                sregno=shop_regno.getText().toString().trim();
                sstartday=shop_start_date.getText().toString().trim();
                saddress=shop_address.getText().toString().trim();
                scontact=shop_contact.getText().toString().trim();
                scontact=shop_contact.getText().toString().trim();
                semail=shop_email.getText().toString().trim();
                sweb=shop_web.getText().toString().trim();
                sopen=shop_open.getText().toString().trim();
                sclose=shop_close.getText().toString().trim();
                //read poya day open or closed
                read_poyaday();
                sspecial_holiday=special_holiday.getText().toString().trim();
                sspecial_service=special_service.getText().toString().trim();
                //read visit site and service vehicle is possible
                read_visite_service();

            }
        });

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

    //read gender type
    private void read_poyaday()
    {
        // get selected radio button from radioGroup
        int selectedId = poya_group.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        poya_state = (RadioButton) findViewById(selectedId);

        poya_day= String.valueOf(poya_state.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    //read gender type
    private void read_visite_service()
    {
        // get selected radio button from radioGroup
        int selectedId = visite_service_group.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        service_state = (RadioButton) findViewById(selectedId);

        visite_service= String.valueOf(service_state.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }

    //validation user data
    private boolean all_right_owner()
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
                            if (validation_client_signup.is_contact(contact)){
                                return true;
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

    private boolean all_right_shop()
    {
        if (validation_provider_signup.is_fill_all(sname,sstartday,saddress,scontact,sopen,sclose))
        {
            if (validation_provider_signup.is_valide_contact(scontact))
            {
                return true;
            }else {
                worng_details.setText("Your shop contact is wrong..!!");
                return false;
            }
        }else {
            worng_details.setText("Please Fill important data..!!");
            return false;
        }
    }


    void variable_assign()
    {
        owner_fname=(EditText)findViewById(R.id.et_fname_provider_signup);
        owner_lname=(EditText)findViewById(R.id.et_lname_provider_signup);
        owner_nic=(EditText)findViewById(R.id.et_nic_provider_signup);
        owner_dob=(EditText)findViewById(R.id.et_dob_provider_signup);
        owner_address=(EditText)findViewById(R.id.et_address_provider_signup);
        owner_city=(EditText)findViewById(R.id.et_city_provider_signup);
        owner_contact=(EditText)findViewById(R.id.et_contact_provider_signup);
        owner_email=(EditText)findViewById(R.id.et_email_provider_signup);
        owner_password=(EditText)findViewById(R.id.et_password_client_signup);
        owner_repassword=(EditText)findViewById(R.id.et_repassword_provider_signup);

        shop_name=(EditText)findViewById(R.id.et_shopname_provider_signup);
        shop_regno=(EditText)findViewById(R.id.et_shopregnum_provider_signup);
        shop_start_date=(EditText)findViewById(R.id.et_start_date_provider_signup);
        shop_address=(EditText)findViewById(R.id.et_shopaddress_provider_signup);
        shop_contact=(EditText)findViewById(R.id.et_shopcontact_provider_signup);
        shop_email=(EditText)findViewById(R.id.et_shopemai_provider_signup);
        shop_web=(EditText)findViewById(R.id.et_shopweb_provider_signup);
        shop_open=(EditText)findViewById(R.id.et_starttime_provider_signup);
        shop_close=(EditText)findViewById(R.id.et_closetime_provider_signup);
        special_holiday=(EditText)findViewById(R.id.et_shopsnote_provider_signup);
        special_service=(EditText)findViewById(R.id.et_service_info_provider_signup);

        go_back=(Button) findViewById(R.id.btn_alredysign_provider_signup);
        register_now =(Button)findViewById(R.id.btn_registernow_provider_signup);

        show_password_cb=(CheckBox)findViewById(R.id.cb_show_password_provider_signup);
        worng_details=(TextView)findViewById(R.id.tv_wrong_provider_signup);

        gender_group=(RadioGroup)findViewById(R.id.radio_gender_provider_signup);
        poya_group=(RadioGroup)findViewById(R.id.radio_poya_provider_signup);
        visite_service_group=(RadioGroup)findViewById(R.id.radio_breakdown_provider_signup);
    }


}
