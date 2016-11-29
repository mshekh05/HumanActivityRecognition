package com.example.hitendra.humanactivityrecignition;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Date;
//import android.

/**
 * Created by mohseenmukaddam on 10/2/16.
 */
public class Accelerometer extends Service implements SensorEventListener {
    /**
     * Sensor Members
     */
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long timeElapsed = 0;
    private long startTime= 0;
    private long lastUpdate = 0;
//    Bundle b;
//    String table_name;

    @Override
    public IBinder onBind(Intent intent) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Accelerometer Started", Toast.LENGTH_SHORT).show();
        this.sensorInit();
    }

    protected void sensorInit(){
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //rate is used in this function
        this.sensorManager.registerListener(this, accelerometer, 10000);// Sampling frequency is 10hz
        startTime = System.currentTimeMillis();
    }

    /**
     * This is the event handler for sensor changes
     * @param event : event fed from the android sensor
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor healthMonitorSensor = event.sensor;
        float x, y, z;
        long timeStamp, delta;

        timeElapsed = System.currentTimeMillis() - startTime;
        if(timeElapsed > 5000){
            // Kick off Accerelations and update values
          //  stopSensing();
        }
        //implementing only for accelerometer
        if(healthMonitorSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

//            timeStamp = System.currentTimeMillis();
//            delta = timeStamp - lastUpdate;
//            if(delta > 100){
//                lastUpdate = timeStamp;
//                //Toast.makeText(this, " @: " + x + " : " + y + " : " + z , Toast.LENGTH_LONG).show();
//            }

            PatientDBHandler PatientDB = new PatientDBHandler(this);
            PatientDB.OnUpdateDB(this, x, y, z);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onDestroy() {
        this.sensorManager.unregisterListener(this);
        Toast.makeText(this, "Accelerometer stopped ", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void stopSensing(){
        Intent stopSenseService = new Intent(getApplicationContext(), Accelerometer.class);
        stopService(stopSenseService);
       // Toast.makeText(this, "stopped", Toast.LENGTH_LONG).show();
    }
}
