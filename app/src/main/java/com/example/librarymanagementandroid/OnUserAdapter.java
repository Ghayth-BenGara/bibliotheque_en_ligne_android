package com.example.librarymanagementandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OnUserAdapter extends RecyclerView.Adapter<OnUserAdapter.UserViewHolder> {
    private ClickChoix clickChoix;
    private Context context;
    private List<ItemListeUser> userList;

    public OnUserAdapter(Context context, List<ItemListeUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    public OnUserAdapter(List<ItemListeUser> userList, Context context, ClickChoix clickChoix) {
        this.context = context;
        this.userList = userList;
        this.clickChoix = clickChoix;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_user,parent,false);
        final UserViewHolder dataViewHolder = new UserViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickChoix.OnMyClick(v,dataViewHolder.getPosition());
            }
        });
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        ItemListeUser itemListeUser = userList.get(position);

        holder.email.setText(itemListeUser.getEmail());
        holder.name.setText(itemListeUser.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView email, name;

        public UserViewHolder(View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.email);
            name = itemView.findViewById(R.id.name);
        }
    }
}

