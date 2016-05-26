package group7.at.ac.univie.cosycon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class RoomConfig_lamp extends AppCompatActivity {

    private SharedPreferences preferencessetting;
    SharedPreferences.Editor editor;
    TextView title;
    Switch powerbutton;
    Switch rotate, blink;
    SeekBar volume;
    String id = "", name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_config_lamp);

        initializeVariables();

        // get id which get passed by from mainactivity
        Intent main = getIntent();
        Bundle extras = main.getExtras();
        if (extras != null) {
            id = extras.getString("GID");
        }
        preferencessetting = getPreferences(0);
        editor = preferencessetting.edit();
        name = preferencessetting.getString(id+"_name",id);
        title.setText(name);
        ///////////// Power button
        setDefaultState();
        powerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean power = preferencessetting.getBoolean(id+"_power",false);
                editor.putBoolean(id+"_power",!power);
                editor.commit();
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean rotating = preferencessetting.getBoolean(id+"_rotate",false);
                editor.putBoolean(id+"_rotate",!rotating);
                editor.commit();
            }
        });

        ///////////Blink
        blink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something to blink
                boolean blinking = preferencessetting.getBoolean(id+"_blink",false);
                editor.putBoolean(id+"_blink",!blinking);
                editor.commit();
            }
        });

        ////////////////Rotate
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int vol = preferencessetting.getInt(id+"_volume",0);
                editor.putInt(id+"_volume",progress);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    private void initializeVariables()
    {
        powerbutton = (Switch) findViewById(R.id.powerbutton);
        rotate = (Switch)findViewById(R.id.rotate);
        blink = (Switch)findViewById(R.id.blink);
        volume = (SeekBar)findViewById(R.id.volume);
        title = (TextView)findViewById(R.id.title);
    }
    private void setDefaultState()
    {
        boolean power = preferencessetting.getBoolean(id+"_power",false);
        powerbutton.setChecked(power);
        boolean rotating = preferencessetting.getBoolean(id+"_rotate",false);
        rotate.setChecked(rotating);
        boolean blinking = preferencessetting.getBoolean(id+"_blink",false);
        blink.setChecked(blinking);
        int vol = preferencessetting.getInt(id+"_volume",0);
        volume.setProgress(vol);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
