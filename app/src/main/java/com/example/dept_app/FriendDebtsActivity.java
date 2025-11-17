package com.example.dept_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dept_app.data.AppDatabase;
import com.example.dept_app.data.DebtDao;
import com.example.dept_app.data.Debts;
import com.example.dept_app.data.Friends;
import com.example.dept_app.data.friendsDao;

import java.util.List;

public class FriendDebtsActivity extends AppCompatActivity {

    private DebtDao debtDao;
    private friendsDao friendsDao;
    private DebtsAdapter adapter;
    private int friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_debts);

        AppDatabase db = AppDatabase.getInstance(this);
        debtDao = db.debtDao();
        friendsDao = db.friendsDao();

        friendId = getIntent().getIntExtra("friend_id", -1);
        Friends friend = friendsDao.getFriendById(friendId);

        TextView friendName = findViewById(R.id.tvFriendName);
        TextView netDebt = findViewById(R.id.tvNetDebt);
        RecyclerView recyclerView = findViewById(R.id.recyclerDebts);

        friendName.setText(friend.getName());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Debts> debts = debtDao.getDebtsById(friendId);
        adapter = new DebtsAdapter(debts, d -> {
            debtDao.delete(d);
            recreate(); // refresh after delete
        });
        recyclerView.setAdapter(adapter);

        // Calculate net debt
        Double oweResult = debtDao.getTotalByType(friendId, "you_owe");
        Double owedResult = debtDao.getTotalByType(friendId, "you_lend");

        double owe = (oweResult != null) ? oweResult : 0.0;
        double owed = (owedResult != null) ? owedResult : 0.0;
        netDebt.setText("Net: " + (owed - owe));

//        findViewById(R.id.btnAddDebt).setOnClickListener(v -> {
//            // TODO: open AddDebtActivity or dialog
//        });
    }
}
