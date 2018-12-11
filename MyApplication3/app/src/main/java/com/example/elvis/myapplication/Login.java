package com.example.elvis.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final String ipValue = "127.0.0.1";

        TextView goToken = findViewById(R.id.go_token);
        goToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent senIntent = new Intent(v.getContext(), Token.class);
                startActivity(senIntent);
            }
        });
        Button logbut = findViewById(R.id.button);
        logbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = findViewById(R.id.login);
                EditText senha = findViewById(R.id.senha);
                String sUser = user.getText().toString();
                String sSenha = senha.getText().toString();
                if (sUser.matches("") || sSenha.matches("")) {
                    Toast.makeText(Login.this, "Há campos em branco", Toast.LENGTH_SHORT).show();
                } else {
                    post(ipValue);
                    /*Toast.makeText(Login.this, "Bem vindo", Toast.LENGTH_SHORT).show();
                    Intent logIntent = new Intent(v.getContext(), Dashboard.class);
                    startActivity(logIntent);*/
                }
            }
        });

    }

    public void post(String ipValue){

        final EditText login = findViewById(R.id.login);
        final EditText senha = findViewById(R.id.senha);

        String url = "http://" + ipValue + "/webservice/login/validaLogin";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,

                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        String sucesso = null;
                        try {
                            jsonObject = new JSONObject(response);
                            sucesso = jsonObject.getString("sucesso");
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                        if(sucesso.matches("true")){
                            Toast.makeText(Login.this, "Bem vindo", Toast.LENGTH_SHORT).show();
                            Intent logIntent = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(logIntent);
                        } else {
                            Toast.makeText(Login.this, "Dados inválidos", Toast.LENGTH_SHORT).show();
                        }

                        /*JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {

                            JSONObject total = jsonArray.getJSONObject(0);
                            Boolean sucesso = total.getBoolean("sucesso");

                            if(sucesso==true){
                                Toast.makeText(Login.this, "Bem vindo", Toast.LENGTH_SHORT).show();
                                Intent logIntent = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(logIntent);
                            } else {
                                Toast.makeText(Login.this, "Usuário inválido", Toast.LENGTH_SHORT).show();
                            }


                            /*JSONArray dados = total.getJSONArray("dados");

                            for (int i = 0; i < dados.length(); i++) {
                                JSONObject dispositivo = jsonArray.getJSONObject(i);
                                String comodo = dispositivo.getString("comodo_nome");
                                String nome = dispositivo.getString("nome");
                                lista.add(comodo + "-" + nome);
                            }*/
                        /*} catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Response", response);*/
                        Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("usuario", login.getText().toString());
                params.put("senha", senha.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);
    }

}


