package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ScenesActivity extends AppCompatActivity {

    private SharedPreferences scene_sp;
    SharedPreferences room_sp;
    String sceneId;
    EditText name;
    Button savebutton;
    Context context;
    Toolbar toolbar;
    LinearLayout devices;
    ArrayList<String> changedDevices;

    String id;
    private long mLastClickTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenes_add);

        initializeVariables();

        showRoomContent(devices);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                savebutton.setEnabled(false);
                if(name.getText().toString().trim().length()<=0)
                {
                    warn("adding requires name");
                }
                else
                {

                    if(savedata())
                    {
                        savebutton.setEnabled(true);
                        finish();
                    }

                    else
                    {
                        savebutton.setEnabled(true);
                        warn("this serial id is already exist. type in a new one");
                    }
                }

            }
        });

    }

    private void initializeVariables() {
        // Get and set actual context
        context = getApplicationContext();

        // Set toolbar and set it at ActionBar
        toolbar = (Toolbar) findViewById(R.id.scenes_toolbar);
        setSupportActionBar(toolbar);

        savebutton = (Button)findViewById(R.id.savebutton);
        name = (EditText)findViewById(R.id.name);
        scene_sp = getSharedPreferences("Scenes", Context.MODE_PRIVATE);
        room_sp = getSharedPreferences("Rooms", Context.MODE_PRIVATE);
        devices = (LinearLayout) findViewById(R.id.add_scene_devices);

        Intent main = getIntent();
        Bundle extras = main.getExtras();
        changedDevices = new ArrayList<>();
        if (extras != null) {
            sceneId = extras.getString("SID");
            changedDevices = new ArrayList<>(
                    Arrays.asList(
                            scene_sp.getString(
                                    sceneId+"_devices", null).split(",\\s*")));
            name.setText(scene_sp.getString(sceneId+"_name", null));
        }

    }
    private boolean dataexist()
    {
        return scene_sp.getString("S"+id+"_name",null)!= null;
    }
    private boolean savedata()
    {
        int idnum = scene_sp.getInt("S_Array_len",0);
        id = "S" + idnum;
        String deviceIds = "";
        for (String deviceId : changedDevices)
            deviceIds += deviceId + ", ";
        if(!dataexist())
        {
            SharedPreferences.Editor editor = scene_sp.edit();
            editor.putString(id+"_name",name.getText().toString());
            editor.putInt("S_Array_len",++idnum);
            editor.putString(id+"_devices",deviceIds);
            editor.apply();
            return true;
        }
        else
            return false;
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
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showRoomContent (LinearLayout roomContent) {
        LinearLayout horizLayout = null;
        int success = 0;

        for (int i = 0; i < room_sp.getInt("G_Array_len",0); i++) {
            String name = room_sp.getString("G" + i + "_name", null);
            if(name!=null) {
                String type = room_sp.getString("G" + i + "_itemtype", null);
                String id = "G"+i;
                View deviceView = createDeviceCard(name, type, true);
                if (changedDevices.contains(id))
                    deviceView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                deviceView.setOnClickListener(new DeviceOnClickListener(id,type) {
                    @Override
                    public void onClick(View v) {
                        switchToRoomConfig(type,id);
                        v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        changedDevices.add(id);
                    }
                });
                if (success % 2 == 0){
                    horizLayout = createLinearLayout(false);
                    roomContent.addView(horizLayout);
                }
                horizLayout.addView(deviceView);
                success++;
            }
        }
    }

    private void switchToRoomConfig(String type, String id)
    {
        Intent intent = null;
        if(type.equals("Lamp"))
            intent = new Intent(this,RoomConfig_lamp.class);
        else if(type.equals("TV"))
            intent = new Intent(this,Roomconfig_TV.class);
        else if(type.equals("Music"))
            intent = new Intent(this,RoomConfig_Music.class);

        intent.putExtra("GID",id);
        startActivity(intent);
    }

    private View createDeviceCard(String name, String type, boolean size) {
        CardView card = new CardView(getApplicationContext());

        card.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        // Add LinearLayout and TextViews to the Card
        if (type.equals("")) {
            card.addView(
                    createLinearLayout(
                            true,
                            createTextView(name, "black", 10)
                    )
            );
        } else {
            card.addView(
                    createLinearLayout(
                            true,
                            createTextView(name, "black", 10),
                            createImageView(type),
                            createTextView(type, "black", 8)
                    )
            );
        }

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        size ? LinearLayout.LayoutParams.WRAP_CONTENT : LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        0
                );
        cardParams.setMargins(
                convertDpToPixel(30),
                convertDpToPixel(5),
                convertDpToPixel(30),
                convertDpToPixel(5)
        );

        card.setLayoutParams(cardParams);

        return card;
    }

    private ImageView createImageView(String type) {
        ImageView imageView = new ImageView(context);

        if(type.equals("Lamp"))
            imageView.setImageResource(R.drawable.lamp_hue);
        else if(type.equals("TV"))
            imageView.setImageResource(R.drawable.tv);
        else if(type.equals("Music"))
            imageView.setImageResource(R.drawable.music_hifi);
        else
            imageView.setImageResource(R.drawable.not_found);

        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        convertDpToPixel(80),
                        convertDpToPixel(80),
                        0
                )
        );

        return imageView;
    }

    private TextView createTextView(String text, String color, float size) {
        TextView textView = new TextView(getApplicationContext());

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
        textView.setTextColor(Color.parseColor(color));
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

    private LinearLayout createLinearLayout(boolean vertHor, View... Views) {
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());

        linearLayout.setPadding(
                convertDpToPixel(10),
                convertDpToPixel(10),
                convertDpToPixel(10),
                convertDpToPixel(10)
        );

        // Set linearLayout's orientation to vertical so the TextViews will be shown underneath each other
        linearLayout.setOrientation(vertHor ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);

        for (View object : Views)
            linearLayout.addView(object);

        return linearLayout;
    }

    public int convertDpToPixel(int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }

}
