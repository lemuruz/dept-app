package com.example.dept_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private FriendsAdapter adapter;
    private friendsDao friendsDao;
    private String selectedDate = null;
    private String currentType = "you_owe";



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


        TextView tvDate = findViewById(R.id.tvSelectDate);
        tvDate.setText(getToday());
        tvDate.setOnClickListener(v -> openDatePicker(tvDate));

        TextView tvType = findViewById(R.id.tvSelectType);
        tvType.setOnClickListener(v -> {
            if (currentType.equals("you_owe")) {
                currentType = "you_lend";
                tvType.setText("You Lend");
                tvType.setBackgroundResource(R.drawable.bg_type_lend);
            } else {
                currentType = "you_owe";
                tvType.setText("You Owe");
                tvType.setBackgroundResource(R.drawable.bg_type_owe);
            }
        });

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

            String dateToUse = (selectedDate == null) ? getToday() : selectedDate;
            selectedDate = null;
            tvDate.setText(getToday());


            double amount = Double.parseDouble(amountStr);
//            String date = "2025-11-03"; // For now, hardcoded — later we’ll make this dynamic
//            String type = "you_owe";   // You can make this user-selectable later
            viewModel.addDebt(friend, dateToUse, desc, amount, currentType);



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

    private void openDatePicker(TextView tv) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, y, m, d) -> {
                    selectedDate = y + "-" + (m + 1) + "-" + d;
                    tv.setText(selectedDate);   // show chosen date
                },
                year, month, day
        );
        dialog.show();
    }

    private String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());
    }


    /**
     * A native method that is implemented by the 'dept_app' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}