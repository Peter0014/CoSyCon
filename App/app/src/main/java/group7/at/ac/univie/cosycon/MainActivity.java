package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
    SharedPreferences room_sp, scene_sp;


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
        LinearLayout
                roomContent = (LinearLayout) room.findViewById(R.id.rooms_content),
                scenesContent = (LinearLayout) scenes.findViewById(R.id.scenes_content),
                timelineContent = (LinearLayout) timeline.findViewById(R.id.timeline_content);

        // Beispiel. Iterieren durch alle Elemente und Informationen extrahieren

        roomContent.removeAllViewsInLayout();
        scenesContent.removeAllViewsInLayout();
        timelineContent.removeAllViewsInLayout();

        showRoomContent(roomContent);
        showScenesContent(scenesContent);
    }

    private void initializeVariables() {
        // Get and set actual context
        context = getApplicationContext();
        room_sp = getSharedPreferences("Rooms", Context.MODE_PRIVATE);
        scene_sp = getSharedPreferences("Scenes", Context.MODE_PRIVATE);

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

    private void switchPage() {
        Intent intent = null;
        // Check BottomBar position and open corresponding Activity
        if (bottomNavigationBar.getCurrentSelectedPosition() == 0)
            intent = new Intent(this, Roomadd.class);
        else if (bottomNavigationBar.getCurrentSelectedPosition() == 1)
            intent = new Intent(this, ScenesActivity.class);
        else if (bottomNavigationBar.getCurrentSelectedPosition() == 2)
            intent = new Intent(this, AddTimelineActivity.class);

        if (intent != null)
            startActivity(intent);
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
    public void switchToSceneConfig(String id)
    {
        Intent intent = new Intent(this,SceneConfig.class);
        intent.putExtra("SID",id);
        startActivity(intent);
    }

    private void showRoomContent (LinearLayout roomContent) {
        LinearLayout horizLayout = null;
        int success = 0;

        for (int i = 0; i < room_sp.getInt("G_Array_len",0); i++) {
            String name = room_sp.getString("G" + i + "_name", null);
            if(name!=null) {
                String type = room_sp.getString("G" + i + "_itemtype", null);
                String id = "G"+i;
                View deviceview = createDeviceCard(name, type, true);
                deviceview.setOnClickListener(new DeviceOnClickListener(id,type) {
                    @Override
                    public void onClick(View v) {
                        switchToRoomConfig(type,id);
                    }
                });
                if (success % 2 == 0){
                    horizLayout = createLinearLayout(false);
                    roomContent.addView(horizLayout);
                }
                horizLayout.addView(deviceview);
                success++;
            }
        }
    }


    private void showScenesContent(LinearLayout scenesContent) {
        LinearLayout horizLayout = null;
        int success = 0;
        for (int i = 0; i < scene_sp.getInt("S_Array_len",0); i++) {
            String name = scene_sp.getString("S" + i + "_name", null);
            if(name!=null) {
                String id = "S"+i;
                View sceneView = createDeviceCard(name, false);
                sceneView.setOnClickListener(new DeviceOnClickListener(id) {
                    @Override
                    public void onClick(View v) {
                        switchToSceneConfig(id);
                    }
                });
                if (success % 2 == 0){
                    horizLayout = createLinearLayout(false);
                    scenesContent.addView(horizLayout);
                }
                horizLayout.addView(sceneView);
                success++;
            }
        }
    }

    // ---------------- Methoden zum Darstellen von Informationen -------------
    private View createDeviceCard(String name, String type, boolean size) {
        CardView card = new CardView(getApplicationContext());

        card.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        // Add LinearLayout and TextViews to the Card
        card.addView(
                createLinearLayout(
                        true,
                        createTextView(name, "black", 10),
                        createImageView(type),
                        createTextView(type, "grey", 8)
                )
        );

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
    private View createDeviceCard(String name, boolean size) {
        CardView card = new CardView(getApplicationContext());

        card.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        // Add LinearLayout and TextViews to the Card
        card.addView(
                createLinearLayout(
                        true,
                        createTextView(name, "black", 10)
                )
        );

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
