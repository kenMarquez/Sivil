package com.example.ken.materialdesginexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpActivity2 extends ActionBarActivity {

    Toolbar toolbar;

    EditText nameET;
    EditText mailET;
    EditText passwordET;
    EditText passwordConfirmET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity2);
//        toolbar = (Toolbar) findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameET = (EditText) findViewById(R.id.name_edit_text);
        mailET = (EditText) findViewById(R.id.mail_et);
        passwordConfirmET = (EditText) findViewById(R.id.password_confirm_et);
        passwordET = (EditText) findViewById(R.id.password_et);
        findViewById(R.id.button_siguiente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInputs();
            }
        });

    }

    public void validateInputs() {

        final String name;

        final String mail;
        final String password;
        final String passwordConfirm;

        boolean error = false;

        name = nameET.getText().toString();
        if (name.length() == 0) {
            error = true;
            nameET.setError("Nombre de usuario no valido");
        } else {
            nameET.setError(null);
        }


        mail = mailET.getText().toString();
        if (mail.length() == 0) {
            error = true;
            mailET.setError("Email no valido");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            error = true;
            mailET.setError("Email no valido");
        }


        password = passwordET.getText().toString();
        if (password.length() == 0) {
            error = true;
            passwordET.setError("Contraseña invalida");
        }

        passwordConfirm = passwordConfirmET.getText().toString();
        if (passwordConfirm.length() == 0) {
            error = true;
            passwordConfirmET.setError("Contraseña invalida");
        } else {
            passwordConfirmET.setError(null);
        }

        if (!passwordConfirm.equals(password)) {
            passwordET.setError("Las contraseñas no coinciden");
            error = true;
        } else {
            passwordET.setError(null);
        }

        //Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
        if (!error) {
            final ProgressDialog dialog = new ProgressDialog(SignUpActivity2.this);
            dialog.setMessage(getString(R.string.progress_signup));
            dialog.show();
            ParseUser user = new ParseUser();
            user.setUsername(mail);
            user.setPassword(password);
            user.setEmail(mail);
            user.put(getString(R.string.key_first_name), name);
            //user.put(getString(R.string.key_last_name), lastName);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    dialog.dismiss();
                    if (e != null) {

                        if (e.getCode() == 125) {
                            Toast.makeText(getApplicationContext(), "Email no valido", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        startActivity(new Intent(SignUpActivity2.this, MainActivity.class));
                        finish();
                    }
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up_activity2, menu);
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
