package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class mechanic_post_view_dash_edit extends AppCompatActivity {

    //key Declare
    private String AD_Number;

    //Layout
    private LinearLayout state_layout,buttons_layout;

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
    }
}
