package com.example.naru.traceofeffort;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Search extends Activity {
    Context context = this;
    List<AppData> dataList = new ArrayList<AppData>();
    public static int count=0;
    final List<AppData> givelist = new ArrayList<AppData>();
    private static final int REQUEST_CODE = 1000;
    static String resultsString = "";
    Toast my_toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Button button_1 = (Button) findViewById(R.id.button6);
        button_1.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            // インテントの生成
                                            Intent intent = new Intent();
                                            intent.setClassName("com.example.naru.traceofeffort", "com.example.naru.traceofeffort.MainActivity");
                                            // subActivity の起動
                                            startActivity(intent);
                                        }
                                    }
        );


        final PackageManager pm = getPackageManager();
        final int flags = PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS;
        final List<ApplicationInfo> installedAppList = pm.getInstalledApplications(flags);


        // リストに一覧データを格納する
        dataList = new ArrayList<AppData>();
        for (ApplicationInfo app : installedAppList) {
            AppData data = new AppData();
            data.label = app.loadLabel(pm).toString();
            data.icon = app.loadIcon(pm);
            data.pname = app.packageName;
            dataList.add(data);
        }


        Collections.sort(dataList, new Comparator<AppData>() {
            public int compare(AppData t1, AppData t2) {
                return t1.getLabel().compareTo(t2.getLabel());
            }
        });

        // リストビューにアプリケーションの一覧を表示する

        //    final ListView listView = new ListView(this);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new AppListAdapter(this, dataList));

        //クリック処理
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             /*   ApplicationInfo item = installedAppList.get(position);
                PackageManager pManager = getPackageManager();
                Intent intent = pManager.getLaunchIntentForPackage(item.packageName);
                startActivity(intent);*/
                String packname = dataList.get(position).pname;
                Intent intent = pm.getLaunchIntentForPackage(packname);
                /*Toast.makeText(Search.this,
                        packname, Toast.LENGTH_LONG).show();*/
                startActivity(intent);
            }
        });

        //アプリキー入力検索
        // EditTextオブジェクトを取得
        final  EditText editText = (EditText)findViewById(R.id.editText);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 入力された文字を取得
                final String text = editText.getText().toString();
                //EnterKeyが押されたかを判定
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER) {

                    //ソフトキーボードを閉じる
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    for (int i = 0; i < dataList.size(); i++){
                        int ck = dataList.get(i).label.toLowerCase().indexOf(text.toLowerCase());

                        if (ck != -1) {
                            count++;
                            AppData temp = new AppData();
                            temp.label = dataList.get(i).label;
                            temp.icon = dataList.get(i).icon;
                            temp.pname = dataList.get(i).pname;
                            givelist.add(temp);

                        }
                        else if (dataList.size() == i+1 && count > 0) {
                            setContentView(R.layout.activity_cs);

                            final Button button = (Button)findViewById(R.id.button7);
                            button.setOnClickListener(new View.OnClickListener() {
                                                           public void onClick(View v) {
                                                               // インテントの生成
                                                               Intent intent = new Intent();
                                                               intent.setClassName("com.example.naru.traceofeffort", "com.example.naru.traceofeffort.MainActivity");
                                                               // subActivity の起動
                                                               startActivity(intent);
                                                           }
                                                       }
                            );

                            final ListView listView = (ListView) findViewById(R.id.listView2);
                            listView.setAdapter(new AppListAdapter(context,givelist));
                            //クリック処理
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String packname = givelist.get(position).pname;
                                    Intent intent = pm.getLaunchIntentForPackage(packname);
                                    startActivity(intent);
                                }
                            });


                        }

                    }
                    if(count==0){
                        my_toast=Toast.makeText(Search.this, "アプリが見つかりませんでした", Toast.LENGTH_SHORT);
                        my_toast.show();
                    }

                    return true;
                }

                return false;
            }
        });


        //アプリ音声検索
        final Button button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultsString = "";
                try {
                    // インテント作成
                    Intent intent = new Intent(
                            RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // ACTION_WEB_SEARCH
                    intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(
                            RecognizerIntent.EXTRA_PROMPT,
                            "音声を認識中"); //音声認識実行中

                    // インテント発行
                    startActivityForResult(intent, REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    // 音声認識が実行できない場合
                    my_toast.cancel();
                    Toast.makeText(Search.this,
                            "音声認識が利用できない環境です", Toast.LENGTH_SHORT);
                    my_toast.show();
                }
            }
        });

            }



    // アクティビティ終了時に呼び出される
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 自分が投げたインテントであれば応答する
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            final PackageManager pm1 = getPackageManager();

            // 結果文字列リスト
            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

                for(int j=0;j<results.size();j++){
                for (int i = 0; i < dataList.size(); i++) {
                    int ck = dataList.get(i).label.toLowerCase().indexOf(results.get(j).toLowerCase());
                    if (ck != -1) {
                       /* String start = dataList.get(i).pname;
                        // トーストを使って結果を表示
                        Toast.makeText(this, start, Toast.LENGTH_LONG).show();
                        Intent intent = pm1.getLaunchIntentForPackage(start);
                        startActivity(intent);*/
                        count++;
                        AppData temp = new AppData();
                        temp.label = dataList.get(i).label;
                        temp.icon = dataList.get(i).icon;
                        temp.pname = dataList.get(i).pname;
                        givelist.add(temp);
                    } else if (dataList.size() == i + 1 && count > 0) {
                        // Toast.makeText(Search.this, "アプリが見つかりませんでした", Toast.LENGTH_LONG).show();
                        setContentView(R.layout.activity_cs);

                        final Button button = (Button) findViewById(R.id.button7);
                        button.setOnClickListener(new View.OnClickListener() {
                                                      public void onClick(View v) {
                                                          // インテントの生成
                                                          Intent intent = new Intent();
                                                          intent.setClassName("com.example.naru.traceofeffort", "com.example.naru.traceofeffort.MainActivity");
                                                          // subActivity の起動
                                                          startActivity(intent);
                                                      }
                                                  }
                        );

                        final ListView listView = (ListView) findViewById(R.id.listView2);
                        listView.setAdapter(new AppListAdapter(context, givelist));
                        //クリック処理
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String packname = givelist.get(position).pname;
                                Intent intent = pm1.getLaunchIntentForPackage(packname);
                                startActivity(intent);
                            }
                        });

                    }
                }
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    // アプリケーションデータ格納クラス
    public static class AppData {
        private String label;
        private Drawable icon;
        private String pname;


        public String getLabel() {
            return label;
        }
       // public void setLabel(String label) {
         //   this.label = label;
       // }
    }

    // アプリケーションのラベルとアイコンを表示するためのアダプタークラス
    public static class AppListAdapter extends ArrayAdapter<AppData> {

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


