package com.example.info306;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

public class MainActivity extends AppCompatActivity {


    BatteryReceiver batteryReceiver;
    IntentFilter intentFilter;
    TextView textView ;
    String fragment;
    BottomNavigationView bottomNav;

    //  -----------------ACTION ON ITEM SELECTED (MENU DE NAVIGATION ENTRE FRAGMENTS)---------------------------------------------------
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            fragment="Home";
                            break;
                        case R.id.nav_quizz:
                            selectedFragment = new QuizzFragment();
                            fragment="quizz";
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            fragment="search";
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//  -----------------MENU DE NAVIGATION ENTRE FRAGMENTS---------------------------------------------------------------------------------
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();



    }


    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
    public void openActivity3(){
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }
//  -----------------MENU DE LA BARRE DES STATUTS---------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

//    -----------------ACTION ON ITEM SELECTED (MENU DE LA BARRE DES STATUTS)---------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Activity 2", Toast.LENGTH_SHORT).show();
                openActivity2();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Activity 3", Toast.LENGTH_SHORT).show();
                openActivity3();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



//---------------------------DEBUT BUNDLE------------------------------------------------------------------------------------
    
    //On récup l'item de la barre nav selectionné avant la rotation de l'écran
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("x", bottomNav.getSelectedItemId());
        super.onSaveInstanceState(outState);

    }

    //On restaure le bon fragment en fonction de l'item de la barre nav récupéré avant la rotation
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        Fragment selectedFragment = null;

        switch (savedInstanceState.getInt("x")){
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;
            case R.id.nav_quizz:
                selectedFragment = new QuizzFragment();
                break;
            case R.id.nav_search:
                selectedFragment = new SearchFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        super.onRestoreInstanceState(savedInstanceState);
    }
//-------------------------FIN BUNDLE-----------------------------------------------------------------------------------------------




    @Override
    protected void onStart() {
        super.onStart();
        Log.i( "CycleDeVie",  "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i( "CycleDeVie",  "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i( "CycleDeVie",  "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i( "CycleDeVie",  "onStop");
//        unregisterReceiver(batteryReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i( "CycleDeVie",  "onDestroy");
    }
}