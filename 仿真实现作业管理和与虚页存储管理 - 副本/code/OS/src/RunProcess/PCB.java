package RunProcess;

import java.util.Queue;

import java.util.Stack;

import CreateJobs.Instruction;



public class PCB {//进程控制块

	int ProID;//进程编号
	int Priority;//进程优先数 优先数越小 优先级越大
	int InTimes;//进程创建时间
	int EndTimes;//进程结束时间
	int PSW;//进程状态  //0新建态,1就绪态,2运行态,3阻塞态 4终止态
	int RunTimes;//进程运行时间列表
	int TurnTimes;//进程周转时间统计
	int InstrucNum;//进程包含的指令数目
	int PC;//下一条指令的编号
	int IR;//正在执行的指令编号
	int mem;
	int HardDiskAddress[][]=new int[2][];//外存地址
	Instruction Instruc[]=new Instruction[128];//一页最多存128条指令
	PageScheduling page;//进程的页面管理
	public PCB()
	{
		ProID=-1;
		Priority=-1;
		InTimes=-1;
		InstrucNum=0;
		PSW=0;
		IR=0;
		RunTimes=0;	
		HardDiskAddress[0]=new int[32];
		HardDiskAddress[1]=new int[64];
	}
	//进程创建原语(后备队列->就绪队列)
		public void CreatePrimitive(Memory memory,JCB jcb,Harddisk harddisk)
		{
			//初始化PCB
			int i;
			this.ProID=jcb.ProID;
			this.Priority=jcb.Priority;
			this.InTimes=jcb.InTimes;
			this.InstrucNum=jcb.InstrucNum;
			for(i=0;i<8;i++)		
			{
				this.HardDiskAddress[1][i]=jcb.HardDiskAddress[1]+i;
			}
			this.HardDiskAddress[0][0]=jcb.HardDiskAddress[0];//磁道号
			for(i=0;i<this.InstrucNum;i++)
			{
				Instruc[i]=new Instruction();
				this.Instruc[i].Instruc_ID=jcb.Instruc[i].Instruc_ID;
				this.Instruc[i].Instruc_State=jcb.Instruc[i].Instruc_State;
				this.Instruc[i].L_Address=jcb.Instruc[i].L_Address;
				this.Instruc[i].InRunTimes=jcb.Instruc[i].InRunTimes;
			}
			//********
			//分配内存
			page=new PageScheduling();
			if(this.page.AllocatedPage(memory, this))
			{
				System.out.println("进程"+this.ProID+"分配内存空间成功!");
				//GUI.ProRec.append("进程"+this.ProID+"分配内存空间成功!"+"\n");
				ClockInterrupt.Scheduling_info+=("进程"+this.ProID+"分配内存空间成功!"+"\n");
				this.mem=1;
			}
			else
			{
				
				this.mem=0;
			}
			//********
			PSW=1;//就绪态
			memory.ReadyQueue.add(this);//进就绪队列

			memory.PcbList.add(this);//加入PCB表
			System.out.println(ClockInterrupt.time+"："+"[创建进程："+this.ProID+"]");
			GUI.ProRec.append(ClockInterrupt.time+"："+"[创建进程："+this.ProID+"]\n");
			ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"："+"[创建进程："+this.ProID+"]\n");
			
