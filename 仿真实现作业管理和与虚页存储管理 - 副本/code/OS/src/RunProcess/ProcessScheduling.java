package RunProcess;

import java.util.Queue;

public class ProcessScheduling extends Thread{
		int x;//指令类型说明
		static int TimeSlice=2;//时间片
		static JCB jcb_tmp=new JCB();
		PCB pcb[]=new PCB[10];
		
	     int ReachedProcess;//已创建进程数目
	     int FinishedProcess;//结束运行进程数目
	    Harddisk h=new Harddisk();
		
		int Work[];//长度为5的工作向量
		boolean Finish[];//长度为5的布尔型工作向量	
		int deadlock[];//发生死锁的进程数组
		
		static int Blocktime;//阻塞时间
		static boolean CheckFinish=false;
		
		ProcessScheduling()
		{	
			
			TimeSlice=2;
			ReachedProcess=0;
			FinishedProcess=0;	
			Work=new int[2];
			Finish=new boolean[10];	
			deadlock=new int[10];
		}
		
		public int SystemRun(Memory memory,Harddisk harddisk,CPU cpu,int time,MMU mmu,ClockInterrupt c)
		{
			if(cpu.flag==1)//Cpu轮转  cpu忙
			{
				//取指
				if(cpu.pcb_cpu.IR<cpu.pcb_cpu.InstrucNum)//当前执行的指令小于指令总数
				{
					
					c.Scheduling_info+=("取址：进程"+cpu.pcb_cpu.ProID+"第"+cpu.pcb_cpu.IR+"条指令"+"\n");
					memory.PtabelIns_info+=("取址：进程"+cpu.pcb_cpu.ProID+"第"+cpu.pcb_cpu.IR+"条指令"+"\n");
					System.out.println("取址：进程"+cpu.pcb_cpu.ProID+"第"+cpu.pcb_cpu.IR+"条指令");
					//GUI.ProRec.append("取址：进程"+cpu.pcb_cpu.ProID+"第"+cpu.pcb_cpu.IR+"条指令"+"\n");				
					cpu.pcb_cpu.page.GetInstruc(cpu.pcb_cpu, mmu, cpu.pcb_cpu.IR, memory,harddisk,cpu);
					cpu.pcb_cpu.page.Ptabel(cpu.pcb_cpu);
					System.out.println("指令类型："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State);
					
					//执指
					if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==0)
					{	
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						GUI.ProRec.append(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"0，用户态计算操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						c.Scheduling_info+=(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"0，用户态计算操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"0，用户态计算操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						
						cpu.pcb_cpu.IR++;
						TimeSlice--;
						
					}
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==1)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						c.Scheduling_info+=(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"1，用户态计算操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"1，用户态计算操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"1，用户态计算操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.IR++;
						
						if(TimeSlice==1)
						{
							TimeSlice--;
						}
						else 
						{
							TimeSlice--;
							TimeSlice--;
						}
						
						
						
						
						
						
					}
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==2)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						c.Scheduling_info+=(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"2，键盘输入变量："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"2，键盘输入变量："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"2，键盘输入变量："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.BlockingPrimitive1(memory);//运行->阻塞1
						
						Keyboard.Keyboardstart(cpu);
						
						cpu.flag=0;//让出cpu，cpu空闲
					}	
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==3)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						
						c.Scheduling_info+=(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"3，屏幕显示输出变量："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"3，屏幕显示输出变量："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"3，屏幕显示输出变量："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.BlockingPrimitive2(memory);//运行->阻塞2
						
						Screen.Screenstart(cpu);
						
						cpu.flag=0;//让出cpu，cpu空闲
					}
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==4)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						c.Scheduling_info+=(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"4，磁盘文件读操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"4，磁盘文件读操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"4，磁盘文件读操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.BlockingPrimitive3(memory);//阻塞后指令+1,运行->阻塞3
						
						Disk_Read.Disk_Readstart(memory,cpu);
						
						cpu.protect(cpu.pcb_cpu);//保护现场
						
						cpu.recover(cpu.pcb_cpu);//恢复现场
						//cpu.flag=0;//让出cpu，cpu空闲
						
					}
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==5)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						c.Scheduling_info+=(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"5，磁盘文件写操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"5，磁盘文件写操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"5，磁盘文件写操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.BlockingPrimitive4(memory);//阻塞后指令+1,运行->阻塞4
						
						cpu.pcb_cpu.page.Allocatedbuffer(memory,cpu.pcb_cpu);//分配缓冲区
						Disk_Write.Disk_Writestart(memory,cpu);
						cpu.protect(cpu.pcb_cpu);//保护现场
						
						cpu.recover(cpu.pcb_cpu);//恢复现场
						//cpu.flag=0;//让出cpu，cpu空闲
						
					}
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==6)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						c.Scheduling_info+=(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"6， 打印操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"6， 打印操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"：[运行进程："+cpu.pcb_cpu.ProID+"："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"，"+"6， 打印操作："+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"，"+physicaladdress+"，"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.BlockingPrimitive5(memory);//运行->阻塞5
						for(int i=0;i<4;i++)
						{
							while(TimeSlice>0)
							{
								TimeSlice--;
								break;
							}
							TimeSlice=2;
							
						}
						cpu.flag=0;//让出cpu，cpu空闲
						cpu.pcb_cpu.WakeupPrimitive5(memory);
					}
					else
					{
						c.Scheduling_info+=("寻址出错!"+"\n");
						System.out.println("寻址出错!");
						//GUI.ProRec.append("寻址出错!"+"\n");
					}
				}
				else//不小于指令总数
				{
					//检查指令是否全部执行完毕
					CheckFinishedProcess(cpu.pcb_cpu,memory,cpu,c,harddisk);//运行->终止
				}
			}
			if(cpu.flag==0)//Cpu空闲
			{
				Ready_To_Running(memory,cpu,c);//就绪->运行				
			}
			Running_To_Ready(memory,cpu,c);//运行->就绪
			return Work_Finish(cpu);
		}
	
		//就绪态到运行态
		public void Ready_To_Running(Memory memory,CPU cpu,ClockInterrupt c)
		{
			if(!memory.ReadyQueue.isEmpty())
			{
				cpu.flag=1;
				Suffle(memory.ReadyQueue);
				cpu.pcb_cpu=memory.ReadyQueue.remove();//找出就绪队列优先级最高的进程，出就绪队列
				memory.RunningQueue.add(cpu.pcb_cpu);//加入运行队列
				cpu.pcb_cpu.PSW=3;
				//memory.PcbList.get(memory.PcbList.indexOf(cpu.pcb_tmp)).ProState=3;//PCB表里的状态	
				TimeSlice=2;
				if(cpu.pcb_cpu!=null)
				{
					memory.PcbList.get(memory.PcbList.indexOf(cpu.pcb_cpu)).PSW=3;
				}
				
			}
		}
		//时间片到了，运行态到就绪态
		public void Running_To_Ready(Memory memory,CPU cpu,ClockInterrupt c)
		{
			if(cpu.pcb_cpu.PSW==3&&TimeSlice==0)//时间片不剩
			{
				System.out.println(c.time+"：[重新进入就绪队列："+cpu.pcb_cpu.ProID+"]");
				GUI.ProRec.append(c.time+"：[重新进入就绪队列："+cpu.pcb_cpu.ProID+"]\n");
				c.Scheduling_info+=(c.time+"：[重新进入就绪队列："+cpu.pcb_cpu.ProID+"]\n");
				memory.RunningQueue.remove();
				memory.ReadyQueue.add(cpu.pcb_cpu);
				cpu.pcb_cpu.PSW=1;
				memory.PcbList.get(memory.PcbList.indexOf(cpu.pcb_cpu)).PSW=1;//PCB表里的状态			
				cpu.flag=0;//Cpu空闲
			}
			
		}
		
		//检查指令是否执行完，即进程是否结束，运行态到终止态 阻塞态到终止态
		public void CheckFinishedProcess(PCB pcb,Memory memory,CPU cpu,ClockInterrupt c,Harddisk harddisk)
		{
			if(pcb.PSW==3&&pcb.IR>=pcb.InstrucNum)
			{
				int i;
				pcb.RevokePrimitive(memory);
				pcb.RunTimes=c.time-pcb.RunTimes;
				System.out.println("进程"+pcb.ProID+"运行时长："+pcb.RunTimes);
				GUI.ProRec.append("进程"+pcb.ProID+"运行时长："+pcb.RunTimes+"\n");
				c.Scheduling_info+=("进程"+pcb.ProID+"运行时长："+pcb.RunTimes+"\n");
				
				cpu.flag=0;
				FinishedProcess++;
			}
		}
		//检查程序是否全部完成
		public int Work_Finish(CPU cpu)
		{
			if(FinishedProcess==ReachedProcess&&ReachedProcess>0)
			{			
				cpu.pcb_cpu.ProID=-1;
				cpu.pcb_cpu.Priority=-1;
				cpu.pcb_cpu.InTimes=-1;
				cpu.pcb_cpu.InstrucNum=0;
				cpu.pcb_cpu.PSW=0;
				cpu.pcb_cpu.RunTimes=0;	
				TimeSlice=2;		
				return 1;
			}
			else
			{
				return 0;
			}
		}

		//就绪队列按照优先级排序（每次有进程进入就绪队列都要排序)
		public void Suffle(Queue<PCB> queue)
		{
			int priority=-1;
			int size=queue.size();
			int i=0,j;
			PCB p[]=new PCB[size];
			PCB t=new PCB();
			//出队列赋值给p数组
			while(i<size)
			{
				//System.out.println("ddd"+queue.size());
				p[i]=new PCB();
				p[i]=queue.remove();
				//System.out.println(p[i].Priority);
				i++;
			}	
			//排序
			for(i=0;i<size;i++)
			{
				priority=p[i].Priority;
				for(j=i+1;j<size;j++)
				{
					if(p[j].Priority<p[i].Priority)
					{
						t=p[i];
						p[i]=p[j];
						p[j]=t;
					}
				}			
			}
			//入队列
			i=0;
			while(i<size)
			{
				//System.out.println(p[i].Priority);
				queue.add(p[i]);
				i++;
			}	
		}

		
}		

		
	

