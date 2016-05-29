package group7.at.ac.univie.cosycon;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class AddTimelineActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Toolbar toolbar;
    LinearLayout timeContent;
    LinearLayout bottomSceneContent;
    NumberPicker numberPicker;
    ArrayList<Integer> numbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_add);

        initializeVariables();

        bottomSceneContent.addView(createSceneElement("Peter", "Das ist eine textuelle Beschreibung"));
        bottomSceneContent.addView(createSceneElement("fdhsdhg", "Das ist eine textuelle Beschreibung. Das ist eine textuelle Beschreibung"));
        bottomSceneContent.addView(createSceneElement("sdhfdsg", "Das ist eine textuelle Beschreibung"));

    }

    private void initializeVariables() {
        // Get and set actual context
        context = getApplicationContext();

        // Set toolbar and set it at ActionBar
        toolbar = (Toolbar) findViewById(R.id.add_timeline_toolbar);
        setSupportActionBar(toolbar);

        timeContent = (LinearLayout) findViewById(R.id.add_timeline_content);
        bottomSceneContent = (LinearLayout) findViewById(R.id.add_timeline_scene);

        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(120);

        numbers = new ArrayList<>();

    }

    public void onClick(View v) {
        int inputNumber = numberPicker.getValue();

        for (int i = 0; i < numbers.size(); i++) {
            if (inputNumber <= numbers.get(i)) {
                timeContent.addView(
                        createTimeElement(
                                numberPicker.getValue(), "Das ist eine textuelle Beschreibung"), i);
                numbers.add(i, inputNumber);
                return;
            }
        }

        timeContent.addView(createTimeElement(numberPicker.getValue(), "Das ist eine textuelle Beschreibung"));
        numbers.add(inputNumber);
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

    private CardView createSceneElement(String sceneName, String scenesDescription) {
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

        contentLayout.setOnClickListener(this);

        return sceneCard;
    }

    public int convertDpToPixel(int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }

}
