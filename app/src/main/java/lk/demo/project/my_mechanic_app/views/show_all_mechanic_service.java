package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.mechanic_service;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class show_all_mechanic_service extends AppCompatActivity {

    //Firebase
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    //Edite text
    private EditText search_details;

    //Recycler
    private RecyclerView recyclerView;

    //Linear layout
    private LinearLayout search_linearLayout;

    //Textview
    private TextView tv_advance_search_show,tv_advance_search_hide;

    //Radio part
    private RadioButton select_search_type;
    private RadioGroup search_type;

    //Firebase
    private FirebaseRecyclerOptions<mechanic_service> options;
    private FirebaseRecyclerAdapter<mechanic_service,MyServiceHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_mechanic_service);

        //Assign variable
        Assign_variable();

        //Load recycler data
        Load_Data();


        //advance show click
        tv_advance_search_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_linearLayout.setVisibility(View.VISIBLE);
                tv_advance_search_hide.setVisibility(View.VISIBLE);
                tv_advance_search_show.setVisibility(View.GONE);
            }
        });

        //advance hide click
        tv_advance_search_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_linearLayout.setVisibility(View.GONE);
                tv_advance_search_hide.setVisibility(View.GONE);
                tv_advance_search_show.setVisibility(View.VISIBLE);
            }
        });


    }

    private void Load_Data() {

        options = new FirebaseRecyclerOptions.Builder<mechanic_service>().setQuery(databaseReference,mechanic_service.class).build();

        adapter = new FirebaseRecyclerAdapter<mechanic_service, MyServiceHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyServiceHolder holder, final int position, @NonNull mechanic_service model) {

                holder.single_service_title.setText(model.getService_Title());
                holder.single_service_store_name.setText(model.getSeller_Store_Name());
                holder.single_service_price.setText("Rs "+model.getService_Cost());
                holder.single_service_location.setText(model.getSeller_Store_Location());

                Picasso.get().load(model.getImage_Uri()).fit().into(holder.single_service_img);

                holder.service_v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(show_all_mechanic_service.this,mechanic_view_service_details.class);
                        intent.putExtra("Service_Key",getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public MyServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_service_ui,parent,false);
                return new MyServiceHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void Assign_variable() {

        //Firebase
        firebaseAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference().child("Mechanic Upload Service Package");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Mechanic Upload Service Package");

        //search box
        search_details = findViewById(R.id.et_all_service_post_wall_search);

        //search type layout
        search_linearLayout = findViewById(R.id.ll_all_service_search_type);

        //text view
        tv_advance_search_show = findViewById(R.id.tv_all_service_advance_search);
        tv_advance_search_hide = findViewById(R.id.tv_all_service_advance_hide);

        //Radio
        search_type = findViewById(R.id.radio__all_service_search_typr);

        //recycler view
        recyclerView = findViewById(R.id.recycler_view_all_service_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        //start ui
        search_linearLayout.setVisibility(View.GONE);
        tv_advance_search_hide.setVisibility(View.GONE);
    }
}
