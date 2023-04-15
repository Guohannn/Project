package RunProcess;



public class JobsScheduling extends Thread{
	static int Checktime=0;
	static boolean HLSFinish=false;
	ClockInterrupt clock=new ClockInterrupt();
	CPU cpu=new CPU();
	Memory memory=new Memory();
	Harddisk harddisk=new Harddisk();
	MMU mmu=new MMU();
	ProcessScheduling scheduling=new ProcessScheduling();
	JobsScheduling(Harddisk h,Memory m,CPU cpu,ProcessScheduling s,MMU mmu,ClockInterrupt clock)
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
		
		while(!JobsScheduling.HLSFinish)
		{
			try{
				//发生时钟中断5次检查是否有新的作业请求
				Checktime++;
				
				if(ClockInterrupt.time>=5&&ClockInterrupt.time%5==0&&!ClockInterrupt.Finish)
				{
					System.out.println("进程请求监测是否有新作业：");
					GUI.ProRec.append("进程请求监测是否有新作业：\n");
					clock.Scheduling_info+=("进程请求监测是否有新作业：\n");
					if((harddisk.BackupQueue.isEmpty()))
					{
						System.out.println("无");
						GUI.ProRec.append("无"+"\n");
						clock.Scheduling_info+=("无"+"\n");
					}
					for(int i=0;(!harddisk.BackupQueue.isEmpty())&&ClockInterrupt.time>=harddisk.BackupQueue.element().getInTimes();i++)
					{
						System.out.println(ClockInterrupt.time+"："+"[新增作业："+(scheduling.ReachedProcess+1)+"]");
						GUI.ProRec.append(ClockInterrupt.time+"："+"[新增作业："+(scheduling.ReachedProcess+1)+"]"+"\n");
						clock.Scheduling_info+=(ClockInterrupt.time+"："+"[新增作业："+(scheduling.ReachedProcess+1)+"]"+"\n");
						scheduling.jcb_tmp=Harddisk.BackupQueue.remove();
						scheduling.pcb[scheduling.ReachedProcess++]=new PCB();
						scheduling.pcb[scheduling.ReachedProcess-1].CreatePrimitive(memory,scheduling.jcb_tmp,harddisk);//新建->就绪,创建作业对应进程
						scheduling.pcb[scheduling.ReachedProcess-1].RunTimes=clock.time;
					}
				}
				if(JobsScheduling.Checktime==ClockInterrupt.time+10)
				{
					JobsScheduling.HLSFinish=true;		
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
