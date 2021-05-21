package com.example.tid.Class;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tid.MainActivity2;

import org.w3c.dom.Text;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Test extends MainActivity2{

    public PCB readyHead = new PCB("就绪态",0);
    public PCB executeHead = new PCB("执行态",0);
    public PCB blockHead = new PCB("阻塞态",0);
    public Memory memoryHead = new Memory();
    int begin;
    int size;


    public Test(){}
    public Test(int begin,int size){
        this.begin = begin;
        this.size = size;
        Memory memory = new Memory(begin,size);
        memoryHead.setNext(memory);
        memoryHead.setFront(memory);
        memory.setFront(memoryHead);
        memory.setNext(memoryHead);
    }

    public void createProcess(PCB pcb){
        readyHead.add(pcb);
        if(executeHead.getNext()==null){
            PCB p = readyHead.deque();
            executeHead.add(p);
        }
    }

    public void timeEnd(){
        if(executeHead.getNext()==null){
           // Toast.makeText(this,"当前没有执行态的进程",Toast.LENGTH_SHORT).show();
        }else{
            PCB p = executeHead.deque();
            readyHead.add(p);
            if(readyHead.getNext()!=null){
                PCB pp = readyHead.deque();
                executeHead.add(pp);
            }else{
                // Toast.makeText(this,"当前没有就绪态的进程",Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void blockProcess(){
        if(executeHead.getNext()==null){
            //Toast.makeText(this,"当前没有执行态的进程",Toast.LENGTH_SHORT).show();

        }else {
            PCB p = executeHead.deque();
            blockHead.add(p);
            if(readyHead.getNext()==null){
              //  Toast.makeText(this,"当前没有就绪态的进程",Toast.LENGTH_SHORT).show();
            }else{
                PCB pp = readyHead.deque();
                executeHead.add(pp);
            }
        }
    }
    public void arouseProcess(){
        if(blockHead.getNext()==null){
         //   Toast.makeText(this,"当前没有阻塞态的进程",Toast.LENGTH_SHORT).show();
        }else{
            PCB p = blockHead.deque();
            readyHead.add(p);
            if(executeHead.getNext()==null){
                executeHead.add(readyHead.deque());
            }
        }
    }

    public PCB overProcess(){
        if(executeHead.getNext()==null){
           // Toast.makeText(this,"当前没有执行态的进程",Toast.LENGTH_SHORT).show();
            return null;
        }else {
            PCB p = executeHead.deque();
            if(readyHead.getNext()==null){
             //   Toast.makeText(this,"当前没有就绪态的进程",Toast.LENGTH_SHORT).show();
            }else {
                PCB pp = readyHead.deque();
                executeHead.add(pp);
            }
            return p;
        }
    }

    public boolean findProcess(String name){
        PCB p = readyHead.getNext();
        while(p!=null){
            if(p.getName().equals(name)){
                return true;
            }
            p = p.getNext();
        }

        p = executeHead.getNext();
        while(p!=null){
            if(p.getName().equals(name)){
                return true;
            }
            p = p.getNext();
        }
        p = blockHead.getNext();
        while(p!=null){
            if(p.getName().equals(name)){
                return true;
            }
            p = p.getNext();
        }
        return false;
    }

    public String showAllProcess(){
        String output="";
        output += readyHead.show(readyHead.getName());
        output += executeHead.show(executeHead.getName());
        output += blockHead.show(blockHead.getName());

        return output+'\n';
    }

    public String showAllMemory(){
        String output="";
        output += memoryHead.show();
        return output+'\n';
    }

}
