package RunProcess;

public class Page_Fault_Interrupt extends Thread{
	//ҳ�����5+5+3+11=24λ����Ҫ4B
		public int Pagenum;//ҳ�� L_Address 10-20������4λ 
		public int Blocknum;//�����ţ�24��5λ
		public int State;//״̬λ������ָʾ�Ƿ�����ڴ�,0,δ���룬1�ѵ��룬1λ
		public int Visit;//�����ֶΣ����ڼ�¼��ҳ������ж೤ʱ��δ�����ʣ�1λ
		public int Modify;//�޸�λ����ʶ��ҳ�ڵ����ڴ���Ƿ��޸ģ�1λ
		public int Address[]=new int[2];//����ַ������ָ����ҳ������ϵĵ�ַ���������ҳʱ�ο���5+6=11λ
		
		public Page_Fault_Interrupt()
		{
			Pagenum=-1;
			Blocknum=-1;
			State=0;
			Visit=0;
			Modify=-1;
			Address[0]=-1;//�ŵ���
			Address[1]=-1;//������		
		}
		//ȱҳ�жϴ������
		public static void PageFault(Page_Fault_Interrupt Process_PageList[],int page,PCB pcb,Memory memory,MMU mmu,Harddisk harddisk)
		{	
			//�Ի�������ҳ��
			if(harddisk.SwapArea_HIT(page))
			{
				Memory.PtabelIns_info+=("�ӶԻ�������ҳ��"+page+"\n");
				System.out.println("�ӶԻ�������ҳ��"+page);
				//GUI.ProRec.append("�ӶԻ�������ҳ��"+page+"\n");
			}
			else
			{
				System.out.println("��ҳ�治�ڶԻ��������ļ�������");
				//GUI.ProRec.append("��ҳ�治�ڶԻ��������ļ�������"+"\n");
				Memory.PtabelIns_info+=("��ҳ�治�ڶԻ��������ļ�������"+"\n");
			}
			//���ұ��滻��ҳ��
			int Number;
			Number=PageLRU(Process_PageList);
			//ҳ���û����޸�ҳ��
			if(Process_PageList[Number].Modify==1)//��ҳ�汻�޸�,����Ҫ����һ���
			{
				//����Ի���
				if(!harddisk.SwapArea_FULL())
				{
					harddisk.In_SwapArea(Process_PageList[Number].Pagenum);
				}
				else
				{
					harddisk.SwapArea_Update(Process_PageList[Number].Pagenum);
				}
				Memory.PtabelIns_info+=("����Ի���"+"\n");
				System.out.println("����Ի���");
				//GUI.ProRec.append("����Ի���"+"\n");
				Process_PageList[Number].Modify=-1;
			}
			else//ҳ��δ���޸�
			{
				pcb.page.Process_PageList[Number].Pagenum=page;//�޸Ľ��̵�ҳ���ҳ��
				
				pcb.page.Process_PageList[Number].Visit=0;//�޸Ľ���ҳ��ķ����ֶ�
				
				pcb.page.Process_PageList[Number].Address[0]=pcb.HardDiskAddress[0][0];//�ŵ���
				
				pcb.page.Process_PageList[Number].Address[1]=pcb.HardDiskAddress[1][page];//������
				
				
			}
			for(int k=0;k<3;k++)
			{
				if(Process_PageList[k].State==1&&k!=Number)
					Process_PageList[k].Visit++;
			}
			//�޸Ŀ��
			if(!mmu.TLB_FULL())//���δ��
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
			else//�������
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
			Memory.PtabelIns_info+=("���滻��ҳ����"+Process_PageList[Number].Pagenum+"\n");
			System.out.println("���滻��ҳ����"+Process_PageList[Number].Pagenum);
			//GUI.ProRec.append("���滻��ҳ����"+Process_PageList[Number].Pagenum+"\n");
			return Number;
		}

		
}
