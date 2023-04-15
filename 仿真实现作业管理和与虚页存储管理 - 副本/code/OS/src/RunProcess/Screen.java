package RunProcess;

public class Screen extends Thread{
	
	public static boolean ifScreen=false;//�Ƿ�����ʾ���

	ClockInterrupt clock=new ClockInterrupt();
	CPU cpu=new CPU();
	Memory memory=new Memory();
	Harddisk harddisk=new Harddisk();
	MMU mmu=new MMU();
	ProcessScheduling scheduling=new ProcessScheduling();
	
	Screen(Harddisk h,Memory m,CPU cpu,ProcessScheduling s,MMU mmu,ClockInterrupt clock)
	{	
		this.harddisk=h;
		this.memory=m;
		this.cpu=cpu;
		this.scheduling=s;
		this.mmu=mmu;
		this.clock=clock;
	}
	//�������ϵͳ����
	public static void Screenstart(CPU cpu) {
		ifScreen=true;
		
	}
	
	//���ϵͳ�������
	public void ScreenFinish() {
		ifScreen=false;
	}
	
	public static void ScreenPrint(Memory memory,PCB pcb)
	{
		
		System.out.println(ClockInterrupt.time+"��"+"��ʾ�����"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������4�������ļ�������������"+pcb.Instruc[pcb.ProID].L_Address+" "+"]");
		GUI.ProRec.append(ClockInterrupt.time+"��"+"��ʾ�����"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������4�������ļ�������������"+pcb.Instruc[pcb.ProID].L_Address+" "+"]\n");
		ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��"+"��ʾ�����"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������4�������ļ�������������"+pcb.Instruc[pcb.ProID].L_Address+" "+"]\n");
		
	}
	
	public void run()
	{
		
		while(ifScreen)
		{
			
				try{
					
					
				
					ScreenPrint(memory,cpu.pcb_cpu);
					sleep(100);
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				ScreenFinish();
			
			}	
		
	}



	
}
