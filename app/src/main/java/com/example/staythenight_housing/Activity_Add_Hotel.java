package com.example.staythenight_housing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Add_Hotel extends AppCompatActivity {

    private EditText hotelNameedt, hotelAddressedt, hotelPhoneNredt, hotelWebpageedt;
    private Button addHotel;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);

        hotelNameedt = findViewById(R.id.et_hotel_name);
        hotelAddressedt = findViewById(R.id.et_address);
        hotelPhoneNredt = findViewById(R.id.et_phone);
        hotelWebpageedt = findViewById(R.id.et_webpage);
        addHotel = findViewById(R.id.btn_save);

        dbHelper = new DBHelper(Activity_Add_Hotel.this);

        addHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotelName = hotelNameedt.getText().toString();
                String hotelAddress = hotelAddressedt.getText().toString();
                String hotelPhoneNr = hotelPhoneNredt.getText().toString();
                String hotelWebpage = hotelWebpageedt.getText().toString();

                dbHelper.addNewHotel(hotelName,hotelAddress, hotelPhoneNr, hotelWebpage);

                Toast.makeText(Activity_Add_Hotel.this, "Hotel added!", Toast.LENGTH_SHORT).show();
                hotelNameedt.setText("");
                hotelAddressedt.setText("");
                hotelPhoneNredt.setText("");
                hotelWebpageedt.setText("");

                Intent intent = new Intent(Activity_Add_Hotel.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}