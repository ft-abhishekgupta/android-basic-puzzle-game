package com.example.abhishek.memorydash;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    static int pt;
    Button b[];
    TextView tf,tf2;
    static CountDownTimer cc;
    int flag=0,flag2=0,flagmute=1,p=-1,i,c=0,a=0,score=250;
    int s[]={20,20,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10,11,11,12,12,13,13,14,14,15,15,16,16,17,17,18,18,19,19};
    static char s2[]={'0','0','1','1','2','2','3','3','4','4','5','5','6','6','7','7','8','8','9','9','/','/','*','*','+','+','-','-','@','@','#','#','$','$','%','%','&','&','=','='};
    MediaPlayer bkk,click,wrong,right,over;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

//        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        bkk = MediaPlayer.create(this,R.raw.background);
        click = MediaPlayer.create(this,R.raw.click);
        wrong = MediaPlayer.create(this,R.raw.wrong);
        right = MediaPlayer.create(this,R.raw.right);
        over= MediaPlayer.create(this,R.raw.stop);
        //bkk.start();
        bkk.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            public void onCompletion(MediaPlayer bkk) {
                if(flag2==0)bkk.start();
            }
        });

        tf2= (TextView) findViewById(R.id.textView2);

        //for storing high score shared pref used
        SharedPreferences prefs2 = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);//for retrieve value
        score = prefs2.getInt("key", 250);//0 default value
        tf2.setText("Min Time = "+score + " sec");

        int ii=0;
        shuffleArray(s);
        tf= (TextView) findViewById(R.id.textView);

        b = initializeButtons(40);
        for (ii=0;ii<40;ii++){
            b[ii].setText(" ");
            b[ii].setOnClickListener(this);
        }
        cc=new CountDownTimer(250000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                pt= (int) millisUntilFinished;
                tf.setText("Time Left = "+String.format("%03d",(millisUntilFinished/1000)));
                if((millisUntilFinished/1000)<10){
                    tf.setTextColor(Color.parseColor("#ff0000"));
                }
            }
            @Override
            public void onFinish() {
                flag=1;
                over.start();
                if(flagmute==0)bkk.stop();
                flag2=1;
                Toast.makeText(MainActivity.this, "Attempts = "+a, Toast.LENGTH_LONG).show();
                for (int ii=0;ii<40;ii++){
                    b[ii].setClickable(false);
                    tf.setText("Time Left = 000");
                }
                MyLoseDialog mddd=new MyLoseDialog();
                mddd.show(getFragmentManager(),"My Lose");
            }
        };

        CountDownTimer c3=new CountDownTimer(6000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                for(int k=0;k<40;k++){
                    b[k].setClickable(false);
                    b[k].setText(s2[k] + "");
                }
            }
            @Override
            public void onFinish() {
                for(int k=0;k<40;k++){
                    b[k].setClickable(true);
                    b[k].setText(" ");
                }
            }
        };
        c3.start();
        cc.start();
    }
    @Override
    public void onBackPressed(){
        MyDialog md=new MyDialog();//to show dialog
        md.show(getFragmentManager(),"My Alert");//calls onCreateDialog, My Alert is a tag for this fragment
    }
    public Button[] initializeButtons(int x) {
        Resources res = getResources();
        Button[] buttons = new Button[x];
        for (int i = 0; i < x; i++) {
            String b = "button" + (i+1);
            buttons[i] = (Button) findViewById(res.getIdentifier(b, "id", getPackageName()));
        } return buttons;
    }
    static void shuffleArray(int[] ar){
        Random rnd=new Random();
        for(int k=1;k<50;k++){
            for (int i = ar.length - 1; i > 0; i--){
                int index = rnd.nextInt(i);
                int a = ar[index];  char b=s2[index];
                ar[index] = ar[i];  s2[index]=s2[i];
                ar[i] = a;          s2[i]=b;
            }
        }
    }
    @Override
    public void onClick(View v) {
        try {
            for (int j = 0; j < 40; j++) {
                if (v.getId() == b[j].getId() && s[j] != -1 && b[j].getText().equals(" ")) {
                    if (p == -1) {
                        try {
                            if (click.isPlaying()) {
                                click.stop();
                                click.release();
                                click = MediaPlayer.create(this, R.raw.click);
                            }
                            click.start();
                            bkk.wait(500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        b[j].setText(s2[j] + "");
                        b[j].setBackgroundColor(Color.parseColor("#efb239"));
                        p = j;
                    } else {
                        if ((s2[j] + "").equals(b[p].getText())) {
                            try {
                                if (right.isPlaying()) {
                                    right.stop();
                                    right.release();
                                    right = MediaPlayer.create(this, R.raw.right);
                                }
                                right.start();
                                bkk.wait(500);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            b[j].setText(s2[j] + "");
                            b[j].setBackgroundColor(Color.parseColor("#67c03d"));
                            b[p].setBackgroundColor(Color.parseColor("#67c03d"));
                            s[j] = -1;
                            s[p] = -1;
                            p = -1;
                            a++;
                        } else {
                            try {
                                if (wrong.isPlaying()) {
                                    wrong.stop();
                                    wrong.release();
                                    wrong = MediaPlayer.create(this, R.raw.wrong);
                                }
                                wrong.start();
                                bkk.wait(500);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            final int j2=j;
                            CountDownTimer c2=new CountDownTimer(300,150) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    b[j2].setBackgroundColor(Color.parseColor("#ef4239"));
                                    b[p].setBackgroundColor(Color.parseColor("#ef4239"));
                                    b[j2].setText(s2[j2] + "");
                                    for(int k=0;k<40;k++){
                                        b[k].setClickable(false);
                                    }
                                }

                                @Override
                                public void onFinish() {

                                    b[p].setText(" ");
                                    b[j2].setText(" ");
                                    b[p].setBackgroundColor(Color.parseColor("#ef8839"));
                                    b[j2].setBackgroundColor(Color.parseColor("#ef8839"));
                                    p = -1;
                                    a++;
                                    for(int k=0;k<40;k++){
                                        b[k].setClickable(true);
                                    }
                                }
                            };
                            c2.start();



                        }
                    }
                }
            }
            c = 0;
            for (int j = 0; j < 40; j++) {
                if (!b[j].getText().equals(" "))
                    c++;
            }
            if (c == 40) {
                Toast.makeText(this, "Congrats! Attempts = " + a, Toast.LENGTH_LONG).show();
                cc.cancel();
                if(flagmute==0)bkk.stop();
                MyWinDialog mdd = new MyWinDialog();
                mdd.show(getFragmentManager(), "My Win");
                //for high score
                if(score>(250-Integer.parseInt(tf.getText().toString().substring(12)))){
                    SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("key", 250-Integer.parseInt(tf.getText().toString().substring(12)));
                    editor.commit();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "something is wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void mute(View view) {
        if(flagmute==0) {bkk.stop();bkk.release();flagmute=1;}
        else {

            bkk = MediaPlayer.create(this,R.raw.background);
            bkk.setLooping(true);
            bkk.start();flagmute=0;
        }
    }
}
