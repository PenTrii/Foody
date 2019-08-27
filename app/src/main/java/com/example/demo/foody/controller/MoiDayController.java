package com.example.demo.foody.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.foody.R;
import com.example.demo.foody.adapter.MoiDayAdapter;
import com.example.demo.foody.controller.interfaces.MoiDayInterface;
import com.example.demo.foody.model.QuanAn;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MoiDayController {

    Context context;
    QuanAn quanAn;
    int itemdaco = 3;
    MoiDayAdapter oDauAdapter;

    public MoiDayController(Context context) {
        this.context = context;
        quanAn = new QuanAn();
    }

    public void getDanhSachQuanAnController(Context context, NestedScrollView nestedScrollView, RecyclerView recyclerViewODau, final ProgressBar progressBar, final Location vitrihientai) {
        final List<QuanAn> quanAnList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewODau.setLayoutManager(layoutManager);
        oDauAdapter = new MoiDayAdapter(context, quanAnList, R.layout.item_moi_day);
        recyclerViewODau.setAdapter(oDauAdapter);

        progressBar.setVisibility(View.VISIBLE);

        final MoiDayInterface moiDayInterface = new MoiDayInterface() {
            @Override
            public void getDanhSachQuanAn(final QuanAn quanAn) {
                final List<Bitmap> bitmaps = new ArrayList<>();
                for (String linkhinh : quanAn.getHinhanhquanan()) {

                    StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps.add(bitmap);
                            quanAn.setBitmapList(bitmaps);

                            if (quanAn.getBitmapList().size() == quanAn.getHinhanhquanan().size()) {
                                quanAnList.add(quanAn);
                                oDauAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }
            }
        };

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if (scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()) {
                        itemdaco += 3;
                        quanAn.getDanhSachQuanAn(moiDayInterface, vitrihientai, itemdaco, itemdaco - 3);
                    }
                }
            }
        });

        quanAn.getDanhSachQuanAn(moiDayInterface, vitrihientai, itemdaco, 0);
    }
}
