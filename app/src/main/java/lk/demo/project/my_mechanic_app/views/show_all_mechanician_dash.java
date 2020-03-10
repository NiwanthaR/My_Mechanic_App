package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class show_all_mechanician_dash extends AppCompatActivity {

    //Ui component
    private EditText seller_search_box;

    private RadioGroup search_type;
    private RadioButton select_search;
    private String search_name;

    private LinearLayout search_type_layout;

    private TextView advanced_search_show,advanced_search_hide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_mechanician_dash);

        //Assign Variable
        Assign_variable();

        //show advanced search
        advanced_search_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                advanced_search_hide.setVisibility(View.VISIBLE);
                search_type_layout.setVisibility(View.VISIBLE);

                advanced_search_show.setVisibility(View.GONE);
            }
        });

        //hide advanced search
        advanced_search_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                advanced_search_hide.setVisibility(View.GONE);
                search_type_layout.setVisibility(View.GONE);

                advanced_search_show.setVisibility(View.VISIBLE);
            }
        });
    }

    private void Assign_variable() {

        seller_search_box = findViewById(R.id.et_all_seller_search_search);
        search_type = findViewById(R.id.radio_select_all_seller_search_typr);
        search_type_layout = findViewById(R.id.ll_all_seller_search_type);
        advanced_search_show = findViewById(R.id.tv_all_seller_advance_search);
        advanced_search_hide = findViewById(R.id.tv_all_seller_advance_search_hide);

        advanced_search_hide.setVisibility(View.GONE);
        search_type_layout.setVisibility(View.GONE);

    }
}
