package com.adadeh.tugas6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.adadeh.tugas6.databinding.ActivityMainBinding;
import android.view.View;

import android.content.SharedPreferences;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    // tugas recycle view
    RecyclerView recylerView;
    String s1[], s2[];
    int images[] ={R.drawable.strom,R.drawable.darkcloud,R.drawable.rainnning};

    private DrawerLayout dl;
    private ActionBarDrawerToggle abut;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // tugas recycle view
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        preferences = getSharedPreferences("AndroidHiveLogin", 0);
        editor = preferences.edit();
        setContentView(R.layout.activity_main);
        recylerView = findViewById(R.id.recyclerView);
        s1 = getResources().getStringArray(R.array.cuaca);
        s2 = getResources().getStringArray(R.array.deskripsi);
        CuacaAdapter appAdapter = new CuacaAdapter(this, s1, s2, images);
        recylerView.setAdapter(appAdapter);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recylerView.setLayoutManager(layoutManager);
        recylerView.setItemAnimator(new DefaultItemAnimator());

        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueueUniqueWork(
                        "Notifikasi", ExistingWorkPolicy.REPLACE,
                        request);
            }
        });
        dl = (DrawerLayout) findViewById(R.id.dl);
        abut = new ActionBarDrawerToggle(this, dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        abut.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abut);
        abut.syncState();
        actionBar.setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_cuaca) {
                    Intent a = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(a);}
                else if (id == R.id.nav_setting) {
                    Intent a = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(a);
                } else if (id == R.id.nav_profile) {
                    Intent a = new Intent(MainActivity.this, MainActivity3.class);
                    startActivity(a);
                }else if (id == R.id.nav_Alarm) {
                    Intent a = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(a);
                }else if (id == R.id.nav_Sqlite) {
                    Intent a = new Intent(MainActivity.this, MainActivity6.class);
                    startActivity(a);
                }else if (id == R.id.nav_Wheater) {
                    editor.remove("isLoggedIn");
                    editor.commit();
                    Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abut.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}