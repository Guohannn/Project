package RunProcess;

public class PageScheduling {
    //24块为进程控制块 8块为缓冲区 单缓冲
	
	Page_Fault_Interrupt Process_PageList[]=new Page_Fault_Interrupt[3];//页表，3*4B=12B
	//内存管理初始化
	 PageScheduling()
	{
		int i;
		for(i=0;i<3;i++)
		{
			Process_PageList[i]=new Page_Fault_Interrupt();
		}
	}
	//页面分配，固定分配局部置换，每个进程分配3 个物理块
	public boolean AllocatedPage(Memory memory,PCB pcb)
	{
		System.out.println("为进程"+pcb.ProID+"分配内存中...");
		if(memory.Memory_Size>=3*1024)
		{
			int i=0;//内存物理块号
			int j=0;//为进程分配的初始逻辑页号
			int k;
			while(i<23&&j<3)
			{
				if(memory.Memory_State[i]==0)//内存物理块未被占用
				{
					for(k=0;k<3;k++)//生成页表
					{
						if(Process_PageList[k].Pagenum<0)
						{
							Process_PageList[k].Pagenum=j;
							Process_PageList[k].Blocknum=i;
							Process_PageList[k].State=1;
							Process_PageList[k].Visit=0;
							//Process_PageList[k].Address=pcb.HardDiskAddress
							Process_PageList[k].Address[0]=pcb.HardDiskAddress[0][0];
							Process_PageList[k].Address[1]=pcb.HardDiskAddress[1][j];//扇区号
							break;							
						}
					}
					memory.Memory_Size-=1024;
					memory.Memory_State[i]=1;//物理块被占用
					memory.Memory_Pro[i]=pcb.ProID;
					LRU_Visit(k);
					j++;
				}
				i++;				
			}
			return true;
		}
		else
		{
			System.out.println("内存不足，无法进行分配");
			return false;
		}
		
	}
	//缓冲区分配
		public boolean Allocatedbuffer(Memory memory,PCB pcb)
		{
			System.out.println("为指令"+pcb.Instruc[pcb.ProID].Instruc_ID+"分配缓冲区中...");//一条指令占缓冲区一块
			
			if(memory.Buffer_Size>=1024)
			{ 
				int i=32-(memory.Buffer_Size/1024);
				int x=8-(memory.Buffer_Size/1024);
				ClockInterrupt.Scheduling_info+=("MMMM:[缓冲区"+x+"："+ClockInterrupt.time+"="+pcb.ProID+"]\n");
				
				
				memory.Memory_State[i]=1;//物理块被占用
				memory.Buffer_Size-=1024;//可用的缓冲区减少一块
				return true;
				
			}
			else
			{
				for(int k=24;k<32;k++)
				{
					memory.Memory_State[k]=0;//物理块被占用
				}
				memory.Buffer_Size=8*1024;
				
				System.out.println("清空缓冲区");
				return false;
			}
			
		}
		
	public void LRU_Visit(int j)
	{
		for(int i=0;i<3;i++)
		{
			if(Process_PageList[i].State==1&&i!=j)
				Process_PageList[i].Visit++;
		}
	}
	//页面回收
	public void RecycleProcessPage(Memory memory,PCB pcb)
	{
		int i;
		System.out.println("进程"+pcb.ProID+"执行完毕，回收页面释放内存");
		memory.PtabelIns_info+="进程"+pcb.ProID+"执行完毕，回收页面释放内存\n";
		//GUI.ProRec.append("进程"+pcb.ProID+"执行完毕，回收页面释放内存"+"\n");
		for(i=0;i<3;i++)
		{
			if(Process_PageList[i].State==1)//回收内存
			{
				memory.Memory_State[Process_PageList[i].Blocknum]=0;
				memory.Memory_Pro[Process_PageList[i].Blocknum]=-1;
				Process_PageList[i].Pagenum=-1;
				Process_PageList[i].Blocknum=-1;
				Process_PageList[i].State=0;
				Process_PageList[i].Visit=0;				
			}
			//回收页表
			Process_PageList[i].Pagenum=-1;
			Process_PageList[i].Blocknum=-1;
			Process_PageList[i].State=0;
			Process_PageList[i].Visit=0;
			Process_PageList[i].Modify=-1;
			Process_PageList[i].Address[0]=-1;
			Process_PageList[i].Address[1]=-1;

		}
		memory.Memory_Size+=3*1024;
	}
	//页面是否被命中
	public int Page_Hit(LogicAddress address)
	{
		int i;
		for(i=0;i<3;i++)
		{
			if(address.block==Process_PageList[i].Pagenum)//命中
			{
				if(Process_PageList[i].State==1)
				{
					Memory.PtabelIns_info+=("页号"+address.block+"已在内存"+"\n");
					System.out.println("页号"+address.block+"已在内存");
					//GUI.ProRec.append("页号"+address.block+"已在内存"+"\n");
					Process_PageList[i].Visit=0;
					LRU_Visit(i);
					return i;
				}
			}
		}
		return -1;
	}

