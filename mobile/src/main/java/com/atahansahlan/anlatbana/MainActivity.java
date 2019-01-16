package com.atahansahlan.anlatbana;

import android.Manifest;
import static android.Manifest.permission.CALL_PHONE;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Notification;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.content.BroadcastReceiver;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.lucem.anb.characterscanner.Scanner;
import com.lucem.anb.characterscanner.ScannerListener;
import com.lucem.anb.characterscanner.ScannerView;
import com.squareup.seismic.ShakeDetector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static android.Manifest.permission.CALL_PHONE;

public class MainActivity extends AppCompatActivity implements ShakeDetector.Listener {
    private Button button, settings_btn, stop_btn;
    private TextView textView;
    private CardView cardView;
    private TextToSpeech textToSpeechSystem;
    private final int REQ_CODE_SPEECH_INPUT = 100;



    int click = 0;
    boolean onoff = true;
    boolean konusulacak = true;
    String textToSay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        TextView textView = (TextView) findViewById(R.id.textView);
        Button settings_btn = (Button)findViewById(R.id.settings_btn);
        SurfaceView surfaceView = findViewById(R.id.surface);
        CardView cardView = (CardView)findViewById(R.id.cardView);
        Button stop_btn = (Button)findViewById(R.id.stop_btn);



        surfaceView.bringToFront();
        surfaceView.setVisibility(View.INVISIBLE);
        cardView.bringToFront();
        cardView.setVisibility(View.INVISIBLE);
        stop_btn.bringToFront();
        stop_btn.setVisibility(View.INVISIBLE);

        Scanner scanner = new Scanner(MainActivity.this, surfaceView, new ScannerListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDetected(String detections) {

                    // some language data is available, create TTS instance
                String Detections = detections;




                stop_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            konusulacak = false;
                            //textToSay = " ";
                            stop_btn.setVisibility(View.INVISIBLE);
                            cardView.setVisibility(View.INVISIBLE);
                        callLifeCycleMethod().callActivityOnPause(MainActivity.this);
                        if(textToSpeechSystem.isSpeaking()){
                            callLifeCycleMethod().callActivityOnPause(MainActivity.this);
                        }
                        if(textToSpeechSystem != null) {
                            textToSpeechSystem.stop();
                            textToSpeechSystem.shutdown();
                        }

                    }
                });
                textToSpeechSystem = new TextToSpeech(getApplicationContext(), ttsInitResult -> {
                    if (konusulacak) {
                        textToSpeechSystem.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                            @Override
                            public void onStart(String s) {
                                System.out.println("onStart: " + s);
                            }

                            @Override
                            public void onDone(String s) {
                                System.out.println("onDone: " + s);
                            }

                            @Override
                            public void onError(String s) {
                                System.out.println("onError: " + s);
                            }
                        });

                        if (TextToSpeech.SUCCESS == ttsInitResult) {
                            textToSay = Detections;
                            System.out.println(textToSay);
                            //System.out.println(Detections);

                            if (konusulacak) {
                                textToSpeechSystem.speak(textToSay, TextToSpeech.QUEUE_FLUSH,null);

                            } else {
                                textToSpeechSystem.speak("", TextToSpeech.QUEUE_FLUSH, null);
                                textToSpeechSystem.stop();
                                textToSpeechSystem.shutdown();
                            }


                        } else {
                            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.dildosyalari);
                            mp.start();
                            Intent installIntent = new Intent();
                            installIntent
                                    .setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                            startActivity(installIntent);
                        }
                                /*if (click == 0){
                                    textToSpeechSystem.shutdown();
                                }*/
                    }

                    });




                //Toast.makeText(MainActivity.this, detections, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStateChanged(String state, int i) {

            }

        });







        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
        settings_btn.bringToFront();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                "com.atahansahlan.anlatbana", Context.MODE_PRIVATE);










        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, settingsActivity.class));

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click++;
                textView.setVisibility(View.INVISIBLE);
                stop_btn.setVisibility(View.INVISIBLE);
                cardView.setVisibility(View.INVISIBLE);
                System.out.println(onoff);


                if (click == 1) {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {

                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            v.vibrate(500);
                        }
                        surfaceView.setVisibility(View.INVISIBLE);
                        if (click == 1) {
                            cardView.setVisibility(View.VISIBLE);
                            surfaceView.setVisibility(View.VISIBLE);
                            scanner.scan();
                            stop_btn.setVisibility(View.VISIBLE);
                            konusulacak = true;
                            click = 0;

                        } else if (click == 2) {
                            askSpeechInput();


                            /*
                            This part is needed to get access to notifications and create one

                            NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            NotificationCompat.Builder ncomp = new NotificationCompat.Builder(getApplicationContext(), "notification_event");
                            ncomp.setContentTitle("My Notification");
                            ncomp.setContentText("Notification Listener Service Example");
                            ncomp.setTicker("Notification Listener Service Example");
                            ncomp.setSmallIcon(R.drawable.round_settings_white_36);
                            ncomp.setAutoCancel(true);
                            nManager.notify((int)System.currentTimeMillis(),ncomp.build());

                            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                            startActivity(intent);
                            */
                            click = 0;
                        } else if (click == 3) {
                            boolean isEnable = false;
                            prefs.edit().putBoolean("key", isEnable).commit();
                            startActivity(new Intent(MainActivity.this, Firstlogin.class));
                            finish();
                            click = 0;
                        } else {
                            textView.setText("Sanırım biraz fazla bastın. Tekrar dene");
                            if (onoff) {
                                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sanirim);
                                mp.start();
                            }
                            textView.setVisibility(View.VISIBLE);
                            click = 0;
                        }

                    }, 3000);
                }

            }


        });


    }
    public static Instrumentation callLifeCycleMethod()
    {
        return new Instrumentation();
    }
    public void onResume(){
        super.onResume();

        if(textToSpeechSystem !=null){
            textToSpeechSystem.stop();
            textToSpeechSystem.shutdown();
        }

    }
    public void onPause(){
        super.onPause();

        System.out.println("durduuuu");
        if(textToSpeechSystem !=null){
            textToSpeechSystem.stop();
            textToSpeechSystem.shutdown();
        }

    }
    private void askSpeechInput() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Aramak istediğin numarayı söyle");
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch(ActivityNotFoundException e) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.lutfen);
            mp.start();
            String appPackageName = "com.google.android.googlequicksearchbox";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }

    // Receiving speech input

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView textView = (TextView) findViewById(R.id.textView);


        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(result.get(0));
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:"+result.get(0)));

                    if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(i);
                    } else {
                        requestPermissions(new String[]{CALL_PHONE}, 1);
                    }

                }
                break;
            }

        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textToSpeechSystem !=null){
            textToSpeechSystem.stop();
            textToSpeechSystem.shutdown();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            finish();
            System.exit(0);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override public void hearShake() {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("salladin");
        textView.setVisibility(View.VISIBLE);



        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

    }




}


