package com.example.minas.bonus.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.minas.bonus.APIService;
import com.example.minas.bonus.ApiUtils;
import com.example.minas.bonus.LoginActivity;
import com.example.minas.bonus.client.Client;
import com.example.minas.bonus.client.ResponsePOJO;
import com.example.minas.bonus.edit.EditAndBonusActivity;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    APIService apiService= ApiUtils.getAPIService();
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setTitle("");

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }
    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
    @Override
    public void handleResult(Result rawResult) {
        Log.v(TAG, rawResult.getText());
        Log.v(TAG, rawResult.getBarcodeFormat().toString());


        Intent intent=new Intent(SimpleScannerActivity.this, EditAndBonusActivity.class);
        intent.putExtra(LoginActivity.USERNAME,rawResult.getText());
        startActivity(intent);

        finish();

    }


}