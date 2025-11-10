package com.example.dept_app.data;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
@Entity(
        tableName = "debts",
        foreignKeys = @ForeignKey(
                entity = Friends.class,
                parentColumns = "id",
                childColumns = "friendsId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(value = {"friendsId"})
)
public class Debts {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int friendsId;
    private String date;
    private String description;
    private double amount;
    private String type; // "you_owe" or "friend_owes"

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFriendsId() { return friendsId; }
    public void setFriendsId(int friendsId) { this.friendsId = friendsId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
