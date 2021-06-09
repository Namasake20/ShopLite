package com.example.shoplite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoplite.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmOrderActivity extends AppCompatActivity {
    EditText nameEdt,phoneEdt,addressEdt,cityEdt;
    Button ConfirmOrderBtn;
    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        nameEdt = findViewById(R.id.shipment_name);
        phoneEdt = findViewById(R.id.shipment_phone);
        addressEdt = findViewById(R.id.shipment_address);
        cityEdt = findViewById(R.id.shipment_city);
        ConfirmOrderBtn = findViewById(R.id.confirm_order_btn);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = Ksh."+totalAmount, Toast.LENGTH_SHORT).show();


        ConfirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
    }

    private void Check() {
        if (TextUtils.isEmpty(nameEdt.getText().toString())){
            Toast.makeText(this, "Please provide your Name.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEdt.getText().toString())){
            Toast.makeText(this, "Please provide your Phone Number.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEdt.getText().toString())){
            Toast.makeText(this, "Please provide your Address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cityEdt.getText().toString())){
            Toast.makeText(this, "Please provide your City Name.", Toast.LENGTH_SHORT).show();
        }
        else {
            confirmOrder();
        }

    }

    private void confirmOrder() {
        final String saveCurrentTime,saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference OrdersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.CurrentOnlineUser.getPhone());
        HashMap<String,Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount",totalAmount);
        ordersMap.put("name",nameEdt.getText().toString());
        ordersMap.put("phone",phoneEdt.getText().toString());
        ordersMap.put("address",addressEdt.getText().toString());
        ordersMap.put("city",cityEdt.getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("state","not yet shipped");

        OrdersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.CurrentOnlineUser.getPhone()).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ConfirmOrderActivity.this, "Final order placed successfully.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmOrderActivity.this,HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }

            }
        });

    }
}