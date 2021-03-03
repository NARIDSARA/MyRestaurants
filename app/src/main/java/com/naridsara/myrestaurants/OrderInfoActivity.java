package com.naridsara.myrestaurants;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderInfoActivity extends AppCompatActivity {

    private String orderId;
    private ArrayList<OrderDetail> orderDetails = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderInfoAdapter mAdapter = new OrderInfoAdapter();
    private TextView tvFoodPriceTotal;
    private TextView tvCartCountSum;
    private Toolbar mToolbar;
    private Button mBtCompleted;
    private Button mBtCheckBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        orderId = getIntent().getStringExtra("orderId");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Order info");
        setSupportActionBar(mToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(mAdapter);

        tvFoodPriceTotal = (TextView) findViewById(R.id.tvFoodPriceTotal);
        tvCartCountSum = (TextView) findViewById(R.id.tvCartCountSum);

        mBtCompleted = (Button) findViewById(R.id.btCompleted);
        mBtCheckBill = (Button) findViewById(R.id.btCheckBill);

        mBtCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOrderCompleted();
            }
        });

        mBtCheckBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOrderCheckBill();
            }
        });

        fetchOrderInfo();
        fetchStatusCompleted();
    }

    private void fetchOrderInfo() {
        String sql = "SELECT * FROM orderdetail INNER JOIN food ON orderdetail.Food_ID = food.Food_ID WHERE Order_ID = '" + orderId + "'";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            while (resultSet.next()) {
                                OrderDetail orderDetail = new OrderDetail(
                                        resultSet.getInt(1),
                                        resultSet.getString(2),
                                        resultSet.getInt(3),
                                        resultSet.getInt(4),
                                        resultSet.getString(6),
                                        resultSet.getString(7),
                                        resultSet.getString(8),
                                        resultSet.getInt(9),
                                        resultSet.getInt(10),
                                        resultSet.getInt(11)
                                );
                                orderDetails.add(orderDetail);
                            }

                            mAdapter.setList(orderDetails);
                            calSumOrder();
                        } catch (Exception e) {
                        }
                    }
                });
    }

    private void fetchStatusCompleted() {
        String sql = "SELECT Status FROM `order` WHERE Order_ID = '" + orderId + "'";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            if (resultSet.next()) {
                                int status = resultSet.getInt(1);
                                mBtCompleted.setEnabled(status == 0);
                                mBtCheckBill.setEnabled(status == 1);
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
    }

    private void dialogOrderCompleted() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Completed");
        dialog.setMessage("Are you sure?");
        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                orderCompleted();
            }
        });
        dialog.show();
    }

    private void orderCompleted() {
        String sql = "UPDATE `order` SET `Status`='1' WHERE Order_ID = '" + orderId + "'";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteUpdate() {
                    @Override
                    public void onComplete() {
                        fetchStatusCompleted();
                        Toast.makeText(OrderInfoActivity.this, "Order completed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void dialogOrderCheckBill() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Check bill");
        dialog.setMessage("Are you sure?");
        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                orderCheckBill();
            }
        });
        dialog.show();
    }

    private void orderCheckBill() {
        String sql = "UPDATE `order` SET `Status`='2' WHERE Order_ID = '" + orderId + "'";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteUpdate() {
                    @Override
                    public void onComplete() {
                        fetchStatusCompleted();
                        Toast.makeText(OrderInfoActivity.this, "Order check bill", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void calSumOrder() {
        int count = 0;
        int price = 0;
        for (OrderDetail orderDetail : orderDetails) {
            count += orderDetail.getQty();
            price += orderDetail.getQty() * orderDetail.getFoodPrice();
        }
        tvCartCountSum.setText(String.valueOf(count));
        tvFoodPriceTotal.setText(String.valueOf(price));
    }

    private class OrderInfoAdapter extends RecyclerView.Adapter<OrderInfoViewHolder> {
        private ArrayList<OrderDetail> list = new ArrayList<>();

        @NonNull
        @Override
        public OrderInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
            return new OrderInfoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderInfoViewHolder holder, int position) {
            OrderDetail item = list.get(position);

            holder.tvFoodName.setText(item.getFoodName());
            holder.tvFoodQty.setText(String.valueOf(item.getQty()));
            holder.tvFoodPrice.setText(String.valueOf(item.getFoodPrice()));

            Dru.loadImage(holder.ivFoodImage, item.getFoodImage());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        void setList(ArrayList<OrderDetail> list) {
            this.list = list;
            notifyDataSetChanged();
        }

    }

    private class OrderInfoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivFoodImage;
        private final TextView tvFoodName;
        private final TextView tvFoodPrice;
        private final TextView tvFoodQty;

        public OrderInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoodImage = (ImageView) itemView.findViewById(R.id.ivFoodImage);
            tvFoodName = (TextView) itemView.findViewById(R.id.tvFoodName);
            tvFoodPrice = (TextView) itemView.findViewById(R.id.tvFoodPrice);
            tvFoodQty = (TextView) itemView.findViewById(R.id.tvFoodQty);
        }
    }

}
