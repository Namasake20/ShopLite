package com.example.shoplite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
    ImageView tshirts,sports,femaleDresses,sweathers;
    ImageView glasses,hats,PursesWallets,Shoes;
    ImageView heapPhone,Laptops,Watches,Mobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        tshirts = findViewById(R.id.t_shirts);
        sports = findViewById(R.id.sports);
        femaleDresses = findViewById(R.id.female_dress);
        sweathers = findViewById(R.id.sweather);
        glasses = findViewById(R.id.glasses);
        hats = findViewById(R.id.hats);
        PursesWallets = findViewById(R.id.purses_bgs);
        Shoes = findViewById(R.id.shoes);
        heapPhone = findViewById(R.id.headphones);
        Laptops = findViewById(R.id.laptops);
        Watches = findViewById(R.id.watches);
        Mobiles = findViewById(R.id.mobiles);

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","tShirts");
                startActivity(intent);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","sports tshirst");
                startActivity(intent);
            }
        });
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","female Dresses");
                startActivity(intent);
            }
        });
        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","sweathers");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","Glasses");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","Hats");
                startActivity(intent);
            }
        });
        PursesWallets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","Purses Wallets");
                startActivity(intent);
            }
        });
        Shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);
            }
        });
        heapPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","Head Phones");
                startActivity(intent);
            }
        });
        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);
            }
        });
        Watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","Watches");
                startActivity(intent);
            }
        });
        Mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddProductActivity.class);
                intent.putExtra("category","Mobile Phones");
                startActivity(intent);
            }
        });
    }
}