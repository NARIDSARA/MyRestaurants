package com.naridsara.myrestaurants;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;
import com.adedom.library.ExecuteUpdate;

import java.sql.ResultSet;
import java.util.ArrayList;

public class FoodManagementActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ArrayList<Food> items;
    private RecyclerView recyclerView;
    private FoodAdapter mAdapter = new FoodAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_management);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Food management");
        setSupportActionBar(mToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(mAdapter);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddFoodActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        fetchFoodAll();
    }

    private void fetchFoodAll() {
        String sql = "SELECT food.Food_ID, food.Food_Name, food.Food_NameUS, food.Food_Image, food.Food_Price, food.Food_Type_ID, foodtype.Food_Type_Name " +
                "FROM `food` INNER JOIN foodtype ON food.Food_Type_ID = foodtype.Food_Type_ID ORDER BY food.Created DESC";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            items = new ArrayList<Food>();
                            while (resultSet.next()) {
                                Food food = new Food(
                                        resultSet.getInt(1),
                                        resultSet.getString(2),
                                        resultSet.getString(3),
                                        resultSet.getString(4),
                                        resultSet.getInt(5),
                                        resultSet.getInt(6),
                                        resultSet.getString(7)
                                );
                                items.add(food);
                            }

                            mAdapter.setList(items);
                        } catch (Exception e) {
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_management, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.foodType:
                startActivity(new Intent(getBaseContext(), AddFoodTypeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {
        private ArrayList<Food> list = new ArrayList<>();

        @NonNull
        @Override
        public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_management, parent, false);
            return new FoodViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
            Food food = list.get(position);
            holder.tvFoodName.setText(food.getFood_Name());
            holder.tvFoodNameUS.setText(food.getFood_NameUS());
            holder.tvFoodPrice.setText("" + food.getFood_Price());
            holder.tvFoodType.setText(food.getFood_Type_Name());
            Dru.loadImage(holder.ivFoodImage, food.getFood_Image());

            holder.btFoodUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), UpdateFoodActivity.class);
                    intent.putExtra("foodId", food.getFood_ID());
                    intent.putExtra("foodName", food.getFood_Name());
                    intent.putExtra("foodNameUs", food.getFood_NameUS());
                    intent.putExtra("foodImage", food.getFood_Image());
                    intent.putExtra("foodPrice", food.getFood_Price());
                    intent.putExtra("foodTypeId", food.getFood_Type_ID());
                    startActivity(intent);
                }
            });

            holder.btFoodDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogRemoveFood(food.getFood_ID());
                }
            });
        }

        private void dialogRemoveFood(int foodId) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(FoodManagementActivity.this);
            dialog.setTitle("Remove food");
            dialog.setMessage("Are you sure?");
            dialog.setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialog.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    removeFood(foodId);
                }
            });
            dialog.show();
        }

        private void removeFood(int foodId) {
            String sql = "DELETE FROM `food` WHERE `food`.`Food_ID` = " + foodId;
            Dru.connection(ConnectDB.getConnection())
                    .execute(sql)
                    .commit(new ExecuteUpdate() {
                        @Override
                        public void onComplete() {
                            fetchFoodAll();
                            Toast.makeText(FoodManagementActivity.this, "Remove food success", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        void setList(ArrayList<Food> list) {
            this.list = list;
            notifyDataSetChanged();
        }

    }

    private class FoodViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivFoodImage;
        private final TextView tvFoodName;
        private final TextView tvFoodNameUS;
        private final TextView tvFoodPrice;
        private final TextView tvFoodType;
        private final Button btFoodUpdate;
        private final Button btFoodDelete;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoodImage = (ImageView) itemView.findViewById(R.id.ivFoodImage);
            tvFoodName = (TextView) itemView.findViewById(R.id.tvFoodName);
            tvFoodNameUS = (TextView) itemView.findViewById(R.id.tvFoodNameUS);
            tvFoodPrice = (TextView) itemView.findViewById(R.id.tvFoodPrice);
            tvFoodType = (TextView) itemView.findViewById(R.id.tvFoodType);
            btFoodUpdate = (Button) itemView.findViewById(R.id.btFoodUpdate);
            btFoodDelete = (Button) itemView.findViewById(R.id.btFoodDelete);
        }
    }

}
