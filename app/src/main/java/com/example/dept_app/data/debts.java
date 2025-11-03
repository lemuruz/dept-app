package com.example.dept_app.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
@Entity(
        tableName = "debts",
        foreignKeys = @ForeignKey(
                entity = friends.class,
                parentColumns = "id",
                childColumns = "personId",
                onDelete = ForeignKey.CASCADE
        )
)
public class debts {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int personId;

    private String date;
    private String description;
    private int amount;
    private String type; // "you_owe" or "friend_owes"

    // --- Getters and Setters ---
//    public int getId() { return id; }
//    public void setId(int id) { this.id = id; }
//
//    public int getPersonId() { return personId; }
//    public void setPersonId(int personId) { this.personId = personId; }
//
//    public String getDate() { return date; }
//    public void setDate(String date) { this.date = date; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public double getAmount() { return amount; }
//    public void setAmount(int amount) { this.amount = amount; }
//
//    public String getType() { return type; }
//    public void setType(String type) { this.type = type; }
}
