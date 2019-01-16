package com.atahansahlan.anlatbana;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class settingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch sesCins = (Switch)findViewById(R.id.sescins);


        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                "com.atahansahlan.anlatbana", Context.MODE_PRIVATE);


        sesCins.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sesCins.setText("Erkek");
                }else{
                    sesCins.setText("Kadın");
                }
            }
        });

    }
}
