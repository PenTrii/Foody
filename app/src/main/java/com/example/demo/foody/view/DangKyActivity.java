package com.example.demo.foody.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.foody.R;
import com.example.demo.foody.controller.DangKyController;
import com.example.demo.foody.model.NguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmit;
    EditText edtEmail, edtPass, edtRePass;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DangKyController dangKyController;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnSubmit = findViewById(R.id.btnSubmit);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtRePass = findViewById(R.id.edtRePass);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setIndeterminate(true);

        progressDialog.show();

        final String email = edtEmail.getText().toString();
        String pass = edtPass.getText().toString();
        String repass = edtRePass.getText().toString();
        String thongbaoloi = "Bạn chưa nhập!!!";

        if (email.trim().length() == 0) {
            thongbaoloi += getString(R.string.email);
            Toast.makeText(this, thongbaoloi, Toast.LENGTH_SHORT).show();
        } else if (pass.trim().length() == 0) {
            thongbaoloi += getString(R.string.pass);
            Toast.makeText(this, thongbaoloi, Toast.LENGTH_SHORT).show();
        } else if (!repass.equals(pass)) {
            Toast.makeText(this, getString(R.string.repass), Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        NguoiDung nguoiDung = new NguoiDung();
                        nguoiDung.setHoten(email);
                        nguoiDung.setHinhanh("user.png");
                        String uid = task.getResult().getUser().getUid();
                        Log.d("kiemtraNguoiDung", uid);

                        dangKyController = new DangKyController();
                        dangKyController.ThemThongTinNguoiDungController(nguoiDung, uid);
                        Toast.makeText(DangKyActivity.this, "Đăng ký thành công!!!", Toast.LENGTH_SHORT).show();
                        finish();

                        firebaseAuth.signOut();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(DangKyActivity.this, "Đăng ký kh thành công!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
