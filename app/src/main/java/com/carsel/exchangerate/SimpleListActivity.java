package com.carsel.exchangerate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
/*
练习自定义列表
 */
public class SimpleListActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<HashMap<String, String>> listItems = new ArrayList<>();
    String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        listView = findViewById(R.id.list_view);
        int list_size;
         Intent intent = getIntent();//获取intent
         Bundle bdl = intent.getExtras();
         if(bdl!=null){
             list_size = bdl.getInt("list_size");
             for(int k=0;k<list_size;k++){
                 HashMap<String,String> map = new HashMap<>();
                 map = (HashMap<String, String>) bdl.get(String.valueOf(k));
                 listItems.add(map);
             }
         }

         if(listView!=null){
             MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, listItems);
             listView.setAdapter(myAdapter);
         }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object itemAtPosition = listView.getItemAtPosition(position);
                HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
                String titleStr = map.get("ItemTitle");
                String detailStr = map.get("ItemDetail");
                Log.i(TAG,"onItemClick: titleStr="+titleStr);
                Log.i(TAG,"onItemClick: detailStr="+detailStr);


                Intent config = new Intent(SimpleListActivity.this, ChangeAnytimeActivity.class);
                Bundle bdl = new Bundle();
                bdl.putString("ItemTitle",titleStr);
                bdl.putString("ItemDetail",detailStr);
                config.putExtras(bdl);
                startActivityForResult(config,3);


            }
        });
    }

}
