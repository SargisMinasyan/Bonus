package com.example.minas.bonus.edit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minas.bonus.APIService;
import com.example.minas.bonus.ApiUtils;
import com.example.minas.bonus.LoginActivity;
import com.example.minas.bonus.R;
import com.example.minas.bonus.client.Bonus;
import com.example.minas.bonus.client.Client;
import com.example.minas.bonus.client.ResponsePOJO;
import com.example.minas.bonus.register.RegistretionActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAndBonusActivity extends AppCompatActivity {

    Calendar calendar;
    String stringdata;
    Date date;
    private ImageView barCode ;
    private TextView username ;
    private TextView email ;
    private TextView phone ;
    private TextView amount_with_percent;
    private TextView your_bonus_Sum;
    private EditText amount;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_edit_and_bonus);
        barCode=(ImageView)findViewById(R.id.bar_code_bonus_edit);
        username=(TextView) findViewById(R.id.username_edit_bonus);
        email=(TextView) findViewById(R.id.email_edit_bonus);
        phone=(TextView) findViewById(R.id.phone_edit_bonus);
        amount_with_percent=(TextView) findViewById(R.id.amount_with_percent);
        your_bonus_Sum=(TextView) findViewById(R.id.your_bonus_sum);
        amount=(EditText) findViewById(R.id.amount_edit_bonus);
        apiService= ApiUtils.getAPIService();
        QRCodeWriter writer = new QRCodeWriter();
        calendar=Calendar.getInstance();

        date=calendar.getTime();
        stringdata=""+date;
        try {
            BitMatrix bitMatrix = writer.encode(getIntent().getStringExtra(LoginActivity.USERNAME), BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLUE : Color.WHITE);
                }
            }
            barCode.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        apiService.getClientInfo(getIntent().getStringExtra(LoginActivity.USERNAME)).enqueue(new Callback<ResponsePOJO<Client>>() {
            @Override
            public void onResponse(Call<ResponsePOJO<Client>> call, Response<ResponsePOJO<Client>> response) {
                if(response.body().getStatus().equals("200")){

                    username.setText(response.body().getObject().getUsername());
                    email.setText(response.body().getObject().getEmail());
                    phone.setText(response.body().getObject().getPhone());

                    Integer sum=0;
                    List<Bonus> list=response.body().getObject().getBonuses();
                    for (int i=0;i<list.size();i++){
                        sum += Integer.parseInt(list.get(i).getAmount());
                    }
                    your_bonus_Sum.setText(""+sum);

                }
                else {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponsePOJO<Client>> call, Throwable t) {

            }
        });
    }

    public void save(View view) {
        apiService.setClientNewInfo(username.getText().toString(),
                stringdata,
                Integer.parseInt(amount.getText().toString())).
                enqueue(new Callback<ResponsePOJO<Client>>() {
            @Override
            public void onResponse(Call<ResponsePOJO<Client>> call, Response<ResponsePOJO<Client>> response) {
                Intent intent=new Intent(EditAndBonusActivity.this, RegistretionActivity.class);
                startActivity(intent);

            }
            @Override
            public void onFailure(Call<ResponsePOJO<Client>> call, Throwable t) {

            }
        });

        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
