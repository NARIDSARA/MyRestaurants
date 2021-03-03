package com.naridsara.myrestaurants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.adedom.library.ExecuteUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateFoodActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Spinner mSpinner;
    private EditText mEtFoodName;
    private EditText mEtFoodNameUs;
    private EditText mEtFoodImage;
    private EditText mEtFoodPrice;
    private Button mBtUpdateFood;

    private int foodId;
    private int foodTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        foodId = getIntent().getIntExtra("foodId", 0);
        String foodName = getIntent().getStringExtra("foodName");
        String foodNameUs = getIntent().getStringExtra("foodNameUs");
        String foodImage = getIntent().getStringExtra("foodImage");
        int foodPrice = getIntent().getIntExtra("foodPrice", 0);
        foodTypeId = getIntent().getIntExtra("foodTypeId", 0);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Update food");
        setSupportActionBar(mToolbar);

        mEtFoodName = (EditText) findViewById(R.id.etFoodName);
        mEtFoodNameUs = (EditText) findViewById(R.id.etFoodNameUs);
        mEtFoodImage = (EditText) findViewById(R.id.etFoodImage);
        mEtFoodPrice = (EditText) findViewById(R.id.etFoodPrice);
        mBtUpdateFood = (Button) findViewById(R.id.btUpdateFood);

        mEtFoodName.setText(foodName);
        mEtFoodNameUs.setText(foodNameUs);
        mEtFoodImage.setText(foodImage);
        mEtFoodPrice.setText("" + foodPrice);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FoodType foodType = (FoodType) parent.getItemAtPosition(position);
                foodTypeId = foodType.getFood_Type_ID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fetchFoodType();

        mBtUpdateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddFood();
            }
        });
    }

    private void fetchFoodType() {
        String sql = "SELECT * FROM `foodtype` ORDER BY Food_Type_ID DESC";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            ArrayList<FoodType> items = new ArrayList<FoodType>();
                            while (resultSet.next()) {
                                FoodType foodType = new FoodType(
                                        resultSet.getInt(1),
                                        resultSet.getString(2)
                                );
                                items.add(foodType);
                            }

                            mSpinner.setAdapter(new FoodTypeAdapter(getBaseContext(), items));
                            for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).getFood_Type_ID() == foodTypeId) {
                                    mSpinner.setSelection(i);
                                }
                            }

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
    }

    private void dialogAddFood() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Update food");
        dialog.setMessage("Are you sure");
        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateFood();
            }
        });
        dialog.show();
    }

    private void updateFood() {
        String foodName = mEtFoodName.getText().toString().trim();
        String foodNameUs = mEtFoodNameUs.getText().toString().trim();
        String foodImage = mEtFoodImage.getText().toString().trim();
        String foodPrice = mEtFoodPrice.getText().toString().trim();

        if (foodName.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter food name", Toast.LENGTH_LONG).show();
            return;
        }

        String sql = "UPDATE `food` SET `Food_Name`='" + foodName + "', `Food_NameUS`='" + foodNameUs + "' ,`Food_Image`='" + foodImage + "' ,`Food_Price`=" + foodPrice + " ,`Food_Type_ID`= " + foodTypeId + " WHERE Food_ID = " + foodId;
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteUpdate() {
                    @Override
                    public void onComplete() {
                        finish();
                        Toast.makeText(getBaseContext(), "Update food success", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class FoodTypeAdapter extends ArrayAdapter<FoodType> {

        public FoodTypeAdapter(Context context, ArrayList<FoodType> foodTypes) {
            super(context, 0, foodTypes);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        private View initView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            TextView textViewName = convertView.findViewById(android.R.id.text1);
            FoodType item = getItem(position);
            if (item != null) {
                textViewName.setText(item.getFood_Type_Name());
            }
            return convertView;
        }
    }

}
