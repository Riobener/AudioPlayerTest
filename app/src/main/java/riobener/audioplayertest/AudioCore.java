package riobener.audioplayertest;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class AudioCore {
    MediaPlayer audio;
    Uri songPath;
    AudioCore(){
        audio = new MediaPlayer();
        audio.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }
    public void setupSong(String path, Context context){
        songPath=Uri.parse(path);
        try {
            audio.setDataSource(context,songPath);
            audio.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void playMusic(){
        audio.start();
    }
    public void pauseMusic(){
        audio.pause();
    }
    public void musicReset(){
        audio.reset();
    }

}
