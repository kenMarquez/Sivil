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
import com.example.ken.materialdesginexample.Util.CirclePicture;
import com.parse.GetCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ken on 07/03/2015.
 */
public class CardsAdapter extends BaseAdapter {

    private final int[] ICONS = {R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4};
    //    private final int[] ICONS = {R.drawable.baches, R.drawable.vialidad, R.drawable.accidente};
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
            viewHolder.textMonto = (TextView) view.findViewById(R.id.monto);
            view.setTag(viewHolder);
        }
        Post post = lista.get(position);
        final ViewHolder holder = (ViewHolder) view.getTag();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(post.getIdUser(), new GetCallback<ParseUser>() {

            @Override
            public void done(ParseUser user, com.parse.ParseException e) {
                if (e == null) {
                    // The query was successful.
                    holder.textTitle.setText(user.getString(actividad.getString(R.string.key_first_name)));

                    String monto = user.getString(actividad.getString(R.string.key_monto));
                    holder.textMonto.setText("Monto: $" + monto);
                    Picasso.with(actividad.getApplicationContext()).load(user.getInt(actividad.getString(R.string.key_image))).resize(60, 60).transform(new CirclePicture()).into(holder.imageTitle);
                    if (user == null) {
                        // no matching user!
                    }
                } else {
                    holder.textTitle.setText("Juan");
                }
            }
        });


//        query.getInBackground(post.getIdUser(),new GetCallback<ParseUser>() {
//
//            @Override
//            public void done(ParseUser parseUser, ParseException e) {
//
//            }

//        });


        holder.textDescription.setText(post.getDescription());
//        holder.textNoComments.setText(post.getNoComments() + "");
//        holder.textNoMatches.setText(post.getNoMatches() + "");

        int y = position % 4;
//        switch (y) {
//            case 0:
        //Picasso.with(actividad.getApplicationContext()).load(ICONS[y]).into(holder.imageTitle);

        //holder.imageTitle.setImageResource(ICONS[y]);
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
        public TextView textMonto;

    }

}
