package com.example.librarymanagementandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OnBoaringAdapter extends RecyclerView.Adapter<OnBoaringAdapter.OnBoaringViewHolder>{
    private List<OnBoaringItem> onBoaringItems;

    public OnBoaringAdapter(List<OnBoaringItem> onBoaringItems) {
        this.onBoaringItems = onBoaringItems;
    }

    @NonNull
    @Override
    public OnBoaringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnBoaringViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_boaring,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoaringViewHolder holder, int position) {
        holder.setOnBoaringData(onBoaringItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onBoaringItems.size();
    }

    class OnBoaringViewHolder extends RecyclerView.ViewHolder{
        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageOnBoaring;

        OnBoaringViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageOnBoaring = itemView.findViewById(R.id.image_boaring);
        }

        void setOnBoaringData(OnBoaringItem onBoaringItem){
            textTitle.setText(onBoaringItem.getTitle());
            textDescription.setText(onBoaringItem.getDescription());
            imageOnBoaring.setImageResource(onBoaringItem.getImage());
        }
    }
}

