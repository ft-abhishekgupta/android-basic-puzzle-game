package com.example.abhishek.memorydash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by abhishek on 12-07-2016.
 */
public class MyLoseDialog extends DialogFragment implements DialogInterface.OnClickListener {

    MediaPlayer win;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic);
        builder.setTitle("YOU LOSE");


        builder.setMessage("Do you want to exit or Try Again?");
        builder.setPositiveButton("Exit",this);//'this' is the obj of listener obj
        builder.setNegativeButton("Try Again!",this);
        AlertDialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        win=  MediaPlayer.create(getActivity(),R.raw.win);
        win.start();
        win.setLooping(true);

        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        win.stop();
        if(which==-1){
//            getActivity().finish(); - this closes the activity
            Toast.makeText(getActivity(), "Bye!!", Toast.LENGTH_SHORT).show();
            android.os.Process.killProcess(android.os.Process.myPid());//it closes the application
        }
        else{
            dialog.dismiss();
            getActivity().recreate();
            Toast.makeText(getActivity(), "All the best", Toast.LENGTH_SHORT).show();

        }
    }
}
