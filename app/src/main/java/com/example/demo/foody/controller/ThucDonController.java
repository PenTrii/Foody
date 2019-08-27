package com.example.demo.foody.controller;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.foody.adapter.ThucDonAdapter;
import com.example.demo.foody.controller.interfaces.ThucDonInterface;
import com.example.demo.foody.model.ThucDon;

import java.util.List;

public class ThucDonController {

    ThucDon thucDon;

    public ThucDonController(){
        thucDon = new ThucDon();
    }

    public void getDanhSachThucDonQuanAn(final Context context, String manquanan, final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ThucDonInterface thucDonInterface = new ThucDonInterface() {
            @Override
            public void getThucDon(List<ThucDon> thucDonList) {
                Log.d("kiemtraTD", thucDonList.size() + "");
                ThucDonAdapter adapterThucDon = new ThucDonAdapter(context, thucDonList);
                recyclerView.setAdapter(adapterThucDon);
                adapterThucDon.notifyDataSetChanged();
            }
        };
        thucDon.getDanhSachThucDonQuanAn(manquanan, thucDonInterface);
    }
}
