package lk.demo.project.my_mechanic_app;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.views.client_login_dash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button logout;

    //Firebase
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //variable assign
        variable_assign();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setLogout();
            }
        });
    }

    void variable_assign()
    {
        logout=(Button)findViewById(R.id.btn_logout_client);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    void setLogout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, client_login_dash.class));
    }
}
