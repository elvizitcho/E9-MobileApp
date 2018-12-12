package com.example.elvis.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controls extends AppCompatActivity{

    //private ArrayList<String> lista1 = new ArrayList<>();
    private final ArrayList<String> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView lv = findViewById(R.id.listview);

        String ipValue = "127.0.0.1";

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
                                //Toast.makeText(Controls.this, dados, Toast.LENGTH_SHORT).show();

                                try {
                                    jsonArray = new JSONArray(dados);
                                    //Toast.makeText(Controls.this, "deu certo", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject dispositivo = jsonArray.getJSONObject(i);
                                        String comodo = dispositivo.getString("comodo_nome");
                                        String nome = dispositivo.getString("nome");
                                        lista.add(comodo + " - " + nome);
                                        //Toast.makeText(Controls.this, comodo + " - " + nome, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e){
                                    e.printStackTrace();
                                }

                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                            //String joined = TextUtils.join(", ", lista2);
                            //Toast.makeText(Controls.this, joined, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(Controls.this, response, Toast.LENGTH_SHORT).show();
                            //lista1 = lista2;
                            //Toast.makeText(Controls.this, "deve aparecer por ultimo", Toast.LENGTH_SHORT).show();
                            //generateListContent();

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
            //String joined = TextUtils.join(", ", lista2);
            //Toast.makeText(Controls.this, joined, Toast.LENGTH_SHORT).show();


        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1/webservice/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService ws = retrofit.create(WebService.class);



        Call<List<Dispositivo>> dispLista = ws.getDispositivos("{\"permissao\": \"default\"}");
        dispLista.enqueue(new Callback<List<Dispositivo>>() {
            @Override
            public void onResponse(Call<List<Dispositivo>> call, Response<List<Dispositivo>> response) {
                for(Dispositivo disp: response.body()){
                    String comodo = disp.getComodo_nome();
                    String dispo = disp.getNome();
                    lista.add(comodo + " - " + dispo);
                }
            }

            @Override
            public void onFailure(Call<List<Dispositivo>> call, Throwable t) {
                lista.add("erro");
            }
        });*/


        //joined = TextUtils.join(", ", lista2);
        //Toast.makeText(Controls.this, joined, Toast.LENGTH_SHORT).show();

        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Controls.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    private void generateListContent() {
        lista.add("Sala");
        lista.add("Banheiro");
        //lista.add("Banheiro B");
        lista.add("Quarto");
        //lista.add("Quarto B");
        lista.add("Escritório");
        lista.add("Corredor");
        lista.add("Garagem");
        lista.add("Churrasco");
        /*lista.add("Trigger");
        lista.add("Echo");
        lista.add("Dht22");
        lista.add("Infravermelho");
        lista.add("Chuva");
        lista.add("LDR");
        lista.add("Umidade do Solo");
        lista.add("Ultravioleta");
        lista.add("Sensor Chamas");*/
        lista.add("RGB Vermelho");
        lista.add("RGB Verde");
        lista.add("RGB Azul");
        lista.add("Postes");
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
            /*case R.id.about:
                Toast.makeText(Controls.this, "vai par activity SOBRE", Toast.LENGTH_SHORT).show();
                return true;*/
            case R.id.logout:
                Toast.makeText(Controls.this, "Saindo...", Toast.LENGTH_SHORT).show();
                Intent outIntent = new Intent(this, Login.class);
                startActivity(outIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            holder.title = convertView.findViewById(R.id.list_item_text);
            holder.switcher = convertView.findViewById(R.id.list_item_swt);
            convertView.setTag(holder);
            holder = (ViewHolder) convertView.getTag();
            /*holder.switcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                }
            });*/

            final ViewHolder finalHolder = (ViewHolder) convertView.getTag();
            holder.switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (finalHolder.switcher.isChecked()) {
                        Toast.makeText(getContext(), "Ligar " + finalHolder.title.getText(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Desligar " + finalHolder.title.getText(), Toast.LENGTH_SHORT).show();
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

            holder.title.setText(getItem(position));

            return convertView;
        }

        /*@Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.switcher = (Switch) convertView.findViewById(R.id.list_item_swt);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.switcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }*/
    }

    public class ViewHolder {
        TextView title;
        Switch switcher;
    }



}
