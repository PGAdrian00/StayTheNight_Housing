package com.example.staythenight_housing;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Hotels";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_HOTEL_NAME = "hotel_name";
    private static final String COLUMN_HOTEL_ADDRESS = "address";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_WEBPAGE = "webpage";

    private Context context;
    private static final String DB_NAME = "Hotels.db";
    private static final int DB_VERSION = 1;

    DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_query = "Create Table " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_HOTEL_NAME + " TEXT,"
                + COLUMN_HOTEL_ADDRESS + " TEXT,"
                + COLUMN_PHONE_NUMBER + " TEXT,"
                + COLUMN_WEBPAGE + " TEXT)";
        sqLiteDatabase.execSQL(create_table_query);
    }

    public void addNewHotel(String hotelName, String hotelAddress,
                            String hotelPhoneNr, String hotelWebpage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_HOTEL_NAME, hotelName);
        values.put(COLUMN_HOTEL_ADDRESS, hotelAddress);
        values.put(COLUMN_PHONE_NUMBER, hotelPhoneNr);
        values.put(COLUMN_WEBPAGE, hotelWebpage);

        long result = db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public Cursor getHotels(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    void updateData(String row_id, String hName, String hAddress, String hPhone, String hWebpage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_HOTEL_NAME, hName);
        cv.put(COLUMN_HOTEL_ADDRESS, hAddress);
        cv.put(COLUMN_PHONE_NUMBER, hPhone);
        cv.put(COLUMN_WEBPAGE, hWebpage);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String hName){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "hotel_name=?", new String[]{hName});
        if(result == -1){
            Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
