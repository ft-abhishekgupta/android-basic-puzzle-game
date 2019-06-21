package com.example.abhishek.memorydash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by abhishek on 12-07-2016.
 */
public class MyWinDialog extends DialogFragment implements DialogInterface.OnClickListener {

    MediaPlayer win;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic);
        builder.setTitle("YOU WON");
        TextView tt=(TextView)getActivity().findViewById(R.id.textView);

        builder.setMessage("Completed in "+(250-Integer.parseInt(tt.getText().toString().substring(12)))+"seconds. Do you want to exit or Play Again?");
        builder.setPositiveButton("Exit",this);//'this' is the obj of listener obj
        builder.setNegativeButton("Play!",this);
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
            //getActivity().finish();

            Toast.makeText(getActivity(), "Bye!!", Toast.LENGTH_SHORT).show();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        else{
            dialog.dismiss();
            //MainActivity.resumeCounter();

            getActivity().recreate();
            Toast.makeText(getActivity(), "Enjoy", Toast.LENGTH_SHORT).show();

        }
    }
}
