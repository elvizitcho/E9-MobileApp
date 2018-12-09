package com.example.elvis.myapplication;

import pl.pawelkleczkowski.customgauge.CustomGauge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        CustomGauge gauge1 = findViewById(R.id.gauge1);
        CustomGauge gauge2 = findViewById(R.id.gauge2);
        CustomGauge gauge3 = findViewById(R.id.gauge3);
        CustomGauge gauge4 = findViewById(R.id.gauge4);

        TextView text1 = findViewById(R.id.text1);
        TextView text2 = findViewById(R.id.text2);
        TextView text3 = findViewById(R.id.text3);
        TextView text4 = findViewById(R.id.text4);

        gauge1.setValue(23);
        gauge2.setValue(78);
        gauge3.setValue(21);
        gauge4.setValue(5);

        text1.setText(String.valueOf(gauge1.getValue()));
        text2.setText(String.valueOf(gauge2.getValue()));
        text3.setText(String.valueOf(gauge3.getValue()));
        text4.setText(String.valueOf(gauge4.getValue()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_sensores:
                Intent senIntent = new Intent(this, Dashboard.class);
                startActivity(senIntent);
                Toast.makeText(Dashboard.this, "Leituras atualizadas", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigation_eletros:
                Intent conIntent = new Intent(this, Controls.class);
                startActivity(conIntent);
                return true;
            case R.id.about:
                Toast.makeText(Dashboard.this, "vai par activity SOBRE", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                Toast.makeText(Dashboard.this, "função para deslogar e retorna para LOGIN", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

}