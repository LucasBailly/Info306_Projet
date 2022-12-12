package com.example.info306;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private Button btn;
    private Button btn1;
    private SharedPreferences sharedPreferences;

    private IntentFilter intentFilter;

    public HomeFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

//---------------------------DEBUT SHARED PREFERENCES------------------------------------------------------------------------------------------------------------------
        btn = (Button) view.findViewById(R.id.btnA);
        btn1 = (Button) view.findViewById(R.id.btnB);

        sharedPreferences = getActivity().getSharedPreferences("night", 0);

        Boolean booleanValue = sharedPreferences.getBoolean("night_mode", true);

        if(booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night-mode", false);
                editor.commit();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night-mode", true);
                editor.commit();
            }
        });
//---------------------------FIN SHARED PREFERENCES------------------------------------------------------------------------------------------------------------------

        return  view;
    }



}

