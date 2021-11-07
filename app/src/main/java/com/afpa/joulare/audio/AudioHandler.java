package com.afpa.joulare.audio;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;

import com.afpa.joulare.R;

public class AudioHandler extends Service implements OnCompletionListener {

    public static MediaPlayer mp;

    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(getBaseContext(), R.raw.magies);
        mp.setLooping(true);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.start();
        return 1;
    }

    public IBinder onUnBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
        stopSelf();
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
    }
}