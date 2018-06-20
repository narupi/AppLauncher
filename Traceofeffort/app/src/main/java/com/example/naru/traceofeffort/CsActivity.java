package com.example.naru.traceofeffort;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class CsActivity extends TabActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cs);
        initTabs();

    }

    protected void initTabs() {
        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, Tab1Fragment.class);
        spec = tabHost.newTabSpec("").setIndicator(
                "", res.getDrawable(R.drawable.ic_tab_1))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Tab2Fragment.class);
        spec = tabHost.newTabSpec("").setIndicator(
                "", res.getDrawable(R.drawable.ic_tab_2))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Tab3Fragment.class);
        spec = tabHost.newTabSpec("").setIndicator(
                "", res.getDrawable(R.drawable.ic_tab_3))
                .setContent(intent);
        tabHost.addTab(spec);

    }
}
