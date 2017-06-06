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
    private boolean mIsPaused;


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

        //init others
        mVrVideoView.setEventListener(new ActivityEventListener());
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    public void playPause() {
        if( mIsPaused ) {
            mVrVideoView.playVideo();
        } else {
            mVrVideoView.pauseVideo();
        }

        mIsPaused = !mIsPaused;
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
        mIsPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVrVideoView.resumeRendering();
        mIsPaused = false;
    }

    @Override
    protected void onDestroy() {
        mVrVideoView.shutdown();
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if( fromUser ) {
            mVrVideoView.seekTo(progress);
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
            mSeekBar.setMax((int) mVrVideoView.getDuration());
            mIsPaused = false;
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

    class VideoLoaderTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                VrVideoView.Options options = new VrVideoView.Options();
                options.inputType = VrVideoView.Options.TYPE_MONO;
                mVrVideoView.loadVideoFromAsset("congo.mp4", options);
            } catch( IOException e ) {
                //Handle exception
            }

            return true;
        }
    }
}
