package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class RoomConfig_Music extends AppCompatActivity {

    private SharedPreferences preferencessetting;
    SharedPreferences.Editor editor;
    SeekBar volume;
    TextView volvalue,title;
    Button play,next,back,random,stop;
    String id ="",name;
    Button deletebutton,savebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_config__music);
        initializeVariables();

        // get id which get passed by from mainactivity
        Intent main = getIntent();
        Bundle extras = main.getExtras();
        if (extras != null) {
            id = extras.getString("GID");
        }
        name = preferencessetting.getString(id+"_name",id);
        title.setText(name);

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int vol = preferencessetting.getInt(id+"_volume",0);
                editor.putInt(id+"_volume",progress);
                volvalue.setText(String.valueOf(progress));
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove(id+"_name");
                editor.remove(id+"_itemtype");
                editor.commit();
                finish();
            }
        });
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void initializeVariables()
    {
        title = (TextView)findViewById(R.id.title);
        volvalue = (TextView)findViewById(R.id.volvalue);
        volume = (SeekBar)findViewById(R.id.volume);
        play = (Button)findViewById(R.id.playbutton);
        next = (Button)findViewById(R.id.nextbutton);
        back = (Button)findViewById(R.id.previousbutton);
        random = (Button)findViewById(R.id.randombutton);
        stop = (Button)findViewById(R.id.stop);
        deletebutton = (Button)findViewById(R.id.deletebutton);
        savebutton = (Button)findViewById(R.id.savebutton);
        preferencessetting = getSharedPreferences("Rooms", Context.MODE_PRIVATE);
        editor = preferencessetting.edit();
    }
}
