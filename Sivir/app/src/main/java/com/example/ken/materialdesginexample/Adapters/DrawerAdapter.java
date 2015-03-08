package com.example.ken.materialdesginexample.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.ken.materialdesginexample.R;

/**
 * Created by Ken on 07/03/2015.
 */
public class DrawerAdapter extends ArrayAdapter<String> {

    Context context;

    public DrawerAdapter(Context context, int item_drawer_list, int resource, String[] strings) {
        super(context, item_drawer_list, strings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAUNCHER_APPS_SERVICE);
        View rowView = inflater.inflate(R.layout.item_drawer_list, parent, false);
        return super.getView(position, convertView, parent);
    }
}
