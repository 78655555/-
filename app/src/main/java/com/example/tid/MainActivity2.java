package com.example.tid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    private EditText begin;
    private EditText size;
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(begin.getText()) || TextUtils.isEmpty(size.getText())) {
                    Toast.makeText(MainActivity2.this,"begin,size不能为空!",Toast.LENGTH_SHORT).show();
                }else {
                    String beginText = begin.getText().toString();
                    String sizeText = size.getText().toString();
                    int size1 = Integer.parseInt(sizeText);
                    if(size1 <= 0){
                        Toast.makeText(MainActivity2.this,"size不满足要求",Toast.LENGTH_SHORT).show();
                        size.setText("");
                    }else{
                        Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                        intent.putExtra("begin",beginText);
                        intent.putExtra("size",sizeText);
                        startActivity(intent);
                    }

                }
            }
        });

    }

    public void init(){
        begin = (EditText) findViewById(R.id.begin);
        size = (EditText) findViewById(R.id.size);
        button = (ImageButton) findViewById(R.id.ok);

    }
}