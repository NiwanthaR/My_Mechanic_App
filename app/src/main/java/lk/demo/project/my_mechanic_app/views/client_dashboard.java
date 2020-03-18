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

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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

import java.util.HashMap;
import java.util.List;

public class client_dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

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


    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    //Geofire Map Location
    private GeoFire geoFire;

    //Request location
    private  LatLng request_location;

    //component
    private TextView header_name, header_email;
    private ImageView heder_pic;
    private Button make_request;



    //Marker
    private Marker service_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashboard);

        /*---------------------Hooks------------------------*/
        value_eqal();

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
            ActivityCompat.requestPermissions(client_dashboard.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
        }else {
            mapFragment.getMapAsync(this);
        }

        //Place Request location to database
        make_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_id = firebaseAuth.getCurrentUser().getUid();

                DatabaseReference DataRef = firebaseDatabase.getReference("Live Details").child("Service Request");
                GeoFire geoFire1 = new GeoFire(DataRef);
                //geoFire1.setLocation(user_id,new GeoLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude()));

                geoFire1.setLocation(user_id, new GeoLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude()), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error!=null)
                        {
                            Toast.makeText(client_dashboard.this,"Can't go Active",Toast.LENGTH_SHORT).show();
                        }
                        request_location = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(request_location).title("I am Here..!"));

                        //Find Nearest Service Station
                        Find_Near_Service();

                        make_request.setText("Searching Service Station");


                    }
                });

            }
        });
    }
    // service station searching area
    private int radius = 1;

    // service station state
    private Boolean service_found = false;

    //service station key
    private String service_station_ID;

    private void Find_Near_Service() {

        DatabaseReference service_location = FirebaseDatabase.getInstance().getReference("Live Details").child("Mechanic Location");
        GeoFire geoFire01 = new GeoFire(service_location);

        GeoQuery geoQuery = geoFire01.queryAtLocation(new GeoLocation(request_location.latitude,request_location.longitude),radius);
        geoQuery.removeAllListeners();

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if (!service_found)
                {
                    service_found = true;
                    service_station_ID = key;

                    //Get submit data
                    DatabaseReference Service_Ref = FirebaseDatabase.getInstance().getReference().child("Live Details").child("Reserved_Mechanic").child(service_station_ID);
                    String customer_id = firebaseAuth.getCurrentUser().getUid();
                    HashMap map = new HashMap();
                    map.put("Customer_ID",customer_id);
                    map.put("Customer_Name",header_name.getText().toString());
                    Service_Ref.updateChildren(map);

                    //Get service station Location
                    Get_service_station_location();
                    make_request.setText("Getting Location");

                }
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                if (!service_found)
                {
                    radius++;
                    Find_Near_Service();
                }
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void Get_service_station_location()
    {
        //Get Location
        DatabaseReference Service_location = FirebaseDatabase.getInstance().getReference().child("Live Details").child("Mechanic Location").child(service_station_ID).child("l");

        Service_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    List<Object> map = (List<Object>) dataSnapshot.getValue();
                    double locationlat = 0;
                    double locationlan = 0;

                    if (map.get(0) != null){
                        locationlat = Double.parseDouble(map.get(0).toString());
                    }
                    if (map.get(1) != null){
                        locationlan = Double.parseDouble(map.get(1).toString());
                    }

                    LatLng serviceLatLan = new LatLng(locationlat,locationlan);
                    if (service_marker != null)
                    {
                        service_marker.remove();
                    }

                    Location loc1 = new Location("");
                    loc1.setLatitude(request_location.latitude);
                    loc1.setLongitude(request_location.longitude);

                    Location loc2 = new Location("");
                    loc2.setLatitude(serviceLatLan.latitude);
                    loc2.setLongitude(serviceLatLan.longitude);

                    float distance = loc1.distanceTo(loc2);
                    make_request.setText("Distance "+String.valueOf(distance));

                    service_marker = mMap.addMarker(new MarkerOptions().position(serviceLatLan).title("Hear Your Mechanic..!!"));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
            case R.id.nav_home:
                Toast.makeText(client_dashboard.this,"My Home",Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;

            case R.id.nav_myservice:
                Toast.makeText(client_dashboard.this,"Select My Service",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_give_feedback:
                Toast.makeText(client_dashboard.this,"Give Feedback",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(client_dashboard.this,show_all_mechanician_dash.class);
                intent.putExtra("User_Name",header_name.getText().toString());
                startActivity(intent);
                break;

            case R.id.nav_mechanic_shop:
                Toast.makeText(client_dashboard.this,"Mechanic Shop Wall",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(client_dashboard.this,show_all_advertise_dash.class));
                break;

            case R.id.nav_service_promotion:
                Toast.makeText(client_dashboard.this,"Mechanic Service Promotion",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(client_dashboard.this,show_all_mechanic_service.class));
                break;

            case R.id.nav_profile:
                Toast.makeText(client_dashboard.this,"Select My Profile",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(client_dashboard.this,client_profile_dashboard.class));
                break;

            case R.id.nav_settings:
                Toast.makeText(client_dashboard.this,"Select Settings",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(client_dashboard.this,settings_panel.class));
                break;

            case R.id.nav_rateus:
                Toast.makeText(client_dashboard.this,"First Please Upload Play Store",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                Logout();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void value_eqal()
    {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("User Dashboard");


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage=FirebaseStorage.getInstance();

        make_request = findViewById(R.id.btn_client_request_service);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(client_dashboard.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
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

        //------------------------------Location upload---------------------------------------------

        String userid = firebaseUser.getUid();
        DatabaseReference ref = firebaseDatabase.getReference("Active Users");

        GeoFire geoFire = new GeoFire(ref);
//        geoFire.setLocation(userid, new GeoLocation(location.getLatitude(),location.getLongitude()));

        geoFire.setLocation(userid, new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error!=null)
                {
                    Toast.makeText(client_dashboard.this,"Can't go Active",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(client_dashboard.this,"You are Active",Toast.LENGTH_SHORT).show();
            }
        });

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
    protected void onStop() {
        super.onStop();

        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        String userid = firebaseUser.getUid();
//        DatabaseReference ref = firebaseDatabase.getReference("Active Users").child(userid);
//
//        ref.removeValue();

    }

    private void user_data()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User's Details").child("User Profile").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                client_profile clientProfile = dataSnapshot.getValue(client_profile.class);

                View header = navigationView.getHeaderView(0);
                header_name = (TextView) header.findViewById(R.id.header_username);
                header_name.setText(clientProfile.getFname()+" "+clientProfile.getLname());
                header_email = (TextView) header.findViewById(R.id.header_useremail);
                header_email.setText(firebaseUser.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(client_dashboard.this,"Can't Load data",Toast.LENGTH_SHORT).show();
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

    private void Logout()
    {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        String userid = firebaseUser.getUid();
        DatabaseReference ref = firebaseDatabase.getReference("Active Users").child(userid);
        ref.removeValue();

        firebaseAuth.signOut();
        Toast.makeText(client_dashboard.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(client_dashboard.this,client_login_dash.class));
        finish();
    }
}
