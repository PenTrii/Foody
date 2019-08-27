package com.example.demo.foody.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demo.foody.R;
import com.example.demo.foody.adapter.BinhLuanAdapter;
import com.example.demo.foody.adapter.HinhBinhLuanAdapter;
import com.example.demo.foody.controller.ThucDonController;
import com.example.demo.foody.model.BinhLuan;
import com.example.demo.foody.model.QuanAn;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    TextView txtTenQuanAn,txtDiaChiQuanAnChiTiet,txtThoiGianHoatDong,txtTrangThaiHoatDong,txtTongSoHinhAnh,
            txtTongSoBinhLuan,txtTongSoCheckIn,txtTongSoLuuLai,txtTieuDeToolbar,
            txtTenWifi, txtMatKhauWifi;
    ImageView imgQuanAn;
    QuanAn quanAn;
    RecyclerView recyclerBinhLuanChiTietQuanAn, recyclerViewHinhBinhLuan, recyclerThucDon;
    BinhLuanAdapter adapterBinhLuan;
    HinhBinhLuanAdapter hinhBinhLuanAdapter;
    LinearLayout layoutContainerBinhLuan;
//    View viewContainerTinhNang;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    List<Bitmap> bitmapList;
    BinhLuan binhLuan;
    ThucDonController thucDonController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_quan_an);

        quanAn = getIntent().getParcelableExtra("quanan");

        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtThoiGianHoatDong = findViewById(R.id.txtThoiGianHoatDong);
        txtTrangThaiHoatDong = findViewById(R.id.txtTrangThaiHoatDong);
        txtTongSoHinhAnh = findViewById(R.id.txtTongSoHinhAnh);
        txtTongSoBinhLuan = findViewById(R.id.txtTongSoBinhLuan);
        txtTongSoCheckIn = findViewById(R.id.txtTongSoCheckIn);
        txtTongSoLuuLai = findViewById(R.id.txtTongSoLuuLai);
        txtTieuDeToolbar = findViewById(R.id.txtTieuDeToolbar);
        txtDiaChiQuanAnChiTiet = findViewById(R.id.txtDiaChiQuanAnChiTiet);
        imgQuanAn = findViewById(R.id.imgQuanAn);
        layoutContainerBinhLuan = findViewById(R.id.layoutContainerBinhLuan);
        txtTenWifi = findViewById(R.id.txtTenWifi);
        txtMatKhauWifi = findViewById(R.id.txtMatKhauWifi);
        recyclerThucDon = findViewById(R.id.recyclerThucDon);
//        viewContainerTinhNang = findViewById(R.id.viewContainerTinhNang);
        recyclerViewHinhBinhLuan = (RecyclerView) findViewById(R.id.recyclerHinhBinhLuan);

        thucDonController = new ThucDonController();

        bitmapList = new ArrayList<>();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recyclerBinhLuanChiTietQuanAn = findViewById(R.id.recyclerBinhLuanChiTietQuanAn);

        hienThiChiTietQuanAn();
    }

    private void hienThiChiTietQuanAn() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = quanAn.getGiomocua();
        String giodongcua = quanAn.getGiodongcua();

        txtTieuDeToolbar.setText(quanAn.getTenquanan());

        try {
            Date dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giomocua);
            Date dateDongCua = dateFormat.parse(giodongcua);

            if(dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua))  {
                //gio mo cua
                txtTrangThaiHoatDong.setText("Đang mở cửa");
            }
            else {
                //dong cua
                txtTrangThaiHoatDong.setText("Đã đóng cửa");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        txtTenQuanAn.setText(quanAn.getTenquanan());
        txtThoiGianHoatDong.setText(quanAn.getGiomocua() + " - " + quanAn.getGiodongcua());
        txtTongSoBinhLuan.setText(quanAn.getBinhLuanList().size() + "");
        txtTongSoHinhAnh.setText(quanAn.getHinhanhquanan().size() + "");
        txtDiaChiQuanAnChiTiet.setText(quanAn.getDiachi());
        txtMatKhauWifi.setText("Mật khẩu:   " + quanAn.getMatkhauwifi());
        txtTenWifi.setText(quanAn.getTenwifi());

        //Load danh sach binh luan cua quan
        if (quanAn.getBinhLuanList().size() == 0) {
            layoutContainerBinhLuan.setVisibility(View.GONE);
        }
        else {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerBinhLuanChiTietQuanAn.setLayoutManager(layoutManager);
            adapterBinhLuan = new BinhLuanAdapter(this, R.layout.item_binh_luan_chi_tiet,
                    quanAn.getBinhLuanList());
            recyclerBinhLuanChiTietQuanAn.setAdapter(adapterBinhLuan);
            adapterBinhLuan.notifyDataSetChanged();
        }

        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAn.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imgQuanAn.setImageBitmap(bitmap);
            }
        });

        NestedScrollView nestedScrollViewChiTiet = findViewById(R.id.nestScrollViewChiTiet);
        nestedScrollViewChiTiet.smoothScrollTo(0,0);

        thucDonController.getDanhSachThucDonQuanAn(this, quanAn.getMaquanan(), recyclerThucDon);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in QuanAn.
        LatLng latLng = new LatLng(quanAn.getLatitude(), quanAn.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title(quanAn.getTenquanan()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        switch (id) {
//            case R.id.viewContainerTinhNang:
//                Intent i = new Intent(ChiTietQuanAnActivity.this, ChiDuongActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putDouble("latitude", quanAn.getLatitude());
//                bundle.putDouble("longitude", quanAn.getLongitude());
//                i.putExtras(bundle);
//                startActivity(i);
//                break;
//        }
    }
}
