package com.example.demo.foody.model;

import com.example.demo.foody.controller.interfaces.ThucDonInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThucDon {

    String mathucdon;
    String tenthucdon;
    List<MonAn> monAnList;

    public List<MonAn> getMonAnList() {
        return monAnList;
    }

    public void setMonAnModelList(List<MonAn> monAnList) {
        this.monAnList = monAnList;
    }

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }

    public void getDanhSachThucDonQuanAn(String maquanan, final ThucDonInterface thucDonInterface){
        DatabaseReference nodeThucDonQuanAn = FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maquanan);
        nodeThucDonQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                final List<ThucDon> thucDons = new ArrayList<>();

                for (DataSnapshot valueThucDon : dataSnapshot.getChildren()){
                    final ThucDon thucDon = new ThucDon();

                    DatabaseReference nodeThucDon = FirebaseDatabase.getInstance().getReference().child("thucdons").child(valueThucDon.getKey());
                    nodeThucDon.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotThucDon) {
                            String mathucdon = dataSnapshotThucDon.getKey();
                            thucDon.setMathucdon(mathucdon);
                            thucDon.setTenthucdon(dataSnapshotThucDon.getValue(String.class));
                            List<MonAn> monAnList = new ArrayList<>();

                            for (DataSnapshot valueMonAn : dataSnapshot.child(mathucdon).getChildren()){
                                MonAn monAn = valueMonAn.getValue(MonAn.class);
                                monAn.setMamon(valueMonAn.getKey());
                                monAnList.add(monAn);
                            }

                            thucDon.setMonAnModelList(monAnList);
                            thucDons.add(thucDon);
                            thucDonInterface.getThucDon(thucDons);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
