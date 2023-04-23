package com.aanaan3.memorableplaces;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import com.aanaan3.memorableplaces.databinding.ActivityMainBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    Intent intent;

    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();

    static ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        places.clear();
        locations.clear();
        latitudes.clear();
        longitudes.clear();

        SharedPreferences sharedPreferences = getSharedPreferences("memorable", Context.MODE_PRIVATE);
        try {

            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats",ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons",ObjectSerializer.serialize(new ArrayList<String>())));


        } catch (Exception e) {
            e.printStackTrace();
        }


        if (places.size() >1 && latitudes.size() >1 && longitudes.size() >1){
            if (places.size() == latitudes.size() && places.size() == longitudes.size()){
                for (int i = 0; i < places.size(); i++) {
                    Log.i("memorable", "onCreate: "+places.get(i));

                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)),
                            Double.parseDouble(longitudes.get(i))));
                }
            }
        }
        else {
            places.add("Add New Place...");
            locations.add(new LatLng(0,0));
        }

        arrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1,places);

        mBinding.listView.setAdapter(arrayAdapter);

        mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                intent = new Intent(getBaseContext(),MapsActivity.class);
                intent.putExtra("placeNumber",0);
                startActivity(intent);

            }
        });
    }

    public void permission(){

    }
}