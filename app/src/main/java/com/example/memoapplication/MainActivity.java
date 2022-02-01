package com.example.memoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static boolean collapse = false;

    FloatingActionButton fab;
    RecyclerView recyclerView;


    RecAdapter recAdapter;
    ArrayList<MainModel> arrayList;
    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.addNewMemo);
        recyclerView = findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbAdapter = new DBAdapter(this);

        arrayList = dbAdapter.getAllMemo();



        if(arrayList.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            recAdapter = new RecAdapter(this,arrayList);
            recyclerView.setAdapter(recAdapter);
        }
        else{
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"No Memos Available",Toast.LENGTH_LONG).show();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NewMemoActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.collapse:
                recAdapter.notifyDataSetChanged();
                if(collapse){
                    item.setIcon(R.drawable.ic_baseline_list_24);
                    collapse = false;
                }
                else {
                    item.setIcon(R.drawable.ic_baseline_layers_24);
                    collapse = true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    int count = 0;
    @Override
    public void onBackPressed() {
        count++;
        if(count<3){
            Toast.makeText(getApplicationContext(),"Press again to exit",Toast.LENGTH_LONG).show();
        }
        else{
            super.onBackPressed();
        }
    }
}