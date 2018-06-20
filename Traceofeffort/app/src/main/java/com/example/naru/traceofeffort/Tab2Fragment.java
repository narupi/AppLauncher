package com.example.naru.traceofeffort;

import android.app.ListActivity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tab2Fragment extends ListActivity {

    private ArrayAdapter<String> arrayadapter = null;
    List<String> savelist = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));

        final ListActivity activity = this;
        ListView list = this.getListView();
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        List<String> arr = new ArrayList<String>();



        // 端末にインストール済のアプリケーション一覧情報を取得
        final PackageManager pm = getPackageManager();
        final int flags = PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS;
        final List<ApplicationInfo> installedAppList = pm.getInstalledApplications(flags);


        //いんすとーるされているアプリのラベル情報
        for (ApplicationInfo app : installedAppList){
            arr.add(app.loadLabel(pm).toString());
        }

        Collections.sort(arr);

       /* try{
            FileInputStream in = openFileInput( "test.txt" );
            BufferedReader reader = new BufferedReader( new InputStreamReader( in , "UTF-8") );
            String tmp;
            while( (tmp = reader.readLine()) != null ){

                for(int i=0;i<list.getChildCount();i++){
                    CheckedTextView check = (CheckedTextView)list.getChildAt(i);

                    if(tmp.equals(check.getText()) && !check.isChecked()){
                        list.setItemChecked(i,true);

                        Toast toast = Toast.makeText(this, check.getText(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            }

            reader.close();
        }catch( IOException e ){
            e.printStackTrace();
        }*/


        arrayadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, arr);
        this.setListAdapter(arrayadapter);



    }
    List<String> ck = new ArrayList<String>();
    @Override
    protected void onListItemClick(ListView list, View v, int position, long id){

        for(int i=0;i<list.getChildCount();i++){
            int flag=0;
            CheckedTextView check = (CheckedTextView)list.getChildAt(i);
          for(int j=0;j<savelist.size();j++){
            if(check.getText().equals(savelist.get(j)))flag = 1;
        }
                if (check.isChecked() && flag != 1) {
                    String text = "";
                    text += check.getText();
                    savelist.add(text);
                    ck.add(text);
                   // Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                    //toast.show();
                }
                //表示上チェックはいってないけど、savelistにはチェックが入っていることになっているとき
                else if(!check.isChecked() && flag == 1){
                    for(int k=0;k<savelist.size();k++){
                        if(check.getText().equals(savelist.get(k))) savelist.remove(k);
                    }
                }

        }

        super.onListItemClick(list, v, position, id);
    }

    protected void onStop() {
        super.onStop();
        String str = "";

        if (savelist.size() != 0) {
            for (int s = 0; s < savelist.size(); s++) {
                str += savelist.get(s) + "\n";
            }

            try {
                FileOutputStream out = openFileOutput("test.txt", MODE_PRIVATE);
                out.write(str.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
/*
protected void onStart(){
    super.onStart();
    try{
        FileInputStream in = openFileInput( "test.txt" );
        BufferedReader reader = new BufferedReader( new InputStreamReader( in , "UTF-8") );
        String tmp;
        while( (tmp = reader.readLine()) != null ){

            for(int i=0;i<list.getChildCount();i++){
                CheckedTextView check = (CheckedTextView)list.getChildAt(i);

              if(/*tmp.equals(check.getText()) && !check.isChecked()){
                   list.setItemChecked(i,true);

                  Toast toast = Toast.makeText(this, check.getText(), Toast.LENGTH_SHORT);
                  toast.show();
               }
            }

        }

        reader.close();
    }catch( IOException e ){
        e.printStackTrace();
    }
}*/



}