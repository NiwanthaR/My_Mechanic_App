package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class user_add_feed_back_dash extends AppCompatActivity {

    private LinearLayout upload_state_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_feed_back_dash);

        //Assign
        Assign_UI();

        //start
        upload_state_layout.setVisibility(View.GONE);
    }

    private void Assign_UI() {

        //upload state hide
        upload_state_layout = findViewById(R.id.ll_feedback_upload_state);
    }
}
