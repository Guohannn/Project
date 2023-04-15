package RunProcess;

public class Screen extends Thread{
	
	public static boolean ifScreen=false;//是否发生显示输出

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
	//发生输出系统调用
	public static void Screenstart(CPU cpu) {
		ifScreen=true;
		
	}
	
	//输出系统调用完成
	public void ScreenFinish() {
		ifScreen=false;
	}
	
	public static void ScreenPrint(Memory memory,PCB pcb)
	{
		
		System.out.println(ClockInterrupt.time+"："+"显示输出："+pcb.ProID+"："+pcb.Instruc[pcb.ProID].Instruc_ID+"，类型4，磁盘文件读操作函数："+pcb.Instruc[pcb.ProID].L_Address+" "+"]");
		GUI.ProRec.append(ClockInterrupt.time+"："+"显示输出："+pcb.ProID+"："+pcb.Instruc[pcb.ProID].Instruc_ID+"，类型4，磁盘文件读操作函数："+pcb.Instruc[pcb.ProID].L_Address+" "+"]\n");
		ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"："+"显示输出："+pcb.ProID+"："+pcb.Instruc[pcb.ProID].Instruc_ID+"，类型4，磁盘文件读操作函数："+pcb.Instruc[pcb.ProID].L_Address+" "+"]\n");
		
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
