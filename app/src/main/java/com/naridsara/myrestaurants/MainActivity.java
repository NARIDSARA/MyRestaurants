package com.naridsara.myrestaurants;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.adedom.library.Dru;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ConnectDB.getConnection() == null) {
            Dru.failed(getBaseContext());
        } else {
            Dru.completed(getBaseContext());
        }
    }

}
