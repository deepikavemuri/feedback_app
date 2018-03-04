package com.example.anantharam.feedbackapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FirstPage extends AppCompatActivity {

    ImageButton ibt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        ibt1 = findViewById(R.id.ibt1);

        ibt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FirstPage.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
