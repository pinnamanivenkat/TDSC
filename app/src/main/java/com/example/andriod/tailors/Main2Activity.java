package com.example.andriod.tailors;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null) {
                return netInfos.isConnected();
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNetworkStatusAvialable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();

        } else {
            setContentView(R.layout.activity_main2);


        }

    }

    public void selectClothing(View view) {
        ImageButton button = (ImageButton) view;
        startActivity(new Intent(getApplicationContext(), Main3Activity.class));
    }

    public void menu(View view) {
        Button button = (Button) view;
        startActivity(new Intent(getApplicationContext(), Main7Activity.class));
    }
}