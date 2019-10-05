package com.carsel.exchangerate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.file.FileVisitOption;
import java.text.DecimalFormat;
public class ConfigActivity extends AppCompatActivity {
    Button save;

    float dollar1,euro1,won1;
    EditText text1,text2,text3;
    String dollar2,euro2,won2;
    SharedPreferences sp;
    String TAG;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);
        TAG="TAG";

        save = findViewById(R.id.button_save);
        text1 = findViewById(R.id.editText2);
        text2 = findViewById(R.id.editText3);
        text3 = findViewById(R.id.editText4);

        Intent intent = getIntent();//获取intent
        Bundle bdl = intent.getExtras();//获取Bundle
        if(bdl!=null) {//如果不为空则提前数据
            dollar1 = bdl.getFloat("key_dollar", 0.0f);//获取原来的数据
            euro1 = bdl.getFloat("key_euro", 0.0f);
            won1 = bdl.getFloat("key_won", 0.0f);

            Log.i(TAG,"Bundle--------------------------------------------");
            Log.i(TAG,"the dollar_rate get from config is = "+dollar1);
            Log.i(TAG,"the euro_rate get from config is = "+euro1);
            Log.i(TAG,"the won_rate get from config is = "+won1);
        }

        text1.setText(String.valueOf(dollar1));//重置数据
        text2.setText(String.valueOf(euro1));
        text3.setText(String.valueOf(won1));
    }

    public void Save(View view){

        dollar2 = text1.getText().toString();//获取输入的新数据
        euro2 = text2.getText().toString();
        won2 = text3.getText().toString();
        if(dollar2==null||euro2==null||won2==null){//如果是空的
            Toast.makeText(this, "please input the exchange!", Toast.LENGTH_SHORT).show();
        }
        else if(dollar2.length()==0||euro2.length()==0||won2.length()==0){//如果没有输入
            Toast.makeText(this, "please input the exchange!", Toast.LENGTH_SHORT).show();
        }
        else {//检查是否输入0汇率
              dollar1=Float.parseFloat(dollar2);
              euro1=Float.parseFloat(euro2);
              won1=Float.parseFloat(won2);
              if(dollar1==0||euro1==0||won1==0){//如果输入汇率为0
                  Toast.makeText(this, "The exchange is zero!", Toast.LENGTH_SHORT).show();
              }

              else{//如果输入的汇率一切正确
                  Intent intent = getIntent();
                  Bundle bdl = new Bundle();
                  sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);

                  SharedPreferences.Editor editor = sp.edit();
                  editor.putFloat("dollar_rate",dollar1);
                  editor.putFloat("euro_rate",euro1);
                  editor.putFloat("won_rate",won1);
                  editor.apply();
                  Log.i(TAG,"myrate--------------------------------------------");
                  Log.i(TAG,"the dollar_rate put in myrate is = "+dollar1);
                  Log.i(TAG,"the euro_rate put in myrate is = "+euro1);
                  Log.i(TAG,"the won_rate put in myrate is = "+won1);

                  bdl.putFloat("key_dollar",dollar1);//注意存放的类型
                  bdl.putFloat("key_euro",euro1);//如果存放与接收时使用的方法不同，则即使关键词一样也找不到
                  bdl.putFloat("key_won",won1);
                  intent.putExtras(bdl);
                  setResult(2,intent);//设置resultCode及带回的数据
                  finish();//返回原来的页面
              }
        }
    }
}
