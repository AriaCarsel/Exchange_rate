package com.carsel.exchangerate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class GridViewActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    GridView gridView;
    MyAdapter adapter;
    String TAG="TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);

        ArrayList<HashMap<String,String>> items=new ArrayList<>();
        for(int i=0;i<10;i++){
            HashMap<String,String> map=new HashMap<>();
                map.put("ItemTitle", "○");
                map.put("ItemDetail", String.valueOf(i));
            items.add(map);
         }

        gridView = findViewById(R.id.grid_view);


        adapter = new MyAdapter(GridViewActivity.this,R.layout.list_item, items);
        gridView.setAdapter(adapter);
        gridView.setEmptyView(findViewById(R.id.no_data));

        gridView.setOnItemLongClickListener(this);


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").
                setMessage("请确认是否删除当前数据").
                setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG,"对话框事件处理--------");
                        adapter.remove(gridView.getItemAtPosition(position));

                    }
                }).setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
}
