package com.therapyforme.therapyforme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        int count=12;
        TextView tv = (TextView) findViewById(R.id.dayProgress);
        tv.setText(""+count+"/100");



        Date date = new Date();
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        TextView favText = (TextView) findViewById(R.id.movieTextFave);
        favText.setText(""+ dateFormat.format(date) + "\t 16:03 \t ORANGE: First tried then..");

        TextView favText1 = (TextView) findViewById(R.id.movieTextFave2);
        favText1.setText(""+ dateFormat.format(date) + "\t 9:03 \t FOREST: Strange Reaction..");

    }
}
