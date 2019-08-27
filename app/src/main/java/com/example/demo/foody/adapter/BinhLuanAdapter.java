package com.example.demo.foody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.foody.R;
import com.example.demo.foody.model.BinhLuan;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.ViewHolder> {

    Context context;
    int layout;
    List<BinhLuan> binhLuanList;

    public BinhLuanAdapter(Context context, int layout, List<BinhLuan> binhLuanList) {
        this.context = context;
        this.layout = layout;
        this.binhLuanList = binhLuanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final BinhLuan binhLuan = binhLuanList.get(position);

        Log.d("kiemtraBinhLuan", binhLuan.getNguoiDung().getHinhanh() + "");

        holder.txtTieuDeBinhLuanChiTiet.setText(binhLuan.getTieude());
        holder.txtNoiDungBinhLuanChiTiet.setText(binhLuan.getNoidung());
        holder.txtDiemBinhLuanChiTiet.setText(binhLuan.getChamdiem() + "");
        setHinhAnhBinhLuan(holder.circleImgUserChiTiet, binhLuan.getNguoiDung().getHinhanh());

        final List<Bitmap>  bitmapList = new ArrayList<>();
        for (String linkhinh : binhLuan.getHinhanhBinhLuanList()) {
            StorageReference storage = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                    bitmapList.add(bitmap);

                    Log.d("----------kiemtraBL", bitmap.toString() + "");
                    if (bitmapList.size() == binhLuan.getHinhanhBinhLuanList().size() && bitmapList.size() > 1) {
                        HinhBinhLuanAdapter hinhBinhLuanAdapter = new
                                HinhBinhLuanAdapter(context, R.layout.item_hinh_binh_luan,
                                bitmapList, binhLuan, false);

                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);
                        holder.recyclerHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerHinhBinhLuan.setAdapter(hinhBinhLuanAdapter);
                        hinhBinhLuanAdapter.notifyDataSetChanged();
                    }
                    else {
                        HinhBinhLuanAdapter hinhBinhLuanAdapter = new
                                HinhBinhLuanAdapter(context, R.layout.item_hinh_binh_luan,
                                bitmapList, binhLuan, false);

                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,1);
                        holder.recyclerHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerHinhBinhLuan.setAdapter(hinhBinhLuanAdapter);
                        hinhBinhLuanAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int soBinhLuan = binhLuanList.size();
        if (soBinhLuan > 5) {
            return 5;
        }
        return binhLuanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImgUserChiTiet;
        TextView txtTieuDeBinhLuanChiTiet, txtNoiDungBinhLuanChiTiet, txtDiemBinhLuanChiTiet;
        RecyclerView recyclerBinhLuanChiTietQuanAn, recyclerHinhBinhLuan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImgUserChiTiet = itemView.findViewById(R.id.circleImgUserChiTiet);
            txtTieuDeBinhLuanChiTiet = itemView.findViewById(R.id.txtTieuDeBinhLuanChiTiet);
            txtNoiDungBinhLuanChiTiet = itemView.findViewById(R.id.txtNoiDungBinhLuanChiTiet);
            txtDiemBinhLuanChiTiet = itemView.findViewById(R.id.txtDiemBinhLuanChiTiet);
            recyclerBinhLuanChiTietQuanAn = itemView.findViewById(R.id.recyclerBinhLuanChiTietQuanAn);
            recyclerHinhBinhLuan = itemView.findViewById(R.id.recyclerHinhBinhLuan);
        }
    }

    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh) {
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("nguoidung").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                Log.d("kiemtra", bitmap.toString());
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}
