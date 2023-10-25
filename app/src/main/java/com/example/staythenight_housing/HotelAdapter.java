package com.example.staythenight_housing;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList<String> id, hotelNumber, hotelName, hotelAddress, hotelPhone, hotelWebpage;

    int position;



    public HotelAdapter (Activity activity, Context context, ArrayList hotelNumber,
                         ArrayList hotelName, ArrayList hotelAddress,
                         ArrayList hotelPhone, ArrayList hotelWebpage){

        this.activity = activity;
        this.context = context;
        this.hotelNumber = hotelNumber;
        this.hotelName = hotelName;
        this.hotelAddress=hotelAddress;
        this.hotelPhone=hotelPhone;
        this.hotelWebpage=hotelWebpage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hotel_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        holder.hotelNumberTv.setText(String.valueOf(hotelNumber.get(position)));
        holder.hotelNameTv.setText(String.valueOf(hotelName.get(position)));
        holder.hotelAddressTv.setText(String.valueOf(hotelAddress.get(position)));
        holder.hotelPhoneNrTv.setText(String.valueOf(hotelPhone.get(position)));
        holder.hotelWebpageTv.setText(String.valueOf(hotelWebpage.get(position)));

        holder.hotelAddressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = holder.hotelAddressTv.getText().toString().trim();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Toast.makeText(context, "Google Maps not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.hotelPhoneNrTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the dialer with the phone number inserted
                String phoneNumber = holder.hotelPhoneNrTv.getText().toString().trim();
                Uri phoneUri = Uri.parse("tel:" + phoneNumber);
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, phoneUri);
                if (dialIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(dialIntent);
                } else {
                    Toast.makeText(context, "Dialer app not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.hotelWebpageTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Webpage loading...", Toast.LENGTH_SHORT).show();

                String url = holder.hotelWebpageTv.getText().toString().trim();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                if(intent.resolveActivity(context.getPackageManager()) !=null){
                    context.startActivity(intent);
                } else{
                    Toast.makeText(context, "No web browser found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, ActivityUpdate.class);
                    intent.putExtra("hNumber", String.valueOf(hotelNumber.get(position)));
                    intent.putExtra("hName", String.valueOf(hotelName.get(position)));
                    intent.putExtra("hAddress", String.valueOf(hotelAddress.get(position)));
                    intent.putExtra("hPhone", String.valueOf(hotelPhone.get(position)));
                    intent.putExtra("hWebpage", String.valueOf(hotelWebpage.get(position)));
                    activity.startActivityForResult(intent, 1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.hotelName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hotelNumberTv;
        TextView hotelNameTv;
        TextView hotelAddressTv;
        TextView hotelPhoneNrTv;
        TextView hotelWebpageTv;
        ImageButton editButton;

        public ViewHolder(View itemView){
            super(itemView);
            hotelNumberTv = itemView.findViewById(R.id.hotelNrTv);
            hotelNameTv = itemView.findViewById(R.id.hotelNameTv);
            hotelAddressTv = itemView.findViewById(R.id.hotelAddressTv);
            hotelPhoneNrTv = itemView.findViewById(R.id.hotelPhoneNrTv);
            hotelWebpageTv = itemView.findViewById(R.id.hotelWebpageTv);
            editButton = itemView.findViewById(R.id.edit_btn);
        }
    }
}
