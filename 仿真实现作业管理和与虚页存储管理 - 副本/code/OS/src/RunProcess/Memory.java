package RunProcess;

import java.util.LinkedList;
import java.util.Queue;

public class Memory {

	//用户进程区16页 PCB8页+缓冲区8(24-31)页 
	int Memory_Size=24*1024;//用户进程区
	int Buffer_Size=8*1024;//缓冲区
	static String PtabelIns_info="";//记录所有进程页表的变化信息和取指令（访问页面）执行过程
	int Memory_Pro[]=new int[32];//内存物理块所属进程
	static int Memory_State[]=new int[32];//内存物理块状态（是否被占用），0未占用，1被进程占用
	  //队列信息
	Queue<PCB> ReadyQueue = new LinkedList<PCB>();//就绪队列
	Queue<PCB> BlockQueue_Keyboard=new LinkedList<PCB>();//键盘输入类阻塞队列1
	Queue<PCB> BlockQueue_Screen=new LinkedList<PCB>();//屏幕显示类阻塞队列2
	Queue<PCB> BlockQueue_DiskRead=new LinkedList<PCB>();//磁盘文件读操作类阻塞队列3
	Queue<PCB> BlockQueue_DiskWrite=new LinkedList<PCB>();//磁盘文件写操作类阻塞队列4
	Queue<PCB> BlockQueue_Print=new LinkedList<PCB>();//打印操作类阻塞队列5
	Queue<PCB> RunningQueue = new LinkedList<PCB>();//运行队列
	LinkedList<PCB> PcbList=new LinkedList<PCB>();//PCB表
	public Memory()//内存初始化
	{
		for(int i=0;i<32;i++)
		{
			Memory_State[i]=0;
			Memory_Pro[i]=-1;
		}
			
	}
}
