package com.example.demo.foody.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.demo.foody.R;
import com.example.demo.foody.controller.MoiDayController;

public class MoiDayFragment extends Fragment {

    MoiDayController moiDayController;
    RecyclerView recyclerOdau;
    ProgressBar progressBarMoiDay;
    SharedPreferences sharedPreferences;
    NestedScrollView nestedScrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moi_day, container, false);
        recyclerOdau = view.findViewById(R.id.recyclerOdau);
        progressBarMoiDay = view.findViewById(R.id.progressBarODau);
        nestedScrollView = view.findViewById(R.id.nestScrollViewODau);

        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));

        moiDayController = new MoiDayController(getActivity());

        moiDayController.getDanhSachQuanAnController(getActivity(), nestedScrollView, recyclerOdau, progressBarMoiDay, vitrihientai);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
