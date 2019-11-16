package com.example.smsmyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TopRecyclerAdapter extends RecyclerView.Adapter<TopRecyclerAdapter.ViewHolder> {
    private ArrayList<SmsClass> topData;
    private OnItemClickListener itemClickListener;
    TopRecyclerAdapter(ArrayList<SmsClass> topData) {
        if (topData != null) {
            this.topData = topData;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sms_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewName.setText(topData.get(position).getName());
        holder.textViewMessage.setText(topData.get(position).getText());
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    @Override
    public int getItemCount() {
        return topData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewMessage;
        ViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.top_name);
            textViewMessage= view.findViewById(R.id.top_message);
            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }
}




