package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class show_all_advertise_dash extends AppCompatActivity {

    private EditText search_bar,price_max,price_min;
    private Button btn_search;
    private TextView txt_advanced,txt_show_less,txt_pricetag;
    private LinearLayout price_range_layout,search_type_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_advertise_dash);

        //Assign UI values
        Assign_Values();

        txt_advanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                price_range_layout.setVisibility(View.VISIBLE);
                search_type_layout.setVisibility(View.VISIBLE);
                txt_pricetag.setVisibility(View.VISIBLE);
                txt_show_less.setVisibility(View.VISIBLE);

                txt_advanced.setVisibility(View.GONE);
            }
        });

        txt_show_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                price_range_layout.setVisibility(View.GONE);
                search_type_layout.setVisibility(View.GONE);
                txt_pricetag.setVisibility(View.GONE);
                txt_show_less.setVisibility(View.GONE);

                txt_advanced.setVisibility(View.VISIBLE);
            }
        });

    }

    private void Assign_Values()
    {
        //Edit text
        search_bar=findViewById(R.id.et_all_post_search_search);
        price_max=findViewById(R.id.et_all_post_max_value);
        price_min=findViewById(R.id.et_all_post_min_value);

        //Button
        btn_search=findViewById(R.id.btn_all_post_search);

        //Text views
        txt_advanced=findViewById(R.id.tv_all_post_advance_search);
        txt_show_less=findViewById(R.id.tv_all_post_advance_search_hide);
        txt_pricetag = findViewById(R.id.txt_all_post_price);

        //Layouts
        price_range_layout=findViewById(R.id.ll_all_post_price_range);
        search_type_layout=findViewById(R.id.ll_all_post_search_type);

        price_range_layout.setVisibility(View.GONE);
        search_type_layout.setVisibility(View.GONE);
        txt_pricetag.setVisibility(View.GONE);
        txt_show_less.setVisibility(View.GONE);
    }
}
