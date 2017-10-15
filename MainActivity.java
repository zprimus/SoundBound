package com.zacprimus.hackgt;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.media.MediaRecorder;
import java.io.IOException;


//****************** TARGET API MUST BE 22 ******************\\

public class MainActivity extends AppCompatActivity {

    private MediaRecorder myAudioRecorder;
    private String outputFile, directoryFile;

    public static int nextFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup for activity_main.xml items
        final ImageButton musicButton = (ImageButton) findViewById(R.id.imageButton);
        final Switch musicSwitch = (Switch) findViewById(R.id.switch1);
        final TextView textSwitch = (TextView) findViewById(R.id.textView);
        final WebView browser = (WebView) findViewById(R.id.musicImage);

        // sets up WebView
        musicButton.bringToFront();
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.loadUrl("file:///android_asset/musicFrame0.png");

        // makes first text say Record
        textSwitch.setText("RECORD");

        // creates audio file
        directoryFile = Environment.getExternalStorageDirectory().getAbsolutePath();




        // functions of the switch
        musicSwitch.setOnClickListener(new View.OnClickListener() {
            int toggle1 = 0;

            public void onClick(View v) {

                // Code here executes on main thread after user presses button1
                if (toggle1 == 0) {
                    textSwitch.setText("PLAYBACK");

                    toggle1 = 1;
                } else if (toggle1 == 1) {

                    textSwitch.setText("RECORD");
                    toggle1 = 0;
                }

            }
        });

        // functions of the image button
        musicButton.setOnClickListener(new View.OnClickListener() {
            int toggle2 = 0;

            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();

                if (toggle2 == 0 && textSwitch.getText() == "RECORD") {
                    musicSwitch.setEnabled(false);  // disables switch
                    browser.loadUrl("file:///android_asset/music.gif"); // changes WebView image
                    outputFile = directoryFile + "/recording" + nextFile + ".wav";
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myAudioRecorder.setOutputFile(outputFile);

                    // record starts
                    //////////////////////////////////////////////
                    try {
                        //myAudioRecorder.reset();
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                    } catch (IllegalStateException ise) {

                    } catch (IOException ioe) {

                    }
                    //////////////////////////////////////////////

                    toggle2 = 1;
                }

                else if (toggle2 == 1 && textSwitch.getText() == "RECORD") {

                    musicSwitch.setEnabled(true);  // enables switch
                    browser.loadUrl("file:///android_asset/musicFrame0.png");

                    // record stops
                    //////////////////////////////////
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                    //////////////////////////////////

                    toggle2 = 0;
                }
                else if (toggle2 == 0 && textSwitch.getText() == "PLAYBACK") {

                    musicSwitch.setEnabled(false);  // enables switch
                    browser.loadUrl("file:///android_asset/music.gif");
                    try {
                        mediaPlayer.setDataSource(outputFile);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                    } catch (Exception e) {

                    }

                    toggle2 = 1;
                }
                else if (toggle2 == 1 && textSwitch.getText() == "PLAYBACK") {

                    musicSwitch.setEnabled(true);  // enables switch
                    browser.loadUrl("file:///android_asset/musicFrame0.png");

                    toggle2 = 0;
                }
            }
        });
    }
}
