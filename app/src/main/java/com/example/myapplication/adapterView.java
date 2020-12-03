package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class adapterView extends RecyclerView.Adapter<adapterView.UserViewHolder> {

    private ArrayList<model> dataList;
    View viewku;

    public adapterView(ArrayList<model> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.activity_adapter_view, parent, false);
        return new UserViewHolder(viewku);

    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, final int position) {
        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtEmail.setText(dataList.get(position).getEmail());
        holder.cvInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(holder.itemView.getContext(), EditData.class);
                in.putExtra("id", dataList.get(position).getId());
                in.putExtra("nama", dataList.get(position).getNama());
                in.putExtra("email", dataList.get(position).getEmail());
                in.putExtra("nohp", dataList.get(position).getNohp());
                in.putExtra("alamat", dataList.get(position).getAlamat());
                in.putExtra("noktp", dataList.get(position).getNoktp());
                holder.itemView.getContext().startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtEmail;
        CardView cvInbox;

        UserViewHolder(View itemView) {
            super(itemView);
            cvInbox = itemView.findViewById(R.id.cvInbox);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtEmail = itemView.findViewById(R.id.txtEmail);
        }
    }
}