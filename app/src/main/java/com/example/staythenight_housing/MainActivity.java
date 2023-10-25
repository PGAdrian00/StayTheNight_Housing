package com.example.staythenight_housing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private FloatingActionButton add_hotel_btn, delete_all_btn;
    private RecyclerView recyclerView;

    private DBHelper dbHelper;
    ArrayList<String> hotelNumber, hotelName, hotelAddress, hotelPhone, hotelWebpage;
    HotelAdapter hotelAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));



        add_hotel_btn = findViewById(R.id.floatingActionButton);
        add_hotel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Activity_Add_Hotel.class);
                startActivity(intent);

            }
        });

        dbHelper = new DBHelper(MainActivity.this);

        hotelNumber = new ArrayList<>();
        hotelName = new ArrayList<>();
        hotelAddress = new ArrayList<>();
        hotelPhone = new ArrayList<>();
        hotelWebpage = new ArrayList<>();

        storeDataInArrays();

        hotelAdapter = new HotelAdapter(MainActivity.this, MainActivity.this, hotelNumber, hotelName,hotelAddress,hotelPhone,hotelWebpage);
        hotelAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(hotelAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        delete_all_btn = findViewById(R.id.delete_all_btn);
        delete_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllConfirmDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = dbHelper.getHotels();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No hotels to be shown.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                hotelNumber.add(cursor.getString(0));
                hotelName.add(cursor.getString(1));
                hotelAddress.add(cursor.getString(2));
                hotelPhone.add(cursor.getString(3));
                hotelWebpage.add(cursor.getString(4));
            }
        }
    }

    void deleteAllConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all hotels ?");
        builder.setMessage("Are you sure you want to delete all hotels ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDB = new DBHelper(MainActivity.this);
                myDB.deleteAllData();

                recreate();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}