package com.example.demo.foody.controller;

import com.example.demo.foody.model.NguoiDung;

public class DangKyController {

    NguoiDung nguoiDung;

    public DangKyController() {
        nguoiDung = new NguoiDung();
    }

    public void ThemThongTinNguoiDungController(NguoiDung nguoiDung, String uid) {
        nguoiDung.ThemThongTinNguoiDung(nguoiDung, uid);
    }
}
