package com.example.ken.materialdesginexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class WelcomeActivity extends ActionBarActivity {

    EditText mailET;
    EditText passwortdET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        passwortdET = (EditText) findViewById(R.id.password_et_login);
        mailET = (EditText) findViewById(R.id.mail_et_login);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                validateInputs();

            }
        });
        findViewById(R.id.singup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, SignUpActivity2.class));

            }
        });
    }

    private void validateInputs() {
        String id;
        String password;

        boolean error = false;


        id = mailET.getText().toString();
        if (id.length() == 0) {
            error = true;
            mailET.setError("Invalid email");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            error = true;
            mailET.setError("Invalid email");
        }


        password = passwortdET.getText().toString();
        if (password.length() == 0) {
            error = true;
            passwortdET.setError("Invalid password ");
        }

        if (!error) {//succefully validation
            final ProgressDialog dialog = new ProgressDialog(WelcomeActivity.this);
            dialog.setMessage(getString(R.string.progress_login));
            dialog.show();
            ParseUser.logInInBackground(id, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    dialog.dismiss();
                    if (e != null) {
                        // Show the error message
                        Toast.makeText(WelcomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        // Start an intent for the dispatch activity
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });
            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
            //finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
}
