package com.naridsara.myrestaurants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class AddFoodActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Add food");
        setSupportActionBar(mToolbar);

    }

}
