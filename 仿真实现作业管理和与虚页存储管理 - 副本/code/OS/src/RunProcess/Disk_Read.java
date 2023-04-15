package RunProcess;

public class Disk_Read extends Thread{

	

	static boolean ifDisk_Read=false;//�Ƿ������̶�����

	ClockInterrupt clock=new ClockInterrupt();
	CPU cpu=new CPU();
	Memory memory=new Memory();
	Harddisk harddisk=new Harddisk();
	MMU mmu=new MMU();
	ProcessScheduling scheduling=new ProcessScheduling();
	
	Disk_Read(Harddisk h,Memory m,CPU cpu,ProcessScheduling s,MMU mmu,ClockInterrupt clock)
	{	
		this.harddisk=h;
		this.memory=m;
		this.cpu=cpu;
		this.scheduling=s;
		this.mmu=mmu;
		this.clock=clock;
	}
	//�������̶�����
	public static void Disk_Readstart(Memory memory,CPU cpu) {
		ifDisk_Read=true;
		Disk_ReadPrint(memory,cpu.pcb_cpu);
	}
	
	public static void Disk_ReadPrint(Memory memory,PCB pcb)
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
		System.out.println(ClockInterrupt.time+"��"+"[�뻺������"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������4�������ļ�������������"+pcb.Instruc[pcb.ProID].L_Address+" "+physicaladdress+" "+i+" "+disk_physicaladdress+"]");
		GUI.ProRec.append(ClockInterrupt.time+"��"+"[�뻺������"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������4�������ļ�������������"+pcb.Instruc[pcb.ProID].L_Address+" "+physicaladdress+" "+i+" "+disk_physicaladdress+"]\n");
		ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��"+"[�뻺������"+pcb.ProID+"��"+pcb.Instruc[pcb.ProID].Instruc_ID+"������4�������ļ�������������"+pcb.Instruc[pcb.ProID].L_Address+" "+physicaladdress+" "+i+" "+disk_physicaladdress+"]\n");
		
	}
	
	//���̶��������
	public void Disk_ReadFinish() {
		ifDisk_Read=false;
	}
	public void run()
	{
		
		while(ifDisk_Read)
		{
			
			try{
					Disk_ReadPrint(memory,cpu.pcb_cpu);
					sleep(100);
					Disk_ReadFinish();
				}
				
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				
			
			
		}
	}

	
}
