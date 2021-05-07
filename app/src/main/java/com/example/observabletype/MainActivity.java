package com.example.observabletype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.observabletype.type.CompletableCObserverActivity;
import com.example.observabletype.type.FlowableObserverActivity;
import com.example.observabletype.type.MaybeMaybeObserverActivity;
import com.example.observabletype.type.ObservableObserverActivity;
import com.example.observabletype.type.SingleSingleObserverActivity;

public class MainActivity extends AppCompatActivity {
    Button oo,ss,mm,cc,fo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        oo.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ObservableObserverActivity.class);
            startActivity(intent);
        });
        ss.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SingleSingleObserverActivity.class);
            startActivity(intent);
        });
        mm.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MaybeMaybeObserverActivity.class);
            startActivity(intent);
        });
        cc.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CompletableCObserverActivity.class);
            startActivity(intent);
        });
        fo.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FlowableObserverActivity.class);
            startActivity(intent);
        });
    }
    private void init(){
        oo=findViewById(R.id.oobtn);
        ss=findViewById(R.id.ssBtn);
        mm=findViewById(R.id.mmbtn);
        cc=findViewById(R.id.ccbtn);
        fo=findViewById(R.id.fobtn);
    }
}