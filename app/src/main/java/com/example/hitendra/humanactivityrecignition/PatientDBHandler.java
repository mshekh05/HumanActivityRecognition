package com.example.hitendra.humanactivityrecignition;

import android.content.Context;
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


    public static final String DATABASE_NAME = "ProductGroup29DB.db";
    public static final String TABLE_NAME = "ActivtyDatasetTable";
    public static int rowCount = 1;                // Should not be greater than 60
    public static int currentDataPointCount = 10;   // Should not be greater than 50


    //public static final String TABLE_PRODUCTS = "products";
    //public static final String COLUMN_ID = "_id";
    //public static final String COLUMN_PRODUCTNAME = "productname";
    protected Context context;
    public PatientDBHandler(Context context){
        this.context = context;
    }



    public void onCreateDB(Context context, String table_name ) {
        SQLiteDatabase dbhandler = null;
        try{
            dbhandler = context.openOrCreateDatabase( DATABASE_NAME,MODE_PRIVATE, null );
            dbhandler.beginTransaction();
            try{

                dbhandler.execSQL("CREATE TABLE IF NOT EXISTS "
                        + table_name + " "
                        + "("
                        + " time_stamp double PRIMARY KEY , "
                        + " x_value float, "  // later change to int
                        + " y_value float, "
                        + " z_value float ); " );

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

    public void retrieveFromDB(String dbNameWithPath, String tableName){

        //DataPoint[][] dataPoints = new DataPoint[3][10];
        Cursor cursor;
        SQLiteDatabase dbhandler = null;
        int i = 0;
        Double xValue = 0.0;
        Double yValue = 0.0;
        Double zValue = 0.0;
        Double tstamp = 0.0;

        try{
            dbhandler = SQLiteDatabase.openDatabase(dbNameWithPath, null, SQLiteDatabase.OPEN_READONLY);
            String query =  "SELECT * FROM (SELECT * FROM "+tableName+
                            " ORDER BY time_stamp " +
                            " DESC LIMIT 10) ORDER BY time_stamp;";



            cursor = dbhandler.rawQuery(query, null);
            int cnt = 0;


            if (cursor.moveToFirst()) {

                cnt = cursor.getCount();

                while (!cursor.isAfterLast()){
                    // get the data into array, or class variable
                    tstamp = ((cursor.getDouble(cursor.getColumnIndex("time_stamp")))/1000);

                    xValue = (double)(cursor.getFloat(cursor.getColumnIndex("x_value")));
                    yValue = (double)(cursor.getFloat(cursor.getColumnIndex("y_value")));
                    zValue = (double)(cursor.getFloat(cursor.getColumnIndex("z_value")));

//                    dataPoints[0][i] = new DataPoint(tstamp, xValue);
//                    dataPoints[1][i] = new DataPoint(tstamp, yValue);
//                    dataPoints[2][i] = new DataPoint(tstamp, zValue);
                    i++;
                    cursor.moveToNext();
                    cnt--;
                }
                cursor.close();
            }


        }catch (SQLException e){
            Toast.makeText( this , e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            while (i < 10){
//                dataPoints[0][i] = new DataPoint(tstamp, xValue++);
//                dataPoints[1][i] = new DataPoint(tstamp, yValue++);
//                dataPoints[2][i] = new DataPoint(tstamp, zValue++);
                tstamp++;
                i++;


            }
            dbhandler.close();
//            return dataPoints;
        }
    }

    public void OnInsertDB(Context context , String table_name, double timeStamp, float x, float y, float z) {
        SQLiteDatabase dbhandler = null;
        try{
            dbhandler = context.openOrCreateDatabase( DATABASE_NAME,MODE_PRIVATE, null );
            dbhandler.beginTransaction();



            try{

                dbhandler.execSQL( "insert into "
                        + table_name
                        + " (time_stamp , x_value, y_value, z_value) VALUES ("
                        + "'" + timeStamp +"', '" + x +"', '" + y +"', '" + z +"');"

                );
                dbhandler.setTransactionSuccessful(); //commit your changes.setTransactionSuccessful();

            }
            catch (SQLiteException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            finally {
                dbhandler.endTransaction();
                dbhandler.close();
            }
        }catch (SQLException e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    }




