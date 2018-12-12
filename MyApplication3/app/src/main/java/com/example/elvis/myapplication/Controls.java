package com.example.elvis.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Controls extends AppCompatActivity{

    final ArrayList<Dispositivo> lista = new ArrayList<>();
    final String ipValue = "127.0.0.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView lv = findViewById(R.id.listview);

        String url = "http://" + ipValue + "/webservice/dispositivo/list";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,

            new com.android.volley.Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    JSONObject jsonObject = null;
                    JSONArray jsonArray;
                    String dados= null;

                    try {
                        jsonObject = new JSONObject(response);
                        dados = jsonObject.getString("dados");

                        try {
                            jsonArray = new JSONArray(dados);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject dispositivo = jsonArray.getJSONObject(i);
                                String id = dispositivo.getString("id");
                                String comodo = dispositivo.getString("comodo_nome");
                                String nome = dispositivo.getString("nome");
                                String ligado = dispositivo.getString("ligado");
                                lista.add(new Dispositivo(id,comodo + " - " + nome,ligado));
                            }

                        } catch (JSONException e){
                                e.printStackTrace();
                        }

                    } catch (JSONException e){
                            e.printStackTrace();
                    }

                    lv.setAdapter(new MyListAdapter(Controls.this, R.layout.list_item, lista));
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
                params.put("permissao", "default");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_sensores:
                Intent senIntent = new Intent(this, Dashboard.class);
                startActivity(senIntent);
                return true;
            case R.id.navigation_eletros:
                Toast.makeText(Controls.this, "Lista Atualizada", Toast.LENGTH_SHORT).show();
                Intent conIntent = new Intent(this, Controls.class);
                startActivity(conIntent);
                return true;
            case R.id.logout:
                Toast.makeText(Controls.this, "Saindo...", Toast.LENGTH_SHORT).show();
                Intent outIntent = new Intent(this, Login.class);
                startActivity(outIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private class MyListAdapter extends ArrayAdapter<Dispositivo> {
        private int layout;
        private List<Dispositivo> mObjects;
        private MyListAdapter(Context context, int resource, List<Dispositivo> objects) {
            super(context, resource, objects);
            mObjects = (ArrayList) objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);

            holder.id = convertView.findViewById(R.id.list_item_id);
            holder.title = convertView.findViewById(R.id.list_item_text);
            holder.switcher = convertView.findViewById(R.id.list_item_swt);

            convertView.setTag(holder);

            holder = (ViewHolder) convertView.getTag();

            Dispositivo item = mObjects.get(position);
            holder.id.setText(item.getId());
            holder.title.setText(item.getNome());
            if(item.getLigado().equals("1")){
                holder.switcher.setChecked(true);
            } else if(item.getLigado().equals("0")){
                holder.switcher.setChecked(false);
            }

            final ViewHolder finalHolder = (ViewHolder) convertView.getTag();
            holder.switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (finalHolder.switcher.isChecked()) {
                        String url = "http://" + ipValue + "/webservice/dispositivo/ligar";
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,

                                new com.android.volley.Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        JSONObject jsonObject = null;

                                        try {
                                            jsonObject = new JSONObject(response);

                                            if (jsonObject.getString("sucesso").equals("true")){
                                                Toast.makeText(getContext(), finalHolder.title.getText() + ": Ligado", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getContext(), "Não foi possível ligar", Toast.LENGTH_SHORT).show();
                                                finalHolder.switcher.setChecked(false);
                                            }

                                        } catch (JSONException e){
                                            e.printStackTrace();
                                        }

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
                                params.put("permissao", "default");
                                params.put("id",finalHolder.id.getText().toString());
                                return params;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(Controls.this);
                        queue.add(postRequest);

                    } else {
                        String url = "http://" + ipValue + "/webservice/dispositivo/desligar";
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,

                                new com.android.volley.Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        JSONObject jsonObject = null;

                                        try {
                                            jsonObject = new JSONObject(response);

                                            if (jsonObject.getString("sucesso").equals("true")){
                                                Toast.makeText(getContext(), finalHolder.title.getText() + ": Desligado", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getContext(), "Não foi possível desligar", Toast.LENGTH_SHORT).show();
                                                finalHolder.switcher.setChecked(true);
                                            }

                                        } catch (JSONException e){
                                            e.printStackTrace();
                                        }

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
                                params.put("permissao", "default");
                                params.put("id",finalHolder.id.getText().toString());
                                return params;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(Controls.this);
                        queue.add(postRequest);

                    }
                }
            });

            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalHolder.switcher.isChecked()){
                        finalHolder.switcher.setChecked(false);
                    } else {
                        finalHolder.switcher.setChecked(true);
                    }
                }
            });

            return convertView;
        }

    }

    public class ViewHolder {
        TextView id;
        TextView title;
        Switch switcher;
    }

}
