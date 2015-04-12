package com.example.ken.materialdesginexample;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

import com.example.ken.materialdesginexample.Adapters.CommentsAdapter;
import com.example.ken.materialdesginexample.Class.Comment;
import com.example.ken.materialdesginexample.Dialogs.CommentDialog;
import com.example.ken.materialdesginexample.Interfaces.OnCommentReady;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class NoticiaActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener, OnCommentReady {

    private String postId;
    private Toolbar toolbar;
    private CircularProgressView progressView;
    private ListView listViewComments;
    private ArrayList<Comment> arrayComments;
    private ParseObject myPost;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvNoMatches;
    private TextView tvNoComments;
    CommentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.logocivilnavbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressView = (CircularProgressView) findViewById(R.id.progress_view);
        progressView.resetAnimation();

        findViewById(R.id.comment).setOnClickListener(this);
        findViewById(R.id.programas).setOnClickListener(this);
        findViewById(R.id.match).setOnClickListener(this);

        arrayComments = new ArrayList<>();
        listViewComments = (ListView) findViewById(R.id.list_comments);
        adapter = new CommentsAdapter(NoticiaActivity.this, arrayComments);
        listViewComments.setAdapter(adapter);

        tvTitle = (TextView) findViewById(R.id.tv_title_noticia);
        tvDescription = (TextView) findViewById(R.id.tv_description_noticia);
        tvNoComments = (TextView) findViewById(R.id.tv_no_comments);
        tvNoMatches = (TextView) findViewById(R.id.tv_no_matches);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        } else {
            postId = extras.getString(getString(R.string.key_object_id));
            //          Toast.makeText(getApplicationContext(), "" + postId, Toast.LENGTH_SHORT).show();
        }
        loadPost();

    }

    private void loadComments() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(getString(R.string.object_comment_id));
        query.whereEqualTo(getString(R.string.key_post_comment), myPost);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                progressView.resetAnimation();
                progressView.setVisibility(View.INVISIBLE);

                if (e == null) {
                    //Toast.makeText(getApplicationContext(), parseObjects.size() + "", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < parseObjects.size(); i++) {
                        String description = parseObjects.get(i).getString(getString(R.string.key_descripcion));
                        ParseUser user = parseObjects.get(i).getParseUser(getString(R.string.key_user_comment));
                        int likes = parseObjects.get(i).getInt(getString(R.string.likes));
                        int disLikes = parseObjects.get(i).getInt(getString(R.string.disLikes));
                        Comment comment;

                        if (user != null) {
                            String name = null;
                            try {
                                name = user.fetchIfNeeded().getString(getString(R.string.key_first_name));
                            } catch (ParseException e1) {
                                Log.i("myLog", "user name error");
                                name = "Anónimo";
                            }
                            //String name = user.getString(getString(R.string.key_first_name));
                            comment = new Comment(description, name, likes, disLikes);
                        } else {
                            Log.i("myLog", "user null");
                            comment = new Comment(description, "", likes, disLikes);
                        }
                        arrayComments.add(comment);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al abrir Comentario", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void loadPost() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(getString(R.string.object_post_id));
        query.whereEqualTo("objectId", postId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    myPost = parseObjects.get(0);
                    tvTitle.setText(myPost.getString(getString(R.string.key_title)));
                    tvDescription.setText(myPost.getString(getString(R.string.key_descripcion)));
                    tvNoComments.setText(myPost.getInt(getString(R.string.key_no_comments)) + "");
                    tvNoMatches.setText(myPost.getInt(getString(R.string.key_no_matches)) + "");
                    loadComments();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al abrir este Post", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void addComment(String descripcion, ParseUser user, int likes, int disLikes) {
        ParseObject parseObject = new ParseObject(getString(R.string.object_comment_id));

        parseObject.put(getString(R.string.key_descripcion), descripcion);
        parseObject.put(getString(R.string.key_user_comment), user);
        parseObject.put(getString(R.string.likes), likes);
        parseObject.put(getString(R.string.disLikes), disLikes);
        parseObject.put(getString(R.string.key_post_comment), myPost);
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //Toast.makeText(getApplicationContext(), "Comentario añadido satisfactoriamente", Toast.LENGTH_SHORT).show();
                    progressView.setVisibility(View.VISIBLE);
                    loadComments();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al escribir este comentario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_noticia, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getPost() {


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment:
                FragmentManager fragmentManager = getSupportFragmentManager();
                CommentDialog dialogo = new CommentDialog(this);
                dialogo.show(fragmentManager, "");
                break;
            case R.id.programas:
                break;
            case R.id.match:
                break;
        }
    }

    @Override
    public void onCommentReady(String content) {

        addComment(content, ParseUser.getCurrentUser(), 0, 0);
    }
}
