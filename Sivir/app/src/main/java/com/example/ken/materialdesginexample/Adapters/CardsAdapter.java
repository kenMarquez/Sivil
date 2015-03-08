package com.example.ken.materialdesginexample.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.materialdesginexample.Class.Post;
import com.example.ken.materialdesginexample.R;

import java.util.ArrayList;

/**
 * Created by Ken on 07/03/2015.
 */
public class CardsAdapter extends BaseAdapter {

    private final int[] ICONS = {R.drawable.baches, R.drawable.vialidad, R.drawable.accidente};
    private final Activity actividad;
    private final ArrayList<Post> lista;

    public CardsAdapter(Activity actividad, ArrayList<Post> lista) {
        super();
        this.actividad = actividad;
        this.lista = lista;
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = actividad.getLayoutInflater();
            view = inflater.inflate(R.layout.item_card_list, null, true);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageTitle = (ImageView) view.findViewById(R.id.image_main_card);
            viewHolder.textTitle = (TextView) view.findViewById(R.id.tv_title_card);
            viewHolder.textDescription = (TextView) view.findViewById(R.id.tv_description_card);
            viewHolder.textFecha = (TextView) view.findViewById(R.id.tv_fecha_card);
            viewHolder.textNoComments = (TextView) view.findViewById(R.id.tv_no_comments);
            viewHolder.textNoMatches = (TextView) view.findViewById(R.id.tv_no_matches);
            view.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Post post = lista.get(position);
        holder.textTitle.setText(post.getTitle());
        holder.textDescription.setText(post.getDescription());
        holder.textNoComments.setText(post.getNoComments() + "");
        holder.textNoMatches.setText(post.getNoMatches() + "");

        int y = position % 3;
//        switch (y) {
//            case 0:
        holder.imageTitle.setImageResource(ICONS[y]);
//                break;
//            case 1:
//                holder.imageTitle.setImageResource(ICONS[1]);
//                break;
//            case 2:
//                holder.imageTitle.setImageResource(ICONS[2]);
//                break;
//        }


        return view;
    }

    public int getCount() {
        return lista.size();
    }

    public Object getItem(int arg0) {
        return lista.get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        public ImageView imageTitle;
        public TextView textTitle;
        public TextView textDescription;
        public TextView textFecha;
        public TextView textNoMatches;
        public TextView textNoComments;

    }

}
