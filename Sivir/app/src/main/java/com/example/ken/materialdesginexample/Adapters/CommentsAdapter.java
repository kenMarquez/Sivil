package com.example.ken.materialdesginexample.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.materialdesginexample.Class.Comment;
import com.example.ken.materialdesginexample.R;

import java.util.ArrayList;

/**
 * Created by Ken on 08/03/2015.
 */
public class CommentsAdapter extends BaseAdapter {

    private final int[] ICONS = {R.drawable.baches, R.drawable.vialidad, R.drawable.accidente};
    private final Activity actividad;
    private final ArrayList<Comment> lista;

    public CommentsAdapter(Activity actividad, ArrayList<Comment> lista) {
        super();
        this.actividad = actividad;
        this.lista = lista;
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = actividad.getLayoutInflater();
            view = inflater.inflate(R.layout.item_comments_list, null, true);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textDescription = (TextView) view.findViewById(R.id.tv_description_comment);
            viewHolder.textLikes = (TextView) view.findViewById(R.id.tv_like);
            viewHolder.textDislikes = (TextView) view.findViewById(R.id.tv_dislike);
            viewHolder.textName = (TextView) view.findViewById(R.id.tv_title_comment);

            view.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        Comment comment = lista.get(position);
        holder.textName.setText(comment.getUserName());
        holder.textDescription.setText(comment.getDescripcion());
        holder.textLikes.setText(comment.getLikes() + "");
        holder.textDislikes.setText(comment.getDisLikes() + "");

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
        public TextView textName;
        public TextView textDescription;
        public TextView textLikes;
        public TextView textDislikes;

    }

}

