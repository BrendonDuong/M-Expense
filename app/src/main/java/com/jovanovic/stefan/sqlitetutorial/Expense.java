package com.jovanovic.stefan.sqlitetutorial;

import java.io.Serializable;

public class Expense implements Serializable {

    private int expense_id;
    private String category;
    private int total_expense;
    private String used_time;
    private String notes;
    private String trip_name;
    private int trip_id;

    public Expense(int expense_id, String category, int total_expense, String used_time,String notes, String trip_name, int trip_id) {
        this.expense_id = expense_id;
        this.category = category;
        this.total_expense = total_expense;
        this.used_time = used_time;
        this.notes = notes;
        this.trip_name = trip_name;
        this.trip_id=trip_id;
    }
    public Expense(){

    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotal_expense() { return total_expense;}

    public void setTotal_expense(int total_expense) {
        this.total_expense = total_expense;
    }

    public String getUsed_Time() {
        return used_time;
    }

    public void setUsed_time(String used_time) {
        this.used_time = used_time;
    }

    public String getNotes() { return notes; }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public int getId() {
        return expense_id;
    }

    public void setId(int expense_id) {
        this.expense_id = expense_id;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expense_id=" + expense_id +
                ", category='" + category + '\'' +
                ", total_expense='" + total_expense + '\'' +
                ", used_time='" + used_time + '\'' +
                ", notes=" + notes + '\'' +
                ", trip_name=" + trip_name + '\'' +
                ", trip_id=" + trip_id +
                '}';
    }
}
