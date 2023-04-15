package RunProcess;

public class Keyboard extends Thread{
	
	
	public static boolean ifKeyboard=false;//�Ƿ��������������
	
	ClockInterrupt clock=new ClockInterrupt();
	CPU cpu=new CPU();
	Memory memory=new Memory();
	Harddisk harddisk=new Harddisk();
	MMU mmu=new MMU();
	ProcessScheduling scheduling=new ProcessScheduling();
	
	Keyboard(Harddisk h,Memory m,CPU cpu,ProcessScheduling s,MMU mmu,ClockInterrupt clock)
	{	
		this.harddisk=h;
		this.memory=m;
		this.cpu=cpu;
		this.scheduling=s;
		this.mmu=mmu;
		this.clock=clock;
	}
	//���������������
	public static void Keyboardstart(CPU cpu) {
		ifKeyboard=true;
	
	}
	
	//��������������
	public void KeyboardFinish() {
		ifKeyboard=false;
	}
	
	public static void KeyboardPrint(Memory memory,PCB pcb)
	{
		
		System.out.println(ClockInterrupt.time+"��"+"�������룺"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������4�������ļ�������������"+pcb.Instruc[pcb.ProID].L_Address+" "+"]");
		GUI.ProRec.append(ClockInterrupt.time+"��"+"�������룺"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������4�������ļ�������������"+pcb.Instruc[pcb.ProID].L_Address+" "+"]\n");
		ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��"+"�������룺"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������4�������ļ�������������"+pcb.Instruc[pcb.ProID].L_Address+" "+"]\n");
		
	}
	public void run()
	{
		
		while(ifKeyboard)
		{
			
				try{
					
					
				
					KeyboardPrint(memory,cpu.pcb_cpu);
					sleep(100);
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				KeyboardFinish();
			
			}	
		}
	



	
}
