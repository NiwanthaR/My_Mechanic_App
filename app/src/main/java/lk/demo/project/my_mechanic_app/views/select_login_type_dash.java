package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class select_login_type_dash extends AppCompatActivity {

    private Button user_account_btn,service_provider_account_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_type);
        Assign_varible();

        //Go to user account login page
        user_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(select_login_type_dash.this, client_login_dash.class));
            }
        });

        //Go to mechanic account login page
        service_provider_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(select_login_type_dash.this, mechanic_login_dash.class));
            }
        });
    }

    //Assign Variables
    private void Assign_varible()
    {
        user_account_btn=(Button)findViewById(R.id.btn_user_accont);
        service_provider_account_btn=(Button)findViewById(R.id.btn_service_provider);
    }
}
