package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class user_change_password extends AppCompatActivity {

    LinearLayout pass_conferm,pass_update;
    private Button re_Auth_pass,update_pass_submit;
    private EditText pass_re_Auth,pass_new_pass,pass_new_re_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password);

        //-------------------------------used variable----------------------------------------------
        Assing_variable();

        pass_conferm.setVisibility(View.VISIBLE);
        pass_update.setVisibility(View.GONE);

        re_Auth_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_conferm.setVisibility(View.GONE);
                pass_update.setVisibility(View.VISIBLE);
            }
        });

    }

    private void Assing_variable()
    {
        re_Auth_pass=findViewById(R.id.btn_reAuth_pass_cp);
        update_pass_submit=findViewById(R.id.btn_update_pass_cp);

        pass_re_Auth=findViewById(R.id.et_reAuth_pass_cp);
        pass_new_pass=findViewById(R.id.et_cp_new_pass);
        pass_new_re_pass=findViewById(R.id.et_cp_new_pass_re);

        pass_conferm=findViewById(R.id.layoutPassword_cp);
        pass_update=findViewById(R.id.layoutUpdatePassword_cp);
    }
}
