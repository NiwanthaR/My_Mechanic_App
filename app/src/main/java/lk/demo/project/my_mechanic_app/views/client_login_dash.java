package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.MainActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class client_login_dash extends AppCompatActivity {

    private Button login,goto_signup,goto_forget;
    private TextView wrong_details;
    private EditText user_email,user_password;
    private CheckBox password_show;

    private String userEmail,userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        //Assign Variable
        Assign_variable();

        //go to forget password dashboard
        goto_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(client_login_dash.this, forget_password_client_dash.class));
            }
        });

        //go to register dashboard
        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(client_login_dash.this,client_signup_dash.class));
            }
        });

        //press login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail=user_email.getText().toString().trim();
                userPassword=user_password.getText().toString().trim();

                //call validation function
                Validate_details(userEmail,userPassword);
            }
        });
    }

    void Validate_details(String user_email,String user_password)
    {
        if (user_email.equals("admin") && user_password.equals("123"))
        {
            Toast.makeText(client_login_dash.this,"Login Successfully....!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(client_login_dash.this, MainActivity.class));
        }else {
            wrong_details.setText("Your Details Warning Check Again");
        }
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
