package com.naridsara.myrestaurants;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteUpdate;

public class AddFoodTypeActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mEtFoodType;
    private Button mBtAddFoodType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_type);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Add food type");
        setSupportActionBar(mToolbar);

        mEtFoodType = (EditText) findViewById(R.id.etFoodType);
        mBtAddFoodType = (Button) findViewById(R.id.btAddFoodType);

        mBtAddFoodType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddFoodType();
            }
        });

    }

    private void dialogAddFoodType() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Add food type");
        dialog.setMessage("Are you type?");
        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addFoodType();
            }
        });
        dialog.show();
    }

    private void addFoodType() {
        String foodTypeName = mEtFoodType.getText().toString().trim();

        if (foodTypeName.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter food type name", Toast.LENGTH_LONG).show();
            return;
        }

        String sql = "INSERT INTO `foodtype`(`Food_Type_Name`) VALUES ('" + foodTypeName + "')";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteUpdate() {
                    @Override
                    public void onComplete() {
                        finish();
                        Toast.makeText(getBaseContext(), "Add food type success", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
