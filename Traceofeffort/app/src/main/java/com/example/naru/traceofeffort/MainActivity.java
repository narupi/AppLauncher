package com.example.naru.traceofeffort;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
  //  public static int county = 0;
    public static int c = 0;
    private static final int REQUEST_CODE = 1000;
    public int kck=0;
    //private static final int REQUEST_CODE = 0;
   // public static List<ApplicationInfo> installedAppList;
   // public static List<AppData> dataList;
   // public static String resultsString = "";
   // public static ArrayList<String> results;
    public static ObjectAnimator alphaAnimator;
    public List<AppData> dataList = new ArrayList<AppData>();
    Toast my_toast;
    /*URL url = null;
    InputStream is = null;
    BufferedReader reader = null;
    Bitmap icon = null;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 端末にインストール済のアプリケーション一覧情報を取得
        final PackageManager pm = getPackageManager();
        final int flags = PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS;
        final List<ApplicationInfo> installedAppList = pm.getInstalledApplications(flags);

        // リストに一覧データを格納する
        dataList = new ArrayList<AppData>();
        for (ApplicationInfo app : installedAppList) {
            AppData data2 = new AppData();
            data2.label = app.loadLabel(pm).toString();
            data2.icon = app.loadIcon(pm);
            data2.pname = app.packageName;
            dataList.add(data2);
        }
/*
        try{
            url = new URL("http://api.openweathermap.org/data/2.5/find?lat=43.067885&lon=141.355539&cnt=1&20e5120da31ad0832923df8ba8701622");
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        try {
            is = url.openConnection().getInputStream();
        }catch (IOException e){
            e.printStackTrace();
        }

        // JSON形式で結果が返るためパースのためにStringに変換する
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while (null != (line = reader.readLine())) {
                sb.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        String data = sb.toString();

        try {
            JSONObject rootObj = new JSONObject(data);
            JSONArray listArray = rootObj.getJSONArray("list");

            JSONObject obj = listArray.getJSONObject(0);

            // 地点ID
            int id = obj.getInt("id");

            // 地点名
            String cityName = obj.getString("name");

            // 気温(Kから℃に変換)
            JSONObject mainObj = obj.getJSONObject("main");
            float currentTemp = (float) (mainObj.getDouble("temp") - 273.15f);

            float minTemp = (float) (mainObj.getDouble("temp_min") - 273.15f);

            float maxTemp = (float) (mainObj.getDouble("temp_max") - 273.15f);

            // 湿度
            if (mainObj.has("humidity")) {
                int humidity = mainObj.getInt("humidity");
            }

            // 取得時間
            long time = obj.getLong("dt");

            // 天気
            JSONArray weatherArray = obj.getJSONArray("weather");
            JSONObject weatherObj = weatherArray.getJSONObject(0);
            String iconId = weatherObj.getString("icon");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        try {
            String requestUrl = "http://openweathermap.org/img/w/04d.png";
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            icon = BitmapFactory.decodeStream(in);
            in.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ImageView wether_icon = (ImageView) findViewById(R.id.imageView);
        wether_icon.setImageBitmap(icon);

*/

        Button button2 = (Button) findViewById(R.id.button4);
        button2.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           // カレンダーアプリを呼び出すIntentの生成
                                           Intent intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                                           //スケジュールのタイトル
                                           intent.putExtra(CalendarContract.Events.TITLE, "タイトル");
                                           //スケジュールの開始時刻 ゼロで現在時刻
                                           intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 0);
                                           //スケジュールの終了時刻　ゼロで現在時刻＋１時間
                                           intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 0);
                                           //スケジュールの場所
                                           intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "場所");
                                           //スケジュールの詳細内容
                                           intent.putExtra(CalendarContract.Events.DESCRIPTION, "サンプル");
                                           //スケジュールに招待するメールアドレス
                                           intent.putExtra(Intent.EXTRA_EMAIL, "スケジュールに招待するメールアドレス");
                                           //スケジュールのアクセスレベル
                                           intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_DEFAULT);
                                           //スケジュールの同時持ちの可否
                                           intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE);
                                           //Intentを呼び出す
                                           startActivity(intent);
                                       }
                                   }
        );

        final Button button_ = (Button) findViewById(R.id.button2);
        button_.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           // インテントの生成
                                           Intent intent = new Intent();
                                           intent.setClassName("com.example.naru.traceofeffort", "com.example.naru.traceofeffort.Search");
                                           // subActivity の起動
                                           startActivity(intent);
                                       }
                                   }
        );

        final Button button_1 = (Button) findViewById(R.id.button3);
        button_1.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            // インテントの生成
                                            Intent intent = new Intent();
                                            intent.setClassName("com.example.naru.traceofeffort", "com.example.naru.traceofeffort.Delete2Activity");
                                            // subActivity の起動
                                            startActivity(intent);
                                        }
                                    }
        );

        //web_search
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                   my_toast = Toast.makeText(MainActivity.this,
                            "音声認識が利用できない環境です", Toast.LENGTH_SHORT);
                    my_toast.show();
                }
            }
        });

        final Button buttony = (Button) findViewById(R.id.button10);
        buttony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kck++;
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
                    my_toast=Toast.makeText(MainActivity.this,
                            "音声認識が利用できない環境です", Toast.LENGTH_SHORT);
                    my_toast.show();
                }
            }
        });

        final ToggleButton tbutton = (ToggleButton) findViewById(R.id.toggleButton);
        tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // トグルボタンがクリックされたと時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                // トグルボタンの状態が変更された時の処理を記述
                if (isChecked) {
                    animateAnimatorSetSample(button, 58, -650, 0);//音声検索＿web
                    animateAnimatorSetSample(button_1, 0, -650, 0);//アプリ削除
                    animateAnimatorSetSample(button_, 28, -650, 0);//アプリ起動
                    animateAnimatorSetSample(buttony,90,-650,0);//音声検索＿アプリ
                } else {
                  /*  animateAnimatorSetSample(button, 0, 0, 1);
                    animateAnimatorSetSample(button_1, 0, 0, 1);
                   animateAnimatorSetSample(button_, 0, 0, 1);
                    animateAnimatorSetSample(buttony,0,0,1);*/
                    // インテントの生成
                    Intent intent = new Intent();
                    intent.setClassName("com.example.naru.traceofeffort", "com.example.naru.traceofeffort.CsActivity");
                    // subActivity の起動
                    startActivity(intent);

                }
            }
        });
    }

    // アクティビティ終了時に呼び出される
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 自分が投げたインテントであれば応答する
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            String resultsString = "";

            // 結果文字列リスト
            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            //結果の候補1を代入
            resultsString += results.get(0);


            // トーストを使って結果を表示
            Toast.makeText(this, resultsString, Toast.LENGTH_SHORT).show();
            if (kck != 0) {
                final PackageManager pm1 = getPackageManager();
                for(int j=0;j < results.size();j++) {
                    for (int i = 0; i < dataList.size(); i++) {
                        int ck = dataList.get(i).label.toLowerCase().indexOf(results.get(j).toLowerCase());
                        if (ck != -1) {
                            String start = dataList.get(i).pname;
                            // トーストを使って結果を表示
                            Toast.makeText(this, start, Toast.LENGTH_SHORT).show();
                            Intent intent = pm1.getLaunchIntentForPackage(start);
                            startActivity(intent);
                        }


                    }
                }
            } else {
                Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                search.putExtra(SearchManager.QUERY, resultsString);
                startActivity(search);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void animateAnimatorSetSample(Button target, float degree, float distance, int state) {

        // AnimatorSetに渡すAnimatorのリスト
        List<Animator> animatorList = new ArrayList<Animator>();
        if(state == 0) {
            // alphaプロパティを0fから1fに変化
            alphaAnimator = ObjectAnimator.ofFloat(target, "alpha", 0f, 1f);
        }
        else {
            alphaAnimator = ObjectAnimator.ofFloat(target, "alpha", 1f, 0f);
        }

        alphaAnimator.setDuration(500);
        // リストに追加します
        animatorList.add(alphaAnimator);

        // 距離と半径から到達点となるX座標、Y座標を求める
        float toX = (float) (distance * Math.cos(Math.toRadians(degree)));
        float toY = (float) (distance * Math.sin(Math.toRadians(degree)));

        // translationXプロパティを0fからtoXに変化
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", 0f, toX);
        // translationYプロパティを0fからtoYに変化
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", 0f, toY);
        // rotationプロパティを0fから360に変化
        // PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat("rotation", 0f, 360f);

        // targetに対してholderX, holderY, holderRotationを同時に実行
        ObjectAnimator translationXYAnimator =
                ObjectAnimator.ofPropertyValuesHolder(target, holderX, holderY/*,holderRotaion*/);

        translationXYAnimator.setDuration(500);
        // リストに追加
        animatorList.add(translationXYAnimator);

        final AnimatorSet animatorSet = new AnimatorSet();
        // リストのAnimatorを順番に実行
        animatorSet.playSequentially(animatorList);

        // 開始
        animatorSet.start();
    }
    public static class AppData {
        private String label;
        private Drawable icon;
        private String pname;
    }
}




