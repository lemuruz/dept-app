package com.example.dept_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
//    public native int calculateNetDebt(int youOwe, int friendOwes);
//    public native void addDebt(String friendName,String date, String desc, double amount, String type);

//    public void insertDebtFromNative(String friendName, String date, String desc, double amount, String type) {
//        AppDatabase db = AppDatabase.getInstance(this);
//        friendsDao friendsDao = db.friendsDao();
//        DebtDao debtDao = db.debtDao();
//        Friends friend = friendsDao.getFriendByName(friendName);
//        if (friend == null){
//            Friends newFriend = new Friends();
//            newFriend.setName(friendName);
//            friendsDao.insert(newFriend);
//        }
//        // for now, assume personId = 1 for simplicity
//        Debts debt = new Debts();
//        debt.setFriendsId(friendsDao.getFriendByName(friendName).getId());
//        debt.setDate(date);
//        debt.setDescription(desc);
//        debt.setAmount(amount);
//        debt.setType(type);
//        debtDao.insert(debt);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        EditText editFriend = findViewById(R.id.editFriend);
        EditText editDesc = findViewById(R.id.editDesc);
        EditText editAmount = findViewById(R.id.editAmount);
        Button btnAddDebt = findViewById(R.id.btnAddDebt);
        TextView allDebts = findViewById(R.id.tvDebts);


        btnAddDebt.setOnClickListener(v -> {
            String friend = editFriend.getText().toString().trim();
            String desc = editDesc.getText().toString().trim();
            String amountStr = editAmount.getText().toString().trim();

            if (friend.isEmpty() || desc.isEmpty() || amountStr.isEmpty()) {
                allDebts.setText("Please fill in all fields.");
                return;
            }

            double amount = Double.parseDouble(amountStr);
            String date = "2025-11-03"; // For now, hardcoded — later we’ll make this dynamic
            String type = "you_owe";   // You can make this user-selectable later
            viewModel.addDebt(friend, date, desc, amount, type);


            AppDatabase db = AppDatabase.getInstance(this);
            DebtDao debtDao = db.debtDao();

            List<Debts> debtslist = debtDao.getAllDebts();
//            String debtsText = debtslist.debtToString();
            StringBuilder builder = new StringBuilder();
            for (Debts d : debtslist) {
                builder.append(d.debtToString()).append("\n");
            }
            allDebts.setText(builder.toString());
            allDebts.setMovementMethod(new ScrollingMovementMethod());
//            allDebts.setText("Added debt: " + desc + " (" + amount + ")");
        });

    }




    /**
     * A native method that is implemented by the 'dept_app' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}