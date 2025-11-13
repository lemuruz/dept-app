package com.example.dept_app.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DebtDao {

    @Insert
    void insert(Debts debt);

    @Update
    void update(Debts debt);

    @Delete
    void delete(Debts debt);

    @Query("SELECT * FROM Debts ORDER BY date DESC")
    List<Debts> getAllDebts();


    @Query("SELECT * FROM Debts where friendsId = :friendsId")
    List<Debts> getDebtsById(int friendsId);
    @Query("SELECT SUM(amount) FROM debts WHERE friendsId = :friendsId AND type = :type")
    Double getTotalByType(int friendsId, String type);

    @Query("SELECT * FROM debts")
    LiveData<List<Debts>> getAllDebtsLive();
}
