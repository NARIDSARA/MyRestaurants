package com.naridsara.myrestaurants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adedom.library.Dru;
import com.adedom.library.ExecuteQuery;

import java.sql.ResultSet;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ArrayList<Order> items;
    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter = new HistoryAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("History");
        setSupportActionBar(mToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(mAdapter);

        fetchOrder();
    }

    private void fetchOrder() {
        String sql = "SELECT * FROM `order` WHERE Status IN (2) ORDER BY Created DESC";
        Dru.connection(ConnectDB.getConnection())
                .execute(sql)
                .commit(new ExecuteQuery() {
                    @Override
                    public void onComplete(ResultSet resultSet) {
                        try {
                            items = new ArrayList<Order>();
                            while (resultSet.next()) {
                                Order order = new Order(
                                        resultSet.getString(1),
                                        resultSet.getInt(2),
                                        resultSet.getString(3),
                                        resultSet.getString(4)
                                );
                                items.add(order);
                            }

                            mAdapter.setList(items);
                        } catch (Exception e) {
                        }
                    }
                });
    }

    private class HistoryAdapter extends RecyclerView.Adapter<OrderViewHolder> {
        private ArrayList<Order> list = new ArrayList<>();

        @NonNull
        @Override
        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
            return new OrderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
            Order order = items.get(position);
            holder.tvOrderId.setText(order.getOrderId());
            holder.tvCreated.setText(order.getCreated());

            switch (order.getStatus()) {
                case "0":
                    holder.rootLayout.setBackgroundColor(Color.rgb(255, 204, 204));
                    break;
                case "1":
                    holder.rootLayout.setBackgroundColor(Color.rgb(255, 255, 204));
                    break;
                case "2":
                    holder.rootLayout.setBackgroundColor(Color.rgb(204, 255, 204));
                    break;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), OrderInfoActivity.class);
                    intent.putExtra("orderId", order.getOrderId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        void setList(ArrayList<Order> list) {
            this.list = list;
            notifyDataSetChanged();
        }

    }

    private class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvOrderId;
        private final TextView tvCreated;
        private final LinearLayout rootLayout;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = (TextView) itemView.findViewById(R.id.tvOrderId);
            tvCreated = (TextView) itemView.findViewById(R.id.tvCreated);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.rootLayout);
        }
    }

}