	//取指令，地址转换
	public void GetInstruc(PCB pcb,MMU mmu,int Instruc_ID,Memory memory,Harddisk harddisk,CPU cpu)
	{
		int logicaddress;
		int physicaladdress;
		LogicAddress laddress=new LogicAddress();
		logicaddress=pcb.Instruc[Instruc_ID].L_Address;//取指令地址
		Memory.PtabelIns_info+=("访问指令,逻辑地址为"+logicaddress);
		System.out.println("访问指令,逻辑地址为"+logicaddress);
		//GUI.ProRec.append("访问指令,逻辑地址为"+logicaddress);
		laddress=mmu.LAddress_Divide(logicaddress);
		physicaladdress=mmu.Logic_To_Physical(laddress, pcb);
		
		switch(physicaladdress)
		{
		case -1:Memory.PtabelIns_info+=("越界中断"+"\n");
			    System.out.println("越界中断");
		        //GUI.ProRec.append("越界中断"+"\n");break;
		case -2:Memory.PtabelIns_info+=("页表未命中,缺页中断"+"\n");
			    System.out.println("页表未命中,缺页中断");		       
		       // GUI.ProRec.append("页表未命中,缺页中断"+"\n");
				Page_Fault_Interrupt.PageFault(pcb.page.Process_PageList,laddress.block, pcb, memory, mmu,harddisk);
				pcb.page.GetInstruc(pcb, mmu, Instruc_ID, memory,harddisk,cpu);
		        break;
		default://转化为物理地址
			Memory.PtabelIns_info+=("物理地址为"+physicaladdress+",取指令"+"\n");
			    System.out.println("物理地址为"+physicaladdress+",取指令");
			    //GUI.ProRec.append("物理地址为"+physicaladdress+",取指令"+"\n");
			    if(pcb.Instruc[Instruc_ID].Instruc_State==0)
			    {
			    	Memory.PtabelIns_info+=("取址成功：用户态计算操作指令"+"\n");
			    	System.out.println("取址成功：用户态计算操作指令");
			    	//GUI.ProRec.append("取址成功：用户态计算操作指令"+"\n");
			    }
			    else if(pcb.Instruc[Instruc_ID].Instruc_State==1)
			    {
			    	Memory.PtabelIns_info+=("取址成功：用户态计算操作指令"+"\n");
			    	System.out.println("取址成功：用户态计算操作指令");
			    	//GUI.ProRec.append("取址成功：用户态计算操作指令"+"\n");
			    }
			    else if(pcb.Instruc[Instruc_ID].Instruc_State==2)
			    {
			    	Memory.PtabelIns_info+=("取址成功：键盘输入变量指令"+"\n");
			    	System.out.println("取址成功：键盘输入变量指令");
			    	//GUI.ProRec.append("取址成功：键盘输入变量指令"+"\n");
			    }
			    if(pcb.Instruc[Instruc_ID].Instruc_State==3)
			    {
			    	Memory.PtabelIns_info+=("取址成功：屏幕显示输出指令"+"\n");
			    	System.out.println("取址成功：屏幕显示输出指令");
			    	//GUI.ProRec.append("取址成功：屏幕显示输出指令"+"\n");
			    }
			    else if(pcb.Instruc[Instruc_ID].Instruc_State==4)
			    {
			    	Memory.PtabelIns_info+=("取址成功：磁盘文件读操作指令"+"\n");
			    	System.out.println("取址成功：磁盘文件读操作指令");
			    	//GUI.ProRec.append("取址成功：磁盘文件读操作指令"+"\n");
			    }
			    else if(pcb.Instruc[Instruc_ID].Instruc_State==5)
			    {
			    	Memory.PtabelIns_info+=("取址成功：磁盘文件写操作指令"+"\n");
			    	System.out.println("取址成功：磁盘文件写操作指令");
			    	//GUI.ProRec.append("取址成功：磁盘文件写操作指令"+"\n");
			    }
			    else if(pcb.Instruc[Instruc_ID].Instruc_State==6)
			    {
			    	Memory.PtabelIns_info+=("取址成功：打印操作指令"+"\n");
			    	System.out.println("取址成功：打印操作指令");
			    	//GUI.ProRec.append("取址成功：打印操作指令"+"\n");
			    }
		}	
	}
	//生成页表
	public void Ptabel(PCB p)
	{
		
		Memory.PtabelIns_info+="----------------进程"+p.ProID+"页表---------------------\n";
		Memory.PtabelIns_info+="Pagenum  Blocknum  State  Visit   Modify  Address\n";
		for(int i=0;i<3;i++)
		{
			Memory.PtabelIns_info+=Process_PageList[i].Pagenum+"\t    "+Process_PageList[i].Blocknum
					+"\t     "+Process_PageList[i].State+"\t   "+Process_PageList[i].Visit+"\t   "
					+Process_PageList[i].Modify+"\t  "+Process_PageList[i].Address[0]+" "+Process_PageList[i].Address[1]+"\t\n";
		}
	}
	
	
}
