package com.example.hitendra.humanactivityrecignition;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Mohd on 10/2/2016.
 */

public class PatientDBHandler extends AppCompatActivity {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "ActivityGroup29DB.db";
    public static final String TABLE_NAME = "ActDataSet";
    public static int rowCount = 1;                // Should not be greater than 60
    public static int currentDataPointCount = 10;   // Should not be greater than 50

    protected Context context;
    public PatientDBHandler(Context context){
        this.context = context;
    }

    public void onCreateDB(Context context) {
        SQLiteDatabase dbhandler = null;
        try{
            dbhandler = context.openOrCreateDatabase( DATABASE_NAME,MODE_PRIVATE, null );
            dbhandler.beginTransaction();
            try{

                dbhandler.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + " ("+ " ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                        + "X_Val_1 float, "+"Y_Val_1 float, "+"Z_Val_1 float, "+"X_Val_2 float, "+"Y_Val_2 float, "+"Z_Val_2 float, "+"X_Val_3 float, "+"Y_Val_3 float, "+"Z_Val_3 float, "+"X_Val_4 float, "+"Y_Val_4 float, "+"Z_Val_4 float, "+"X_Val_5 float, "+"Y_Val_5 float, "+"Z_Val_5 float, "+"X_Val_6 float, "+"Y_Val_6 float, "+"Z_Val_6 float, "+"X_Val_7 float, "+"Y_Val_7 float, "+"Z_Val_7 float, "+"X_Val_8 float, "+"Y_Val_8 float, "+"Z_Val_8 float, "+"X_Val_9 float, "+"Y_Val_9 float, "+"Z_Val_9 float, "+"X_Val_10 float, "+"Y_Val_10 float, "+"Z_Val_10 float, "+"X_Val_11 float, "+"Y_Val_11 float, "+"Z_Val_11 float, "+"X_Val_12 float, "+"Y_Val_12 float, "+"Z_Val_12 float, "+"X_Val_13 float, "+"Y_Val_13 float, "+"Z_Val_13 float, "+"X_Val_14 float, "+"Y_Val_14 float, "+"Z_Val_14 float, "+"X_Val_15 float, "+"Y_Val_15 float, "+"Z_Val_15 float, "+"X_Val_16 float, "+"Y_Val_16 float, "+"Z_Val_16 float, "+"X_Val_17 float, "+"Y_Val_17 float, "+"Z_Val_17 float, "+"X_Val_18 float, "+"Y_Val_18 float, "+"Z_Val_18 float, "+"X_Val_19 float, "+"Y_Val_19 float, "+"Z_Val_19 float, "+"X_Val_20 float, "+"Y_Val_20 float, "+"Z_Val_20 float, "+"X_Val_21 float, "+"Y_Val_21 float, "+"Z_Val_21 float, "+"X_Val_22 float, "+"Y_Val_22 float, "+"Z_Val_22 float, "+"X_Val_23 float, "+"Y_Val_23 float, "+"Z_Val_23 float, "+"X_Val_24 float, "+"Y_Val_24 float, "+"Z_Val_24 float, "+"X_Val_25 float, "+"Y_Val_25 float, "+"Z_Val_25 float, "+"X_Val_26 float, "+"Y_Val_26 float, "+"Z_Val_26 float, "+"X_Val_27 float, "+"Y_Val_27 float, "+"Z_Val_27 float, "+"X_Val_28 float, "+"Y_Val_28 float, "+"Z_Val_28 float, "+"X_Val_29 float, "+"Y_Val_29 float, "+"Z_Val_29 float, "+"X_Val_30 float, "+"Y_Val_30 float, "+"Z_Val_30 float, "+"X_Val_31 float, "+"Y_Val_31 float, "+"Z_Val_31 float, "+"X_Val_32 float, "+"Y_Val_32 float, "+"Z_Val_32 float, "+"X_Val_33 float, "+"Y_Val_33 float, "+"Z_Val_33 float, "+"X_Val_34 float, "+"Y_Val_34 float, "+"Z_Val_34 float, "+"X_Val_35 float, "+"Y_Val_35 float, "+"Z_Val_35 float, "+"X_Val_36 float, "+"Y_Val_36 float, "+"Z_Val_36 float, "+"X_Val_37 float, "+"Y_Val_37 float, "+"Z_Val_37 float, "+"X_Val_38 float, "+"Y_Val_38 float, "+"Z_Val_38 float, "+"X_Val_39 float, "+"Y_Val_39 float, "+"Z_Val_39 float, "+"X_Val_40 float, "+"Y_Val_40 float, "+"Z_Val_40 float, "+"X_Val_41 float, "+"Y_Val_41 float, "+"Z_Val_41 float, "+"X_Val_42 float, "+"Y_Val_42 float, "+"Z_Val_42 float, "+"X_Val_43 float, "+"Y_Val_43 float, "+"Z_Val_43 float, "+"X_Val_44 float, "+"Y_Val_44 float, "+"Z_Val_44 float, "+"X_Val_45 float, "+"Y_Val_45 float, "+"Z_Val_45 float, "+"X_Val_46 float, "+"Y_Val_46 float, "+"Z_Val_46 float, "+"X_Val_47 float, "+"Y_Val_47 float, "+"Z_Val_47 float, "+"X_Val_48 float, "+"Y_Val_48 float, "+"Z_Val_48 float, "+"X_Val_49 float, "+"Y_Val_49 float, "+"Z_Val_49 float, "+"X_Val_50 float, "+"Y_Val_50 float, "+"Z_Val_50 float, "+"Activity_Label varchar(30)  ); "
                );

                dbhandler.setTransactionSuccessful();
            }
            catch (SQLiteException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            finally {
                dbhandler.endTransaction();
                dbhandler.close();
            }
        }catch (SQLException e){
            Toast.makeText( this , e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Returns 10 latest datapoints from DATABASE_NAME, and table tableName based on timeStamp
     * @param  dbNameWithPath = This should be full path to database
     * @param tableName
     * @return DataPoint , 10 records of datapoint
     */
//TODO : not used at the moment can be used for part 2

    public float[][] retrieveFromDB(String dbNameWithPath, String tableName){

        float data [][] = new float[60][151];
        float label = 0;
        Cursor cursor;
        SQLiteDatabase dbhandler = null;

        try{
            dbhandler = SQLiteDatabase.openDatabase(dbNameWithPath, null, SQLiteDatabase.OPEN_READONLY);
            String query =  "SELECT * FROM "+tableName+";";

            cursor = dbhandler.rawQuery(query, null);
            int rowCnt = 0;

            if (cursor.moveToFirst()) {

                int datapoint = 0;
                while (!cursor.isAfterLast()){

                    for(datapoint=0; datapoint<50; datapoint++){
                        String xColName = "X_Val_"+datapoint;
                        String yColName = "Y_Val_"+datapoint;
                        String zColName = "Z_Val_"+datapoint;

                        data[rowCnt][datapoint*3] = (cursor.getFloat(cursor.getColumnIndex(xColName)));
                        data[rowCnt][datapoint*3+1] = (cursor.getFloat(cursor.getColumnIndex(yColName)));
                        data[rowCnt][datapoint*3+2] = (cursor.getFloat(cursor.getColumnIndex(zColName)));
                        
                    }
                    String activity = (cursor.getString(cursor.getColumnIndex("Activity_Label")));

                    // Enter the label
                    if(activity.equals("running")){
                        data[rowCnt][datapoint] = 1;
                    }else if(activity.equals("walking")){
                        data[rowCnt][datapoint] = 2;
                    }else{
                        data[rowCnt][datapoint] = 3;
                    }

                    cursor.moveToNext();
                    rowCnt += 1;
                }
                cursor.close();
            }


        }catch (SQLException e){
            Toast.makeText( this , e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {

            dbhandler.close();
            return data;
        }
    }

    public void OnInsertDB(Context context , String activity_name) {
        SQLiteDatabase dbhandler = null;

        currentDataPointCount =1;
        try{
            dbhandler = context.openOrCreateDatabase( DATABASE_NAME,MODE_PRIVATE, null );
            dbhandler.beginTransaction();

            try{

                dbhandler.execSQL( "INSERT INTO "
                        + TABLE_NAME
                        + "( Activity_Label ) VALUES ('"
                        + activity_name + "');"

                );
                dbhandler.setTransactionSuccessful(); //commit your changes.setTransactionSuccessful();

            }
            catch (SQLiteException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            finally {
                dbhandler.endTransaction();
                dbhandler.close();
            }
        }catch (SQLException e){

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void OnUpdateDB(Context context , Float x_val, Float y_val , Float z_val) {
        SQLiteDatabase dbhandler = null;
        try{
            dbhandler = context.openOrCreateDatabase( DATABASE_NAME,MODE_PRIVATE, null );
            dbhandler.beginTransaction();

            try{

                dbhandler.execSQL( "UPDATE " + TABLE_NAME + " SET X_Val_"+currentDataPointCount+ " = " + x_val+ " , Y_Val_"+currentDataPointCount+ " = " + y_val+ ", Z_Val_"+currentDataPointCount+ " = " + z_val+" WHERE ID =  (Select Max(ID) from "+ TABLE_NAME + " ); "
                );
                dbhandler.setTransactionSuccessful(); //commit your changes.setTransactionSuccessful();

            }
            catch (SQLiteException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            finally {
                dbhandler.endTransaction();
                dbhandler.close();
            }
        }catch (SQLException e){

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    //    currentDataPointCount++;

        if (currentDataPointCount == 50) {
            MainActivity.stopService(context);
//            Toast.makeText(context, "stopped", Toast.LENGTH_LONG).show();
        }
        else {
            currentDataPointCount++;
        }
    }
}




