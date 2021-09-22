package br.com.dw.comanda_facil_pro.telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

import br.com.dw.comanda_facil_pro.R;

public class Inicial extends AppCompatActivity {
    static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION=1;
    static final int REQUEST_CODE_CAMERA_STORAGE_PERMISSION = 1;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        //chama permissão
        writeStoragePermissionGranted();
        cameraPermissionGranted();
        //fim permissões

    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    public void tela_principal(View view) throws IOException, InterruptedException {
            Intent intent = new Intent(this, Principal.class);
            startActivity(intent);
    }


    //chama permissões
    public void writeStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //startPeriodicRequest();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
            }
        } else {
            return;
        }
    }

    public void cameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //startPeriodicRequest();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA_STORAGE_PERMISSION);
            }
        } else {
            return;
        }
    }
}