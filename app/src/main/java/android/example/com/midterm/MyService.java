package android.example.com.midterm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MyService extends Service implements MediaPlayer.OnPreparedListener, AudioManager.OnAudioFocusChangeListener {
    public static final String ACTION_PLAY = "android.example.com.midterm.actionplay";
    public static final String PAUSE_PLAY = "android.example.com.midterm.actionpause";
    public static final String CHANGE_PLAY = "android.example.com.midterm.actionchange";
    public static final String STOP_PLAY = "android.example.com.midterm.actionstop";

    private MediaPlayer mMediaPlayer = null;
    private AudioManager mAudioManager;
    private int songid;

    public void onCreate() { return; }

    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case ACTION_PLAY:
                songid = intent.getIntExtra("songid", 1);
                Toast.makeText(this, "Music Start~", Toast.LENGTH_SHORT).show();
                initMediaPlayer();
                break;
            case PAUSE_PLAY:
                if (mMediaPlayer != null) {
                    mMediaPlayer.pause();
                    Toast.makeText(this, "Pause~", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Service does not start yet..", Toast.LENGTH_SHORT).show();
                break;
            case STOP_PLAY:
                this.stopSelf();
                Toast.makeText(this, "Stop Music!", Toast.LENGTH_SHORT).show();
                break;
            case CHANGE_PLAY:
                songid = intent.getIntExtra("songname", 1);
                if(mMediaPlayer!= null) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                        initMediaPlayer();
                    } else {
                        this.stopSelf();
                    }
                }
                break;
        }

        return START_NOT_STICKY;
    }
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                if(mMediaPlayer==null) initMediaPlayer();
                else if (!mMediaPlayer.isPlaying()) mMediaPlayer.start();
                mMediaPlayer.setVolume(1.0f, 1.0f);
                Toast.makeText(this,"AUDIOFOCUS_GAIN", Toast.LENGTH_SHORT).show();
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                if(mMediaPlayer==null) stopSelf();
                else if(mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    stopSelf();
                }
                Toast.makeText(this, "AUDIOFOCUS_LOSS", Toast.LENGTH_SHORT).show();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if(mMediaPlayer==null) stopSelf();
                if(mMediaPlayer.isPlaying()) mMediaPlayer.pause();
                Toast.makeText(this, "AUDIOFOCUS_LOSS_TRANSIENT", Toast.LENGTH_SHORT).show();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if(mMediaPlayer==null) stopSelf();
                if(mMediaPlayer.isPlaying()) mMediaPlayer.setVolume(0.1f,0.1f);
                Toast.makeText(this, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private  void initMediaPlayer() {
        if(mMediaPlayer!=null) {
            mMediaPlayer.start();
            mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            while(true) {
                int result = mAudioManager.requestAudioFocus(
                        this,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) break;
                try {
                    wait(1000);
                } catch (Exception e) {
                    stopSelf();
                }
            }
            return;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            AssetFileDescriptor afd = this.getResources().openRawResourceFd(songid);
            if (afd == null) return;
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        } catch (IOException e) {
            Log.i("MediaPlayBackTest", "Cannot get datasource...exiting");
            stopSelf();
        }
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        while(true) {
            int result = mAudioManager.requestAudioFocus(
                    this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) break;
            Log.i("MediaPlayBackText", "Gaining AUDIOFOCUS again...");
            try {
                wait(1000);
            } catch (Exception e) {
                Log.i("MediaPlayBackTest","Wait failed....");
                stopSelf();
            }
        }
        mMediaPlayer.prepareAsync();
        return;
    }

    public void onPrepared(MediaPlayer player) {
        player.start();
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onDestroy() {
        Toast.makeText(this, "Service-onDestroy()", Toast.LENGTH_SHORT).show();
        if(mMediaPlayer!=null) {
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }
}