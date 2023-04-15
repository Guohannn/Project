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
				//����ʱ���ж�5�μ���Ƿ����µ���ҵ����
				Checktime++;
				
				if(ClockInterrupt.time>=5&&ClockInterrupt.time%5==0&&!ClockInterrupt.Finish)
				{
					System.out.println("�����������Ƿ�������ҵ��");
					GUI.ProRec.append("�����������Ƿ�������ҵ��\n");
					clock.Scheduling_info+=("�����������Ƿ�������ҵ��\n");
					if((harddisk.BackupQueue.isEmpty()))
					{
						System.out.println("��");
						GUI.ProRec.append("��"+"\n");
						clock.Scheduling_info+=("��"+"\n");
					}
					for(int i=0;(!harddisk.BackupQueue.isEmpty())&&ClockInterrupt.time>=harddisk.BackupQueue.element().getInTimes();i++)
					{
						System.out.println(ClockInterrupt.time+"��"+"[������ҵ��"+(scheduling.ReachedProcess+1)+"]");
						GUI.ProRec.append(ClockInterrupt.time+"��"+"[������ҵ��"+(scheduling.ReachedProcess+1)+"]"+"\n");
						clock.Scheduling_info+=(ClockInterrupt.time+"��"+"[������ҵ��"+(scheduling.ReachedProcess+1)+"]"+"\n");
						scheduling.jcb_tmp=Harddisk.BackupQueue.remove();
						scheduling.pcb[scheduling.ReachedProcess++]=new PCB();
						scheduling.pcb[scheduling.ReachedProcess-1].CreatePrimitive(memory,scheduling.jcb_tmp,harddisk);//�½�->����,������ҵ��Ӧ����
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
