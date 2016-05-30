package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddTimelineActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    String timelineId;
    Button saveButton;
    LinearLayout timeContent;
    LinearLayout bottomSceneContent;
    NumberPicker numberPicker;
    ArrayList<String> numbers, addedScenes;
    SharedPreferences timeline_sp, room_sp, scene_sp;

    String id;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_add);

        initializeVariables();

        getScenesContent(bottomSceneContent);

        for (int i = 0; i < numbers.size(); i++) {
            timeContent.addView(
                    createTimeElement(
                            Integer.parseInt(numbers.get(i)),
                            scene_sp.getString(addedScenes.get(i)+"_name", null)));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                saveButton.setEnabled(false);
                saveData();
                finish();
            }
        });

    }

    private void initializeVariables() {
        // Get and set actual context
        context = getApplicationContext();

        // Set toolbar and set it at ActionBar
        toolbar = (Toolbar) findViewById(R.id.add_timeline_toolbar);
        setSupportActionBar(toolbar);

        saveButton = (Button) findViewById(R.id.add_timeline_save);

        timeContent = (LinearLayout) findViewById(R.id.add_timeline_content);
        bottomSceneContent = (LinearLayout) findViewById(R.id.add_timeline_scene);

        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(120);

        numbers = new ArrayList<>();
        addedScenes = new ArrayList<>();

        scene_sp = getSharedPreferences("Scenes", Context.MODE_PRIVATE);
        room_sp = getSharedPreferences("Rooms", Context.MODE_PRIVATE);
        timeline_sp = getSharedPreferences("Timeline", Context.MODE_PRIVATE);

        Intent main = getIntent();
        Bundle extras = main.getExtras();
        if (extras != null) {
            timelineId = extras.getString("TID");
            numbers = new ArrayList<>(
                    Arrays.asList(
                            timeline_sp.getString(
                                    timelineId+"_times", null).split(",\\s*")));
            addedScenes = new ArrayList<>(
                    Arrays.asList(
                            timeline_sp.getString(
                                    timelineId+"_scenes", null).split(",\\s*")));
        }

    }

    private boolean dataexist()
    {
        return timeline_sp.getString("T"+id+"_name",null)!= null;
    }
    private boolean saveData()
    {
        int idnum = timeline_sp.getInt("T_Array_len",0);
        id = "T" + idnum;
        String times = "";
        String sceneIds = "";
        for (String time : numbers)
            times += time + ", ";
        for (String deviceId : addedScenes)
            sceneIds += deviceId + ", ";
        if(!dataexist())
        {
            SharedPreferences.Editor editor = timeline_sp.edit();
            editor.putInt("T_Array_len",++idnum);
            editor.putString(id+"_times", times);
            editor.putString(id+"_scenes", sceneIds);
            editor.apply();
            System.out.println(idnum);
            return true;
        }
        else
            return false;
    }

    private void getScenesContent(LinearLayout scenesContent) {
        for (int i = 0; i < scene_sp.getInt("S_Array_len",0); i++) {
            String deviceIds = scene_sp.getString("S"+i+"_devices", null);
            String description = "Changed Devices: ";
            if (deviceIds != null) {
                List<String> deviceId = Arrays.asList(deviceIds.split(",\\s*"));

                for (String id : deviceId) {
                    description += room_sp.getString(id + "_name", null) + ", ";
                }
            }
            String name = scene_sp.getString("S" + i + "_name", null);
            if(name!=null) {
                View sceneView = createSceneElement(name, description, "S"+i);
                scenesContent.addView(sceneView);
            }
        }
    }

    private LinearLayout createTimeElement(int minutes, String scenesDescription) {
        LinearLayout layoutElement = new LinearLayout(context);
        ImageView image = new ImageView(context);
        TextView time = new TextView(context);
        CardView sceneCard = new CardView(context);
        TextView cardContent = new TextView(context);

        // First create and design a LinearLayout
        layoutElement.setOrientation(LinearLayout.HORIZONTAL);
        layoutElement.setLayoutParams(
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0
                )
        );
        layoutElement.setGravity(Gravity.CENTER_VERTICAL);

        // Add the Time Container Image
        image.setLayoutParams(
                new LinearLayout.LayoutParams(
                        convertDpToPixel(80),
                        convertDpToPixel(80),
                        0
                )
        );
        image.setContentDescription("Time Container at" + minutes);
        image.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent));
        image.setImageResource(R.drawable.timeline_left_1);

        // Create A TextField time and position it over image
        LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(
                convertDpToPixel(80),
                ViewGroup.LayoutParams.MATCH_PARENT,
                0
        );
        timeParams.setMargins(convertDpToPixel(-80), 0, 0, 0);
        time.setLayoutParams(timeParams);
        time.setText(String.valueOf(minutes));
        time.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        time.setGravity(Gravity.CENTER);

        // Create a content CardView
        LinearLayout.LayoutParams sceneCardParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0
        );
        sceneCardParams.setMargins(convertDpToPixel(20), 0, convertDpToPixel(20), 0);
        sceneCard.setLayoutParams(sceneCardParams);
        sceneCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));


        cardContent.setLayoutParams(
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0
            )
        );
        cardContent.setPadding(
                convertDpToPixel(15),
                convertDpToPixel(15),
                convertDpToPixel(10),
                convertDpToPixel(15)
        );
        cardContent.setText(scenesDescription);

        sceneCard.addView(cardContent);
        layoutElement.addView(image);
        layoutElement.addView(time);
        layoutElement.addView(sceneCard);

        return layoutElement;
    }

    private CardView createSceneElement(String sceneName, String scenesDescription, String SID) {
        CardView sceneCard = new CardView(context);
        ScrollView scroller = new ScrollView(context);
        LinearLayout contentLayout = new LinearLayout(context);
        TextView name = new TextView(context);
        TextView description = new TextView(context);

        LinearLayout.LayoutParams sceneParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0
        );
        sceneParams.setMargins(
                convertDpToPixel(15),
                convertDpToPixel(15),
                convertDpToPixel(15),
                convertDpToPixel(15)
        );
        sceneCard.setLayoutParams(sceneParams);
        sceneCard.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));


        scroller.setLayoutParams(
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0
                )
        );

        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(
                convertDpToPixel(150),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0
        );
        contentParams.setMargins(
                convertDpToPixel(15),
                convertDpToPixel(15),
                convertDpToPixel(15),
                convertDpToPixel(15)
        );
        contentLayout.setLayoutParams(contentParams);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(
                convertDpToPixel(15),
                convertDpToPixel(15),
                convertDpToPixel(15),
                convertDpToPixel(15)
        );


        name.setText(String.valueOf(sceneName));
        name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);

        description.setText(String.valueOf(scenesDescription));
        description.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        contentLayout.addView(name);
        contentLayout.addView(description);
        scroller.addView(contentLayout);
        sceneCard.addView(scroller);

        contentLayout.setOnClickListener(new DeviceOnClickListener(SID, sceneName + " - " + scenesDescription) {
            @Override
            public void onClick(View v) {
                int inputNumber = numberPicker.getValue();

                for (int i = 0; i < numbers.size(); i++) {
                    if (inputNumber <= Integer.parseInt(numbers.get(i))) {
                        timeContent.addView(
                                createTimeElement(
                                        numberPicker.getValue(), type), i);
                        numbers.add(i, String.valueOf(inputNumber));
                        addedScenes.add(id);
                        return;
                    }
                }
                timeContent.addView(createTimeElement(numberPicker.getValue(), type));
                numbers.add(String.valueOf(inputNumber));
                addedScenes.add(id);
            }
        });

        return sceneCard;
    }

    public int convertDpToPixel(int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }

}
