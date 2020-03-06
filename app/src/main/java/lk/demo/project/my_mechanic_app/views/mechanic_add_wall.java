package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.wall_post;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class mechanic_add_wall extends AppCompatActivity {

    private EditText search_box;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private FirebaseRecyclerOptions<wall_post> options;
    private FirebaseRecyclerAdapter<wall_post,MyViewHolder> adapter;

    private DatabaseReference DataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_add_wall);

        //Assign UI
        Assign_UI();

        Load_Data();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),mechanic_add_post_dash.class));
            }
        });
    }

    private void Load_Data() {

        options=new FirebaseRecyclerOptions.Builder<wall_post>().setQuery(DataRef,wall_post.class).build();

        adapter=new FirebaseRecyclerAdapter<wall_post, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull wall_post model) {

                holder.textView_in_posts_title.setText(model.getPost_Title());
                holder.textView_in_posts_ownere.setText(model.getOwner_ID());
                Picasso.get().load(model.getImageUri()).into(holder.imageView_in_posts);
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

    private void Assign_UI() {
        search_box=findViewById(R.id.tv_mechanic_add_wall_etsearch);
        recyclerView=findViewById(R.id.mwchanic_wall_post_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        floatingActionButton=findViewById(R.id.flt_btn_mechanic_add_wall);

        DataRef=FirebaseDatabase.getInstance().getReference().child("Mechanicians wall posts");
    }
}
