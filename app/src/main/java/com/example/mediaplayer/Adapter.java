package com.example.mediaplayer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter
{
    private Activity activity;
    private ArrayList<String> names;
    Adapter(Activity activity, ArrayList names)
    {
        super(activity, R.layout.element, names);
        this.activity = activity;
        this.names = names;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView;

        if(convertView == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.element, parent, false);

        }
        else
        {
            rowView = convertView;
        }
        TextView name = rowView.findViewById(R.id.name);

        name.setText(names.get(position));


        return rowView;

    }
}
