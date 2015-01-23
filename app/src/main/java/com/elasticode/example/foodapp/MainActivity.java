package com.elasticode.example.foodapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elasticode.ElastiCode;


public class MainActivity extends Activity {
    // hold the food types categories
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listView);

        // Defined Array values to show in ListView
        String[] values = (String[])ElastiCode.valueForDynamicObject("FoodTypes");

        RowArrayAdapter adapter = new RowArrayAdapter(this, values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        itemValue + " selected", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        // Take a snap show of the screen after finish loading
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.mainLayout);
        doAfterLayout(layout, new Runnable() {
            @Override
            public void run() {
                ElastiCode.takeSnapShot("FoodTypes", MainActivity.this);
            }
        });
    }


    /**
     * Simple array adapter to show the values that we got
     */
    public class RowArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public RowArrayAdapter(Context context, String[] values) {
            super(context, R.layout.row, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if(rowView == null){
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.row, parent, false);
            }

            TextView textView = (TextView) rowView.findViewById(R.id.rowLabel);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.rowIcon);
            textView.setText(values[position]);

            // change the icon for relevant food type
            String s = values[position];
            if (s.contains("Healthy")) {
                imageView.setImageResource(R.drawable.row_apple);
            } else if (s.contains("Pizza")) {
                imageView.setImageResource(R.drawable.row_pizza);
            } else if (s.contains("coffee")) {
                imageView.setImageResource(R.drawable.row_coffee);
            }else if (s.contains("Asian")) {
                imageView.setImageResource(R.drawable.row_asian);
            } else if (s.contains("BBQ")) {
                imageView.setImageResource(R.drawable.row_chicken);
            } else {
                imageView.setImageResource(R.drawable.row_food);
            }

            // Some color difference, so the rows to look nicer
            if(position % 2 == 0){
                rowView.setBackgroundColor(Color.rgb(46,46,46));
            }
            else{
                rowView.setBackgroundColor(Color.rgb(59,59,59));
            }
            return rowView;
        }
    }

    /**
     * Runs a piece of code after the next layout run
     */
    public static void doAfterLayout(final View view,final Runnable runnable){
        final ViewTreeObserver.OnGlobalLayoutListener listener=new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override public void onGlobalLayout(){
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                runnable.run();
            }
        };

        view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

}
