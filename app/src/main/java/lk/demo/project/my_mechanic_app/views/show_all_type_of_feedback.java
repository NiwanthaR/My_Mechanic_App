package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class show_all_type_of_feedback extends AppCompatActivity {

    private TextView positive_feed,neutrel_feed,negative_fee,all_feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_type_of_feedback);

        //Assign UI Values
        Assign_Value();

        //start view
        all_feed.setBackgroundColor(Color.parseColor("#95EAFF"));


        //click all feed
        all_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all_feed.setBackgroundColor(Color.parseColor("#95EAFF"));
                positive_feed.setBackgroundColor(Color.parseColor("#FBFCFC"));
                neutrel_feed.setBackgroundColor(Color.parseColor("#FBFCFC"));
                negative_fee.setBackgroundColor(Color.parseColor("#FBFCFC"));
            }
        });

        //click positive feed
        positive_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                positive_feed.setBackgroundColor(Color.parseColor("#95EAFF"));
                neutrel_feed.setBackgroundColor(Color.parseColor("#FBFCFC"));
                negative_fee.setBackgroundColor(Color.parseColor("#FBFCFC"));
                all_feed.setBackgroundColor(Color.parseColor("#FBFCFC"));
            }
        });

        //click neutral feed
        neutrel_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                neutrel_feed.setBackgroundColor(Color.parseColor("#95EAFF"));
                positive_feed.setBackgroundColor(Color.parseColor("#FBFCFC"));
                negative_fee.setBackgroundColor(Color.parseColor("#FBFCFC"));
                all_feed.setBackgroundColor(Color.parseColor("#FBFCFC"));
            }
        });

        //click negative feed
        negative_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                negative_fee.setBackgroundColor(Color.parseColor("#95EAFF"));
                neutrel_feed.setBackgroundColor(Color.parseColor("#FBFCFC"));
                positive_feed.setBackgroundColor(Color.parseColor("#FBFCFC"));
                all_feed.setBackgroundColor(Color.parseColor("#FBFCFC"));
            }
        });
    }

    private void Assign_Value() {

        //Assign Text view
        positive_feed = findViewById(R.id.tv_all_user_feedback_positive);
        neutrel_feed = findViewById(R.id.tv_all_user_feedback_neutral);
        negative_fee = findViewById(R.id.tv_all_user_feedback_negative);
        all_feed = findViewById(R.id.tv_all_user_feedback_all);
    }
}
