package com.example.tid.Class;

public class PCB {
    private String name;
    private int start;
    private int length;
    private PCB next;

    public PCB(){
        next = null;
    }
    public PCB(String name,int length) {
        this.name = name;
        this.length = length;
        this.next=null;
    }

    public PCB(String name, int start, int length) {
        this.name = name;
        this.start = start;
        this.length = length;
    }

    public String getName() {
        return name;
    }
    public int getStart() {
        return start;
    }
    public int getLength() {
        return length;
    }
    public PCB getNext() {
        return next;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStart(int index) {
        this.start = index;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public void setNext(PCB next) {
        this.next = next;
    }

    public void add(PCB pcb){
        PCB p=this;
        while(p.next!=null){
             p = p.next;
        }
        p.next = pcb;
    }
    public PCB deque(){
        PCB p = this.next;
        PCB pp = this.next;
        if(p==null){
            return this;
        }else {
            this.next = p.next;
            p.next=null;
        }
        return pp;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "name='" + name + '\'' +
                ", start=" + start +
                ", length=" + length + '}';
    }

    public String show(String name){
        String text="";
        PCB p = this.next;
        if(p!=null){
            while (p!=null){
                text+=p.toString()+"\n";
                p=p.next;
            }
        }else {
            text += "为空\n";
        }
        return name + ":\n" + text   ;
    }
}
