package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import lk.demo.project.my_mechanic_app.R;
import lk.demo.project.my_mechanic_app.model.client_profile;
import lk.demo.project.my_mechanic_app.model.mechanic_profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class mechanic_dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    //Variables for Navigation drover
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;


    //Variables for Map
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    private SupportMapFragment mapFragment;
    final  int LOCATION_REQUEST_CODE = 1;

    //component
    private TextView header_name, header_email;
    private ImageView heder_pic;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_dashboard);


        /*---------------------Hooks------------------------*/
        value_equal();

        /*------------------------Tool Bar-------------------*/
        setSupportActionBar(toolbar);

        /*------------------------Navigation Drawer Menu-------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //navigation icon
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.header_icon_et);

        //Navigation Drawer user name and email
        user_data();



        //-----------------------Map Load-----------------------------------*/
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(mechanic_dashboard.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
        }else {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(mechanic_dashboard.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    protected synchronized void buildGooleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        buildGooleApiClient();

        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.nav_home_mechanic:
                Toast.makeText(mechanic_dashboard.this,"My Home",Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;

            case R.id.nav_myservice_mechanic:
                Toast.makeText(mechanic_dashboard.this,"Service Details",Toast.LENGTH_SHORT).show();
                Intent name_intent = new Intent(mechanic_dashboard.this,show_all_mechanician_dash.class);
                name_intent.putExtra("User_Name", header_name.getText().toString());
                startActivity(name_intent);
                break;

            case R.id.nav_myfeedback_mechanic:
                Toast.makeText(mechanic_dashboard.this,"Client Feedback",Toast.LENGTH_SHORT).show();
                Intent intent_01 = new Intent(mechanic_dashboard.this,show_all_type_of_feedback.class);
                intent_01.putExtra("Seller_Key",firebaseUser.getUid());
                startActivity(intent_01);
                break;

            case R.id.nav_my_wall_mechanic:
                Toast.makeText(mechanic_dashboard.this,"My Wall",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mechanic_dashboard.this,mechanic_add_wall.class));
                break;

            case R.id.nav_all_add_mechanic:
                Toast.makeText(mechanic_dashboard.this,"All Advertise Wall",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mechanic_dashboard.this,show_all_advertise_dash.class));
                break;

            case R.id.nav_my_profil_mechanice:
                Toast.makeText(mechanic_dashboard.this,"My Profile",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mechanic_dashboard.this,mechanic_profile_dashboard.class));
                break;

            case R.id.nav_shop_profile_mechanic:
                Toast.makeText(mechanic_dashboard.this,"Shop Profile",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mechanic_dashboard.this,mechanic_shop_profile_dashboard.class);
                intent.putExtra("Seller_Key",firebaseUser.getUid());
                startActivity(intent);
                //startActivity(new Intent(mechanic_dashboard.this,mechanic_shop_profile_dashboard.class));
                break;

            case R.id.nav_settings_mechanic:
                Toast.makeText(mechanic_dashboard.this,"Select Settings",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mechanic_dashboard.this,mechanic_settings_panel.class));
                break;

            case R.id.nav_rateus_mechanic:
                Toast.makeText(mechanic_dashboard.this,"Select Rate Us",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mechanic_dashboard.this,mechanic_add_new_service_package.class));
                break;

            case R.id.nav_logout_mechanic:
                Toast.makeText(mechanic_dashboard.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
                Logout();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void value_equal()
    {
        drawerLayout = findViewById(R.id.drawer_layout_mechanic);
        navigationView = findViewById(R.id.nav_view_mechanic);
        toolbar = findViewById(R.id.toolbar_mechanic);
        toolbar.setTitle("Mechanic Dashboard");


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage=FirebaseStorage.getInstance();
    }

    private void Logout()
    {
        firebaseAuth.signOut();
        startActivity(new Intent(mechanic_dashboard.this,mechanic_login_dash.class));
        finish();
    }

    private void user_data()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mechanic_profile mechanicProfile = dataSnapshot.getValue(mechanic_profile.class);

                View header = navigationView.getHeaderView(0);
                header_name = (TextView) header.findViewById(R.id.header_username);
                header_name.setText(mechanicProfile.getOwner_fname()+" "+mechanicProfile.getOwner_sname());
                header_email = (TextView) header.findViewById(R.id.header_useremail);
                header_email.setText(firebaseUser.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mechanic_dashboard.this,"Can't Load data",Toast.LENGTH_SHORT).show();
            }
        });

        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Profile Picture").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                View header = navigationView.getHeaderView(0);
                heder_pic = header.findViewById(R.id.heder_pro_pic);
                Picasso.get().load(uri).fit().centerCrop().into(heder_pic);
            }
        });
    }

}
