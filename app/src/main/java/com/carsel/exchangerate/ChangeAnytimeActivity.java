package com.carsel.exchangerate;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import static android.widget.Toast.LENGTH_LONG;

public class ChangeAnytimeActivity extends  AppCompatActivity implements TextWatcher {
    EditText editText;
    TextView textView;
    String detailStr;
    DecimalFormat df;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_anytime);
        Intent intent = getIntent();
        Bundle bdl = intent.getExtras();

        String titleStr = bdl.getString("ItemTitle");
        detailStr = bdl.getString("ItemDetail");

        df = new DecimalFormat("#.00");
        editText = findViewById(R.id.editText007);
        textView = findViewById(R.id.textView007);

        editText.setHint(titleStr);
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Float res=0.0f;
        Float rate = 100/(Float.parseFloat(detailStr));
        String money=s.toString();
        if(money==null||money.equals("0")){//如果输入不规范，则提示输入错误
            Toast.makeText(this,"please input correct RMB",LENGTH_LONG).show();
            textView.setText("hello");
        }
        else if(money.length()==0){
            textView.setText("hello");
        }
        else{//输入有效
            res=Float.parseFloat(money);
                res=res*rate;
                money=df.format(res);
                textView.setText(money);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
