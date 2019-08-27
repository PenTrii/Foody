package com.example.demo.foody.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.foody.R;
import com.example.demo.foody.model.BinhLuan;
import com.example.demo.foody.model.QuanAn;
import com.example.demo.foody.view.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MoiDayAdapter extends RecyclerView.Adapter<MoiDayAdapter.ViewHolder> {

    List<QuanAn> quanAnList;
    int resource;
    Context context;

    public MoiDayAdapter(Context context, List<QuanAn> quanAnList, int resource) {
        this.quanAnList = quanAnList;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final QuanAn quanAn = quanAnList.get(position);
        Log.d("kiemtra", quanAn.getTenquanan());
        holder.txtTenODau.setText(quanAn.getTenquanan());
        holder.txtDiaChiQuanAn.setText(quanAn.getDiachi());
        holder.txtKhoangCach.setText(String.format("%.1f", quanAn.getKhoangcach()) + " km");

        if (quanAn.isGiaohang()) {
            holder.btnDatMon.setVisibility(View.VISIBLE);
        }

        if (quanAn.getHinhanhquanan().size() > 0) {
            Log.d("kiemtra", "" + quanAn.getHinhanhquanan().get(0));
            StorageReference storageRefHinhAnh = FirebaseStorage.getInstance().getReference()
                    .child("hinhanh").child(quanAn.getHinhanhquanan().get(0));
            long ONE_MEGABYTE = 1024 * 1024;

            storageRefHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Log.d("----------kiemtra", bitmap.toString());
                    holder.imgQuanAn.setImageBitmap(bitmap);
                }
            });
        }

        //Lấy danh sách bình luận của quán ăn
        if (quanAn.getBinhLuanList().size() > 0) {
            BinhLuan binhLuan = quanAn.getBinhLuanList().get(0);
            Log.d("kiemtra", "" + binhLuan.getNguoiDung().getHinhanh());
            holder.txtTieuDeBinhLuan.setText(binhLuan.getTieude());
            holder.txtNoiDungBinhLuan.setText(binhLuan.getNoidung());
            holder.txtChamDiemBinhLuan.setText(binhLuan.getChamdiem() + "");
            setHinhAnhBinhLuan(holder.circleImgUser, binhLuan.getNguoiDung().getHinhanh());
            if (quanAn.getBinhLuanList().size() > 1) {
                BinhLuan binhLuan2 = quanAn.getBinhLuanList().get(1);
                holder.txtTieuDeBinhLuan2.setText(binhLuan2.getTieude());
                holder.txtNoiDungBinhLuan2.setText(binhLuan2.getNoidung());
                holder.txtChamDiemBinhLuan2.setText(binhLuan2.getChamdiem() + "");
                setHinhAnhBinhLuan(holder.circleImgUser2, binhLuan2.getNguoiDung().getHinhanh());
            } else {
                holder.txtTieuDeBinhLuan2.setVisibility(View.GONE);
                holder.txtNoiDungBinhLuan2.setVisibility(View.GONE);
                holder.txtChamDiemBinhLuan2.setVisibility(View.GONE);
                holder.circleImgUser2.setVisibility(View.GONE);
            }
            holder.txtTongBinhLuan.setText(quanAn.getBinhLuanList().size() + "");

            int tongsohinhbinhluan = 0;
            double tongdiem = 0;
            //Tính tổng điểm trung bình của bình luận và đếm tổng số hình của bình luận
            for (BinhLuan binhLuan1 : quanAn.getBinhLuanList()) {
                tongsohinhbinhluan += binhLuan1.getHinhanhBinhLuanList().size();
                tongdiem += binhLuan1.getChamdiem();
            }

            double diemtrungbinh = tongdiem / quanAn.getBinhLuanList().size();
            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.1f", diemtrungbinh));

            if (tongsohinhbinhluan > 0) {
                holder.txtTongHinhBinhLuan.setText(tongsohinhbinhluan + "");
            }

        } else {
            holder.containerBinhLuan.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
        }

        holder.cardviewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChiTietQuanAnActivity.class);
                i.putExtra("quanan", quanAn);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quanAnList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenODau, txtTieuDeBinhLuan2, txtTieuDeBinhLuan, txtDiemTrungBinhQuanAn,
                txtNoiDungBinhLuan2, txtNoiDungBinhLuan,
                txtChamDiemBinhLuan, txtChamDiemBinhLuan2,
                txtTongBinhLuan, txtTongHinhBinhLuan, txtDiaChiQuanAn, txtKhoangCach;
        Button btnDatMon;
        CircleImageView circleImgUser2, circleImgUser;
        ImageView imgQuanAn;
        LinearLayout containerBinhLuan, containerBinhLuan2;
        CardView cardviewContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenODau = itemView.findViewById(R.id.txtTenODau);
            txtDiaChiQuanAn = itemView.findViewById(R.id.txtDiaChiQuanAn);
            txtKhoangCach = itemView.findViewById(R.id.txtKhoangCach);
            btnDatMon = itemView.findViewById(R.id.btnDatMon);
            imgQuanAn = itemView.findViewById(R.id.imgQuanAn);
            txtDiemTrungBinhQuanAn = itemView.findViewById(R.id.txtDiemTrungBinhQuanAn);
            txtTieuDeBinhLuan2 = itemView.findViewById(R.id.txtTieuDeBinhLuan2);
            txtTieuDeBinhLuan = itemView.findViewById(R.id.txtTieuDeBinhLuan);
            txtNoiDungBinhLuan2 = itemView.findViewById(R.id.txtNoiDungBinhLuan2);
            txtNoiDungBinhLuan = itemView.findViewById(R.id.txtNoiDungBinhLuan);
            txtChamDiemBinhLuan = itemView.findViewById(R.id.txtChamDiemBinhLuan);
            txtChamDiemBinhLuan2 = itemView.findViewById(R.id.txtChamDiemBinhLuan2);
            circleImgUser2 = itemView.findViewById(R.id.circleImgUser2);
            circleImgUser = itemView.findViewById(R.id.circleImgUser);
            txtTongBinhLuan = itemView.findViewById(R.id.txtTongBinhLuan);
            txtTongHinhBinhLuan = itemView.findViewById(R.id.txtTongHinhBinhLuan);
            containerBinhLuan = itemView.findViewById(R.id.containerBinhLuan);
            containerBinhLuan2 = itemView.findViewById(R.id.containerBinhLuan2);
            cardviewContainer = itemView.findViewById(R.id.cardviewContainer);
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
