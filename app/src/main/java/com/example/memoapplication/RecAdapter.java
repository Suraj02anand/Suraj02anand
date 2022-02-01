package com.example.memoapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

    CardView cardView;
    Context context;
    private SQLiteDatabase sqLiteDatabase;
    ArrayList<MainModel> arrayList , _arrayList;

    public RecAdapter(Context context,ArrayList<MainModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this._arrayList= arrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rec_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainModel mainModel = arrayList.get(position);
        holder.title.setText(mainModel.getTitle());

        //TODO change content size

        if(MainActivity.collapse){
            if(mainModel.content.length() < 20){
                 holder.content.setText(mainModel.getContent());
            }
            else{
                String s  = mainModel.getContent();
                String str = s.substring(0,20)+"...";
                holder.content.setText(str);
            }
        }
        else {
            holder.content.setText(mainModel.getContent());
            holder.date.setText(mainModel.getDate());
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainModel mainModel = arrayList.get(position);

                Intent i = new Intent(context,NewMemoActivity.class);
                i.putExtra("COLUMN_ID",mainModel.getId());
                i.putExtra("COLUMN_TITLE",mainModel.getTitle());
                i.putExtra("COLUMN_CONTENT",mainModel.getContent());
                i.putExtra("COLUMN_DATE",mainModel.getDate());
                //Toast.makeText(cardView.getContext()," : "+position,Toast.LENGTH_LONG).show();
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,date,content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            content = itemView.findViewById(R.id.content);
        }

    }
}

