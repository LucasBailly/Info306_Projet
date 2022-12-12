package com.example.info306;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
    private TextView textView;
    private BatteryReceiver batteryReceiver;
    private IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

//---------------------------DEBUT BROADCAST RECEIVER------------------------------------------------------------------------------------------------------------------
        textView = view.findViewById(R.id.battery);
        batteryReceiver = new BatteryReceiver(textView);
//---------------------------FIN BROADCAST RECEIVER------------------------------------------------------------------------------------------------------------------
        return  view;
    }
    @Override
    public void onStart() {
        getActivity().registerReceiver(batteryReceiver, new IntentFilter(getActivity().getIntent().ACTION_BATTERY_CHANGED));
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.i( "CycleDeVie",  "onStop");
        getActivity().unregisterReceiver(batteryReceiver);
        super.onStop();
    }
}
