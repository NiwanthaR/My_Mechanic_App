package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.control.validation_client_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_password_client_dash extends AppCompatActivity {

    private Button go_back,send_reset_email;
    private EditText client_mail;
    private TextView wrong_details;

    private String reset_email;

    //Firebase
    private FirebaseAuth firebaseAuth;

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

        send_reset_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reset_email=client_mail.getText().toString();

                if (validation_client_signup.is_Validmail(reset_email))
                {
                    firebaseAuth.sendPasswordResetEmail(reset_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(forget_password_client_dash.this,"Password Reset Email Send..!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forget_password_client_dash.this,client_login_dash.class));
                            }else{
                                Toast.makeText(forget_password_client_dash.this,"Something was Wrong Try Again..!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    wrong_details.setText("Please Enter Valid Email..!");
                }
            }
        });

    }
    void Assign_varible()
    {
        send_reset_email=(Button)findViewById(R.id.btn_resetsend_client_forget);
        go_back=(Button)findViewById(R.id.btn_goback_client_forget);
        client_mail=(EditText)findViewById(R.id.et_resetmail_client_forget);
        wrong_details=(TextView)findViewById(R.id.tv_wrong_client_forget);

        firebaseAuth=FirebaseAuth.getInstance();
    }
}
