package com.example.ken.materialdesginexample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class DescriptionActivity extends ActionBarActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText titleET;
    private EditText descriptionET;
    private int category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        toolbar = (Toolbar) findViewById(R.id.app_bar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleET = (EditText) findViewById(R.id.categoria_addreporte);
        descriptionET = (EditText) findViewById(R.id.descripcion_post);
        category = -1;
        findViewById(R.id.image_accidente).setOnClickListener(this);
        findViewById(R.id.image_baches).setOnClickListener(this);
        findViewById(R.id.image_delincuencia).setOnClickListener(this);
        findViewById(R.id.image_salud).setOnClickListener(this);
        findViewById(R.id.image_vial).setOnClickListener(this);

        findViewById(R.id.publicar_button_description).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInputs();
            }
        });


    }

    private void validateInputs() {

        final ProgressDialog dialog = new ProgressDialog(DescriptionActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Publicando ....");
        dialog.show();

        String title;
        String description;
        boolean error = false;

        title = titleET.getText().toString();
        if (title.length() == 0) {
            titleET.setError(getString(R.string.error_title));
            error = true;
        } else {
            titleET.setError(null);
        }
        description = descriptionET.getText().toString();
        if (description.length() == 0) {
            descriptionET.setError("Descripción no valida");
            error = true;
        } else {
            descriptionET.setError(null);
        }

        if (!error) {

            ParseObject post = new ParseObject("Post");
            if (category != -1) {
                post.put(getString(R.string.key_category), category);
            }
            post.put(getString(R.string.key_latlon), "19.449312,-99.142999");
            post.put(getString(R.string.key_descripcion), description);
            post.put(getString(R.string.key_title), title);
            post.put(getString(R.string.key_parent), ParseUser.getCurrentUser());
            post.put(getString(R.string.key_no_comments), 0);
            post.put(getString(R.string.key_no_matches), 0);

            post.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    dialog.dismiss();
                    if (e == null) {
                        Toast.makeText(DescriptionActivity.this, getString(R.string.valid_post), Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(DescriptionActivity.this, MainActivity.class));
                        NavUtils.navigateUpFromSameTask(DescriptionActivity.this);
                    } else {
                        Log.i("myLog", e.toString());
                        Toast.makeText(DescriptionActivity.this, getString(R.string.invalid_post), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_description, menu);
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


    public void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_accidente:
                category = 0;
                break;
            case R.id.image_baches:
                category = 1;
                break;
            case R.id.image_delincuencia:
                category = 2;
                break;
            case R.id.image_salud:
                category = 3;
                break;
            case R.id.image_vial:
                category = 4;
                break;
        }
    }
}
