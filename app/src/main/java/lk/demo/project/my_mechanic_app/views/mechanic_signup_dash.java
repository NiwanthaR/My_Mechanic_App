package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class mechanic_signup_dash extends AppCompatActivity {

    private EditText owner_fname,owner_lname,owner_nic,owner_dob,ownwe_address,owner_city,owner_contact,owner_email,owner_password,owner_repassword;
    private EditText shop_name,shop_regno,shop_start_date,shop_address,shop_contact,shop_email,shop_web,shop_open,shop_close,special_holiday,special_service;

    private Button go_back,register_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_signup_dash);
    }
}
