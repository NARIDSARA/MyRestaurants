package com.naridsara.myrestaurants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Toast;

public class OrderInfoActivity extends AppCompatActivity {

    private String orderId;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        orderId = getIntent().getStringExtra("orderId");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Order info");
        setSupportActionBar(mToolbar);

        Toast.makeText(getBaseContext(), "" + orderId, Toast.LENGTH_SHORT).show();
    }

}
