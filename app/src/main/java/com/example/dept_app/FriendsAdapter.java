package com.example.dept_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dept_app.data.AppDatabase;
import com.example.dept_app.data.DebtDao;
import com.example.dept_app.data.Debts;
import com.example.dept_app.data.Friends;
import com.example.dept_app.data.friendsDao;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {
    private List<Friends> friends;
    private OnFriendClickListener listener;

    public interface OnFriendClickListener {
        void onFriendClick(Friends friend);
    }

    public FriendsAdapter(List<Friends> friends, OnFriendClickListener listener) {
        this.friends = friends;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friends friend = friends.get(position);
        holder.textView.setText(friend.getName());
        holder.itemView.setOnClickListener(v -> listener.onFriendClick(friend));
    }

    @Override
    public int getItemCount() { return friends.size(); }

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        FriendViewHolder(View view) {
            super(view);
            textView = view.findViewById(android.R.id.text1);
        }
    }

    public static class FriendDebtsActivity extends AppCompatActivity {

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
            Double owedToYouResult = debtDao.getTotalByType(friendId, "owe_you");

// handle nulls
            double owe = (oweResult != null) ? oweResult : 0.0;
            double owedToYou = (owedToYouResult != null) ? owedToYouResult : 0.0;
            netDebt.setText("Net: " + (owedToYou - owe));

//            findViewById(R.id.btnAddDebt).setOnClickListener(v -> {
//                // TODO: open AddDebtActivity or dialog
//            });
        }
    }
}
