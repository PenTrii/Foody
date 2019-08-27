package com.example.demo.foody.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.foody.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.annotations.Nullable;

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {

    ImageButton btnLoginGg, btnLoginFb;
    Button btnLogin, btnSignup;
    EditText edtEmail, edtPass;
    GoogleApiClient apiClient;
    public static int REQUEST_CODE_GOOGLE = 99;
    public static int CHECK_AUTH_PROVIDER_SIGN = 0;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        firebaseAuth = FirebaseAuth.getInstance();

        btnLoginGg = findViewById(R.id.btnLoginGg);
        btnLoginFb = findViewById(R.id.btnLoginFb);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);

        btnLoginGg.setOnClickListener(this);
        btnLoginFb.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        clientGoogle();
    }

    private void clientGoogle() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    private void loginGoogle(GoogleApiClient apiClient) {
        CHECK_AUTH_PROVIDER_SIGN = 1;
        Intent iloginGg = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(iloginGg, REQUEST_CODE_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GOOGLE) {
            Log.d("kiemtra", "handleSignInResult:" + resultCode);
            if (resultCode == RESULT_OK) {
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount account = signInResult.getSignInAccount();
                String tokenID = account.getIdToken();
                checkProviderFibase(tokenID);
            }
        } else {

        }
    }

    private void checkProviderFibase(String tokenID) {
        Log.d("kiemtra tokenID", tokenID);
        switch (CHECK_AUTH_PROVIDER_SIGN) {
            case 1:
                AuthCredential authCredentialGg = GoogleAuthProvider.getCredential(tokenID, null);
                firebaseAuth.signInWithCredential(authCredentialGg);
                break;
            case 2:
                AuthCredential authCredentialFb = FacebookAuthProvider.getCredential(tokenID);
                firebaseAuth.signInWithCredential(authCredentialFb).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            Toast.makeText(DangNhapActivity.this, "Failed Registration: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("kiemtra", e + "");
                            return;
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLoginGg:
                loginGoogle(apiClient);
                break;
            case R.id.btnLogin:
                DangNhap();
                break;
            case R.id.btnSignup:
                Intent iSignup = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(iSignup);
                break;
        }
    }

    private void DangNhap() {
        String email = edtEmail.getText().toString();
        String matkhau = edtPass.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Toast.makeText(this, "Dang nhap thanh cong!!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DangNhapActivity.this, TrangChuActivity.class);
            startActivity(intent);
        } else {
        }
    }
}
