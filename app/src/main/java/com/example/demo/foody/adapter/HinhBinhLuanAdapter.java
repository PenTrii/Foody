package com.example.demo.foody.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.foody.R;
import com.example.demo.foody.model.BinhLuan;

import java.util.List;

public class HinhBinhLuanAdapter extends RecyclerView.Adapter<HinhBinhLuanAdapter.ViewHolderHinhBinhLuan> {

    Context context;
    int resource;
    List<Bitmap> listHinh;
    BinhLuan binhLuan;
    boolean isChiTietBinhLuan;

    public HinhBinhLuanAdapter(Context context, int resource, List<Bitmap> listHinh, BinhLuan binhLuan, boolean isChiTietBinhLuan){
        this.context = context;
        this.resource = resource;
        this.binhLuan = binhLuan;
        this.listHinh = listHinh;
        this.isChiTietBinhLuan = isChiTietBinhLuan;

    }

    @Override
    public HinhBinhLuanAdapter.ViewHolderHinhBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent,false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);

        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(HinhBinhLuanAdapter.ViewHolderHinhBinhLuan holder, int position) {

        holder.imageHinhBinhLuan.setImageBitmap(listHinh.get(position));

        if(!isChiTietBinhLuan) {
            if(position == 3) {
                int sohinhconlai = listHinh.size() - 4;
                if(sohinhconlai > 0){
                    holder.fragmentContainerSoHinhBinhLuan.setVisibility(View.VISIBLE);
                    holder.txtSoHinhBinhLuan.setText("+" + sohinhconlai);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if(!isChiTietBinhLuan) {
            if(listHinh.size() < 4){
                return listHinh.size();
            }
            else {
                return 4;
            }
        }
        else {
            return listHinh.size();
        }
    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imageHinhBinhLuan;
        TextView txtSoHinhBinhLuan;
        FrameLayout fragmentContainerSoHinhBinhLuan;

        public ViewHolderHinhBinhLuan(View itemView) {
            super(itemView);

            imageHinhBinhLuan =  itemView.findViewById(R.id.imgBinhLuan);
            txtSoHinhBinhLuan =  itemView.findViewById(R.id.txtSoHinhBinhLuan);
            fragmentContainerSoHinhBinhLuan =  itemView.findViewById(R.id.fragmentContainerSoHinhBinhLuan);
        }
    }
}