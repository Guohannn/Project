package RunProcess;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class ClockInterrupt extends Thread{

	public static int time=0;//时钟记录
	static boolean Finish=false;
	static String Scheduling_info="";
	CPU cpu=new CPU();
	Memory memory=new Memory();
	Harddisk harddisk=new Harddisk();
	MMU mmu=new MMU();
	ProcessScheduling scheduling=new ProcessScheduling();
	int situation=0;//是否执行完毕
	ClockInterrupt()
	{
		
	}
	public void run()
	{
		while(!ClockInterrupt.Finish)
		{
			try 
			{
				time++;
				
				
				harddisk.InBackupQueue(this);//进入后备队列
				situation=scheduling.SystemRun(memory,harddisk,cpu,time,mmu,this);//situation=1,完全执行完毕
				if(CPU.flag==0)
				{
					System.out.println(time+"：[CPU空闲]");
					GUI.ProRec.append(time+"：[CPU空闲]"+"\n");
				}
				if(situation==1)
				{
					try
					{
						File f1=new File("PageResults.txt");//进程结果
						FileWriter fin =new FileWriter(f1);//输出流
						BufferedWriter bin=new BufferedWriter(fin);//缓冲区
						bin.write(memory.PtabelIns_info);//页表和指令执行（访问页面）信息变化
						bin.close();
						fin.close();
						File f2=new File("ProcessResults-277.txt");
						FileWriter f=new FileWriter(f2);
						BufferedWriter b=new BufferedWriter(f);
						b.write(Scheduling_info);//进程调度信息变化
						b.close();
						f.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
					System.out.println("当前进程已全部执行完毕");
					GUI.ProRec.append("当前进程已全部执行完毕");
					for(int i=0;i<32;i++)
					{
						Memory.Memory_State[i]=0;
					}
					ClockInterrupt.Finish=true;
					JobsScheduling.Checktime=ClockInterrupt.time;
				}
			
				sleep(100);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
