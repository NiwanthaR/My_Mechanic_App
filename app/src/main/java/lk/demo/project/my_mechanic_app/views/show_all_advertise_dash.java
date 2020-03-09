package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.wall_post;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class show_all_advertise_dash extends AppCompatActivity {

    //Ui component
    private EditText search_bar,price_max,price_min;
    private Button btn_search;
    private TextView txt_advanced,txt_show_less,txt_pricetag;

    //Layouts
    private LinearLayout price_range_layout,search_type_layout;
    private RecyclerView recyclerView;

    //Firebase
    private FirebaseRecyclerOptions<wall_post> options;
    private FirebaseRecyclerAdapter<wall_post,MyViewHolder> adapter;

    private DatabaseReference DataRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_advertise_dash);

        //Assign UI values
        Assign_Values();

        //show advanced search
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

        //hide advanced search
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

        //Search All Ads
        Load_Data("");


        //search by name
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()!=null)
                {
                    Load_Data(s.toString());
                }else {
                    Load_Data("");
                }
            }
        });

    }

    //data lording function
    private void Load_Data(String data) {

        Query query = DataRef.orderByChild("Post_Title").startAt(data).endAt(data+"\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<wall_post>().setQuery(query,wall_post.class).build();

        adapter=new FirebaseRecyclerAdapter<wall_post, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull wall_post model) {

                holder.textView_in_posts_title.setText(model.getPost_Title());
                holder.textView_in_posts_storename.setText(model.getStore_Name());
                holder.textView_in_posts_price.setText("Rs "+model.getPost_Cost());
                holder.textView_in_posts_condition.setText(model.getPost_Type());
                Picasso.get().load(model.getImageUri()).into(holder.imageView_in_posts);

                //click event
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(show_all_advertise_dash.this,mechanic_post_view_dash.class);
                        intent.putExtra("Item_Key",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_add_ui,parent,false);
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    //Assign variable
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

        //Recycler view
        recyclerView=findViewById(R.id.recycler_view_all_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        //database refarance
        DataRef=FirebaseDatabase.getInstance().getReference().child("Mechanicians wall posts");

        //Open UI
        price_range_layout.setVisibility(View.GONE);
        search_type_layout.setVisibility(View.GONE);
        txt_pricetag.setVisibility(View.GONE);
        txt_show_less.setVisibility(View.GONE);
    }
}
