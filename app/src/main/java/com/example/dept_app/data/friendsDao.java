package com.example.dept_app.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface friendsDao {

    @Insert
    void insert(friends friend);

    @Update
    void update(friends friend);

    @Delete
    void delete(friends friend);

    @Query("SELECT * FROM friends ORDER BY id DESC")
    List<friends> getAllFriends();

    @Query("SELECT * FROM friends WHERE id = :id")
    friends getFriendById(int id);


}