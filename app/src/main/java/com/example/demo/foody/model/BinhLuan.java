package com.example.demo.foody.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;

public class BinhLuan implements Parcelable {

    double chamdiem;
    long luotthich;
    NguoiDung nguoiDung;
    String noidung;
    String tieude;


    public String getMabinhluan() {
        return mabinhluan;
    }

    public void setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
    }

    String mabinhluan;

    public List<String> getHinhanhBinhLuanList() {
        return hinhanhBinhLuanList;
    }

    public void setHinhanhBinhLuanList(List<String> hinhanhList) {
        this.hinhanhBinhLuanList = hinhanhList;
    }

    List<String> hinhanhBinhLuanList;

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    String mauser;


    public BinhLuan() {

    }

    public double getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(double chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setThanhVienModel(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public void ThemBinhLuan(String maQuanAn, BinhLuan binhLuan, final List<String> listHinh) {
        DatabaseReference nodeBinhLuan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String mabinhluan = nodeBinhLuan.child(maQuanAn).push().getKey();

        nodeBinhLuan.child(maQuanAn).child(mabinhluan).setValue(binhLuan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    if (listHinh.size() > 0) {
                        for (String valueHinh : listHinh) {
                            Uri uri = Uri.fromFile(new File(valueHinh));
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh/" + uri.getLastPathSegment());
                            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                }
                            });
                        }
                    }

                }
            }
        });

        if (listHinh.size() > 0) {
            for (String valueHinh : listHinh) {
                Uri uri = Uri.fromFile(new File(valueHinh));
                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.getLastPathSegment());
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(chamdiem);
        dest.writeLong(luotthich);
        dest.writeString(noidung);
        dest.writeString(tieude);
        dest.writeString(mabinhluan);
        dest.writeStringList(hinhanhBinhLuanList);
        dest.writeString(mauser);
        dest.writeParcelable(nguoiDung, flags);
    }

    protected BinhLuan(Parcel in) {
        chamdiem = in.readDouble();
        luotthich = in.readLong();
        noidung = in.readString();
        tieude = in.readString();
        mabinhluan = in.readString();
        hinhanhBinhLuanList = in.createStringArrayList();
        mauser = in.readString();
        nguoiDung = in.readParcelable(NguoiDung.class.getClassLoader());
    }

    public static final Creator<BinhLuan> CREATOR = new Creator<BinhLuan>() {
        @Override
        public BinhLuan createFromParcel(Parcel in) {
            return new BinhLuan(in);
        }

        @Override
        public BinhLuan[] newArray(int size) {
            return new BinhLuan[size];
        }
    };
}
