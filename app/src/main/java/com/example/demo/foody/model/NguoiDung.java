package com.example.demo.foody.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NguoiDung implements Parcelable {

    private DatabaseReference dataNguoiDung;

    String hoten;
    String hinhanh;
    String manguoidung;

    public NguoiDung() {
        dataNguoiDung = FirebaseDatabase.getInstance().getReference().child("nguoidungs");
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public void ThemThongTinNguoiDung(NguoiDung nguoiDung, String idu) {
        dataNguoiDung.child(idu).setValue(nguoiDung);
        Log.d("kiemtraNguoiDung", dataNguoiDung.child(idu).setValue(nguoiDung) + "");
    }

    public String getManguoidung() {
        return manguoidung;
    }

    public void setManguoidung(String manguoidung) {
        this.manguoidung = manguoidung;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hoten);
        dest.writeString(hinhanh);
        dest.writeString(manguoidung);
    }

    protected NguoiDung(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
        manguoidung = in.readString();
    }

    public static final Creator<NguoiDung> CREATOR = new Creator<NguoiDung>() {
        @Override
        public NguoiDung createFromParcel(Parcel in) {
            return new NguoiDung(in);
        }

        @Override
        public NguoiDung[] newArray(int size) {
            return new NguoiDung[size];
        }
    };
}
