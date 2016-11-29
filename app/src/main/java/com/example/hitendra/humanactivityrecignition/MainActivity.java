package com.example.hitendra.humanactivityrecignition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
    final TextView status = null;
    TextView runningValue = null;
    TextView walkingValue = null;
    TextView EatingValue = null;



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


        final TextView status = (TextView) findViewById(R.id.statusValue);
        TextView runningValue = (TextView) findViewById(R.id.runningValue);
        TextView walkingValue = (TextView) findViewById(R.id.WalkingValue);
        TextView EatingValue = (TextView) findViewById(R.id.EatingValue);

        walkingValue.setText("" + PatientDBHandler.rowCount);
        runningValue.setText("" + PatientDBHandler.currentDataPointCount);

        Button start = (Button) findViewById(R.id.button);
        Button file = (Button) findViewById(R.id.filegenerate);

        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/libsvm/"+"training_set";
                PatientDBHandler.writeToFile(filename, PatientDBHandler.retrieveFromDB(PatientDBHandler.DATABASE_NAME, PatientDBHandler.TABLE_NAME));
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activityRunning) {
                    activityRunning = true;

                    PatientDB.OnInsertDB(view.getContext(),dropdown.getSelectedItem().toString());
                    status.setText("Capturing Data - DND");
                    startService(view);

                } // Make Sure to disable activtyRunning Once Data is Gathered
            }
        });
    }

    public static void startService(View view) {
        Intent startSenseService = new Intent(view.getContext(), Accelerometer.class);
        view.getContext().startService(startSenseService);
    }
    public static void stopService(Context context) {

        Intent startSenseService = new Intent(context, Accelerometer.class);

        context.stopService(startSenseService);


    }


}





