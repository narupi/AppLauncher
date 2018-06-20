package com.example.naru.traceofeffort;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tab1Fragment extends Activity {

    List<String>cklist = new ArrayList<String>();
    List<String>cklist2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1_fragment);

        // 端末にインストール済のアプリケーション一覧情報を取得
        final PackageManager pm = getPackageManager();
        final int flags = PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS;
        final List<ApplicationInfo> installedAppList = pm.getInstalledApplications(flags);


        try {
            FileInputStream in = openFileInput("test.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                cklist.add(tmp);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<cklist.size();i++) {
            //cklistの要素の中にダブってるやつが何個あるかをカウントする
            int count=0;
            for(int j=0;j<cklist.size();j++) {
                if(cklist.get(i).equals(cklist.get(j))){
                    count++;
                }
            }
            if(count%2 != 0){
                cklist2.add(cklist.get(i));
            }
        }


        // リストに一覧データを格納する
        final List<AppData> dataList = new ArrayList<AppData>();
        for(int i=0;i<cklist2.size();i++) {
            for (ApplicationInfo app : installedAppList) {
                if(cklist2.get(i).equals(app.loadLabel(pm).toString())) {
                    AppData data = new AppData();
                    data.label = app.loadLabel(pm).toString();
                    data.icon = app.loadIcon(pm);
                    data.pname = app.packageName;
                    dataList.add(data);
                }
            }
        }
        final ListView listView = (ListView) findViewById(R.id.listView3);
        listView.setAdapter(new AppListAdapter(this, dataList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String packname = dataList.get(position).pname;
                Intent intent = pm.getLaunchIntentForPackage(packname);
                startActivity(intent);
            }
        });

    }
        // アプリケーションデータ格納クラス
        private static class AppData {
            String label;
            Drawable icon;
            String pname;
        }

        // アプリケーションのラベルとアイコンを表示するためのアダプタークラス
        private static class AppListAdapter extends ArrayAdapter<AppData> {

            private final LayoutInflater mInflater;

            public AppListAdapter(Context context, List<AppData> dataList) {
                super(context, R.layout.activity_s_);
                mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                addAll(dataList);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder holder = new ViewHolder();

                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.activity_s_, parent, false);
                    holder.textLabel = (TextView) convertView.findViewById(R.id.label);
                    holder.imageIcon = (ImageView) convertView.findViewById(R.id.icon);
                    holder.packageName = (TextView) convertView.findViewById(R.id.pname);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                // 表示データを取得
                final AppData data = getItem(position);
                // ラベルとアイコンをリストビューに設定
                holder.textLabel.setText(data.label);
                holder.imageIcon.setImageDrawable(data.icon);
                holder.packageName.setText(data.pname);

                return convertView;
            }
        }
    // ビューホルダー
    private static class ViewHolder {
        TextView textLabel;
        ImageView imageIcon;
        TextView packageName;
    }
}



