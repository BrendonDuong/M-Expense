package com.jovanovic.stefan.sqlitetutorial;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "M-Expense.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TRIP = "trip";
    private static final String COLUMN_TRIP_ID = "trip_id";
    private static final String COLUMN_TRIP_NAME = "trip_name";
    private static final String COLUMN_DATE_START = "date_start";
    private static final String COLUMN_DATE_END = "date_end";
    private static final String COLUMN_PLACE_FROM = "place_from";
    private static final String COLUMN_PLACE_TO = "place_to";
    private static final String COLUMN_RISK = "Risk_Assessment";

    private static final String TABLE_EXPENSE = "expense";
    private static final String COLUMN_EXPENSE_ID = "expense_id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_TOTAL_EXPENSE = "total";
    private static final String COLUMN_USED_TIME = "used_time";
    private static final String COLUMN_NOTES = "notes";

    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query0 = "CREATE TABLE " + TABLE_USER +
                " (" + COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT " + ");";
        db.execSQL(query0);

        String query1 = "CREATE TABLE " + TABLE_TRIP +
                " (" + COLUMN_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRIP_NAME + " TEXT, " +
                COLUMN_DATE_START + " TEXT, " +
                COLUMN_DATE_END + " TEXT," +
                COLUMN_PLACE_FROM + " TEXT," +
                COLUMN_PLACE_TO + " TEXT," +
                COLUMN_RISK + " TEXT,"+
                COLUMN_USER_ID + " INTERGER,"+
                " FOREIGN KEY ("+COLUMN_USER_ID +") REFERENCES "+TABLE_USER+"("+COLUMN_USER_ID +"));";
        db.execSQL(query1);

        String query2 = "CREATE TABLE " + TABLE_EXPENSE +
                " (" + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_TOTAL_EXPENSE + " INTERGER, " +
                COLUMN_USED_TIME + " TEXT, " +
                COLUMN_NOTES + " TEXT," +
                COLUMN_TRIP_NAME + " TEXT, " +
                COLUMN_TRIP_ID + " INTERGER,"+
                " FOREIGN KEY ("+COLUMN_TRIP_ID +") REFERENCES "+TABLE_TRIP+"("+COLUMN_TRIP_ID +"));";
        db.execSQL(query2);

        db.execSQL("INSERT INTO " + TABLE_USER + "  VALUES ('tuong','tuongdv@fpt.edu.vn', 'tuong2001')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public long addTrip(Trip trip, String risk){
        long insertId;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_NAME, trip.getTrip_name());
        values.put(COLUMN_DATE_START, trip.getDate_start());
        values.put(COLUMN_DATE_END, trip.getDate_end());
        values.put(COLUMN_PLACE_FROM, trip.getPlace_from());
        values.put(COLUMN_PLACE_TO, trip.getPlace_to());
        values.put(COLUMN_RISK, risk);

        // Inserting Row
        insertId = db.insert(TABLE_TRIP, null, values);
        return insertId;
    }

    public long addExpense(Expense expense, String category, String trip_name, Integer trip_id){
        long insertId;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_TOTAL_EXPENSE, expense.getTotal_expense());
        values.put(COLUMN_USED_TIME, expense.getUsed_Time());
        values.put(COLUMN_NOTES, expense.getNotes());
        values.put(COLUMN_TRIP_NAME, trip_name);
        values.put(COLUMN_TRIP_ID, String.valueOf(trip_id));
        // Inserting Row
        insertId = db.insert(TABLE_EXPENSE, null, values);
        return insertId;
    }

    public List<Trip> getAllTrip() {
        final String query = "SELECT * FROM " + TABLE_TRIP;
        SQLiteDatabase db = this.getReadableDatabase();
        final List<Trip> list = new ArrayList<>();
        final Cursor cursor;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Trip trip = new Trip();
                    trip.setId(cursor.getInt(0));
                    trip.setTrip_name(cursor.getString(1));
                    trip.setDate_start(cursor.getString(2));
                    trip.setDate_end(cursor.getString(3));
                    trip.setPlace_from(cursor.getString(4));
                    trip.setPlace_to(cursor.getString(5));
                    trip.setRisk(cursor.getString(6));
                    // Adding object to list
                    list.add(trip);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    public  String getTotalExpense(Integer id){
        final String query = "SELECT * FROM " + TABLE_EXPENSE;
        SQLiteDatabase db = this.getReadableDatabase();
        Float cost =0f;
        String $ ="$";
        ArrayList<Expense> expenses = new ArrayList<>();
        Cursor cursor = db.query(true, TABLE_EXPENSE,
                new String[]{COLUMN_EXPENSE_ID,COLUMN_CATEGORY, COLUMN_TOTAL_EXPENSE,COLUMN_USED_TIME,COLUMN_NOTES,COLUMN_TRIP_NAME,COLUMN_TRIP_ID}, COLUMN_TRIP_ID + "=?",
                new String[]{String.valueOf(id) }, null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Expense expense = new Expense(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),
                    cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getInt(6));
            cost +=expense.getTotal_expense();
            cursor.moveToNext();
        }
        String total = cost + $;
        return total;
    }

    public ArrayList<Expense> getListExpenseByTripID(int ID){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_EXPENSE,
                new String[]{COLUMN_EXPENSE_ID,COLUMN_CATEGORY, COLUMN_TOTAL_EXPENSE,COLUMN_USED_TIME,COLUMN_NOTES,COLUMN_TRIP_NAME,COLUMN_TRIP_ID}, COLUMN_TRIP_ID + "=?",
                new String[]{String.valueOf(ID) }, null,null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Expense expense = new Expense(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),
                    cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getInt(6));
            expenses.add(expense);
            cursor.moveToNext();
        }if (expenses == null){
            return null;
        }else return expenses;
    }

    public long updateTrip(Trip trip, String risk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRIP_NAME, trip.getTrip_name());
        values.put(COLUMN_DATE_START, trip.getDate_start());
        values.put(COLUMN_DATE_END, trip.getDate_end());
        values.put(COLUMN_PLACE_FROM, trip.getPlace_from());
        values.put(COLUMN_PLACE_TO, trip.getPlace_to());
        values.put(COLUMN_RISK, risk);

        return db.update(TABLE_TRIP, values, "trip_id=?", new String[]{String.valueOf(trip.getId())});
    }

    public long deleteExpense(String row_id_expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EXPENSE, "expense_id=?", new String[]{row_id_expense});
    }

    public long deleteTrip(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses = getListExpenseByTripID((int) id);
        for (Expense ex:expenses){
            deleteExpense(String.valueOf(ex.getId()));
        }
        return db.delete(TABLE_TRIP, COLUMN_TRIP_ID +" =?", new String[]{String.valueOf(id)});
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TRIP);
        db.execSQL("DELETE FROM " + TABLE_EXPENSE);
    }

    public int checkLogin(String email, String password){
        String sql="select * from User where email=? and password=?";
        List<User> list =getdata(sql,email,password);
        if (list.size()==0){
            return -1;
        }
        return 1;
    }

    @SuppressLint("Range")
    public List<User> getdata(String sql, String...selectionArgs){
        List<User>list=new ArrayList<User>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c =db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            User obj =new User();
            obj.setUserId(c.getString(0));
            obj.setEmail(c.getString(1));
            obj.setPassword(c.getString(2));
            list.add(obj);
        }
        return list;
    }
}
