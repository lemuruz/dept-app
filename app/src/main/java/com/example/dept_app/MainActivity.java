package com.example.dept_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dept_app.data.AppDatabase;
import com.example.dept_app.data.Debts;
import com.example.dept_app.data.DebtDao;
import com.example.dept_app.data.Friends;
import com.example.dept_app.data.friendsDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'dept_app' library on application startup.
    static {
        System.loadLibrary("dept_app");
//        System.loadLibrary("native-lib");
    }
    public native int calculateNetDebt(int youOwe, int friendOwes);
    public native void addDebt(String friendName,String date, String desc, double amount, String type);

    public void insertDebtFromNative(String friendName, String date, String desc, double amount, String type) {
        AppDatabase db = AppDatabase.getInstance(this);
        friendsDao friendsDao = db.friendsDao();
        DebtDao debtDao = db.debtDao();
        Friends friend = friendsDao.getFriendByName(friendName);
        if (friend == null){
            Friends newFriend = new Friends();
            newFriend.setName(friendName);
            friendsDao.insert(newFriend);
        }
        // for now, assume personId = 1 for simplicity
        Debts debt = new Debts();
        debt.setFriendsId(friendsDao.getFriendByName(friendName).getId());
        debt.setDate(date);
        debt.setDescription(desc);
        debt.setAmount(amount);
        debt.setType(type);
        debtDao.insert(debt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = findViewById(R.id.sample_text);
        int youOwe = 50;
        int friendOwes = 120;


        int netDebt = calculateNetDebt(youOwe, friendOwes);

        text.setText("Net debt = " + netDebt);

        AppDatabase db = AppDatabase.getInstance(this);
        friendsDao friendsDao = db.friendsDao();
        DebtDao debtDao = db.debtDao();

        // Insert a sample person (if not already in DB)
        if (friendsDao.getAllFriends().isEmpty()) {
            Friends john = new Friends();
            john.setName("John");
            friendsDao.insert(john);
        }

        // Get the first person’s ID
        int johnId = friendsDao.getAllFriends().get(0).getId();

        // Insert a sample debt record for that person
        Debts debt = new Debts();
        debt.setFriendsId(johnId);
        debt.setDate("2025-11-03");
        debt.setDescription("Lunch");
        debt.setAmount(120);
        debt.setType("you_owe");
        debtDao.insert(debt);

        // Query all debts for John
        List<Debts> johnDebts = debtDao.getDebtsById(johnId);

        // Print debts to Logcat (for debugging)
        for (Debts d : johnDebts) {
            System.out.println("Debt: " + d.getDescription() + " = " + d.getAmount());
        }

        // Get total "you owe" debts
        double totalYouOwe = debtDao.getTotalByType("you_owe");
        System.out.println("Total you owe: " + totalYouOwe);

        addDebt("anakin","2025-11-03", "Dinner", 150.0, "you_owe");

        List<Debts> allDebts = debtDao.getAllDebts();
        for (Debts d : allDebts) {
            System.out.println("DebtDebug "+ "Debt: " +friendsDao.getFriendById(d.getFriendsId()).getName()+ " "+ d.getDate() + ", description: " + d.getDescription() + ", amount: " + d.getAmount() + ", type: " + d.getType());
        }

    }
    private void runDatabaseTest() {
        AppDatabase db = AppDatabase.getInstance(this);
        friendsDao friendsDao = db.friendsDao();
        DebtDao debtDao = db.debtDao();

        // Insert a sample person if not exists
        if (friendsDao.getAllFriends().isEmpty()) {
            Friends john = new Friends();
            john.setName("John");
            friendsDao.insert(john);
        }

        int johnId = friendsDao.getAllFriends().get(0).getId();

        // Insert a sample debt
        Debts debt = new Debts();
        debt.setFriendsId(johnId);
        debt.setDate("2025-11-03");
        debt.setDescription("Lunch");
        debt.setAmount(120);
        debt.setType("you_owe");
        debtDao.insert(debt);

        // Print all debts for verification
        List<Debts> allDebts = debtDao.getAllDebts();
        for (Debts d : allDebts) {
            System.out.println("DebtDebug " + friendsDao.getFriendById(d.getFriendsId()).getName()
                    + " " + d.getDate() + ", description: " + d.getDescription()
                    + ", amount: " + d.getAmount() + ", type: " + d.getType());
        }
    }

    /**
     * A native method that is implemented by the 'dept_app' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}