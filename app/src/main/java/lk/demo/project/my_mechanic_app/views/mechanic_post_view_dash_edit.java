package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class mechanic_post_view_dash_edit extends AppCompatActivity {

    //key Declare
    private String AD_Number;
    //Layout
    private LinearLayout state_layout,buttons_layout;
    //Edittext
    private EditText post_title,post_description,post_price,post_contact;
    //Textview
    private TextView post_condition,post_store,post_store_location,post_owner,wrong_details,presantage;
    //Progressbar
    private ProgressBar upload_state;
    //Buttons
    private Button btn_update,btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_post_view_dash_edit);

        //String Key
        AD_Number = getIntent().getStringExtra("AD_Number");

        //Ui Declare
        UI_Declare();
    }

    private void UI_Declare() {
        //layouts
        state_layout = findViewById(R.id.ll_mechanic_post_details_edit_upload_state);
        buttons_layout = findViewById(R.id.li_mechanic_post_details_edit_Buttons);

        state_layout.setVisibility(View.GONE);

        //Buttons
        btn_delete = findViewById(R.id.btn_mechanic_post_details_edit_delete_add);
        btn_update = findViewById(R.id.btn_mechanic_post_details_edit_edit_add);

        //Textview
        post_condition = findViewById(R.id.tv_mechanic_post_details_edit_condition);
        post_store = findViewById(R.id.tv_mechanic_post_details_edit_store_name);
        post_store_location = findViewById(R.id.tv_mechanic_post_details_edit_store_location);
        post_owner = findViewById(R.id.tv_mechanic_post_details_edit_owner_name);
        wrong_details = findViewById(R.id.tv_mechanic_post_details_edit_wrong_details);
        presantage = findViewById(R.id.et_mechanic_post_details_edit_progress_presantage);

        //Editetext
        post_title = findViewById(R.id.et_mechanic_post_details_edit_title);
        post_description = findViewById(R.id.et_mechanic_post_details_edit__description);
        post_price = findViewById(R.id.et_mechanic_post_details_edit_price);
        post_contact = findViewById(R.id.tv_mechanic_post_details_edit_contact_number);

        //Progressbar
        upload_state = findViewById(R.id.et_mechanic_post_details_edit_upload_progess_bar);
    }
}
