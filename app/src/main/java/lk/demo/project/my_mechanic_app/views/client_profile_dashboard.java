package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class client_profile_dashboard extends AppCompatActivity {

    private Button go_profile_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile_dashboard);

        Assign_varible();

        go_profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(client_profile_dashboard.this,client_profile_edite.class));
            }
        });


    }

    private void Assign_varible()
    {
        go_profile_edit=findViewById(R.id.btn_goedite_client_profile);
    }
}
