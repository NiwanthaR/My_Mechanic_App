package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class user_change_email extends AppCompatActivity {

    private LinearLayout password_layout,new_email_layout;
    private EditText password_check, newemail_submit;
    private Button password_submit,email_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_email);

        //----------------------------Assign Variable-----------------------------------------------
        Assign_value();

        password_layout.setVisibility(View.VISIBLE);
        new_email_layout.setVisibility(View.GONE);

        password_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_layout.setVisibility(View.GONE);
                new_email_layout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void Assign_value()
    {
        password_layout=findViewById(R.id.layoutPassword_ce);
        new_email_layout=findViewById(R.id.layoutUpdateEmail_ce);

        password_check=findViewById(R.id.et_passAuth_ce);
        newemail_submit=findViewById(R.id.et_updateEmai_ce);

        password_submit=findViewById(R.id.btn_passAuth_ce);
        email_submit=findViewById(R.id.btn_st_change_email);
    }
}
