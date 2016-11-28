package com.example.hitendra.humanactivityrecignition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

//Motion Sensor
// SQLITE Database handler and exception

public class MainActivity extends AppCompatActivity {

    private static Boolean activityRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PatientDBHandler PatientDB = new PatientDBHandler(this);
        PatientDB.onCreateDB(this);

        final Spinner dropdown = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[]{"Running", "Walking", "Eating"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        Button start = (Button) findViewById(R.id.button);
        final TextView status = (TextView) findViewById(R.id.statusValue);
        TextView runningValue = (TextView) findViewById(R.id.runningValue);
        TextView walkingValue = (TextView) findViewById(R.id.WalkingValue);
        TextView EatingValue = (TextView) findViewById(R.id.EatingValue);

        walkingValue.setText("" + PatientDBHandler.rowCount);
        runningValue.setText("" + PatientDBHandler.currentDataPointCount);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activityRunning) {
                    activityRunning = true;
                    // Do Some Task
                    // Start Sensor
                    // Register in DataBase
                    PatientDB.OnInsertDB(view.getContext(),dropdown.getSelectedItem().toString());
                    status.setText("Capturing Data");


                    startService(view);
                } // Make Sure to disable activtyRunning Once Data is Gathered
            }
        });

    }

    public static void startService(View view) {

        Intent startSenseService = new Intent(view.getContext(), Accelerometer.class);

        view.getContext().startService(startSenseService);


    }

    public static int getCount(String Activity) {
        return 0;
    }

}



