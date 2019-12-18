package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class forget_password_client_dash extends AppCompatActivity {

    private Button go_back,send_reset_email;
    private EditText client_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_client_dash);

        //Assign Variable
        Assign_varible();

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(forget_password_client_dash.this,client_login_dash.class));
            }
        });

    }
    void Assign_varible()
    {
        send_reset_email=(Button)findViewById(R.id.btn_resetsend_client_forget);
        go_back=(Button)findViewById(R.id.btn_goback_client_forget);
        client_mail=(EditText)findViewById(R.id.et_resetmail_client_forget);
    }
}
