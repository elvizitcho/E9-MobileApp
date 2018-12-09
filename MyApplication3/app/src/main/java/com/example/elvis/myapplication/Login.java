package com.example.elvis.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView goToken = findViewById(R.id.go_token);
        goToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent senIntent = new Intent(v.getContext(), Token.class);
                startActivity(senIntent);
            }
        });
    }
}


