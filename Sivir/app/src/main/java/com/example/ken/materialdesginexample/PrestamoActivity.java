package com.example.ken.materialdesginexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ken.materialdesginexample.Util.CirclePicture;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PrestamoActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private String postId;
    private CircularProgressView progressView;
    private ParseObject myPost;
    private ImageView userImage;
    private TextView tvName;
    private TextView tvMonto;
    private TextView tvConfianza;
    private TextView tvDireccion;

    private ParseUser userLender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamo);
        toolbar = (Toolbar) findViewById(R.id.app_bar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        progressView = (CircularProgressView) findViewById(R.id.progress_view);
//        progressView.resetAnimation();

        tvName = (TextView) findViewById(R.id.name);
        tvMonto = (TextView) findViewById(R.id.monto_tv_title);
        userImage = (ImageView) findViewById(R.id.imageView3);
        findViewById(R.id.aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManagerDialog = getFragmentManager();
                DialogSignIn myDialog = new DialogSignIn();
                myDialog.show(fragmentManagerDialog, "tagAlerta");
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        } else {
            postId = extras.getString(getString(R.string.key_object_id));
            //          Toast.makeText(getApplicationContext(), "" + postId, Toast.LENGTH_SHORT).show();
        }
        loadUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_prestamo, menu);
        return true;
    }

    private void loadPost() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(getString(R.string.object_post_id));
        query.whereEqualTo("objectId", postId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    myPost = parseObjects.get(0);
//                    tvTitle.setText(myPost.getString(getString(R.string.key_title)));
                    ParseUser user = myPost.getParseUser("parent");
                    tvName.setText(user.getString(getString(R.string.key_first_name)));
                    tvMonto.setText(myPost.getString(getString(R.string.key_descripcion)));
                    userImage.setImageResource(user.getInt(getString(R.string.key_image)));
//                    tvDescription.setText(myPost.getString(getString(R.string.key_descripcion)));
//                    tvNoComments.setText(myPost.getInt(getString(R.string.key_no_comments)) + "");
//                    tvNoMatches.setText(myPost.getInt(getString(R.string.key_no_matches)) + "");
//                    loadComments();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al abrir este Post", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void loadUser() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(postId, new GetCallback<ParseUser>() {

            @Override
            public void done(ParseUser user, com.parse.ParseException e) {
                if (e == null) {
                    // The query was successful.
                    userLender = user;
                    tvName.setText(user.getString(getString(R.string.key_first_name)));
                    tvMonto.setText("Monto: " + user.getString(getString(R.string.key_monto)));
                    //userImage.setImageResource(user.getInt(getString(R.string.key_image)));
                    Picasso.with(getApplicationContext()).load(user.getInt(getString(R.string.key_image))).resize(84, 84).transform(new CirclePicture()).into(userImage);
                    if (user == null) {
                        // no matching user!
                    }
                } else {
                    tvName.setText("Juan Camacho");
                    tvMonto.setText("Monto: $500.00");
                    Picasso.with(getApplicationContext()).load(R.drawable.user62).resize(84, 84).transform(new CirclePicture()).into(userImage);
                }
            }
        });
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

    private class DialogSignIn extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.dialog_apoyar, null);
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog
            // layout
            builder.setView(view)
                    // Add action buttons
                    .setPositiveButton("Ayudar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    EditText mont = (EditText) view
                                            .findViewById(R.id.monto_et);
                                    // Toast.makeText(getActivity(),
                                    // "pass:" + pass.getText(),
                                    // Toast.LENGTH_SHORT).show();
                                    String text = mont.getText().toString();
                                    if (!text.trim().equals("")) {

//                                        try {
                                        double monto = Double.parseDouble(text);
                                        Log.i("myLog", (userLender == null) + "");
                                        double montoUser = Double.parseDouble(userLender.getString(getString(R.string.key_monto)));
                                        double total = montoUser - monto;
                                        Log.i("myLog", total + "");
                                        userLender.put("validation", 2);
                                        userLender.put(getString(R.string.key_monto), total + "");
                                        userLender.put("notification", true);

                                        userLender.put("cad", "no");
                                        userLender.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null) {
                                                    Toast.makeText(PrestamoActivity.this, "Operacion exitosa", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(PrestamoActivity.this,
                                                            "Error en la transaccion",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
//                                        } catch (Exception e) {
//                                            Log.i("myLog", "" + e.toString());
//                                        }
                                    } else

                                    {
                                        Toast.makeText(PrestamoActivity.this,
                                                "Cantidad Incorrecta",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                    )
                    .

                            setNegativeButton("Cancelar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            DialogSignIn.this.getDialog().cancel();

                                        }
                                    }

                            );
            return builder.create();
        }
    }
}
