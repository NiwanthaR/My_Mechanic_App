package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class mechanic_view_service_details_edit extends AppCompatActivity {

    //key
    private String Service_Key;
    //Buttons
    private Button btn_update,btn_delete;
    //Edit text
    private EditText service_title,service_description,service_price,service_contact;
    //Text view
    private TextView store_name,store_location,wrong_details,presantage;
    //Progressbar
    private ProgressBar upload_prestage;
    //Imageview
    private ImageView service_image;
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

        //Edit Text
        service_title = findViewById(R.id.et_service_details_edit_title);
        service_description = findViewById(R.id.et_service_details_edit_details);
        service_contact = findViewById(R.id.et_service_details_edit_contact);
        service_price = findViewById(R.id.et_service_details_edit_price);

        //Text view
        store_name = findViewById(R.id.tv_service_details_edit_store_name);
        store_location = findViewById(R.id.tv_service_details_edit_location);
        wrong_details = findViewById(R.id.tv_service_details_edit_wrong_details);
        presantage = findViewById(R.id.et_service_details_edit__progress_presantage);
        //Progressbar
        upload_prestage = findViewById(R.id.et_service_details_edit_upload_progess_bar);
        //Imageview
        service_image = findViewById(R.id.img_service_details_edit_img);
    }
}
