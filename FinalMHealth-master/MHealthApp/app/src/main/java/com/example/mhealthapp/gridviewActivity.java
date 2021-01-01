package com.example.mhealthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class gridviewActivity extends AppCompatActivity {

    TextView name;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        name = findViewById(R.id.txtViewGD);
        img = findViewById(R.id.imgViewGD);

        Intent intent = getIntent();
//        name.setText(intent.getStringExtra("name"));
//        img.setImageResource(intent.getIntExtra("image",0));
//        if(intent.getText().toString()=="profile") {
//            startActivity(new Intent(gridviewActivity.this, ProfileActivity.class));
//        }else{
//            Toast.makeText(gridviewActivity.this, "error", Toast.LENGTH_SHORT).show();
//        }
    }
}
