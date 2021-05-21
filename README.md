# -
1、 定义管理每个进程的数据结构 PCB：包含进程名称、队列指针、分配的 物理内存区域（基址和长度）。每创建一个进程时，需要为其创建 PCB 并分配空 闲内存空间，对 PCB 进行初始化，并加入就绪队列。 2、 模拟触发进程状态转换的事件：采用键盘控制方法来模拟触发进程状态切换的事件（例如输入 1 代表创建新进程、2 执行进程时间片到、3 阻塞执行进程、4 唤醒第一个阻塞进程、5 终止执行进程），实现对应的控制程序。 3、 根据当前发生的事件对进程的状态进行切换，并显示出当前系统中的执行队列、就绪队列和阻塞队列。  4、 *（选做）完成可变分区的分配与回收，创建进程的同时申请一块连续的内存空间，在 PCB 中设置好基址和长度，结束进程时回收分配的内存空间分配。可采用首次适应、最佳适应或最差适应算法，碎片大小为 2Kb,最后回收所有进程的空间，对空间分区的合并。可以查看进程所占的空间和系统空闲空间。 
定义PCB类:
public class PCB {
    private String name;
    private int start;
    private int length;
    private PCB next;
}
定义Memory类:
public class Memory {
    private int start;
    private int length;
    private int end;
    private Memory next;
    private Memory front;
    final int fragment = 2;
    boolean occupy;
}
定义Test类:整体功能的实现类
public class Test extends MainActivity2{
    public PCB readyHead = new PCB("就绪态",0);
    public PCB executeHead = new PCB("执行态",0);
    public PCB blockHead = new PCB("阻塞态",0);
    public Memory memoryHead = new Memory();
    int begin;
    int size;
}

1.4算法设计及流程图
创建进程：将新创建的进程（不与已存在的进程重名）申请内存空间成功后加入就绪队列，进行自动调度。 
时间片到：执行队列出队的进程入队就绪队列，进行自动调度。 
自动调度：执行队列是否为空，为空则就绪队列出队的进程入队执行列。 
阻塞：执行队列出队的进程入队阻塞队列，进行自动调度。 
唤醒：阻塞队列出队的进程入队就绪队列，进行自动调度。 
终止进程：执行队列出队，出队的进程将内存空间释放
