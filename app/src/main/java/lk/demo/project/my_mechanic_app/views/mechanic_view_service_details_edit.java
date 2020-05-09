package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class mechanic_view_service_details_edit extends AppCompatActivity {

    //key
    private String Service_Key;

    //Buttons
    Button btn_update,btn_delete;

    //Layout
    private LinearLayout button_layout,state_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_view_service_details_edit);

        //get key
        Service_Key = getIntent().getStringExtra("Service_Key");

        UI_Declare();
    }

    private void UI_Declare() {

        //Layouts
        button_layout = findViewById(R.id.ll_service_details_edit_buttons);
        state_layout = findViewById(R.id.ll_et_service_details_edit_upload_state);

        //Hide
        state_layout.setVisibility(View.GONE);

        //Buttons
        btn_delete = findViewById(R.id.btn_et_service_details_edit_delete);
        btn_update = findViewById(R.id.btn_et_service_details_edit_update);

    }
}
