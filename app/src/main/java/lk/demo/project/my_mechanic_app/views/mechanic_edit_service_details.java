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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class mechanic_edit_service_details extends AppCompatActivity {

    //Firebase Part
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private FirebaseRecyclerOptions<mechanic_service> options;
    private FirebaseRecyclerAdapter<mechanic_service,MyServiceHolder> adapter;

    //Recycler
    private RecyclerView recyclerView;

    //floating btn
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_edit_service_details);

        //Assign ui variable
        Assign_Variable();

        //Loard Data
        Loard_Data();

        //go to add new service
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mechanic_edit_service_details.this,mechanic_add_new_service_package.class));
            }
        });
    }

    private void Loard_Data() {

        Query query = databaseReference.orderByChild("Seller_Uid").startAt(firebaseAuth.getUid()).endAt(firebaseAuth.getUid()+"\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<mechanic_service>().setQuery(query,mechanic_service.class).build();

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
                        Intent intent = new Intent(mechanic_edit_service_details.this,mechanic_view_service_details.class);
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

    private void Assign_Variable() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Mechanic Upload Service Package");
        firebaseAuth = FirebaseAuth.getInstance();

        floatingActionButton = findViewById(R.id.btn_floating_service_details_edit);

        recyclerView = findViewById(R.id.recycler_service_details_edit_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
    }
}
