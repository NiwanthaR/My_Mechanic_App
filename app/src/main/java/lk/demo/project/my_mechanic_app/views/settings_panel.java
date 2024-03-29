package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class settings_panel extends AppCompatActivity {

    private Button change_password,change_email,delete_account,change_pro_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_panel);

        Assign_varible();

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings_panel.this,user_change_password.class));
            }
        });

        change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings_panel.this,user_change_email.class));
            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings_panel.this,user_delete_profile.class));
            }
        });

        change_pro_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings_panel.this,user_change_profile_picture.class));
            }
        });
    }

    private void Assign_varible()
    {
        change_password=findViewById(R.id.btn_st_change_password);
        change_email=findViewById(R.id.btn_st_change_email);
        delete_account=findViewById(R.id.btn_st_delete_account);
        change_pro_pic=findViewById(R.id.btn_st_change_profilepic);
    }
}
