package com.example.ken.materialdesginexample;

import android.content.Intent;
import android.os.Bundle;
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

import com.astuetz.PagerSlidingTabStrip;
import com.example.ken.materialdesginexample.Adapters.CardsAdapter;
import com.example.ken.materialdesginexample.Class.Post;
import com.example.ken.materialdesginexample.fragments.DrawerFragment;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private final int[] ICONS = {R.drawable.noticiason, R.drawable.mapaoff, R.drawable.categoriason};

    private ListView cardList;
    private CardsAdapter adapter;
    private ArrayList<Post> arrayPost;
    private CircularProgressView progressView;
    private PagerSlidingTabStrip tabs;
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
        arrayPost = new ArrayList<>();

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
        Intent in = new Intent(MainActivity.this, NoticiaActivity.class);
        in.putExtra(getString(R.string.key_object_id), arrayPost.get(position).getPostId());

        startActivity(in);
    }
}
