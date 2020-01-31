package lk.demo.project.my_mechanic_app.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import lk.demo.project.my_mechanic_app.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class client_dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashboard);

        /*---------------------Hooks------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //textView = findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("User Dashboard");


        /*------------------------Tool Bar-------------------*/
        setSupportActionBar(toolbar);

        /*------------------------Navigation Drawer Menu-------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.header_icon_et);
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
                break;

            case R.id.nav_myservice:
                Toast.makeText(client_dashboard.this,"Select My Service",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_myfeedback:
                Toast.makeText(client_dashboard.this,"Select My Feedback",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_offers:
                Toast.makeText(client_dashboard.this,"Select My Offers",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_profile:
                Toast.makeText(client_dashboard.this,"Select My Profile",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_settings:
                Toast.makeText(client_dashboard.this,"Select Settings",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_rateus:
                Toast.makeText(client_dashboard.this,"Select Rate Us",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                Toast.makeText(client_dashboard.this,"Select Logout",Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
