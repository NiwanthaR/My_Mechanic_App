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
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class mechanic_add_wall extends AppCompatActivity {

    private EditText search_box;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private FirebaseRecyclerOptions<wall_post> options;
    private FirebaseRecyclerAdapter<wall_post,MyViewHolder> adapter;

    private DatabaseReference DataRef;

    //firbase auth
    private FirebaseAuth firebaseAuth;
    private String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_add_wall);

        //Assign UI
        Assign_UI();

        //load user
         user_key = firebaseAuth.getUid();

        Load_Data("");

        //searchbar
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null)
                {
                    Load_Data(s.toString());
                }else {
                    Load_Data("");
                }
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),mechanic_add_post_dash.class));
            }
        });
    }

    private void Load_Data(String data) {

        Query query = DataRef.orderByChild("Post_Title").startAt(data).endAt(data+"\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<wall_post>().setQuery(query,wall_post.class).build();

        adapter=new FirebaseRecyclerAdapter<wall_post, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull wall_post model) {

                if(model.getOwner_UID().equals(user_key))
                {
                    holder.textView_in_posts_title.setText(model.getPost_Title());
                    holder.textView_in_posts_storename.setText(model.getStore_Name());
                    holder.textView_in_posts_price.setText("Rs "+model.getPost_Cost());
                    holder.textView_in_posts_condition.setText(model.getPost_Type());
                    Picasso.get().load(model.getImageUri()).into(holder.imageView_in_posts);

                    //click event
                    holder.v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mechanic_add_wall.this,mechanic_post_view_dash.class);
                            intent.putExtra("Item_Key",getRef(position).getKey());
                            startActivity(intent);
                        }
                    });
                }
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

        firebaseAuth=FirebaseAuth.getInstance();

        DataRef=FirebaseDatabase.getInstance().getReference().child("Mechanicians wall posts");
    }
}
