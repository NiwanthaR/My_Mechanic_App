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

public class client_login_dash extends AppCompatActivity {

    private Button login,goto_signup,goto_forget;
    private TextView wrong_details;
    private EditText user_email,user_password;
    private CheckBox password_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        //Assign Variable
        Assign_variable();

        goto_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(client_login_dash.this, forget_password_client_dash.class));
            }
        });
    }

    void Assign_variable()
    {
        login=(Button)findViewById(R.id.btn_login_client);
        goto_forget=(Button)findViewById(R.id.btn_goforget_client_login);
        goto_signup=(Button)findViewById(R.id.btn_gosignin_client_login);
        wrong_details=(TextView)findViewById(R.id.tv_wrongdetails_client_login);
        user_email=(EditText)findViewById(R.id.et_clientemail_login);
        user_password=(EditText)findViewById(R.id.et_clientpassword_login);
        password_show=(CheckBox)findViewById(R.id.cb_show_password_client_login);
    }
}
