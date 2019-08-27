package com.example.demo.foody.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.demo.foody.R;
import com.example.demo.foody.adapter.ViewPageAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private ViewPager viewpagerContainer;
    private RadioButton rbtnMoiNhat, rbtnGanToi;
    private RadioGroup radioGrTab;
    private ViewPageAdapter pageAdapter;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        viewpagerContainer = findViewById(R.id.viewpagerContainer);
        rbtnMoiNhat = findViewById(R.id.rbtnMoiNhat);
        rbtnGanToi = findViewById(R.id.rbtnGanToi);
        radioGrTab = findViewById(R.id.radioGrTab);

        firebaseAuth = FirebaseAuth.getInstance();

        pageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewpagerContainer.setAdapter(pageAdapter);
        viewpagerContainer.addOnPageChangeListener(this);

        viewpagerContainer.addOnPageChangeListener(this);
        radioGrTab.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("kiemtra", position + "");
        switch (position) {
            case 0:
                rbtnMoiNhat.setChecked(true);
                break;
            case 1:
                rbtnGanToi.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.d("kiemtra", checkedId + "");
        switch (checkedId) {
            case R.id.rbtnMoiNhat:
                viewpagerContainer.setCurrentItem(0);
                break;
            case R.id.rbtnGanToi:
                viewpagerContainer.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        firebaseAuth.signOut();
    }
}
