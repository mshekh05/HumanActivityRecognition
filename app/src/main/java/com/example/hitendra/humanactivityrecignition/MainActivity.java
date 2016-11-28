package com.example.hitendra.humanactivityrecignition;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

//Motion Sensor
// SQLITE Database handler and exception

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public static void startService(View view , String table_name) {

        Intent startSenseService = new Intent(view.getContext(), Accelerometer.class);
        Bundle b = new Bundle();
        b.putString("Tablename", table_name);
        startSenseService.putExtras(b);
        view.getContext().startService(startSenseService);


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
