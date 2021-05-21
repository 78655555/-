package com.example.tid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tid.Class.Memory;
import com.example.tid.Class.PCB;
import com.example.tid.Class.Test;

import java.util.concurrent.BlockingDeque;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView processInfo;
    private TextView memoryInfo;
    private Button creat_process;
    private Button block_process;
    private Button arouse_process;
    private Button timeEnd_process;
    private Button over_process;
    private String begin;
    private String size;
    private Test test;
    private static final String TAG = "MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        begin = intent.getStringExtra("begin");
        size = intent.getStringExtra("size");
        Log.d(TAG, "onCreate: " + begin + " " + size);
        int a = Integer.parseInt(begin);
        int b = Integer.parseInt(size);
        test = new Test(a,b);
        init();

        //测试数据
//        Memory m1 = new Memory(110,20);
//        Memory m2 = new Memory(120,50);
//        Memory m3 = new Memory(10,80);
//        Memory m4 = new Memory(100,10);
//        Memory m5 = new Memory(200,110);
//        Memory m6 = new Memory(300,55);
//        Memory m7 = new Memory(30,36);
//        test.memoryHead.setNext(m1);
//        m1.setFront(test.memoryHead);
//
//        m1.setNext(m2);
//        m2.setFront(m1);
//
//        m2.setNext(m3);
//        m3.setFront(m2);
//
//        m3.setNext(m4);
//        m4.setFront(m3);
//
//        m4.setNext(m5);
//        m5.setFront(m4);
//
//        m5.setNext(m6);
//        m6.setFront(m5);
//
//        m6.setNext(m7);
//        m7.setFront(m6);
//
//        m7.setNext(test.memoryHead);
//        test.memoryHead.setFront(m7);
//        test.memoryHead.sortMemory();
//        Log.d(TAG, "onCreate: \n" + test.showAllMemory());
//
//        test.memoryHead.sortMemoryByStart();
//        Log.d(TAG, "onCreate: \n" + test.showAllMemory());



        processInfo.setText(test.showAllProcess());
        memoryInfo.setText(test.showAllMemory());
        creat_process.setOnClickListener(this);
        block_process.setOnClickListener(this);
        arouse_process.setOnClickListener(this);
        timeEnd_process.setOnClickListener(this);
        over_process.setOnClickListener(this);

    }


    public void init(){
        processInfo = (TextView) findViewById(R.id.process_info);
        memoryInfo = (TextView) findViewById(R.id.memory_info);
        creat_process = (Button) findViewById(R.id.create_process);
        block_process = (Button) findViewById(R.id.block_process);
        arouse_process = (Button) findViewById(R.id.arouse_process);
        timeEnd_process = (Button) findViewById(R.id.timeEnd_process);
        over_process = (Button) findViewById(R.id.over_process);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_process:
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.mydialog,(ViewGroup)findViewById(R.id.mydialog));
                EditText name = (EditText) layout.findViewById(R.id.process_name);
                EditText size = (EditText) layout.findViewById(R.id.process_size);
                    //Toast.makeText(MainActivity.this,"aaa",Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this).setTitle("新建一个进程")
                        .setIcon(R.drawable.moon)
                        .setView(layout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(size.getText())){
                                    Toast.makeText(MainActivity.this,"name 和 size不能为空",Toast.LENGTH_SHORT).show();
                                }else{
                                    if(test.findProcess(name.getText().toString())){
                                        //提醒
                                        name.setText("");
                                    }else{
                                        int sizeText = Integer.parseInt(size.getText().toString());
                                        PCB pcb = new PCB(name.getText().toString(),sizeText);
                                        test.memoryHead.sortMemoryBylength();
                                        if(test.memoryHead.distributeMemory(pcb)){
                                            test.createProcess(pcb);
                                            String output = test.showAllProcess();
                                            processInfo.setText(output);
                                            Log.d(TAG, "onClick: \n" + test.showAllMemory());
                                            test.memoryHead.sortMemoryByStart();
                                            memoryInfo.setText(test.showAllMemory());

                                        }else {
                                            //Toast.makeText(MainActivity2.this,"内存分配不足",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } }}).setNegativeButton("取消",null).show();


                break;
            case R.id.block_process:
                test.blockProcess();
                processInfo.setText(test.showAllProcess());
                break;
            case R.id.arouse_process:
                test.arouseProcess();
                processInfo.setText(test.showAllProcess());
                break;
            case R.id.timeEnd_process:
                test.timeEnd();
                processInfo.setText(test.showAllProcess());
                break;
            case R.id.over_process:
                PCB p = test.overProcess();
                test.memoryHead.sortMemoryByStart();
                Log.d(TAG, "onClick: " + "bbb");
                test.memoryHead.memoryRecall(p);
                Log.d(TAG, "onClick: " + "Ccc");
                processInfo.setText(test.showAllProcess());
                test.memoryHead.sortMemoryByStart();
                memoryInfo.setText(test.showAllMemory());
                //Log.d(TAG, "onClick: \n" + test.showAllMemory( );
                break;
            default:
                break;
        }
    }
}