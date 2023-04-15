package RunProcess;

public class Page_Fault_Interrupt extends Thread{
	//页表项，共5+5+3+11=24位，需要4B
		public int Pagenum;//页号 L_Address 10-20的数，4位 
		public int Blocknum;//物理块号，24，5位
		public int State;//状态位，用于指示是否调入内存,0,未调入，1已调入，1位
		public int Visit;//访问字段，用于记录本页最近已有多长时间未被访问，1位
		public int Modify;//修改位，标识该页在调入内存后是否被修改，1位
		public int Address[]=new int[2];//外存地址，用于指出该页在外存上的地址，供调入该页时参考，5+6=11位
		
		public Page_Fault_Interrupt()
		{
			Pagenum=-1;
			Blocknum=-1;
			State=0;
			Visit=0;
			Modify=-1;
			Address[0]=-1;//磁道号
			Address[1]=-1;//扇区号		
		}
		//缺页中断处理程序
		public static void PageFault(Page_Fault_Interrupt Process_PageList[],int page,PCB pcb,Memory memory,MMU mmu,Harddisk harddisk)
		{	
			//对换区查找页面
			if(harddisk.SwapArea_HIT(page))
			{
				Memory.PtabelIns_info+=("从对换区调入页面"+page+"\n");
				System.out.println("从对换区调入页面"+page);
				//GUI.ProRec.append("从对换区调入页面"+page+"\n");
			}
			else
			{
				System.out.println("该页面不在对换区，从文件区调入");
				//GUI.ProRec.append("该页面不在对换区，从文件区调入"+"\n");
				Memory.PtabelIns_info+=("该页面不在对换区，从文件区调入"+"\n");
			}
			//查找被替换的页面
			int Number;
			Number=PageLRU(Process_PageList);
			//页面置换，修改页表
			if(Process_PageList[Number].Modify==1)//该页面被修改,则需要送入兑换区
			{
				//送入对换区
				if(!harddisk.SwapArea_FULL())
				{
					harddisk.In_SwapArea(Process_PageList[Number].Pagenum);
				}
				else
				{
					harddisk.SwapArea_Update(Process_PageList[Number].Pagenum);
				}
				Memory.PtabelIns_info+=("进入对换区"+"\n");
				System.out.println("进入对换区");
				//GUI.ProRec.append("进入对换区"+"\n");
				Process_PageList[Number].Modify=-1;
			}
			else//页面未被修改
			{
				pcb.page.Process_PageList[Number].Pagenum=page;//修改进程的页表该页号
				
				pcb.page.Process_PageList[Number].Visit=0;//修改进程页表的访问字段
				
				pcb.page.Process_PageList[Number].Address[0]=pcb.HardDiskAddress[0][0];//磁道号
				
				pcb.page.Process_PageList[Number].Address[1]=pcb.HardDiskAddress[1][page];//扇区号
				
				
			}
			for(int k=0;k<3;k++)
			{
				if(Process_PageList[k].State==1&&k!=Number)
					Process_PageList[k].Visit++;
			}
			//修改快表
			if(!mmu.TLB_FULL())//快表未满
			{
				for(int j=0;j<8;j++)
				{
					if(mmu.tlb[j].Pagenum<0)
					{
						mmu.tlb[j].Pagenum=pcb.page.Process_PageList[Number].Pagenum;
						mmu.tlb[j].Blocknum=pcb.page.Process_PageList[Number].Blocknum;
						mmu.tlb[j].State=pcb.page.Process_PageList[Number].State;
						mmu.tlb[j].Visit=pcb.page.Process_PageList[Number].Visit;
						mmu.BelongPro[j]=pcb.ProID;
						mmu.TLB_Visit(j);
						break;
					}
				}
			}
			else//快表已满
			{
				mmu.TLB_REPLACE(Number,pcb);
			}
		}
		public static int PageLRU(Page_Fault_Interrupt Process_PageList[])
		{
			int Number=-1;
			int i,j;
			for(i=0;i<3;i++)
			{
				if(Process_PageList[i].State==1)
				{
					if(Number<0)
					{
						Number=i;
					}
					else if(Process_PageList[i].Visit>Process_PageList[Number].Visit)
					{
						Number=i;
					}
				}
			}
			Memory.PtabelIns_info+=("被替换的页面是"+Process_PageList[Number].Pagenum+"\n");
			System.out.println("被替换的页面是"+Process_PageList[Number].Pagenum);
			//GUI.ProRec.append("被替换的页面是"+Process_PageList[Number].Pagenum+"\n");
			return Number;
		}

		
}
