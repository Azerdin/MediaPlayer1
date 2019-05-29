package com.example.mediaplayer;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class list extends Activity {

    public ArrayList <String> names = new ArrayList<>();
    public ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        names.add("Queen We will rock you");
        names.add("AC DC Back in Black");
        names.add("AC DC Hells Bell");
        names.add("AC DC Highway to hell");
        names.add("AC DC Thunderstruck");

        ListView list = findViewById(R.id.list);
        Adapter adapter = new Adapter(this, names);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = names.get(position);
                Intent i = new Intent(list.this, MainActivity.class);
                i.putExtra("name", name);
                i.putExtra("id", position);
                startActivity(i);
            }
        });

    }
}
