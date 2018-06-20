package com.example.naru.traceofeffort;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Tab3Fragment extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // インテントの生成
        Intent intent = new Intent();
        intent.setClassName("com.example.naru.traceofeffort", "com.example.naru.traceofeffort.MainActivity");
        // subActivity の起動
        startActivity(intent);


    }
}