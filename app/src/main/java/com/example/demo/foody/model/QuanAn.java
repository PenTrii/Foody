package com.example.demo.foody.model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.demo.foody.controller.interfaces.MoiDayInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanAn implements Parcelable {

    boolean giaohang;
    String giodongcua, giomocua, tenquanan, maquanan, tenwifi, matkhauwifi;

    public String getTenwifi() {
        return tenwifi;
    }

    public void setTenwifi(String tenwifi) {
        this.tenwifi = tenwifi;
    }

    public String getMatkhauwifi() {
        return matkhauwifi;
    }

    public void setMatkhauwifi(String matkhauwifi) {
        this.matkhauwifi = matkhauwifi;
    }

    long luotthich;
    List<String> tienich, hinhanhquanan;
    List<BinhLuan> binhLuanList;
    List<Bitmap> bitmapList;
    String diachi;
    double latitude, longitude, khoangcach;

    public double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(double khoangcach) {
        this.khoangcach = khoangcach;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public List<BinhLuan> getBinhLuanList() {
        return binhLuanList;
    }

    public void setBinhLuanList(List<BinhLuan> binhLuanList) {
        this.binhLuanList = binhLuanList;
    }

    private DatabaseReference databaseReference;

    public QuanAn() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public void LayDanhSachQuanAn(DataSnapshot dataSnapshot, final MoiDayInterface moiDayInterface, Location vitrihientai, int itemtieptheo, int itemdaco) {

        DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");
        //Lấy danh sách quán ăn
        int i = 0;
        for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()) {

            if (i == itemtieptheo) {
                break;
            }
            if (i < itemdaco) {
                i++;
                continue;
            }
            i++;
            QuanAn quanAnModel = valueQuanAn.getValue(QuanAn.class);
            quanAnModel.setMaquanan(valueQuanAn.getKey());

            //lay khoang cach
            Location vitriquanan = new Location("");
            vitriquanan.setLatitude(quanAnModel.getLatitude());
            vitriquanan.setLongitude(quanAnModel.getLongitude());
            double khoangcach = vitrihientai.distanceTo(vitriquanan) / 1000;
            quanAnModel.setKhoangcach(khoangcach);

            //Lấy danh sách hình ảnh của quán ăn theo mã
            DataSnapshot dataSnapshotHinhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());

            List<String> hinhanhlist = new ArrayList<>();

            for (DataSnapshot valueHinhQuanAn : dataSnapshotHinhQuanAn.getChildren()) {
                hinhanhlist.add(valueHinhQuanAn.getValue(String.class));
            }
            quanAnModel.setHinhanhquanan(hinhanhlist);

            //Lấy danh sách bình luân của quán ăn
            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
            List<BinhLuan> binhLuanModels = new ArrayList<>();

            for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                BinhLuan binhLuanModel = valueBinhLuan.getValue(BinhLuan.class);
                binhLuanModel.setMabinhluan(valueBinhLuan.getKey());
                NguoiDung nguoiDung = dataSnapshot.child("nguoidungs").child(binhLuanModel.getMauser()).getValue(NguoiDung.class);
                binhLuanModel.setThanhVienModel(nguoiDung);

                List<String> hinhanhBinhLuanList = new ArrayList<>();
                DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getMabinhluan());
                for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()) {
                    hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);

                binhLuanModels.add(binhLuanModel);
            }
            quanAnModel.setBinhLuanList(binhLuanModels);

            moiDayInterface.getDanhSachQuanAn(quanAnModel);
        }
    }

    private DataSnapshot dataRoot;

    public void getDanhSachQuanAn(final MoiDayInterface moiDayInterface, final Location vitrihientai, final int itemtieptheo, final int itemdaco) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                LayDanhSachQuanAn(dataSnapshot, moiDayInterface, vitrihientai, itemtieptheo, itemdaco);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if (dataRoot != null) {
            LayDanhSachQuanAn(dataRoot, moiDayInterface, vitrihientai, itemtieptheo, itemdaco);
        } else {
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tenquanan);
        dest.writeString(maquanan);
        dest.writeString(tenwifi);
        dest.writeString(matkhauwifi);
        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhquanan);
        dest.writeLong(luotthich);
        dest.writeString(diachi);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(khoangcach);
        dest.writeTypedList(binhLuanList);
    }

    protected QuanAn(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        maquanan = in.readString();
        tenwifi = in.readString();
        matkhauwifi = in.readString();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        luotthich = in.readLong();
        diachi = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        khoangcach = in.readDouble();
        binhLuanList = new ArrayList<>();
        in.readTypedList(binhLuanList, BinhLuan.CREATOR);
    }

    public static final Creator<QuanAn> CREATOR = new Creator<QuanAn>() {
        @Override
        public QuanAn createFromParcel(Parcel in) {
            return new QuanAn(in);
        }

        @Override
        public QuanAn[] newArray(int size) {
            return new QuanAn[size];
        }
    };
}
