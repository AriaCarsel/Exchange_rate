package com.carsel.exchangerate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;
public class MainActivity extends AppCompatActivity implements Runnable {
    EditText editText;
    TextView textView;
    Button btn1,btn2,btn3;
    String money,TAG;
    float dollarRate,euroRate,wonRate,res;
    ArrayList<HashMap<String, String>> listItems = new ArrayList<>();

    Handler handler;//管理不同线程之间的消息，协助线程消息传递
    Message msg;//线程之间消息实例
    DecimalFormat df;
    SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建实例
        TAG="TAG";
        editText = findViewById(R.id.editText1);
        textView = findViewById(R.id.textView1);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        df = new DecimalFormat("#.00");
        sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);//第一种写法
        // 获取上次修改的数据
        if(sharedPreferences!=null) {//如果不为空则修改
            dollarRate = sharedPreferences.getFloat("dollar_rate", 0.1405f);
            euroRate = sharedPreferences.getFloat("euro_rate", 0.1279f);
            wonRate = sharedPreferences.getFloat("won_rate", 167.8282f);
            Log.i(TAG,"myrate--------------------------------------------");
            Log.i(TAG,"the dollar_rate from myrate is = "+dollarRate);
            Log.i(TAG,"the euro_rate from myrate is = "+euroRate);
            Log.i(TAG,"the won_rate from myrate is = "+wonRate);
        }
        //收快递
        handler = new Handler(){//生命周期为窗口的打开到关闭
            public void handleMessage(Message mesg){//时刻监测

                if(mesg!=null&&mesg.what==5){//检查发送过来的快递单号
                    String res=mesg.obj.toString();//获得HTML
                    Document doc = Jsoup.parse(res);
                    Elements trs = doc.select("table").select("tr"); // 关键的一步 从html中把课表解析出来

                    for (int i = 1; i < trs.size(); i++) {
                        Elements tds = trs.get(i).select("td");
                        String name=tds.get(0).text().trim();//获取名称

                        for(int j=1;j<tds.size();j++) {
                            HashMap<String,String> map = new HashMap<>();

                            String x = tds.get(j).text().trim();//获取汇率
                            if(x.equals("--"))//如果当前汇率为空
                                continue;
                            else{
                                switch(name) {
                                    case "美元" : dollarRate=100/(Float.parseFloat(x));break;
                                    case "欧元" : euroRate=100/(Float.parseFloat(x));break;
                                    case "韩币" : wonRate=100/(Float.parseFloat(x));break;
                                    default : break;
                                }
                                map.put("ItemTitle",name);
                                map.put("ItemDetail",x);
                                listItems.add(map);
                                break;
                            }
                        }
                    }
               Log.i(TAG,"美元汇率为："+dollarRate);
               Log.i(TAG,"欧元汇率为："+euroRate);
               Log.i(TAG,"美元汇率为："+wonRate);
                //处理结束
                    for(int k=0;k<listItems.size();k++) {
                        Log.i("",listItems.get(k).get("ItemTitle")+":");
                        Log.i("",listItems.get(k).get("ItemDetail"));
                    }
                }
                super.handleMessage(mesg);//标配语句，不用理解但是必须由
            }
        };//Handel结束
        Thread thread = new Thread(this);//线程初始化，注意线程的初始化应该在Handel后面
        thread.start();
    }//onCreate方法结束
    public void run(){ //子线程开启
        URL url; // 获取网络数据
        try {
            url = new URL("http://www.usd-cny.com/icbc.htm");//在调试时一定要记得把手机的网打开
            Log.i(TAG,"run:url="+url);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = inputStream2String(in);

            if(html!=null){
                msg = handler.obtainMessage(5);//填写快递单号
                msg.obj = html;//快递内容
                handler.sendMessage(msg);//将HTML发过去
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }//run方法结束
    public void Dollar(View view){//Dollar
        money=editText.getText().toString();
        if(money==null||money.length()==0||money.equals("0")){//如果输入不规范，则提示输入错误
            Toast.makeText(this,"please input correct RMB",LENGTH_LONG).show();
            textView.setText("hello");
            editText.setText("");//同时清空
        }
        else{//输入有效
            res=Float.parseFloat(money);
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
    public void Euro(View view){//    EURO
        money=editText.getText().toString();
        if(money==null||money.length()==0||money.equals("0")){//如果输入不规范，则提示输入错误
            Toast.makeText(this,"please input correct RMB",LENGTH_LONG).show();
            textView.setText("hello");
            editText.setText("");//同时清空
        }
        else{//输入有效
            res=Float.parseFloat(money);
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
    public void Won(View view){//WON
        money=editText.getText().toString();
        if(money==null||money.length()==0||money.equals("0")){//如果输入不规范，则提示输入错误
            Toast.makeText(this,"please input correct RMB",LENGTH_LONG).show();
            textView.setText("hello");
            editText.setText("");//同时清空
        }
        else{//输入有效
            res=Float.parseFloat(money);
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
        config = new Intent(MainActivity.this, ConfigActivity.class);//打开config页面
        //装入Bundle
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar",dollarRate);
        bdl.putFloat("key_euro",euroRate);
        bdl.putFloat("key_won",wonRate);
        Log.i(TAG,"Bundle--------------------------------------------");
        Log.i(TAG,"the dollar_rate pushed in  Bundle is = "+dollarRate);
        Log.i(TAG,"the euro_rate pushed in Bundle is = "+euroRate);
        Log.i(TAG,"the won_rate pushed in Bundle is = "+wonRate);

        //config传输数据
        config.putExtras(bdl);
        startActivityForResult(config,1);
    }
    public void Reference(View view){
        Intent intent;
        intent = new Intent(MainActivity.this,SimpleListActivity.class);//打开列表页面
        //装入Intent
        Bundle bdl = new Bundle();
        for(int i=0;i<listItems.size();i++){
            bdl.putSerializable(String.valueOf(i), listItems.get(i));
        }
        bdl.putInt("list_size",listItems.size());
        Log.i(TAG,"put list into Intent:"+listItems);
        intent.putExtras(bdl);
        //intent传输数据
        startActivityForResult(intent,2);
    }
    public void GridList(View view){
        Intent intent;
        intent = new Intent(MainActivity.this,GridViewActivity.class);//打开列表页面

        startActivityForResult(intent,3);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){//其他页面跳转过来时的内容接收
        if(requestCode==1&&resultCode==2){
            Bundle bdl = data.getExtras();
            if(bdl!=null) {
                dollarRate = bdl.getFloat("key_dollar", 0.0f);
                euroRate = bdl.getFloat("key_euro", 0.0f);
                wonRate = bdl.getFloat("key_won", 0.0f);

                Log.i(TAG,"Bundle--------------------------------------------");
                Log.i(TAG,"the dollar_rate get from save is = "+dollarRate);
                Log.i(TAG,"the euro_rate get from save is = "+euroRate);
                Log.i(TAG,"the won_rate get from save is = "+wonRate);
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    private static String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0) break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }



}
