package RunProcess;

public class MMU {//�ڴ����Ԫ

	int StartAddress=30*1024;//ҳ����ʼ��ַ �ڴ����һ��
	TLB tlb[]=new TLB[8];
	int BelongPro[]=new int[8];
	//һ������3��ҳ�� ҳ�����С4B��ҳ���С��12B
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
	//�߼���ַ�ֽ��ҳ��
	public LogicAddress LAddress_Divide(int LAddress)
	{
		LogicAddress address=new LogicAddress();
		address.block=LAddress;
		return address;		
	}
	//����Ƿ�����
	public boolean TLB_FULL()
	{
		int i;
		for(i=0;i<8;i++)
		{
			if(tlb[i].Pagenum<0)
			{
				return false;//tlb���ﻹ�пռ䣬��δ��
			}
		}
		return true;//�������
	}
	//��������������滻
	public void TLB_REPLACE(int p,PCB pcb)//p��������
	{
		//���ұ��滻��ҳ�棨�ʱ��δ�����ʵ�ҳ�棩
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
		//�滻��ҳ��
				tlb[Visit_min].Pagenum=pcb.page.Process_PageList[p].Pagenum;
				tlb[Visit_min].Blocknum=pcb.page.Process_PageList[p].Blocknum;
				tlb[Visit_min].State=pcb.page.Process_PageList[p].State;
				tlb[Visit_min].Visit=pcb.page.Process_PageList[p].Visit;
				BelongPro[Visit_min]=pcb.ProID;
				TLB_Visit(Visit_min);
				
			}
	//����Ƿ�����
	public int TLB_HIT(LogicAddress logicaddress,PCB pcb)
			{
				int i;
				for(i=0;i<8;i++)
				{
					if(logicaddress.block==tlb[i].Pagenum&&pcb.ProID==BelongPro[i])
					{
						return i;//����
					}
				}
				return -1;//δ����
			}
	//LRU�㷨
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
	//�߼���ַת���������ַ
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
					System.out.println("��ѯ���");
					//GUI.ProRec.append("��ѯ���"+"\n");
					Memory.PtabelIns_info+=("��ѯ���"+"\n");
					int TLB_HIT_NUM=TLB_HIT(logicaddress,pcb);
					if(TLB_HIT_NUM>=0)
					{			
						System.out.println("�������");
						//GUI.ProRec.append("�������"+"\n");
						Memory.PtabelIns_info+=("�������"+"\n");
						PhysicalAddress=tlb[TLB_HIT_NUM].Blocknum;
						TLB_Visit(TLB_HIT_NUM);
					}
					else
					{
						System.out.println("���δ����");
						//GUI.ProRec.append("���δ����"+"\n");
						Memory.PtabelIns_info+=("���δ����"+"\n");
						//�ж�ҳ���Ƿ�����
						p=pcb.page.Page_Hit(logicaddress);
						if(p>=0)
						{
							System.out.println("ҳ������");
							//GUI.ProRec.append("ҳ������"+"\n");
							Memory.PtabelIns_info+=("ҳ������"+"\n");
							Blocknum=pcb.page.Process_PageList[p].Blocknum;
							PhysicalAddress=Blocknum;
							//�޸Ŀ��
							if(!TLB_FULL())//���δ��
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
							else//�������
							{
								TLB_REPLACE(p,pcb);
							}
						}
						else//ҳ��δ����
						{
							return -2;
						}
					}
					
				}
				return PhysicalAddress;
			}
	}
	

