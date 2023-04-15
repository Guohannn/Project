package RunProcess;

public class Disk_Write extends Thread{

	
	public static boolean ifDisk_Write=false;//�Ƿ�������д����
	
	ClockInterrupt clock=new ClockInterrupt();
	CPU cpu=new CPU();
	Memory memory=new Memory();
	Harddisk harddisk=new Harddisk();
	MMU mmu=new MMU();
	ProcessScheduling scheduling=new ProcessScheduling();
	
	Disk_Write(Harddisk h,Memory m,CPU cpu,ProcessScheduling s,MMU mmu,ClockInterrupt clock)
	{	
		this.harddisk=h;
		this.memory=m;
		this.cpu=cpu;
		this.scheduling=s;
		this.mmu=mmu;
		this.clock=clock;
	}
	//��������д����
	public static void Disk_Writestart(Memory memory,CPU cpu) {
		ifDisk_Write=true;
		Disk_WritePrint(memory,cpu.pcb_cpu);
	}
	
	//����д�������
	public void Disk_WriteFinish() {
		ifDisk_Write=false;
	}
	
	public static void Disk_WritePrint(Memory memory,PCB pcb)
	{
		int physicaladdress=0;
		int disk_physicaladdress=0;
		for(int i=0;i<3;i++)
		{
			if(pcb.page.Process_PageList[i].Pagenum==pcb.Instruc[pcb.IR].L_Address)
			{
				physicaladdress=pcb.page.Process_PageList[i].Blocknum*1024;
				disk_physicaladdress=pcb.page.Process_PageList[i].Address[0]+pcb.page.Process_PageList[i].Address[1]*64;
			}
			
		}
		int i=32-(memory.Buffer_Size/1024);
		System.out.println(ClockInterrupt.time+"��"+"[����������"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������5�������ļ�д����������"+pcb.Instruc[pcb.ProID].L_Address+" "+physicaladdress+" "+i+" "+disk_physicaladdress+"]");
		GUI.ProRec.append(ClockInterrupt.time+"��"+"[����������"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������5�������ļ�д����������"+pcb.Instruc[pcb.ProID].L_Address+" "+physicaladdress+" "+i+" "+disk_physicaladdress+"]\n");
		ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��"+"[����������"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������5�������ļ�д����������"+pcb.Instruc[pcb.ProID].L_Address+" "+physicaladdress+" "+i+" "+disk_physicaladdress+"]\n");
		
	}
	
	public void run()
	{
		
		while(ifDisk_Write)
		{
				try{
					
					
					Disk_WritePrint(memory,cpu.pcb_cpu);
					
					sleep(100);
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				Disk_WriteFinish();
			
			}	
		}
	
	
}
