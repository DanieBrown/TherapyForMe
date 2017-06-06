package com.therapyforme.therapyforme;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

public class PanoramaActivity extends AppCompatActivity {


    private VrPanoramaView mVrPanoramaView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);
        mVrPanoramaView = (VrPanoramaView) findViewById(R.id.pano_view);
        loadPhotoShpere();
    }

    private void loadPhotoShpere() {
        //weird loading code snippit
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        InputStream inputStream = null;
        AssetManager assetManager = getAssets();

        try{
            inputStream = assetManager.open("examplePanorama.jpg");
            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            mVrPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options);
            inputStream.close();
        }catch(IOException e){
            Log.e("Tuts+", "Exception in loadPhotoShpere: " + e.getMessage() );
        }
    }


    @Override
    protected void onPause(){
        mVrPanoramaView.pauseRendering();
        super.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mVrPanoramaView.resumeRendering();
    }
    @Override
    protected void onDestroy(){
        mVrPanoramaView.shutdown();
        super.onDestroy();
    }
}
