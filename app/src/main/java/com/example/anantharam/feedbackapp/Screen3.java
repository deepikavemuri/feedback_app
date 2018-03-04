package com.example.anantharam.feedbackapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;


public class Screen3 extends AppCompatActivity {
    ListView listview;
    //String[] data;
    String data= "";
    boolean flag = false;
    //Button b;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen3);

        textview = findViewById(R.id.tv);

        if (Config.isNetworkStatusAvailable(getApplicationContext())) {
            new getTweets().execute();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Screen3.this);
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

    public class getTweets extends AsyncTask<Void, Void, Void> {

        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(Screen3.this);
            dialog.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String url = "http://123.176.47.87:3002/retrieveFeedbacks";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            Log.e(TAG, "URL : " + url);
            Log.e(TAG, "Got Response from url!");

            if (!jsonStr.equals("Nothing")) {
                try {
                    JSONObject x = new JSONObject(jsonStr);
                    JSONArray y =  x.getJSONArray("feedbacks");
                    for(int i = 0; i < y.length(); i++){
                        JSONObject z = y.getJSONObject(i);
                        data += z.getString("name")+" ";
                        data+= z.getString("subject")+" ";
                        data += z.getString("feedback`")+"\n";

                    }



                } catch (final JSONException e) {
                    //    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    flag = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                //  Log.v(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.dismiss();
            textview.setText(data, TextView.BufferType.EDITABLE);
        }
    }
}