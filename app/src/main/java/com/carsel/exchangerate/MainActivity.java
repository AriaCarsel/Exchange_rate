package com.carsel.exchangerate;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import static android.widget.Toast.LENGTH_LONG;
public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Button btn1,btn2,btn3;
    String money;
    double dollarRate,euroRate,wonRate,res;
    DecimalFormat df;
    String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText1);
        textView = findViewById(R.id.textView1);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        df = new DecimalFormat("#.00");

        //初始汇率
        dollarRate=0.1405;
        euroRate=0.1279;
        wonRate=167.8282;
    }

    public void Dollar(View view){
        money=editText.getText().toString();
        if(money==null||money.length()==0||money.equals("0")){//如果输入不规范，则提示输入错误
            Toast.makeText(this,"please input correct RMB",LENGTH_LONG).show();
            textView.setText("hello");
            editText.setText("");//同时清空
        }
        else{//输入有效
            res=Double.parseDouble(money);
            if(dollarRate==0){
                textView.setText("exchange is null!");//汇率为空
                editText.setText("");//同时清空
            }
            else{
                res=res*dollarRate;
                money=df.format(res);
                textView.setText(money);
                editText.setText("");//清空
            }
        }
    }
//    EURO
    public void Euro(View view){
        money=editText.getText().toString();
        if(money==null||money.length()==0||money.equals("0")){//如果输入不规范，则提示输入错误
            Toast.makeText(this,"please input correct RMB",LENGTH_LONG).show();
            textView.setText("hello");
            editText.setText("");//同时清空
        }
        else{//输入有效
            res=Double.parseDouble(money);
            if(euroRate==0){
                textView.setText("exchange is null!");//汇率为空
                editText.setText("");//同时清空
            }
            else{
                res=res*euroRate;
                money=df.format(res);
                textView.setText(money);
                editText.setText("");//清空
            }
        }
    }
//WON
    public void Won(View view){
        money=editText.getText().toString();
        if(money==null||money.length()==0||money.equals("0")){//如果输入不规范，则提示输入错误
            Toast.makeText(this,"please input correct RMB",LENGTH_LONG).show();
            textView.setText("hello");
            editText.setText("");//同时清空
        }
        else{//输入有效
            res=Double.parseDouble(money);
            if(wonRate==0){
                textView.setText("exchange is null!");//汇率为0
                editText.setText("");//同时清空
            }
            else{
                res=res*wonRate;
                money=df.format(res);
                textView.setText(money);
                editText.setText("");//清空
            }
        }
    }
    public void Config(View view){//打开新页面
        Intent config;//跳转工具
        config = new Intent(this, ConfigActivity.class);//打开config页面
        //装入Bundle
        Bundle bdl = new Bundle();
        bdl.putDouble("key_dollar",dollarRate);
        bdl.putDouble("key_euro",euroRate);
        bdl.putDouble("key_won",wonRate);
        //config传输数据
        config.putExtras(bdl);
        startActivityForResult(config,1);
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1&&resultCode==2){
            Bundle bdl = data.getExtras();
            if(bdl!=null) {
                dollarRate = bdl.getDouble("key_dollar", 0.0);
                euroRate = bdl.getDouble("key_euro", 0.0);
                wonRate = bdl.getDouble("key_won", 0.0);
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
}
