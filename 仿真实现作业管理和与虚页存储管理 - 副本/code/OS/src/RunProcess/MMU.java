package RunProcess;

public class MMU {//内存管理单元

	int StartAddress=30*1024;//页表起始地址 内存最后一块
	TLB tlb[]=new TLB[8];
	int BelongPro[]=new int[8];
	//一个进程3个页面 页表项大小4B，页表大小：12B
	int PageLength=20;
	MMU()
	{
		int i;
		for(i=0;i<8;i++)
		{
			tlb[i]=new TLB();
			BelongPro[i]=-1;
		}
		
	}
	//逻辑地址分解成页号
	public LogicAddress LAddress_Divide(int LAddress)
	{
		LogicAddress address=new LogicAddress();
		address.block=LAddress;
		return address;		
	}
	//快表是否已满
	public boolean TLB_FULL()
	{
		int i;
		for(i=0;i<8;i++)
		{
			if(tlb[i].Pagenum<0)
			{
				return false;//tlb表里还有空间，即未满
			}
		}
		return true;//快表已满
	}
	//快表已满，进行替换
	public void TLB_REPLACE(int p,PCB pcb)//p是索引号
	{
		//查找被替换的页面（最长时间未被访问的页面）
		int k;
		int Visit_min=-1;
		for(k=0;k<8;k++)
		{
			if(tlb[k].State==1){
				if(Visit_min<0)
					Visit_min=k;
				else if(tlb[k].Visit>tlb[Visit_min].Visit)
					Visit_min=k;
			}
		}
		//替换该页面
				tlb[Visit_min].Pagenum=pcb.page.Process_PageList[p].Pagenum;
				tlb[Visit_min].Blocknum=pcb.page.Process_PageList[p].Blocknum;
				tlb[Visit_min].State=pcb.page.Process_PageList[p].State;
				tlb[Visit_min].Visit=pcb.page.Process_PageList[p].Visit;
				BelongPro[Visit_min]=pcb.ProID;
				TLB_Visit(Visit_min);
				
			}
	//快表是否命中
	public int TLB_HIT(LogicAddress logicaddress,PCB pcb)
			{
				int i;
				for(i=0;i<8;i++)
				{
					if(logicaddress.block==tlb[i].Pagenum&&pcb.ProID==BelongPro[i])
					{
						return i;//命中
					}
				}
				return -1;//未命中
			}
	//LRU算法
	public void TLB_Visit(int k)
			{
				int i;
				tlb[k].Visit=0;
				for(i=0;i<8;i++)
				{
					if(i!=k)
					{
						tlb[i].Visit++;
					}
				}
				
			}
	//逻辑地址转换成物理地址
	public int Logic_To_Physical(LogicAddress logicaddress,PCB pcb)
			{
				int PhysicalAddress=0;
				int p=-1;
				int Blocknum;
				if(logicaddress.block>PageLength)
				{
					return -1;
				}
				else
				{
					System.out.println("查询快表：");
					//GUI.ProRec.append("查询快表"+"\n");
					Memory.PtabelIns_info+=("查询快表"+"\n");
					int TLB_HIT_NUM=TLB_HIT(logicaddress,pcb);
					if(TLB_HIT_NUM>=0)
					{			
						System.out.println("快表命中");
						//GUI.ProRec.append("快表命中"+"\n");
						Memory.PtabelIns_info+=("快表命中"+"\n");
						PhysicalAddress=tlb[TLB_HIT_NUM].Blocknum;
						TLB_Visit(TLB_HIT_NUM);
					}
					else
					{
						System.out.println("快表未命中");
						//GUI.ProRec.append("快表未命中"+"\n");
						Memory.PtabelIns_info+=("快表未命中"+"\n");
						//判断页表是否命中
						p=pcb.page.Page_Hit(logicaddress);
						if(p>=0)
						{
							System.out.println("页表命中");
							//GUI.ProRec.append("页表命中"+"\n");
							Memory.PtabelIns_info+=("页表命中"+"\n");
							Blocknum=pcb.page.Process_PageList[p].Blocknum;
							PhysicalAddress=Blocknum;
							//修改快表
							if(!TLB_FULL())//快表未满
							{
								for(int j=0;j<8;j++)
								{
									if(tlb[j].Pagenum<0)
									{
										tlb[j].Pagenum=pcb.page.Process_PageList[p].Pagenum;
										tlb[j].Blocknum=pcb.page.Process_PageList[p].Pagenum;
										tlb[j].Visit=pcb.page.Process_PageList[p].Visit;
										BelongPro[j]=pcb.ProID;
										TLB_Visit(j);
										break;
									}
								}
							}
							else//快表已满
							{
								TLB_REPLACE(p,pcb);
							}
						}
						else//页表未命中
						{
							return -2;
						}
					}
					
				}
				return PhysicalAddress;
			}
	}
	

