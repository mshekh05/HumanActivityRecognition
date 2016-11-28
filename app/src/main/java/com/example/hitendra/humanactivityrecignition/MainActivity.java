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

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Running", "Walking", "Eating"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        Button start = (Button) findViewById(R.id.button);
        final TextView status = (TextView) findViewById(R.id.statusValue);
        TextView runningValue = (TextView) findViewById(R.id.runningValue);
        TextView walkingValue = (TextView) findViewById(R.id.WalkingValue);
        TextView EatingValue = (TextView) findViewById(R.id.EatingValue);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!activityRunning) {
                    activityRunning = true;
                    // Do Some Task
                    // Start Sensor
                    // Register in DataBase
                    status.setText("Capturing Data");
                    startService();
                } // Make Sure to disable activtyRunning Once Data is Gathered
            }
        });

    }

    public static void startService(View view , String table_name) {

        Intent startSenseService = new Intent(view.getContext(), Accelerometer.class);
        Bundle b = new Bundle();
        b.putString("Tablename", table_name);
        startSenseService.putExtras(b);
        view.getContext().startService(startSenseService);


    }

    public static int getCount(String Activity){
        return 0;
    }




    private void processDownloadClick() {

        final DownloadTask downloadTask = new DownloadTask(MainActivity.this);
        downloadTask.execute("https://impact.asu.edu/CSE535Fall16Folder/ProductGroup29DB.db", "ProductGroup29DB.db");
        Intent stopSenseService = new Intent(MainActivity.this, Accelerometer.class);
        stopService(stopSenseService);

//        started = 0;
//        timerHandler.removeCallbacks(timerRunnable);
//        //graph.setVisibility(View.GONE);
//        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                downloadTask.cancel(true);
//            }
//        });
    }

    private void processUploadClick() {

        final UploadTask UploadTask = new UploadTask(MainActivity.this);
        UploadTask.execute("https://impact.asu.edu/CSE535Fall16Folder/UploadToServer.php" , "ProductGroup29DB.db");

//        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                UploadTask.cancel(true);
//            }
//        });
    }
}
