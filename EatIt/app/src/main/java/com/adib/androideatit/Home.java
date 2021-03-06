package com.adib.androideatit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.installations.local.PersistedInstallationEntry;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.media.VolumeProviderCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Common.Common;
import Interface.ItemClickListener;
import Model.Category;
import ViewHolder.MenuViewHolder;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle( "Menu" );
        setSupportActionBar(toolbar);



        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent cartIntent = new Intent( Home.this,Cart.class );
               startActivity(cartIntent);

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close );
        drawer.setDrawerListener( toggle );
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        View headerView = navigationView.getHeaderView( 0 );
        txtFullName = headerView.findViewById( R.id.txtFullName );
        txtFullName.setText( Common.currentUser.getName() );

        recycler_menu = findViewById( R.id.recycler_menu );
        recycler_menu.setHasFixedSize( true );
        layoutManager=new LinearLayoutManager( this );
        recycler_menu.setLayoutManager( layoutManager);

        loadMenu();

    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {

               menuViewHolder.txtMenuName.setText( category.getName() );
               Picasso.with(getBaseContext()).load(category.getImage()).into(menuViewHolder.imageView);
               final Category clickItem = category;
               menuViewHolder.setItemClickListener( new ItemClickListener() {
                   @Override
                   public void onClick(View view, int position, boolean isLongClick) {
                      Intent foodList = new Intent( Home.this, FoodList.class );

                      foodList.putExtra( "CategoryId", adapter.getRef( position ).getKey() );
                      startActivity( foodList );


                   }
               } );

            }
        };
        recycler_menu.setAdapter( adapter );
    }

    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START ))
        {
            drawer.closeDrawer( GravityCompat.START );
        }else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected( item );
    }


    @SuppressWarnings( "StatementWithEmptyBody" )
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id==R.id.nav_menu)
        {

        }else if (id==R.id.nav_cart)
        {
            Intent cartIntent = new Intent( Home.this,Cart.class );
            startActivity( cartIntent );

        }else if (id==R.id.nav_orders)
        {
            Intent orderIntent = new Intent( Home.this,OrderStatus.class );
            startActivity( orderIntent );

        }else if(id==R.id.nav_log_out)
        {
            Intent signIn = new Intent( Home.this,SignIn.class );
            signIn.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( signIn );

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }



}
