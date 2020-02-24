package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.widget.Button;

public class mechanic_shop_profile_dashboard extends AppCompatActivity {

    private Button go_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_shop_dashboard);
    }

    private void Assign_value()
    {
        go_edit=findViewById(R.id.btn_goedite_mechanic_shop_profile);
    }
}
