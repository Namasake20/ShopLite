package com.example.shoplite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shoplite.Model.Products;
import com.example.shoplite.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    ImageView productImage;
    ElegantNumberButton numberBtn;
    TextView productName,productDescription,productPrice;
    Button AddToCart;
    private String productID = "",state = "Normal";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        AddToCart = findViewById(R.id.pd_add_to_cart);
        productImage = findViewById(R.id.product_details_image);
        numberBtn = findViewById(R.id.number_btn);
        productName = findViewById(R.id.product_details_name);
        productDescription = findViewById(R.id.product_details_description);
        productPrice = findViewById(R.id.product_details_price);

        getProductDetails(productID);

        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (state.equals("Order Placed") || state.equals("Order shipped")){
                    Toast.makeText(ProductDetailsActivity.this, "You can only placed another order once your order is shipped or confirmed.", Toast.LENGTH_LONG).show();
                }
                else {
                    addingToCartList();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
    }

    private void addingToCartList() {
        String saveCurrentTime,saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> CartMap = new HashMap<>();
        CartMap.put("pid",productID);
        CartMap.put("pname",productName.getText().toString());
        CartMap.put("price",productPrice.getText().toString());
        CartMap.put("date",saveCurrentDate);
        CartMap.put("time",saveCurrentTime);
        CartMap.put("quantity",numberBtn.getNumber());
        CartMap.put("discount","");

        cartListRef.child("User View").child(Prevalent.CurrentOnlineUser.getPhone()).child("Poducts").child(productID)
                .updateChildren(CartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            cartListRef.child("Admin View").child(Prevalent.CurrentOnlineUser.getPhone()).child("Poducts").child(productID)
                                    .updateChildren(CartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(ProductDetailsActivity.this, "Added to Cart List", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ProductDetailsActivity.this,HomeActivity.class);
                                                startActivity(intent);
                                            }


                                        }
                                    });
                        }

                    }
                });

    }

    private void getProductDetails(String productID) {
        DatabaseReference productRefs = FirebaseDatabase.getInstance().getReference().child("Products");

        productRefs.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Products products = snapshot.getValue(Products.class);

                    productName.setText(products.getName());
                    productDescription.setText(products.getDescription());
                    productPrice.setText( products.getPrice());
                    Picasso.get().load(products.getImg()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void CheckOrderState(){
        DatabaseReference OrdersRef;
        OrdersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.CurrentOnlineUser.getPhone());

        OrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String shippingstate = snapshot.child("state").getValue().toString();

                    if (shippingstate.equals("shipped")){
                        state = "Order shipped";


                    }
                    else if (shippingstate.equals("not yet shipped")){
                        state = "Order Placed";

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}