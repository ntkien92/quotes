package com.kien.quote.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.kien.quote.Helper.DatabaseHandler;
import com.kien.quote.R;
import com.kien.quote.adapter.GridViewAdapter;
import com.kien.quote.model.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GridViewAdapter.OnClickImage {

    private GridView mGridView;
    private GridViewAdapter mGridAdapter;
    private static final int RABBIT_COLOR = 1;
    private static final int INSPRISION = 2;
    private static final int IFACT = 3;
    private static final int OTHER = 4;
    private static int status = INSPRISION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        loadToGridview(status);

    }

    public void loadToGridview(int status) {
        ArrayList<Item> gridItems;
        DatabaseHandler db = new DatabaseHandler(this);
        gridItems = db.getAllItems(status);
        mGridView = (GridView) findViewById(R.id.gvImages);
        mGridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, gridItems, this);
        mGridView.setAdapter(mGridAdapter);
    }

    public void showDialog(final Context context, String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cảm ơn bạn đã sử dụng ứng dụng này\n Bạn muốn thoát chứ !!!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                            if (drawer.isDrawerOpen(GravityCompat.START)) {
                                drawer.closeDrawer(GravityCompat.START);
                            }
                            else {
                                finish();
                            }
                        Toast.makeText(MainActivity.this, "Chúc bạn một ngày vui vẻ", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.show();

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            status = RABBIT_COLOR;
            loadToGridview(status);
        } else if (id == R.id.nav_gallery) {
            status = INSPRISION;
            loadToGridview(status);
        }
        else if (id == R.id.nav_ifact) {
            status = IFACT;
            loadToGridview(status);
        }
        else if (id == R.id.nav_other) {
            status = OTHER;
            loadToGridview(status);
        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about_me) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void changeToPage(int position) {
        Intent myIntent = new Intent(MainActivity.this, FullScreenActivity.class);
        myIntent.putExtra("position", position);
        myIntent.putExtra("status", status);
        startActivity(myIntent);
    }
}
