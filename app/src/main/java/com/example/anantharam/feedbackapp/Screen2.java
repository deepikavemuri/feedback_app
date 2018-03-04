package com.example.anantharam.feedbackapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import static android.content.ContentValues.TAG;

/**
 * Created by anantharam on 3/3/18.
 */

public class Screen2 extends Activity {

    Button subbt;
    EditText et1, et2, et3;
    ListView listview;
    //  String[] data;
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        subbt = findViewById(R.id.subbt);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);

        subbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Config.isNetworkStatusAvailable(getApplicationContext())) {
                    new save().execute();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Screen2.this);
                    builder.setMessage("No Internet Connection").setTitle("Information");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }

            class save extends AsyncTask<String, Void, String> {
                protected void onPreExecute() {
                }

                protected String doInBackground(String... arg0) {
                    String name = et1.getText().toString();
                    String subject = et2.getText().toString();
                    String feedback = et3.getText().toString();


                    HttpHandler sh = new HttpHandler();

                    try {

                        String url = "http://123.176.47.87:3002/saveFeedback?student_name="+name+"&subject="+subject+"&feedback="+feedback;
                        String jsonStr = sh.makeServiceCall(url);

                        Log.e(TAG, "Response from url: " + jsonStr);
                        Log.e(TAG, "URL : " + url);
                        Log.e(TAG, "Got Response from url!");

                    } catch(Exception e){
                        return new String("Exception: " + e.getMessage());
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String result) {
                    Toast.makeText(Screen2.this, "Your feedback has been submitted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
