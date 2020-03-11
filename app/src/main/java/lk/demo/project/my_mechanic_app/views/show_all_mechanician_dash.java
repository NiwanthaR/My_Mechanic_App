package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.mechanic_profile;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class show_all_mechanician_dash extends AppCompatActivity {

    //Ui component
    private EditText seller_search_box;
    private TextView advanced_search_show,advanced_search_hide;

    //Radio Group
    private RadioGroup search_type;
    private RadioButton select_search;
    private String search_name;

    //Recycler view
    private RecyclerView recyclerView;

    //Layouts
    private LinearLayout search_type_layout;

    //firebase
    private FirebaseRecyclerOptions <mechanic_profile> options;
    private FirebaseRecyclerAdapter <mechanic_profile,MyallMechanicHolder> adapter;

    private DatabaseReference DataRef;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;


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

        Load_Data();

    }

    private void Load_Data() {

        String user_type = "mechanic";

        Query query = DataRef.orderByChild("usertype").startAt(user_type).endAt(user_type+"\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<mechanic_profile>().setQuery(query,mechanic_profile.class).build();

        adapter = new FirebaseRecyclerAdapter<mechanic_profile, MyallMechanicHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyallMechanicHolder holder, final int position, @NonNull mechanic_profile model) {

                holder.mechanic_store_name.setText(model.getShop_name());
                holder.mechanic_store_location.setText(model.getShop_city());

                String Owner =  model.getOwner_fname()+" "+model.getOwner_sname();

                holder.mechanic_store_owner_name.setText(Owner);
                holder.mechanic_store_contact.setText(model.getShop_contact());

                String key = getRef(position).getKey();
                storageReference = FirebaseStorage.getInstance().getReference();
                storageReference.child("Profile Picture").child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().into(holder.mechanic_logo);
                    }
                });


                holder.mechanic_v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(show_all_mechanician_dash.this,mechanic_shop_profile_dashboard.class);
                        intent.putExtra("Seller_Key",getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public MyallMechanicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_seller_ui,parent,false);
                return new MyallMechanicHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


    private void Assign_variable() {

        seller_search_box = findViewById(R.id.et_all_seller_search_search);
        search_type_layout = findViewById(R.id.ll_all_seller_search_type);
        advanced_search_show = findViewById(R.id.tv_all_seller_advance_search);
        advanced_search_hide = findViewById(R.id.tv_all_seller_advance_search_hide);

        //Radiogroup
        search_type = findViewById(R.id.radio_select_all_seller_search_typr);

        //Display Open
        advanced_search_hide.setVisibility(View.GONE);
        search_type_layout.setVisibility(View.GONE);

        //Recyclerview
        recyclerView = findViewById(R.id.recycler_view_all_mechanic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        DataRef = FirebaseDatabase.getInstance().getReference().child("User's Details").child("User Profile");

    }

    private void read_search_type()
    {
        // get selected radio button from radioGroup
        int selectedId = search_type.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        select_search = (RadioButton) findViewById(selectedId);

        search_name= String.valueOf(select_search.getText());
        //Toast.makeText(Post_Add_Home_Dash.this,h_kitchen,Toast.LENGTH_SHORT).show();
    }
}
