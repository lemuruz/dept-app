package com.example.dept_app;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.dept_app.data.AppDatabase;
import com.example.dept_app.data.DebtDao;
import com.example.dept_app.data.Debts;
import com.example.dept_app.data.Friends;
import com.example.dept_app.data.friendsDao;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    @Test
    public void insertAndQueryTest() {
        AppDatabase db = AppDatabase.getInstance(ApplicationProvider.getApplicationContext());
        friendsDao friendsDao = db.friendsDao();
        DebtDao debtDao = db.debtDao();

        // Insert a friend if none exists
        if (friendsDao.getAllFriends().isEmpty()) {
            Friends f = new Friends();
            f.setName("John");
            friendsDao.insert(f);
        }

        int johnId = friendsDao.getFriendByName("John").getId();

        // Insert a debt
        Debts debt = new Debts();
        debt.setFriendsId(johnId);
        debt.setDate("2025-11-03");
        debt.setDescription("Lunch");
        debt.setAmount(120);
        debt.setType("you_owe");
        debtDao.insert(debt);

        // Read all debts
        List<Debts> allDebts = debtDao.getAllDebts();
        for (Debts d : allDebts) {
            System.out.println("TestDebt: " + friendsDao.getFriendById(d.getFriendsId()).getName()
                    + " owes " + d.getAmount() + " for " + d.getDescription());
        }

        assert(!allDebts.isEmpty());
    }
}
