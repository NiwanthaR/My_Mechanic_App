package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mechanic_settings_panel extends AppCompatActivity {

    private Button change_email,change_password,change_profile_pic,change_shop_logo,change_shop_location,delete_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_settings_panel);

        //Value Equal
        Assign_Variable();

        change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_settings_panel.this,user_change_email.class));
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_settings_panel.this,user_change_password.class));
            }
        });

        change_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_settings_panel.this,mechanic_change_profile_image.class));
            }
        });

        change_shop_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_settings_panel.this,user_change_profile_picture.class));
            }
        });

        change_shop_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void Assign_Variable() {

        change_email = findViewById(R.id.btn_mechanic_st_change_email);
        change_password = findViewById(R.id.btn_mechanic_st_change_password);
        change_profile_pic = findViewById(R.id.btn_mechanic_st_change_profilepic);
        change_shop_logo = findViewById(R.id.btn_mechanic_st_change_shoplogo);
        change_shop_location = findViewById(R.id.btn_mechanic_st_change_shop_location);
        delete_account = findViewById(R.id.btn_mechanic_st_change_delete_account);
    }
}
