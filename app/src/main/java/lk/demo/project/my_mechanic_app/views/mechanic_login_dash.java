package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class mechanic_login_dash extends AppCompatActivity {

    private Button login, goto_forget_password,goto_signup;
    private EditText provider_mail,provider_password;
    private TextView wrong_details;
    private CheckBox password_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_login);

        //call assign variable function
        Assign_variable();

        goto_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_login_dash.this, forget_password_provider_dash.class));
            }
        });
    }

    void Assign_variable()
    {
        login=(Button)findViewById(R.id.btn_login_service);
        goto_forget_password=(Button)findViewById(R.id.btn_goforget_service_login);
        goto_signup=(Button)findViewById(R.id.btn_gosignin_service_login);
        provider_mail=(EditText)findViewById(R.id.et_serviceemail_login);
        provider_password=(EditText)findViewById(R.id.et_servicepassword_login);
        wrong_details=(TextView)findViewById(R.id.tv_wrong_service_details);
        password_show=(CheckBox)findViewById(R.id.cb_service_show_password_login);
    }
}
