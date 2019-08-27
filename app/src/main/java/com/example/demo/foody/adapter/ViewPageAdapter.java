package com.example.demo.foody.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demo.foody.view.fragment.GanToiFragment;
import com.example.demo.foody.view.fragment.MoiDayFragment;


public class ViewPageAdapter extends FragmentStatePagerAdapter {

    MoiDayFragment moiDayFragment;
    GanToiFragment ganToiFragment;

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
        moiDayFragment = new MoiDayFragment();
        ganToiFragment = new GanToiFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return moiDayFragment;
            case 1:
                return ganToiFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
