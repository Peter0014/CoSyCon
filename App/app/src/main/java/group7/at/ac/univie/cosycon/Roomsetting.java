package group7.at.ac.univie.cosycon;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

public class Roomsetting extends AppCompatActivity {

    Button powerbutton, applybutton;
    Switch rotate, blink, esaving;
    SeekBar volume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomsetting);

        initializeVariables();

        // get id which get passed by
        int id = 0;//Integer.parseInt(getIntent().getStringExtra(MainActivity.class));
        G object = getG(id);
        powerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void initializeVariables()
    {
        powerbutton = (Button)findViewById(R.id.powerbutton);
        applybutton = (Button)findViewById(R.id.applybutton);
        rotate = (Switch)findViewById(R.id.switch1);
        blink = (Switch)findViewById(R.id.switch2);
        esaving =(Switch)findViewById(R.id.switch3);
        volume = (SeekBar)findViewById(R.id.volume);
    }
    private G getG(int id)
    {
        return null;
    }
    public void switchpage()
    {
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }
    public void warn(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void success(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        switchpage();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
