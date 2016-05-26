package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends AppCompatActivity {
    Context context;
    Toolbar toolbar;
    FloatingActionButton fab;
    BottomNavigationBar bottomNavigationBar;
    RelativeLayout scenes, timeline, room;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();

        // Hide Title in ToolBar to make place for own
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Listener for changes made in BottomNavigationBar
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                switch(position) {
                    case 0:
                        scenes.setVisibility(View.INVISIBLE);
                        timeline.setVisibility(View.INVISIBLE);
                        room.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        timeline.setVisibility(View.INVISIBLE);
                        room.setVisibility(View.INVISIBLE);
                        scenes.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        scenes.setVisibility(View.INVISIBLE);
                        room.setVisibility(View.INVISIBLE);
                        timeline.setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }

        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayout roomContent = (LinearLayout) room.findViewById(R.id.rooms_content),
                     scenesContent = (LinearLayout) scenes.findViewById(R.id.scenes_content),
                     timelineContent = (LinearLayout) timeline.findViewById(R.id.timeline_content);

        // Beispiel. Iterieren durch alle Elemente und Informationen extrahieren

        for (int i = 0; i < sp.getInt("G_Array_len",0); i++) {
            String name = sp.getString("G" + i + "_name", null);
            String type = sp.getString("G" + i + "_itemtype", null);
            String id = "G"+i;
            boolean sensor = sp.getBoolean("G" + i + "_isSensor", false);
            View deviceview = createDeviceCard(name);
            deviceview.setOnClickListener(new DeviceOnClickListener(id,type) {
                @Override
                public void onClick(View v) {
                    switchToRoomConfig(type,id);
                    finish();
                }
            });
            roomContent.addView(deviceview);
        }
    }

    private void initializeVariables() {
        // Get and set actual context
        context = getApplicationContext();
        sp = getSharedPreferences("Rooms", Context.MODE_PRIVATE);

        // Find Content Views
        room = (RelativeLayout) findViewById(R.id.main_rooms);
        timeline = (RelativeLayout) findViewById(R.id.main_timeline);
        scenes = (RelativeLayout) findViewById(R.id.main_scenes);

        // Set toolbar and set it at ActionBar
        toolbar = (Toolbar) findViewById(R.id.room_toolbar);
        setSupportActionBar(toolbar);

        // Set BottomNavigationBar
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.room_bottom_navigation_bar);

        // Set Style, Color and add Items
        bottomNavigationBar.setFirstSelectedPosition(0);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomNavigationBar
                .setInActiveColor("#607D8B")
                .setBarBackgroundColor("#FFFFFF");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_home, "Rooms").setActiveColor("#4DD0E1"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_star, "Scenes").setActiveColor("#9FA8DA"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_play_clip, "TimeLine").setActiveColor("#AED581"))
                .initialise();

        // Set FloatingActionButton and set onClickListener to do something
        fab = (FloatingActionButton) findViewById(R.id.room_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                switchPage();
            }
        });

    }
    public void switchPage()
    {


        Intent intent = null;
        // Check BottomBar position and open corresponding Activity
        if (bottomNavigationBar.getCurrentSelectedPosition() == 0)
            intent = new Intent(this, Roomadd.class);
        else if (bottomNavigationBar.getCurrentSelectedPosition() == 1)
            intent = new Intent(this, ScenesActivity.class);
        else if (bottomNavigationBar.getCurrentSelectedPosition() == 2)
            intent = new Intent(this, TimelineActivity.class);

        if (intent != null)
            startActivity(intent);
        finish();
    }
    public void switchToRoomConfig(String type, String id)
    {
        Intent intent = null;
        if(type.equals("Lamp"))
        {
            intent = new Intent(this,RoomConfig_lamp.class);

        }
        else if(type.equals("TV"))
        {

        }
        else
        {

        }
        intent.putExtra("GID",id);
        startActivity(intent);
    }



    // ---------------- Methoden zum Darstellen von Informationen -------------
    public View createDeviceCard(String name, String type, boolean isSensor) {
        CardView card = new CardView(getApplicationContext());

        // Set Padding so different CardViews don't stick together
        card.setUseCompatPadding(true);

        // Add LinearLayout and TextViews to the Card
        card.addView(
                (createLinearLayout(
                        createTextView(name, "black", 15),
                        createTextView(type, "grey", 12),
                        createTextView(isSensor?"Sensor":"Actor", "grey", 12)
                )));

        return card;
    }
    public View createDeviceCard(String name) {
        CardView card = new CardView(getApplicationContext());
        card.setUseCompatPadding(true);
        card.addView(
                (createLinearLayout(
                        createTextView(name, "black", 15)
                )));

        return card;
    }

    public TextView createTextView(String text, String color, float size) {
        TextView textView = new TextView(getApplicationContext());

        textView.setPadding(convertToDp(200), convertToDp(60), convertToDp(200), convertToDp(60));

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
        textView.setTextColor(Color.parseColor(color));
        textView.setText(text);

        return textView;
    }

    public LinearLayout createLinearLayout(TextView... textViews) {
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());

        // Set linearLayout's orientation to vertical so the TextViews will be shown underneath each other
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.WHITE);

        for (TextView object : textViews)
            linearLayout.addView(object);

        return linearLayout;
    }

    public int convertToDp(int px) {
        return (int) (px / this.getApplicationContext().getResources().getDisplayMetrics().density);
    }


}
