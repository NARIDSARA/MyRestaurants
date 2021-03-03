package com.naridsara.myrestaurants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class FoodManagementActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_management);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Food management");
        setSupportActionBar(mToolbar);
    }

}
