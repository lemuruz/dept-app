package com.example.dept_app.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DebtDao {

    @Insert
    void insert(debts debt);

    @Update
    void update(debts debt);

    @Delete
    void delete(debts debt);

    @Query("SELECT * FROM debts ORDER BY date DESC")
    List<debts> getAllDebts();

    @Query("SELECT SUM(amount) FROM debts WHERE type = :type")
    double getTotalByType(String type);

}