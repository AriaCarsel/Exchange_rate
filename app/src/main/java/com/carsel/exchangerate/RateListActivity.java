package com.carsel.exchangerate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
public class RateListActivity extends AppCompatActivity {
    String TAG;
    ListView listView;
    List<String> list2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylist);
        TAG="TAG";
        listView = findViewById(R.id.my_list);

        Intent intent = getIntent();//获取intent
        Bundle bdl = intent.getExtras();//获取Bundle
        if(bdl!=null){
            list2=bdl.getStringArrayList("list");
        }

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String> (
                this, android.R.layout.simple_list_item_1,list2);
        listView.setAdapter(arrayAdapter);
    }
}