			ClockInterrupt.Scheduling_info+=("FFFF:[就绪队列 1：");
			PrintfReadyQueue(memory.ReadyQueue);
			
			
			
		}
		

		//进程唤醒原语(阻塞队列1->就绪队列)
		public void WakeupPrimitive1(Memory memory)
		{
			PCB pcb_tmp=new PCB();
			if(!memory.BlockQueue_Keyboard.isEmpty())
			{
				pcb_tmp=DeQueue(memory.BlockQueue_Keyboard,this);//出阻塞队列1
				System.out.println("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕");//系统调用指令
				//GUI.ProRec.append("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕"+"\n");
				ClockInterrupt.Scheduling_info+=("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕"+"\n");
				pcb_tmp.IR++;
				System.out.println(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]");
				GUI.ProRec.append(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]\n");
				ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]\n");
				pcb_tmp.PSW=1;//进程状态
				memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=1;//PCB表里的状态
				memory.ReadyQueue.add(pcb_tmp);//进就绪队列
				ClockInterrupt.Scheduling_info+=("FFFF:[就绪队列 1：");
				PrintfReadyQueue(memory.ReadyQueue);
				
			}
			else
			{
				System.out.println("阻塞队列1为空");
				//GUI.ProRec.append("阻塞队列1为空"+"\n");
				ClockInterrupt.Scheduling_info+=("阻塞队列1为空"+"\n");
			}
		}
		//进程唤醒原语(阻塞队列2->就绪队列)
				public void WakeupPrimitive2(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.BlockQueue_Screen.isEmpty())
					{
						pcb_tmp=DeQueue(memory.BlockQueue_Screen,this);//出阻塞队列
						System.out.println("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕");//系统调用指令
						//GUI.ProRec.append("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕"+"\n");
						ClockInterrupt.Scheduling_info+=("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕"+"\n");
						pcb_tmp.IR++;
						System.out.println(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]");
						GUI.ProRec.append(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]"+"\n");
						pcb_tmp.PSW=1;//进程状态
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=1;//PCB表里的状态
						memory.ReadyQueue.add(pcb_tmp);//进就绪队列
						ClockInterrupt.Scheduling_info+=("FFFF:[就绪队列 1：");
						PrintfReadyQueue(memory.ReadyQueue);
						}
					else
					{
						System.out.println("阻塞队列2为空");
						//GUI.ProRec.append("阻塞队列2为空"+"\n");
						ClockInterrupt.Scheduling_info+=("阻塞队列2为空"+"\n");
					}
				}
				//进程唤醒原语(阻塞队列3->就绪队列)
				public void WakeupPrimitive3(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.BlockQueue_DiskRead.isEmpty())
					{
						
						pcb_tmp=DeQueue(memory.BlockQueue_DiskRead,this);//出阻塞队列
						System.out.println("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕");//系统调用指令
						//GUI.ProRec.append("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕"+"\n");
						ClockInterrupt.Scheduling_info+=("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕"+"\n");
						pcb_tmp.IR++;
						System.out.println(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]");
						GUI.ProRec.append(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]"+"\n");
						pcb_tmp.PSW=1;//进程状态
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=1;//PCB表里的状态
						memory.ReadyQueue.add(pcb_tmp);//进就绪队列
						ClockInterrupt.Scheduling_info+=("FFFF:[就绪队列 1：");
						PrintfReadyQueue(memory.ReadyQueue);
						
					}
					else
					{
						System.out.println("阻塞队列3为空");
						//GUI.ProRec.append("阻塞队列3为空"+"\n");
						ClockInterrupt.Scheduling_info+=("阻塞队列3为空"+"\n");
					}
				}
				//进程唤醒原语(阻塞队列4->就绪队列)
				public void WakeupPrimitive4(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.BlockQueue_DiskWrite.isEmpty())
					{
						pcb_tmp=DeQueue(memory.BlockQueue_DiskWrite,this);//出阻塞队列
						System.out.println("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕");//系统调用指令
						//GUI.ProRec.append("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕"+"\n");
						ClockInterrupt.Scheduling_info+=("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕"+"\n");
						pcb_tmp.IR++;
						System.out.println(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]");
						GUI.ProRec.append(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]"+"\n");
						pcb_tmp.PSW=1;//进程状态
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=1;//PCB表里的状态
						memory.ReadyQueue.add(pcb_tmp);//进就绪队列
						ClockInterrupt.Scheduling_info+=("FFFF:[就绪队列 1：");
						PrintfReadyQueue(memory.ReadyQueue);
						
					}
					else
					{
						System.out.println("阻塞队列4为空");
						//GUI.ProRec.append("阻塞队列4为空"+"\n");
						ClockInterrupt.Scheduling_info+=("阻塞队列4为空"+"\n");
					}
				}
				//进程唤醒原语(阻塞队列5->就绪队列)
				public void WakeupPrimitive5(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.BlockQueue_Print.isEmpty())
					{
						pcb_tmp=DeQueue(memory.BlockQueue_Print,this);//出阻塞队列
						System.out.println("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕");//系统调用指令
						//GUI.ProRec.append("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕"+"\n");
						ClockInterrupt.Scheduling_info+=("进程"+pcb_tmp.ProID+"的第"+pcb_tmp.IR+"条指令执行完毕"+"\n");
						pcb_tmp.IR++;
						System.out.println(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]");
						GUI.ProRec.append(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[重新进入就绪队列："+this.ProID+"]"+"\n");
						pcb_tmp.PSW=1;//进程状态
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=1;//PCB表里的状态
						memory.ReadyQueue.add(pcb_tmp);//进就绪队列
						ClockInterrupt.Scheduling_info+=("FFFF:[就绪队列 1：");
						PrintfReadyQueue(memory.ReadyQueue);
						
					}
					else
					{
						System.out.println("阻塞队列5为空");
						//GUI.ProRec.append("阻塞队列5为空"+"\n");
						ClockInterrupt.Scheduling_info+=("阻塞队列5为空"+"\n");
					}
				}
		//进程阻塞原语(运行队列->阻塞队列1)
		public void BlockingPrimitive1(Memory memory)
		{
			PCB pcb_tmp=new PCB();
			if(!memory.RunningQueue.isEmpty())
			{
				pcb_tmp=DeQueue(memory.RunningQueue,this);//出运行队列
				
				pcb_tmp.PSW=2;//进程状态
				memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=2;//PCB表里的状态
				memory.BlockQueue_Keyboard.add(pcb_tmp);//进阻塞队列1
				System.out.println(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：1]");
				GUI.ProRec.append(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：1]"+"\n");
				ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：1]"+"\n");
				ClockInterrupt.Scheduling_info+=("FFFF:[阻塞队列 1：");
				PrintfBlockQueue(memory.BlockQueue_Keyboard,2);
			}
			else
			{
				System.out.println("运行队列为空");
				//GUI.ProRec.append("运行队列为空"+"\n");
				ClockInterrupt.Scheduling_info+=("运行队列为空"+"\n");
			}
			
		}
		//进程阻塞原语(运行队列->阻塞队列2)
				public void BlockingPrimitive2(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.RunningQueue.isEmpty())
					{
						pcb_tmp=DeQueue(memory.RunningQueue,this);//出运行队列
						
						pcb_tmp.PSW=2;//进程状态
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=2;//PCB表里的状态
						memory.BlockQueue_Screen.add(pcb_tmp);//进阻塞队列
						System.out.println(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：2]");
						GUI.ProRec.append(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：2]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：2]"+"\n");
						ClockInterrupt.Scheduling_info+=("FFFF:[阻塞队列 2：");
						PrintfBlockQueue(memory.BlockQueue_Screen,1);
						//System.out.println("阻塞队列："+memory.BlockingQueue);//???????这里直接输出可能有问题，验证
					}
					else
					{
						System.out.println("运行队列为空");
						//GUI.ProRec.append("运行队列为空"+"\n");
						ClockInterrupt.Scheduling_info+=("运行队列为空"+"\n");
					}
					
				}
				//进程阻塞原语(运行队列->阻塞队列3)
				public void BlockingPrimitive3(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.RunningQueue.isEmpty())
					{
						pcb_tmp=DeQueue(memory.RunningQueue,this);//出运行队列
						
						pcb_tmp.PSW=2;//进程状态
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=2;//PCB表里的状态
						memory.BlockQueue_DiskRead.add(pcb_tmp);//进阻塞队列
						System.out.println(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：3]");
						GUI.ProRec.append(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：3]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：3]"+"\n");
						ClockInterrupt.Scheduling_info+=("FFFF:[阻塞队列3：");
						PrintfBlockQueue(memory.BlockQueue_DiskRead,3);
					}
					else
					{
						System.out.println("运行队列为空");
						//GUI.ProRec.append("运行队列为空"+"\n");
						ClockInterrupt.Scheduling_info+=("运行队列为空"+"\n");
					}
					
				}
				//进程阻塞原语(运行队列->阻塞队列4)
				public void BlockingPrimitive4(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.RunningQueue.isEmpty())
					{
						pcb_tmp=DeQueue(memory.RunningQueue,this);//出运行队列
					
						pcb_tmp.PSW=2;//进程状态
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=2;//PCB表里的状态
						memory.BlockQueue_DiskWrite.add(pcb_tmp);//进阻塞队列4
						System.out.println(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：4]");
						GUI.ProRec.append(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：4]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：4]"+"\n");
						ClockInterrupt.Scheduling_info+=("FFFF:[阻塞队列 4：");
						PrintfBlockQueue(memory.BlockQueue_DiskWrite,4);
					}
					else
					{
						System.out.println("运行队列为空");
						//GUI.ProRec.append("运行队列为空"+"\n");
						ClockInterrupt.Scheduling_info+=("运行队列为空"+"\n");
					}
					
				}
				//进程阻塞原语(运行队列->阻塞队列5)
				public void BlockingPrimitive5(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.RunningQueue.isEmpty())
					{
						pcb_tmp=DeQueue(memory.RunningQueue,this);//出运行队列
						System.out.println(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：5]");
						GUI.ProRec.append(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：5]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[阻塞进程："+this.ProID+"：5]"+"\n");
						pcb_tmp.PSW=2;//进程状态
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=2;//PCB表里的状态
						memory.BlockQueue_Print.add(pcb_tmp);//进阻塞队列5
						
						ClockInterrupt.Scheduling_info+=("FFFF:[阻塞队列5：");
						PrintfBlockQueue(memory.BlockQueue_Print,4);
					}
					else
					{
						System.out.println("运行队列为空");
						//GUI.ProRec.append("运行队列为空"+"\n");
						ClockInterrupt.Scheduling_info+=("运行队列为空"+"\n");
					}
					
				}
		//进程撤销原语(阻塞队列->终止队列)
		public void RevokePrimitive(Memory memory)
		{
			DeQueue(memory.RunningQueue,this);//出运行队列
			//memory.PcbList.remove(memory.PcbList.indexOf(this));
			memory.PcbList.remove(this);//从PCB表删除该进程
			System.out.println(ClockInterrupt.time+"：[终止进程："+this.ProID+"]");	
			GUI.ProRec.append(ClockInterrupt.time+"：[终止进程："+this.ProID+"]"+"\n");
			ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"：[终止进程："+this.ProID+"]"+"\n");
			PSW=5;//终止态
			memory.PcbList.remove(this);//从PCB表删除
		}
		//指定进程出队列
		public PCB DeQueue(Queue<PCB> queue,PCB pcb)
		{
			int i=0;
			PCB pcb_tmp1=new PCB();
			PCB pcb_tmp2=new PCB();
			while(i<queue.size())
			{
				pcb_tmp1=queue.remove();
				if(pcb_tmp1.ProID==this.ProID)
				{
					pcb_tmp2=pcb_tmp1;
					i++;
					continue;
				}
				queue.add(pcb_tmp1);
				i++;
			}
			return pcb_tmp2;
		}
		//遍历就绪队列
		public void PrintfReadyQueue(Queue<PCB> queue)
		{
			int i;
			PCB pcb_tmp1=new PCB();
			int size=queue.size();
			for(i=0;i<size;i++)
			{
				pcb_tmp1=queue.remove();
				queue.add(pcb_tmp1);
				ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"=进程"+pcb_tmp1.ProID+"；");
			}
			
			ClockInterrupt.Scheduling_info+=("]\n");
		}
		//遍历阻塞队列
		public void PrintfBlockQueue(Queue<PCB> queue,int j)
		{
			int i;
			PCB pcb_tmp1=new PCB();
			int size=queue.size();
			for(i=0;i<size;i++)
			{
				pcb_tmp1=queue.remove();
				
				
				queue.add(pcb_tmp1);
				ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"/"+(ClockInterrupt.time+j)+"=进程"+pcb_tmp1.ProID);
			}
			
			ClockInterrupt.Scheduling_info+=("]\n");
		}
		

	}
