package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.user_feedback;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class show_all_type_of_feedback extends AppCompatActivity {

    //uiComponent
    private TextView positive_feed,neutrel_feed,negative_fee,all_feed;
    private FloatingActionButton go_add_feedback;
    private RecyclerView recyclerView;

    //Firebase
    private FirebaseRecyclerOptions<user_feedback> options;
    private FirebaseRecyclerAdapter<user_feedback,MyFeedbackHolder> adapter;
    private DatabaseReference DataRef;


    //Seller ID
    private String Seller_Key,Seller_Store,User_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_type_of_feedback);

        //Seller_id
        Seller_Key = getIntent().getStringExtra("Seller_Key");
        Seller_Store = getIntent().getStringExtra("Seller_Store");
        User_Name = getIntent().getStringExtra("User_Name");

        //Assign UI Values
        Assign_Value();

        //start view
        all_feed.setBackgroundColor(Color.parseColor("#95EAFF"));

        //Load Recycler Data
        Load_Data();


        //click all go add feedback
        go_add_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(show_all_type_of_feedback.this,user_add_feed_back_dash.class);
                intent.putExtra("Seller_Key",Seller_Key);
                intent.putExtra("Seller_Store",Seller_Store);
                intent.putExtra("User_Name",User_Name);
                startActivity(intent);
                //startActivity(new Intent(show_all_type_of_feedback.this,user_add_feed_back_dash.class));
            }
        });


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

        //Recycler
        recyclerView = findViewById(R.id.recycler_view_all_user_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        //loatingaction button
        go_add_feedback = findViewById(R.id.float_btn_go_add_feedback);

        //Firebase
        DataRef = FirebaseDatabase.getInstance().getReference().child("User Feedback");

    }

    private void Load_Data()
    {
        options = new FirebaseRecyclerOptions.Builder<user_feedback>().setQuery(DataRef.child(Seller_Key),user_feedback.class).build();

        adapter = new FirebaseRecyclerAdapter<user_feedback, MyFeedbackHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyFeedbackHolder holder, int position, @NonNull user_feedback model) {

                holder.txt_feedback_username.setText(model.getUser_Name());
                holder.txt_feedback_satify_type.setText(model.getUser_Satisfaction());
                holder.txt_feedback_description.setText(model.getUser_Description());

                Picasso.get().load(model.getImage_Uri()).fit().into(holder.img_feedback_image);
            }

            @NonNull
            @Override
            public MyFeedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_feedback_ui,parent,false);
                return new MyFeedbackHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

}
