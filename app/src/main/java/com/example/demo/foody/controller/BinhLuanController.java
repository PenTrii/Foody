package com.example.demo.foody.controller;

import com.example.demo.foody.model.BinhLuan;

import java.util.List;

public class BinhLuanController {
    BinhLuan binhLuan;

    public BinhLuanController() {
        binhLuan = new BinhLuan();
    }

    public void ThemBinhLuan(String maQuanAn, BinhLuan binhLuan, List<String> listHinh) {
        binhLuan.ThemBinhLuan(maQuanAn, binhLuan, listHinh);
    }
}
