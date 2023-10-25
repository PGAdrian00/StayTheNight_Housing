package com.example.staythenight_housing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityUpdate extends AppCompatActivity {
    EditText hNumber, hName, hAddr, hPhone, hWebpage;
    Button update_button, delete_button;

    String hotelNumberS, hotelNameS, hotelAddressS, hotelPhoneS, hotelWebpageS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        hName = findViewById(R.id.et_hotel_name_u);
        hAddr=findViewById(R.id.et_address_u);
        hPhone = findViewById(R.id.et_phone_u);
        hWebpage=findViewById(R.id.et_webpage_u);
        update_button = findViewById(R.id.update_btn);
        delete_button = findViewById(R.id.delete_btn);
        getAndSetIntentData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(ActivityUpdate.this);
                hotelNameS = hName.getText().toString().trim();
                hotelAddressS = hAddr.getText().toString().trim();
                hotelPhoneS = hPhone.getText().toString().trim();
                hotelWebpageS = hWebpage.getText().toString().trim();
                db.updateData(hotelNumberS, hotelNameS, hotelAddressS, hotelPhoneS, hotelWebpageS);

                Intent intent = new Intent(ActivityUpdate.this, MainActivity.class);
                startActivity(intent);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("hName") && getIntent().hasExtra("hAddress") &&
                getIntent().hasExtra("hPhone")&&getIntent().hasExtra("hWebpage")){
            hotelNumberS = getIntent().getStringExtra("hNumber");
            hotelNameS = getIntent().getStringExtra("hName");
            hotelAddressS = getIntent().getStringExtra("hAddress");
            hotelPhoneS = getIntent().getStringExtra("hPhone");
            hotelWebpageS = getIntent().getStringExtra("hWebpage");

            hName.setText(hotelNameS);
            hAddr.setText(hotelAddressS);
            hPhone.setText(hotelPhoneS);
            hWebpage.setText(hotelWebpageS);

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + hotelNameS + " ?");
        builder.setMessage("Are you sure you want to delete " + hotelNameS + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDB = new DBHelper(ActivityUpdate.this);
                myDB.deleteOneRow(hotelNumberS);
                finish();
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