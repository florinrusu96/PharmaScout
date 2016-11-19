package com.example.pangi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickFunc(View event){
        CheckBox myCheck = (CheckBox) findViewById(R.id.checkBox);

        if (myCheck.equals(event)){
            myCheck.setText("eurika");
        }
        else{
            if (myCheck.isChecked()) {
                myCheck.setText("1");
            }
            else {
                myCheck.setText("CheckBox");
            }
        }
    }
}
