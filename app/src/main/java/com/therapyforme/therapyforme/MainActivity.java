package com.therapyforme.therapyforme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//Here will be the code for attempt to connect to the database





public class MainActivity extends AppCompatActivity {
    //Here will be the code for attempt to connect to the database
    //private static final String url = "mysql://user:pass@172.30.177.154:3306/myhealth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button one redirect to video, (or panorama in this case)
        ImageButton btn = (ImageButton) findViewById(R.id.playButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PanoramaActivity.class));
            }
        });

        //Button two, to redirect to video layout page
        ImageButton btn2 = (ImageButton) findViewById(R.id.videoViewButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoActivity.class));
            }
        });


        URL url = null;
        try {
            url = new URL("jdbc:mysql://user:pass@172.30.177.154:3306/myhealth");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

