package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class adapterViewSepeda extends RecyclerView.Adapter<adapterViewSepeda.UserViewHolder> {

    private ArrayList<modelSepeda> datalist;
    View viewku;
    
    public adapterViewSepeda(ArrayList<modelSepeda> datalist){
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.activity_adapter_view_sepeda, parent, false);
        return new UserViewHolder(viewku);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, final int position) {
        holder.txtJenisSepeda.setText(datalist.get(position).getJenis());
        holder.txtMerkSepeda.setText(datalist.get(position).getMerk());
        holder.cvInboxData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(holder.itemView.getContext(), InfoSepeda.class);
                in.putExtra("id", datalist.get(position).getId());
                in.putExtra("kode", datalist.get(position).getKode());
                in.putExtra("merk", datalist.get(position).getMerk());
                in.putExtra("jenis", datalist.get(position).getJenis());
                in.putExtra("warna", datalist.get(position).getWarna());
                in.putExtra("hargasewa", datalist.get(position).getHargaSewa());
                holder.itemView.getContext().startActivity(in);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return datalist.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView txtJenisSepeda, txtMerkSepeda;
        CardView cvInboxData;

        UserViewHolder(View itemView) {
            super(itemView);
            cvInboxData = itemView.findViewById(R.id.cvInboxData);
            txtJenisSepeda = itemView.findViewById(R.id.txtJenisSepeda);
            txtMerkSepeda = itemView.findViewById(R.id.txtMerkSepeda);
        }
    }

}