package com.example.elvis.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.List;

public class Controls extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lv = findViewById(R.id.listview);
        generateListContent();
        lv.setAdapter(new MyListAdapter(this, R.layout.list_item, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Controls.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    private void generateListContent() {
        data.add("Sala A");
        data.add("Sala B");
        data.add("Banheiro A");
        data.add("Banheiro B");
        data.add("Quarto A");
        data.add("Quarto B");
        data.add("Escritório");
        data.add("Corredor");
        data.add("Garagem");
        data.add("Churrasco");
        data.add("Trigger");
        data.add("Echo");
        data.add("Dht22");
        data.add("Infravermelho");
        data.add("Chuva");
        data.add("LDR");
        data.add("Umidade do Solo");
        data.add("Ultravioleta");
        data.add("Sensor Chamas");
        data.add("RGB Vermelho");
        data.add("RGB Verde");
        data.add("RGB Azul");
        data.add("Postes");
        data.add("Garagem");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_sensores:
                Intent senIntent = new Intent(this, Dashboard.class);
                startActivity(senIntent);
                return true;
            case R.id.navigation_eletros:
                Toast.makeText(Controls.this, "opção redundante", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about:
                Toast.makeText(Controls.this, "vai par activity SOBRE", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                Toast.makeText(Controls.this, "função para deslogar e retorna para LOGIN", Toast.LENGTH_SHORT).show();
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
