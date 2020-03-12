package lk.demo.project.my_mechanic_app.views;

import androidx.appcompat.app.AppCompatActivity;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class user_add_feed_back_dash extends AppCompatActivity {

    //UI Component
    private EditText feeback_description;
    private TextView store_name,user_name;
    private ImageView feedback_img;
    private LinearLayout upload_state_layout;

    //Sprinner
    private Spinner satisfaction_type;

    //Seller key
    private String Seller_Key,Seller_Store,User_Name;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_feed_back_dash);

        //Get Seller Key
        Seller_Key=getIntent().getStringExtra("Seller_Key");
        Seller_Store=getIntent().getStringExtra("Seller_Store");
        User_Name=getIntent().getStringExtra("User_Name");


        //Assign
        Assign_UI();

        //start
        upload_state_layout.setVisibility(View.GONE);
        store_name.setText(Seller_Store);
        user_name.setText(User_Name);
    }

    private void Assign_UI() {

        //upload state hide
        upload_state_layout = findViewById(R.id.ll_feedback_upload_state);

        //ui variable
        feeback_description = findViewById(R.id.et_feedback_details);
        store_name = findViewById(R.id.tv_feedback_store_name);
        user_name = findViewById(R.id.tv_feedback_user_name);
        feedback_img = findViewById(R.id.img_feedback_upload_add);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();




        //Sprinner Part
        satisfaction_type = findViewById(R.id.spn_feedback_type);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(user_add_feed_back_dash.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.satify_typr));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        satisfaction_type.setAdapter(myAdapter);
    }
}
