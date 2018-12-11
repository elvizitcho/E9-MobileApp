package com.example.elvis.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Token extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        ImageView retorno = findViewById(R.id.token_return);
        retorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent senIntent = new Intent(v.getContext(), Login.class);
                startActivity(senIntent);
            }
        });

        Button logbut = findViewById(R.id.button);
        logbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText token = findViewById(R.id.editText4);
                String sToken = token.getText().toString();
                if (sToken.matches("")) {
                    Toast.makeText(Token.this, "Insira um token", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Token.this, "Bem vindo", Toast.LENGTH_SHORT).show();
                    Intent logIntent = new Intent(v.getContext(), Dashboard.class);
                    startActivity(logIntent);
                }
            }
        });
    }

}
