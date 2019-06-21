package com.example.abhishek.memorydash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by abhishek on 09-07-2016.
 */
public class MyDialog extends DialogFragment implements DialogInterface.OnClickListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic);
        builder.setTitle("EXIT?");
        builder.setMessage("Are you sure to exit?");
        builder.setPositiveButton("Yes",this);//'this' is the obj of listener obj
        builder.setNegativeButton("No",this);
        AlertDialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);


        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if(which==-1){

            Toast.makeText(getActivity(), "Bye!!", Toast.LENGTH_SHORT).show();
            android.os.Process.killProcess(android.os.Process.myPid());//it closes the application
        }
        else{

            dialog.dismiss();
            Toast.makeText(getActivity(), "Enjoy", Toast.LENGTH_SHORT).show();

        }
    }
}
