package com.example.dept_app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dept_app.data.AppDatabase;
import com.example.dept_app.data.DebtDao;
import com.example.dept_app.data.Debts;
import com.example.dept_app.data.Friends;
import com.example.dept_app.data.friendsDao;

import java.util.List;
import java.util.concurrent.Executors;

public class ViewModel extends AndroidViewModel {
    static {
        System.loadLibrary("dept_app");
    }

    public native int calculateNetDebt(int youOwe, int friendOwes);
    public native void addDebt(String friendName, String date, String desc, double amount, String type);

    private final DebtDao debtDao;
    private final friendsDao friendsDao;

    public ViewModel(@NonNull Application app) {
        super(app);
        AppDatabase db = AppDatabase.getInstance(app);
        debtDao = db.debtDao();
        friendsDao = db.friendsDao();
    }

    public void insertDebtFromNative(String friendName, String date, String desc, double amount, String type) {
        Friends friend = friendsDao.getFriendByName(friendName);
        if (friend == null) {
            friend = new Friends();
            friend.setName(friendName);
            friendsDao.insert(friend);
        }

        Debts debt = new Debts();
        debt.setFriendsId(friendsDao.getFriendByName(friendName).getId());
        debt.setDate(date);
        debt.setDescription(desc);
        debt.setAmount(amount);
        debt.setType(type);
        debtDao.insert(debt);
    }

    public LiveData<List<Debts>> getAllDebts() {
        return debtDao.getAllDebtsLive();
    }
}