package com.example.ken.materialdesginexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ken.materialdesginexample.Adapters.CardsAdapter;
import com.example.ken.materialdesginexample.Class.Post;
import com.example.ken.materialdesginexample.fragments.DrawerFragment;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private final int[] ICONS = {R.drawable.noticiason, R.drawable.mapaoff, R.drawable.categoriason};

    private ListView cardList;
    private CardsAdapter adapter;
    private ArrayList<Post> arrayPost;
    private CircularProgressView progressView;
    private TextView calidad_aire;
//    private ViewPager pager;
//    private MyPagerAdapter adapter;
//    private IconPagerAdapter adapterIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar2);
        toolbar.setNavigationIcon(R.drawable.logocivilnavbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        arrayPost = new ArrayList<>();
        //new Prestatario().execute("Mensaje Prueba");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigate_drawer);
        drawerFragment.setUp(R.id.navigate_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        calidad_aire = (TextView) findViewById(R.id.calidad_aire);
        //tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        pager = (ViewPager) findViewById(R.id.pager);
//        adapter = new MyPagerAdapter(getSupportFragmentManager());
//        adapterIcon = new IconPagerAdapter(this);


        cardList = (ListView) findViewById(R.id.card_list);
        progressView = (CircularProgressView) findViewById(R.id.progress_view);
        progressView.resetAnimation();
        progressView.setVisibility(View.INVISIBLE);
        cardList.setOnItemClickListener(MainActivity.this);
        getComments();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Menu", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.navigate) {
            startActivity(new Intent(getApplicationContext(), DescriptionActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getComments() {
        //final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        //progressDialog.setTitle("Cargando datos.........");
        //progressDialog.show();
        progressView.startAnimation();
        progressView.setVisibility(View.VISIBLE);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(getString(R.string.object_post_id));

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> listPost, ParseException e) {
                if (e == null) {//query salio bien
                    Log.i("myLog", "salio bien");
                    for (int i = 0; i < listPost.size(); i++) {
                        String title = listPost.get(i).getString(getString(R.string.key_title));
                        String category = listPost.get(i).getString(getString(R.string.key_category));
                        String description = listPost.get(i).getString(getString(R.string.key_descripcion));
                        String latLon = listPost.get(i).getString(getString(R.string.key_latlon));
                        String userId = listPost.get(i).getParseUser(getString(R.string.key_parent)).getObjectId();
                        int noMatches = listPost.get(i).getInt(getString(R.string.key_no_matches));
                        int noComments = listPost.get(i).getInt(getString(R.string.key_no_comments));
                        String objectId = listPost.get(i).getObjectId();
                        Post post = new Post(title, category, description, latLon, userId, noComments, noMatches, objectId);
                        Log.i("myLog", post.toString());
                        new task().execute();
                        arrayPost.add(post);
                    }

                    //progressDialog.dismiss();
                    Collections.reverse(arrayPost);
                    adapter = new CardsAdapter(MainActivity.this, arrayPost);
                    cardList.setAdapter(adapter);
                    progressView.resetAnimation();
                    progressView.setVisibility(View.INVISIBLE);


                } else {//error)
                    progressView.resetAnimation();
                    progressView.setVisibility(View.INVISIBLE);
                    new task().execute();
                    Log.i("myLog", "error al cargar los post");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerFragment.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerFragment.mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    private Toolbar toolbar;
    private DrawerFragment drawerFragment;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent in = new Intent(MainActivity.this, PrestamoActivity.class);
        in.putExtra(getString(R.string.key_object_id), arrayPost.get(position).getIdUser());
        //in.putExtra(getString(R.string.key_object_id), arrayPost.get(position).getPostId());

        startActivity(in);
    }

    public class task extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            Log.i("myLog", "not : ");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        try {
                            ParseUser user = ParseUser.getCurrentUser();

                            boolean isMessage = user.getBoolean("notification");
                            String cad = user.getString("cad");
                            String name = user.getString(getString(R.string.key_first_name));
                            Number val = user.getNumber("validation");

                            Log.i("myLog", "user : " + (name) + " val: " + val.intValue());
                            if (isMessage) {
                                NotificationManager mNotificationManager = (NotificationManager)
                                        getSystemService(Context.NOTIFICATION_SERVICE);
                                Intent in = new Intent(MainActivity.this, MainActivity.class);
                                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, in, 0);

                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                                        MainActivity.this)
                                        .setSmallIcon(R.drawable.icon)
                                        .setContentTitle("Sivil")
                                        .setContentText(
                                                "Un usuario ha aprobado tu prestamo");
                                // new NotificationCompat.
                                // .setContentText("contentsismo");
                                mBuilder.setAutoCancel(true);
                                mBuilder.setDefaults(Notification.DEFAULT_SOUND
                                        | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
                                mBuilder.setContentIntent(contentIntent);
                                mNotificationManager.notify(0, mBuilder.build());
                                user.put("notification", false);
                                return;
                            }

                            Thread.sleep(10000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.run();
            return null;
        }
    }
}
