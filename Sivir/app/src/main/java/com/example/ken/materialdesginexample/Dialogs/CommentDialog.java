package com.example.ken.materialdesginexample.Dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ken.materialdesginexample.Interfaces.OnCommentReady;
import com.example.ken.materialdesginexample.R;

/**
 * Created by Ken on 08/03/2015.
 */
public class CommentDialog extends DialogFragment {

    OnCommentReady listener;

    public CommentDialog() {

    }

    public CommentDialog(OnCommentReady listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_layout, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog
        // layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Ingresa Tu Comentario",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                EditText pass = (EditText) view
                                        .findViewById(R.id.password);
                                // Toast.makeText(getActivity(),
                                // "pass:" + pass.getText(),
                                // Toast.LENGTH_SHORT).show();
                                String text = pass.getText().toString();
                                if (text.length() > 0) {
                                    listener.onCommentReady(text);
                                } else {
                                    Toast.makeText(getActivity(),
                                            "Comentario Invalido",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                CommentDialog.this.getDialog().cancel();

                            }
                        });
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
