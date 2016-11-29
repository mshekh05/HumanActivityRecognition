package com.example.hitendra.humanactivityrecignition;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//Motion Sensor
// SQLITE Database handler and exception

public class MainActivity extends AppCompatActivity {

    private static Boolean activityRunning = false;
    final TextView status = null;
    TextView runningValue = null;
    TextView walkingValue = null;
    TextView EatingValue = null;

    public static final String LOG_TAG = "AndroidLibSvm";

    String appFolderPath;
    String systemPath;

    // link jni library
    static {
        System.loadLibrary("jnilibsvm");
    }

    // connect the native functions
    private native void jniSvmTrain(String cmd);
    private native void jniSvmPredict(String cmd);


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
//                    activityRunning = true;

                    PatientDB.OnInsertDB(view.getContext(),dropdown.getSelectedItem().toString());
                    status.setText("Capturing Data - DND");
                    startService(view);

                } // Make Sure to disable activtyRunning Once Data is Gathered
            }
        });


        //Libsvm code
//        systemPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
//        appFolderPath = systemPath+"libsvm/";
//
//        // 1. create necessary folder to save model files
//        CreateAppFolderIfNeed();
//        copyAssetsDataIfNeed();
//
//        // 2. assign model/output paths
//        String dataTrainPath = appFolderPath+"training_set ";
//        String dataPredictPath = appFolderPath+"training_set ";
//        String modelPath = appFolderPath+"model ";
//        String outputPath = appFolderPath+"predict ";
//
//        // 3. make SVM train
//        String svmTrainOptions = "-t 2 ";
//        jniSvmTrain(svmTrainOptions+dataTrainPath+modelPath);
//
//        // 4. make SVM predict
//        jniSvmPredict(dataPredictPath+modelPath+outputPath);
    }

    public static void startService(View view) {
        Intent startSenseService = new Intent(view.getContext(), Accelerometer.class);
        view.getContext().startService(startSenseService);
    }
    public static void stopService(Context context) {

        Intent startSenseService = new Intent(context, Accelerometer.class);

        context.stopService(startSenseService);


    }

//    /*
//    * Some utility functions
//    * */
//    private void CreateAppFolderIfNeed(){
//        // 1. create app folder if necessary
//        File folder = new File(appFolderPath);
//
//        if (!folder.exists()) {
//            folder.mkdir();
//            Log.d(LOG_TAG,"Appfolder is not existed, create one");
//        } else {
//            Log.w(LOG_TAG,"WARN: Appfolder has not been deleted");
//        }
//
//
//    }
//
//    private void copyAssetsDataIfNeed(){
//        String assetsToCopy[] = {"training_set_predict","training_set_train","training_set"};
//        //String targetPath[] = {C.systemPath+C.INPUT_FOLDER+C.INPUT_PREFIX+AudioConfigManager.inputConfigTrain+".wav", C.systemPath+C.INPUT_FOLDER+C.INPUT_PREFIX+AudioConfigManager.inputConfigPredict+".wav",C.systemPath+C.INPUT_FOLDER+"SomeoneLikeYouShort.mp3"};
//
//        for(int i=0; i<assetsToCopy.length; i++){
//            String from = assetsToCopy[i];
//            String to = appFolderPath+from;
//
//            // 1. check if file exist
//            File file = new File(to);
//            if(file.exists()){
//                Log.d(LOG_TAG, "copyAssetsDataIfNeed: file exist, no need to copy:"+from);
//            } else {
//                // do copy
//                boolean copyResult = copyAsset(getAssets(), from, to);
//                Log.d(LOG_TAG, "copyAssetsDataIfNeed: copy result = "+copyResult+" of file = "+from);
//            }
//        }
//    }
//
//    private boolean copyAsset(AssetManager assetManager, String fromAssetPath, String toPath) {
//        InputStream in = null;
//        OutputStream out = null;
//        try {
//            in = assetManager.open(fromAssetPath);
//            new File(toPath).createNewFile();
//            out = new FileOutputStream(toPath);
//            copyFile(in, out);
//            in.close();
//            in = null;
//            out.flush();
//            out.close();
//            out = null;
//            return true;
//        } catch(Exception e) {
//            e.printStackTrace();
//            Log.e(LOG_TAG, "[ERROR]: copyAsset: unable to copy file = "+fromAssetPath);
//            return false;
//        }
//    }
//
//    private void copyFile(InputStream in, OutputStream out) throws IOException {
//        byte[] buffer = new byte[1024];
//        int read;
//        while((read = in.read(buffer)) != -1){
//            out.write(buffer, 0, read);
//        }
//    }


}





