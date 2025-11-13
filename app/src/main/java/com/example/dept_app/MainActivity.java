package com.example.dept_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dept_app.data.AppDatabase;
import com.example.dept_app.data.Debts;
import com.example.dept_app.data.DebtDao;
import com.example.dept_app.data.Friends;
import com.example.dept_app.data.friendsDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private FriendsAdapter adapter;
    private friendsDao friendsDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        EditText editFriend = findViewById(R.id.editFriend);
        EditText editDesc = findViewById(R.id.editDesc);
        EditText editAmount = findViewById(R.id.editAmount);
        Button btnAddDebt = findViewById(R.id.btnAddDebt);
//        TextView allDebts = findViewById(R.id.tvDebts);
        TextView debtlog = findViewById(R.id.tvDebts);
        AppDatabase db = AppDatabase.getInstance(this);
        friendsDao = db.friendsDao();
        RecyclerView recyclerView = findViewById(R.id.recyclerFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<Friends> friends = friendsDao.getAllFriends();
        adapter = new FriendsAdapter(friends, friend_ -> {
            Intent intent = new Intent(this, FriendDebtsActivity.class); //go to debt page
            intent.putExtra("friend_id", friend_.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        btnAddDebt.setOnClickListener(v -> {
            String friend = editFriend.getText().toString().trim();
            String desc = editDesc.getText().toString().trim();
            String amountStr = editAmount.getText().toString().trim();

            if (friend.isEmpty() || desc.isEmpty() || amountStr.isEmpty()) {
//                allDebts.setfriendText("Please fill in all fields.");
                debtlog.setText("Please fill in all fields.");
                return;
            }

            double amount = Double.parseDouble(amountStr);
            String date = "2025-11-03"; // For now, hardcoded — later we’ll make this dynamic
            String type = "you_owe";   // You can make this user-selectable later
            viewModel.addDebt(friend, date, desc, amount, type);



//            DebtDao debtDao = db.debtDao();
//            List<Debts> debtslist = debtDao.getAllDebts();
////            String debtlog = debtslist.debtToString();
//            StringBuilder builder = new StringBuilder();
//            for (Debts d : debtslist) {
//                builder.append(d.debtToString()).append("\n");
//            }
//            allDebts.setText(builder.toString());
//            allDebts.setMovementMethod(new ScrollingMovementMethod());
            debtlog.setText("Added debt: " + desc + " (" + amount + ")");



        });

    }




    /**
     * A native method that is implemented by the 'dept_app' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}