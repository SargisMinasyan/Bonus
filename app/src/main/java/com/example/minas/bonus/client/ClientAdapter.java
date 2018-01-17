package com.example.minas.bonus.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minas.bonus.R;

import java.util.List;

public class ClientAdapter extends BaseAdapter {

    Context context;
    List<UserDataAndAmount> userList;
    LayoutInflater layoutInflater;
    public ClientAdapter() {
    }

    public ClientAdapter(Context context, List<UserDataAndAmount> userList) {
        layoutInflater= LayoutInflater.from(context);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public UserDataAndAmount getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view==null){
            view=layoutInflater.inflate(R.layout.client_item,viewGroup,false);


        }

        TextView amount=(TextView) view.findViewById(R.id.amount);
        TextView data=(TextView) view.findViewById(R.id.data);
        amount.setText(getItem(i).getAmount());
        data.setText(getItem(i).getDate().toString());
        return view;
    }
}
