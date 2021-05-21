package com.example.tid.Class;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Memory {

    private int start;
    private int length;
    private int end;
    private Memory next;
    private Memory front;
    final int fragment = 2;
    boolean occupy;
    private static final String TAG = "Memory";

    public Memory(){
        next=null;
        front=null;
        occupy = false;
    }
    public Memory(int start, int length) {
        this.start = start;
        this.length = length;
        this.end = start + length;
        next=null;
        front=null;
        occupy = false;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public int getEnd() {
        return end;
    }

    public Memory getNext() {
        return next;
    }

    public Memory getFront() {
        return front;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setNext(Memory next) {
        this.next = next;
    }

    public void setFront(Memory front) {
        this.front = front;
    }



    public boolean distributeMemory(PCB pcb){
        Memory m = this.next;
        int flag = 0;
        while(m!=this){
            if(m.getLength() < pcb.getLength() && m.occupy == false){
                m=m.next;
                continue;
            }
            else {
                if(m.occupy == false){//这个关键点要考虑,内存是不是被占用
                    pcb.setStart(m.start);//修改pcb的起始点
                    if(m.getLength() - pcb.getLength() <= fragment){
//                    m.front.next = m.next;
//                    m.next.front = m.front;
                        pcb.setLength(m.getLength());//很重要
                        m.occupy = true;
//                    m =null;
                    }else{
                        Memory mm = new Memory();
                        mm.setStart(m.getStart());
                        mm.setLength(pcb.getLength());
                        mm.setEnd(m.getStart()+pcb.getLength());
                        mm.occupy = true;

                        m.setStart(pcb.getLength()+m.getStart());
                        m.setLength(m.getLength()-pcb.getLength());
                        m.setEnd(m.getStart()+m.getLength());
                        m.occupy = false;

                        m.front.next = mm;
                        mm.front = m.front;
                        mm.next = m;
                        m.front = mm;
                    }
                    flag = 1;
                    m = m.next;
                    break;
                    //break;
                    //return true;
                }
                else {
                    m = m.next;
                    continue;
                }
            }

        }
        if(flag == 1) return true;
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortMemoryBylength(){
        Memory memory = this.next;
        Memory mHead = null;
        Memory mp = null;
        List<Memory> memories = new ArrayList<>();
        while(memory!=this){
            memories.add(memory);
            memory=memory.next;
        }
        memories.sort((o1,o2)->o1.length-o2.length);
        for(Memory m:memories){
            if(mHead==null){
                mHead=m;
                mp=m;
            }else {
                mp.next = m;
                m.front = mp;
                mp = m;
            }
        }
        this.next = mHead;
        mHead.front = this;
        mp.next = this;
        this.front = mp;

//        Memory memory = this.next;
//        Memory mHead = new Memory();
//        Memory mp = null;
//        if(memory.next!=this){
//            while(memory!=this){
//                Memory m = memory;
//                while(memory!=this){
//                    if(m.length > memory.length){
//                        m = memory;
//                    }
//                    if(mHead.next==null){
//                        mHead.next = m;
//                        m.front = mHead;
//                        mp = m;
//                    }else {
//                        mp.next=m;
//                        m.front = mp;
//                        mp =m;
//                    }
//                    mHead.front = mp;
//                    mp.next = mHead;
//                    memory = memory.next;
//                }
//                memory = memory.next;
//            }
//        }else {
//            return this;
//        }
//        return mHead;
    }
    public void sortMemoryByStart(){
        Memory memory = this.next;
        Memory mHead = null;
        Memory mp = null;
        List<Memory> memories = new ArrayList<>();
        while(memory!=this){
            memories.add(memory);
            memory=memory.next;
        }
        memories.sort((o1,o2)->o1.start-o2.start);
        for(Memory m:memories){
            if(mHead==null){
                mHead=m;
                mp=m;
            }else {
                mp.next = m;
                m.front = mp;
                mp = m;
            }
        }
        this.next = mHead;
        mHead.front = this;
        mp.next = this;
        this.front = mp;
    }

    public void memoryRecall(PCB pcb){
        Memory memory = this.next;
        Memory m1 = this.next;
        Memory m2 = this.front;
        if(pcb == null){

        }else if(this.next == this.front && this.next!=null){//只有一个内存节点 1
            if(memory.start == pcb.getStart()){
                memory.occupy = false;
            }
        }else{
            Log.d(TAG, "memoryRecall:aaa " + memory.start + " = " + pcb.getStart());
            while(memory!=this){
                if(memory.start==pcb.getStart() && memory.length == pcb.getLength()){
                    if(m1.start == memory.start){//第一个点
                        Memory mm = memory.next;
                        if(mm.occupy == false){//1
                            memory.setLength(memory.getLength() + mm.getLength());
                            memory.setEnd(memory.start + memory.getLength());
                            memory.occupy = false;

                            memory.next = mm.next;
                            mm.next.front = memory;
                        }else {//1
                            memory.occupy = false;
                        }
                    }else if(m2.start == memory.start){//最后一个节点
                        Memory mm = memory.front;
                        if(mm.occupy == false){//1
                            memory.setStart(mm.start);
                            memory.setLength(mm.length + memory.length);
                            memory.setEnd(memory.start+memory.length);
                            memory.occupy = false;

                            memory.front = mm.front;
                            mm.front.next = memory;

                        }else {//1
                            memory.occupy = false;
                        }
                    }else {//中间节点
                        Memory frontMemory = memory.front;
                        Memory nextMemory = memory.next;
                        if(frontMemory.occupy == true && nextMemory.occupy == true){//1
                            memory.occupy = false;
                        }else if(frontMemory.occupy == false && nextMemory.occupy == false){//1
                            memory.start = frontMemory.start;
                            memory.length = frontMemory.length + memory.length + nextMemory.length;
                            memory.end = memory.start + memory.length;
                            memory.occupy = false;

                            memory.front = frontMemory.front;
                            memory.front.next = memory;

                            memory.next = nextMemory.next;
                            nextMemory.next.front = memory;

                        }else if(frontMemory.occupy == false && nextMemory.occupy == true){//1
                            memory.start = frontMemory.start;
                            memory.length = frontMemory.length + memory.length;
                            memory.end = memory.start + memory.length;
                            memory.occupy = false;

                            memory.front = frontMemory.front;
                            frontMemory.front.next = memory;
                        }else {//1
                            memory.length = memory.length + nextMemory.length;
                            memory.end = memory.start + memory.length;
                            memory.occupy = false;

                            memory.next = nextMemory.next;
                            nextMemory.next.front = memory;
                        }
                    }
                }
                memory = memory.next;
            }
        }
    }


    @Override
    public String toString() {
        return "Memory{" +
                "start=" + start +
                ", end=" + end +
                ", length=" + length + "," + occupy+ '}';
    }
    public String show(){
        Memory memory = this.next;
        String text="";
        if(memory==null){
            text+="为空";
        }else {
            while(memory!=this){
               if(memory.occupy==false){
                    text+=memory.toString()+"\n";
                }
                memory = memory.next;
            }
            //text+=memory.toString();
        }
        return text+'\n';
    }
}
