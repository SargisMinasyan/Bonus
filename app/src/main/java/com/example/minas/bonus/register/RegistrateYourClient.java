package com.example.minas.bonus.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.minas.bonus.APIService;
import com.example.minas.bonus.ApiUtils;
import com.example.minas.bonus.R;
import com.example.minas.bonus.client.Client;
import com.example.minas.bonus.client.ResponsePOJO;
import com.example.minas.bonus.client.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrateYourClient extends AppCompatActivity {

    APIService apiService;
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText password;
    private APIService mAPIService;

    private ImageView qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");

        apiService=ApiUtils.getAPIService();
        setContentView(R.layout.activity_ragistrate_your_client);
        username = (EditText) findViewById(R.id.username_registration_client);
        email = (EditText) findViewById(R.id.email_registration_client);
        phone = (EditText) findViewById(R.id.phone_registration_client);
        password = (EditText) findViewById(R.id.password_registration_client);
        mAPIService= ApiUtils.getAPIService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, RegistretionActivity.class);
        startActivity(intent);
        finish();
    }

    public void registration(View view) {


        if (username.getText().toString().isEmpty()
                ||password.getText().toString().isEmpty()
                ||email.getText().toString().isEmpty()
                ||phone.getText().toString().isEmpty()) {
            if (username.getText().toString().isEmpty())
                username.setError(""+R.string.required);
            if (password.getText().toString().isEmpty())
                password.setError(""+R.string.required);
            if (email.getText().toString().isEmpty())
                email.setError(""+R.string.required);
            if (phone.getText().toString().isEmpty())
                phone.setError(""+R.string.required);

        }else {

            if (qrCode == null) {

                QRCodeWriter writer = new QRCodeWriter();
                try {
                    BitMatrix bitMatrix = writer.encode(username.getText().toString(), BarcodeFormat.QR_CODE, 512, 512);
                    int width = bitMatrix.getWidth();
                    int height = bitMatrix.getHeight();
                    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }
                    (qrCode = (ImageView) findViewById(R.id.img_result_qr)).setImageBitmap(bmp);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            } else {

                apiService.registretion(username.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString()).
                        enqueue(new Callback<ResponsePOJO<Client>>() {
                            @Override
                            public void onResponse(Call<ResponsePOJO<Client>> call, Response<ResponsePOJO<Client>> response) {
                                if (response.body().getStatus().equals("200")){
                                    Intent intent=new Intent(RegistrateYourClient.this,RegistretionActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    username.setError(getResources().getString(R.string.theirnamealreadyinuse));
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponsePOJO<Client>> call, Throwable t) {

                            }
                        });


            }
        }
    }
}
