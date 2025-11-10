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
    void insert(Friends friend);

    @Update
    void update(Friends friend);

    @Delete
    void delete(Friends friend);

    @Query("SELECT * FROM friends ORDER BY id DESC")
    List<Friends> getAllFriends();

    @Query("SELECT * FROM friends WHERE id = :id")
    Friends getFriendById(int id);

    @Query("SELECT * FROM friends WHERE name = :name")
    Friends getFriendByName(String name);




}