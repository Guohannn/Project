package RunProcess;

public class CheckWakeup extends Thread{
	
	static int Blocktime1=0;//阻塞时间1
	static int Blocktime2=0;//阻塞时间2
	static int Blocktime3=0;//阻塞时间3
	static int Blocktime4=0;//阻塞时间4
	static int Blocktime5=0;//阻塞时间5
	static boolean CheckFinish=false;
	ClockInterrupt clock=new ClockInterrupt();
	CPU cpu=new CPU();
	Memory memory=new Memory();
	Harddisk harddisk=new Harddisk();
	MMU mmu=new MMU();
	ProcessScheduling scheduling=new ProcessScheduling();
	CheckWakeup(Harddisk h,Memory m,CPU cpu,ProcessScheduling s,MMU mmu,ClockInterrupt clock)
	{	
		this.harddisk=h;
		this.memory=m;
		this.cpu=cpu;
		this.scheduling=s;
		this.mmu=mmu;
		this.clock=clock;
	}
	public void run()
	{
		
		while(!CheckWakeup.CheckFinish)
		{
			try{
				
				if(!memory.BlockQueue_Keyboard.isEmpty())
				{
					Blocktime1++;
					if(Blocktime1==2)
					{
						memory.BlockQueue_Keyboard.element().WakeupPrimitive1(memory);
						Blocktime1=0;
					}
					
				}
				if(!memory.BlockQueue_Screen.isEmpty())
				{
					Blocktime2++;
					if(Blocktime2==1)
					{
						memory.BlockQueue_Screen.element().WakeupPrimitive2(memory);
						Blocktime2=0;
					}
				}
				if(!memory.BlockQueue_DiskRead.isEmpty())
				{
					Blocktime3++;
					if(Blocktime3==3)
					{
						memory.BlockQueue_DiskRead.element().WakeupPrimitive3(memory);
						Blocktime3=0;
						CPU.flag=0;
					}
					
				}
				if(!memory.BlockQueue_DiskWrite.isEmpty())
				{
					Blocktime4++;
					if(Blocktime4==4)
					{
						memory.BlockQueue_DiskWrite.element().WakeupPrimitive4(memory);
						Blocktime4=0;
						CPU.flag=0;
					}
					
				}
				if(memory.Buffer_Size==8*1024)
				{
					clock.Scheduling_info+=(clock.time+"：[缓冲区无进程]"+"\n");
					System.out.println(clock.time+"：[缓冲区无进程]");
					GUI.ProRec.append(clock.time+"：[缓冲区无进程]"+"\n");	
					
				}
				if(JobsScheduling.HLSFinish==true)
				{
					CheckWakeup.CheckFinish=true;		
				}
				sleep(100);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			
		}
	}

}
