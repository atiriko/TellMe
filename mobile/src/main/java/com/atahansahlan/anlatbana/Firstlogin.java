package com.atahansahlan.anlatbana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.TextView;


public class Firstlogin extends AppCompatActivity {
    private TextView text;
    private Animation fade_in, fade_out;
int x = 1;
boolean ilk = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstlogin);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                "com.atahansahlan.anlatbana", Context.MODE_PRIVATE);
        //To read preferences:

        boolean isEna = prefs.getBoolean("key", false);
        boolean isoff = prefs.getBoolean("onOff", false);

        ilk = isEna;
        if (isEna){
            startActivity(new Intent(Firstlogin.this, MainActivity.class));
            finish();
        }


        final TextView text = (TextView) findViewById(R.id.logintext);
        final Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        text.bringToFront();
        text.invalidate();
        text.startAnimation(fade_in);

            MediaPlayer mp = MediaPlayer.create(this, R.raw.merhaba);
            mp.start();

           waitfor();

        }
        public void waitfor(){
            Handler handler = new Handler();
            handler.postDelayed(() -> {

                if (!ilk) {


                    final TextView text = (TextView) findViewById(R.id.logintext);
                    final Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
                    Animation fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

                    SharedPreferences prefs = this.getSharedPreferences(
                            "com.atahansahlan.anlatbana", Context.MODE_PRIVATE);

                    if (x == 1) {
                        text.setText("Umarım uygulama işine yarar!");

                            MediaPlayer mp = MediaPlayer.create(this, R.raw.umarim);
                            mp.start();

                        waitfor();
                    } else if (x == 2) {
                        text.setText("Hadi sana uygulamayı kullanmayı öğretelim!");

                            MediaPlayer mp = MediaPlayer.create(this, R.raw.hadi);
                            mp.start();

                        waitfor();
                    } else if (x == 3) {
                        text.setText("Ekrana bir kere dokunursan titreşimden sonra kamera açılır" +
                                " ve etrafındaki yazıları sana okur.");

                            MediaPlayer mp = MediaPlayer.create(this, R.raw.birkere);
                            mp.start();

                        waitfor();
                    } else if (x == 4) {
                        text.setText("Ekrana iki kere dokunursan titreşimden sonra söylediğin telefon numarasını arar.");

                            MediaPlayer mp = MediaPlayer.create(this, R.raw.ikikere);
                            mp.start();

                        waitfor();
                    } else if (x == 5) {
                        text.setText("Ekrana üç kere dokunursan yapabileceklerini sana tekrar anlatır.");

                            MediaPlayer mp = MediaPlayer.create(this, R.raw.uckere);
                            mp.start();

                        waitfor();
                    } else if (x == 6) {
                        text.setText("Telefonu sallarsan sana son bildirimini okur.");

                            MediaPlayer mp = MediaPlayer.create(this, R.raw.salla);
                            mp.start();

                        waitfor();
                    } else if (x == 7) {
                        text.setText("Hadi Başlayalım!");

                            MediaPlayer mp = MediaPlayer.create(this, R.raw.baslayalim);
                            mp.start();

                        waitfor();
                    } else {
                        boolean isEnable = true;
                        prefs.edit().putBoolean("key", isEnable).commit();
                        startActivity(new Intent(Firstlogin.this, MainActivity.class));
                        finish();

                    }
                    text.startAnimation(fade_out);
                    text.setTextSize(30);
                    text.startAnimation(fade_in);
                    x++;
                }

            }, 5000);
        }


}
