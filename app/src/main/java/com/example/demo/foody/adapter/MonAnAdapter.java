package com.example.demo.foody.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.foody.R;
import com.example.demo.foody.model.MonAn;

import java.util.ArrayList;
import java.util.List;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ViewHolderMonAn> {

    Context context;
    List<MonAn> monAnList;

    public MonAnAdapter(Context context, List<MonAn> monAnList) {
        this.context = context;
        this.monAnList = monAnList;
    }

    @NonNull
    @Override
    public ViewHolderMonAn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mon_an, parent,false);
        return new ViewHolderMonAn(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMonAn holder, int position) {
        final MonAn monAn = monAnList.get(position);
        holder.txtTenMonAn.setText(monAn.getTenmon());
        holder.txtGiaTien.setText(monAn.getGiatien() + "đ");
        Log.d("kiemtraMA", monAn.getTenmon() + "");
    }

    @Override
    public int getItemCount() {
        return monAnList.size();
    }

    public class ViewHolderMonAn extends RecyclerView.ViewHolder {
        TextView txtTenMonAn, txtGiaTien;

        public ViewHolderMonAn(@NonNull View itemView) {
            super(itemView);

            txtTenMonAn = itemView.findViewById(R.id.txtTenMonAn);
            txtGiaTien = itemView.findViewById(R.id.txtGiaTien);
        }
    }
}
