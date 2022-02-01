package com.example.memoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewMemoActivity  extends AppCompatActivity {

    TextView title , content;
    DBAdapter dbAdapter;
    Intent intent;
    int _id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);

        dbAdapter = new DBAdapter(this);
        title = findViewById(R.id.edtTxtTitle);
        content = findViewById(R.id.edtTxtContent);

        intent = getIntent();
        if(intent.getStringExtra("COLUMN_CONTENT")!=null && intent.getStringExtra("COLUMN_TITLE")!=null){
            title.setText(intent.getStringExtra("COLUMN_TITLE"));
            content.setText(intent.getStringExtra("COLUMN_CONTENT"));
            _id = intent.getIntExtra("COLUMN_ID",1);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"Save");
        menu.add(1,1,1,"Cancel");
        menu.add(2,2,2,"Update");
        menu.add(3,3,3,"Delete");
        getMenuInflater().inflate(R.menu.activity_new_memo_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    static int countAlign = 0;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        countAlign++ ;
        if(item.getItemId() == R.id.align){
            if(countAlign == 1){
                content.setGravity(Gravity.LEFT);
                item.setIcon(R.drawable.ic_align_left);
            }
            else if(countAlign == 2){
                content.setGravity(Gravity.CENTER_HORIZONTAL);
                item.setIcon(R.drawable.ic_align_center);
            }
            else if(countAlign == 3){
                content.setGravity(Gravity.RIGHT);
                item.setIcon(R.drawable.ic_align_right);
            }
            else{
                countAlign = 0;
            }
        }

        if (item.getItemId() == 0){

            while(true){
                if(title.getText().toString().length()<=0 || content.getText().toString().length() <= 0){
                    Toast.makeText(getApplicationContext(),"Avoid empty title/memo",Toast.LENGTH_LONG).show();
                    break;
                }
                else {
                    break;
                }
            }

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");

            //TODO save the content to local storage
            MainModel mainModel = new MainModel();
            mainModel.setContent(content.getText().toString());
            mainModel.setTitle(title.getText().toString());
            mainModel.setDate(sdf.format(date));

            dbAdapter.addMemo(mainModel);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        else if(item.getItemId() == 1){
            Toast.makeText(getApplicationContext(),"Memo will not be saved/changed",Toast.LENGTH_LONG).show();
            SystemClock.sleep(2000);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }
        else if(item.getItemId()==2){
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");

            MainModel mainModel = new MainModel();
            mainModel.setId(_id);
            mainModel.setContent(content.getText().toString());
            mainModel.setTitle(title.getText().toString());
            mainModel.setDate(sdf.format(date));

            dbAdapter.updateMemo(mainModel);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }
        else if(item.getItemId()==3){
            MainModel mainModel = new MainModel();
            mainModel.setId(_id);;
            dbAdapter.deleteMemo(mainModel);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}


