package riobener.audioplayertest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    int playPauseImage = 0;
    ImageButton play;
    ImageButton next;
    ImageButton back;
    String filePath;
    Button songButton;
    TextView pathName;
    AudioCore audioCore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
        }
        audioCore = new AudioCore();
        play = (ImageButton) findViewById(R.id.play);
        songButton = (Button)findViewById(R.id.fileChooserButton);
        pathName = (TextView)findViewById(R.id.pathName);

        songButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filePath!=null&&playPauseImage==0){
                    audioCore.playMusic();
                    playPauseImage=1;
                    play.setImageResource(R.drawable.pause);
                }else if(playPauseImage!=0){
                    play.setImageResource(R.drawable.play);
                    playPauseImage=0;
                    audioCore.pauseMusic();
                }
            }
        });

    }
    private void openFilePicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1000)
                .withFilter(Pattern.compile(".*\\.mp3$"))
                .withFilterDirectories(false)
                .withHiddenFiles(true)
                .start();
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            if(filePath!=null){
            audioCore.musicReset();
            play.setImageResource(R.drawable.play);
            playPauseImage=0;
            }
            filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

            // Do anything with file
            audioCore.setupSong(filePath, getApplicationContext());
            pathName.setText("" + filePath);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch(requestCode){
           case 1001:{
               if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   Toast.makeText(this,"Permission granted!",Toast.LENGTH_LONG).show();

               }else{
                   Toast.makeText(this,"Permission not granted!",Toast.LENGTH_LONG).show();
                   finish();
               }
           }

       }
    }
}







