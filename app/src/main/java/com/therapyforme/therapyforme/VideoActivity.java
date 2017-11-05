package com.therapyforme.therapyforme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity implements OnSeekBarChangeListener{

    private VrVideoView mVrVideoView;
    private SeekBar mSeekBar;
    private VrVideoView mVrVideoView2;
    private SeekBar mSeekBar2;
    private VrVideoView mVrVideoView3;
    private SeekBar mSeekBar3;
    private boolean mIsPaused;
    private boolean mIsPaused2;
    private boolean mIsPaused3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        VideoLoaderTask mBackgroundVideoLoaderTask = new VideoLoaderTask();
        mBackgroundVideoLoaderTask.execute();
        initViews();
    }

    private void initViews() {
        mVrVideoView = (VrVideoView) findViewById(R.id.videoView);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);

        mVrVideoView2 = (VrVideoView) findViewById(R.id.videoView2);
        mSeekBar2 = (SeekBar) findViewById(R.id.seek_bar2);

        mVrVideoView3 = (VrVideoView) findViewById(R.id.videoView3);
        mSeekBar3 = (SeekBar) findViewById(R.id.seek_bar3);

        //init others
        mVrVideoView.setEventListener(new ActivityEventListener());
        playPause();
        mSeekBar.setOnSeekBarChangeListener(this);

        //init others
        mVrVideoView2.setEventListener(new ActivityEventListener2());
        playPause2();
        mSeekBar2.setOnSeekBarChangeListener(this);

        //init others
        mVrVideoView3.setEventListener(new ActivityEventListener3());
        playPause3();
        mSeekBar3.setOnSeekBarChangeListener(this);
    }

    public void playPause() {
        if( mIsPaused ) {
            mVrVideoView.playVideo();
        } else {
            mVrVideoView.pauseVideo();
        }
        mIsPaused = !mIsPaused;


    }

    public void playPause2(){
        if( mIsPaused2 ) {
            mVrVideoView2.playVideo();
        } else {
            mVrVideoView2.pauseVideo();
        }
        mIsPaused2 = !mIsPaused2;
    }

    public void playPause3(){
        if( mIsPaused3 ) {
            mVrVideoView3.playVideo();
        } else {
            mVrVideoView3.pauseVideo();
        }
        mIsPaused3 = !mIsPaused3;
    }

    public void onPlayPausePressed() {

    }

//    public void onVolumeToggleClicked() {
//        mIsMuted = !mIsMuted;
//        mVrVideoView.setVolume(mIsMuted ? 0.0f : 1.0f);
//
//    }

    @Override
    protected void onPause() {
        super.onPause();
        mVrVideoView.pauseRendering();
        mVrVideoView2.pauseRendering();
        mVrVideoView3.pauseRendering();
        mIsPaused = true;
        mIsPaused2 = true;
        mIsPaused3 = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVrVideoView.resumeRendering();
        mVrVideoView2.resumeRendering();
        mVrVideoView3.resumeRendering();
        mIsPaused = false;
        mIsPaused3 = false;
    }

    @Override
    protected void onDestroy() {
        mVrVideoView.shutdown();
        mVrVideoView2.shutdown();
        mVrVideoView3.shutdown();
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if( fromUser ) {
            mVrVideoView.seekTo(progress);
            mVrVideoView2.seekTo(progress);
            mVrVideoView3.seekTo(progress);
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }


    /**
     * New inner class to deal with sub class methods
     *
     */
    private class ActivityEventListener extends VrVideoEventListener {
        @Override
        public void onLoadSuccess() {
            super.onLoadSuccess();
            mIsPaused = true;
            mSeekBar.setMax((int) mVrVideoView.getDuration());

        }

        @Override
        public void onLoadError(String errorMessage) {
            super.onLoadError(errorMessage);
        }

        @Override
        public void onClick() {
            playPause();
        }

        @Override
        public void onNewFrame() {
            super.onNewFrame();
            mSeekBar.setProgress((int) mVrVideoView.getCurrentPosition());
        }

        @Override
        public void onCompletion() {
            //restart the video allowing it to loop
            mVrVideoView.seekTo(0);
        }
    }

    /**
     * New inner class to deal with sub class methods
     *
     */
    private class ActivityEventListener2 extends VrVideoEventListener {
        @Override
        public void onLoadSuccess() {
            super.onLoadSuccess();
            mIsPaused2 = true;
            mSeekBar2.setProgress((int) mVrVideoView2.getCurrentPosition());

        }

        @Override
        public void onLoadError(String errorMessage) {
            super.onLoadError(errorMessage);
        }

        @Override
        public void onClick() {
            playPause2();
        }

        @Override
        public void onNewFrame() {
            super.onNewFrame();
            mSeekBar2.setProgress((int) mVrVideoView2.getCurrentPosition());
        }

        @Override
        public void onCompletion() {
            //restart the video allowing it to loop
            mVrVideoView2.seekTo(0);
        }
    }

    /**
     * New inner class to deal with sub class methods
     *
     */
    private class ActivityEventListener3 extends VrVideoEventListener {
        @Override
        public void onLoadSuccess() {
            super.onLoadSuccess();
            mIsPaused3 = true;
            mSeekBar3.setProgress((int) mVrVideoView3.getCurrentPosition());

        }

        @Override
        public void onLoadError(String errorMessage) {
            super.onLoadError(errorMessage);
        }

        @Override
        public void onClick() {
            playPause3();
        }

        @Override
        public void onNewFrame() {
            super.onNewFrame();
            mSeekBar3.setProgress((int) mVrVideoView3.getCurrentPosition());
        }

        @Override
        public void onCompletion() {
            //restart the video allowing it to loop
            mVrVideoView3.seekTo(0);
        }
    }

    class VideoLoaderTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                VrVideoView.Options options = new VrVideoView.Options();
                options.inputType = VrVideoView.Options.TYPE_MONO;
                mVrVideoView.loadVideoFromAsset("rollerCoaster.mp4", options);
                mVrVideoView2.loadVideoFromAsset("scary.mp4", options);
                mVrVideoView3.loadVideoFromAsset("flying.mp4", options);
            } catch( IOException e ) {
                //Handle exception
            }

            return true;
        }
    }
}
