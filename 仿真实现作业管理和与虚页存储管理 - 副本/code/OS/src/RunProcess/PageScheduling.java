package RunProcess;

public class PageScheduling {
    //24��Ϊ���̿��ƿ� 8��Ϊ������ ������
	
	Page_Fault_Interrupt Process_PageList[]=new Page_Fault_Interrupt[3];//ҳ��3*4B=12B
	//�ڴ�����ʼ��
	 PageScheduling()
	{
		int i;
		for(i=0;i<3;i++)
		{
			Process_PageList[i]=new Page_Fault_Interrupt();
		}
	}
	//ҳ����䣬�̶�����ֲ��û���ÿ�����̷���3 �������
	public boolean AllocatedPage(Memory memory,PCB pcb)
	{
		System.out.println("Ϊ����"+pcb.ProID+"�����ڴ���...");
		if(memory.Memory_Size>=3*1024)
		{
			int i=0;//�ڴ�������
			int j=0;//Ϊ���̷���ĳ�ʼ�߼�ҳ��
			int k;
			while(i<23&&j<3)
			{
				if(memory.Memory_State[i]==0)//�ڴ������δ��ռ��
				{
					for(k=0;k<3;k++)//����ҳ��
					{
						if(Process_PageList[k].Pagenum<0)
						{
							Process_PageList[k].Pagenum=j;
							Process_PageList[k].Blocknum=i;
							Process_PageList[k].State=1;
							Process_PageList[k].Visit=0;
							//Process_PageList[k].Address=pcb.HardDiskAddress
							Process_PageList[k].Address[0]=pcb.HardDiskAddress[0][0];
							Process_PageList[k].Address[1]=pcb.HardDiskAddress[1][j];//������
							break;							
						}
					}
					memory.Memory_Size-=1024;
					memory.Memory_State[i]=1;//����鱻ռ��
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
			System.out.println("�ڴ治�㣬�޷����з���");
			return false;
		}
		
	}
	//����������
		public boolean Allocatedbuffer(Memory memory,PCB pcb)
		{
			System.out.println("Ϊָ��"+pcb.Instruc[pcb.ProID].Instruc_ID+"���仺������...");//һ��ָ��ռ������һ��
			
			if(memory.Buffer_Size>=1024)
			{ 
				int i=32-(memory.Buffer_Size/1024);
				int x=8-(memory.Buffer_Size/1024);
				ClockInterrupt.Scheduling_info+=("MMMM:[������"+x+"��"+ClockInterrupt.time+"="+pcb.ProID+"]\n");
				
				
				memory.Memory_State[i]=1;//����鱻ռ��
				memory.Buffer_Size-=1024;//���õĻ���������һ��
				return true;
				
			}
			else
			{
				for(int k=24;k<32;k++)
				{
					memory.Memory_State[k]=0;//����鱻ռ��
				}
				memory.Buffer_Size=8*1024;
				
				System.out.println("��ջ�����");
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
	//ҳ�����
	public void RecycleProcessPage(Memory memory,PCB pcb)
	{
		int i;
		System.out.println("����"+pcb.ProID+"ִ����ϣ�����ҳ���ͷ��ڴ�");
		memory.PtabelIns_info+="����"+pcb.ProID+"ִ����ϣ�����ҳ���ͷ��ڴ�\n";
		//GUI.ProRec.append("����"+pcb.ProID+"ִ����ϣ�����ҳ���ͷ��ڴ�"+"\n");
		for(i=0;i<3;i++)
		{
			if(Process_PageList[i].State==1)//�����ڴ�
			{
				memory.Memory_State[Process_PageList[i].Blocknum]=0;
				memory.Memory_Pro[Process_PageList[i].Blocknum]=-1;
				Process_PageList[i].Pagenum=-1;
				Process_PageList[i].Blocknum=-1;
				Process_PageList[i].State=0;
				Process_PageList[i].Visit=0;				
			}
			//����ҳ��
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
	//ҳ���Ƿ�����
	public int Page_Hit(LogicAddress address)
	{
		int i;
		for(i=0;i<3;i++)
		{
			if(address.block==Process_PageList[i].Pagenum)//����
			{
				if(Process_PageList[i].State==1)
				{
					Memory.PtabelIns_info+=("ҳ��"+address.block+"�����ڴ�"+"\n");
					System.out.println("ҳ��"+address.block+"�����ڴ�");
					//GUI.ProRec.append("ҳ��"+address.block+"�����ڴ�"+"\n");
					Process_PageList[i].Visit=0;
					LRU_Visit(i);
					return i;
				}
			}
		}
		return -1;
	}

	//ȡָ���ַת��
	public void GetInstruc(PCB pcb,MMU mmu,int Instruc_ID,Memory memory,Harddisk harddisk,CPU cpu)
	{
		int logicaddress;
		int physicaladdress;
		LogicAddress laddress=new LogicAddress();
		logicaddress=pcb.Instruc[Instruc_ID].L_Address;//ȡָ���ַ
		Memory.PtabelIns_info+=("����ָ��,�߼���ַΪ"+logicaddress);
		System.out.println("����ָ��,�߼���ַΪ"+logicaddress);
		//GUI.ProRec.append("����ָ��,�߼���ַΪ"+logicaddress);
		laddress=mmu.LAddress_Divide(logicaddress);
		physicaladdress=mmu.Logic_To_Physical(laddress, pcb);
		
		switch(physicaladdress)
		{
		case -1:Memory.PtabelIns_info+=("Խ���ж�"+"\n");
			    System.out.println("Խ���ж�");
		        //GUI.ProRec.append("Խ���ж�"+"\n");break;
		case -2:Memory.PtabelIns_info+=("ҳ��δ����,ȱҳ�ж�"+"\n");
			    System.out.println("ҳ��δ����,ȱҳ�ж�");		       
		       // GUI.ProRec.append("ҳ��δ����,ȱҳ�ж�"+"\n");
				Page_Fault_Interrupt.PageFault(pcb.page.Process_PageList,laddress.block, pcb, memory, mmu,harddisk);
				pcb.page.GetInstruc(pcb, mmu, Instruc_ID, memory,harddisk,cpu);
		        break;
		default://ת��Ϊ�����ַ
			Memory.PtabelIns_info+=("�����ַΪ"+physicaladdress+",ȡָ��"+"\n");
			    System.out.println("�����ַΪ"+physicaladdress+",ȡָ��");
			    //GUI.ProRec.append("�����ַΪ"+physicaladdress+",ȡָ��"+"\n");
			    if(pcb.Instruc[Instruc_ID].Instruc_State==0)
			    {
			    	Memory.PtabelIns_info+=("ȡַ�ɹ����û�̬�������ָ��"+"\n");
			    	System.out.println("ȡַ�ɹ����û�̬�������ָ��");
			    	//GUI.ProRec.append("ȡַ�ɹ����û�̬�������ָ��"+"\n");
			    }
			    else if(pcb.Instruc[Instruc_ID].Instruc_State==1)
			    {
			    	Memory.PtabelIns_info+=("ȡַ�ɹ����û�̬�������ָ��"+"\n");
			    	System.out.println("ȡַ�ɹ����û�̬�������ָ��");
			    	//GUI.ProRec.append("ȡַ�ɹ����û�̬�������ָ��"+"\n");
			    }
			    else if(pcb.Instruc[Instruc_ID].Instruc_State==2)
			    {
			    	Memory.PtabelIns_info+=("ȡַ�ɹ��������������ָ��"+"\n");
			    	System.out.println("ȡַ�ɹ��������������ָ��");
			    	//GUI.ProRec.append("ȡַ�ɹ��������������ָ��"+"\n");
			    }
			    if(pcb.Instruc[Instruc_ID].Instruc_State==3)
			    {
			    	Memory.PtabelIns_info+=("ȡַ�ɹ�����Ļ��ʾ���ָ��"+"\n");
			    	System.out.println("ȡַ�ɹ�����Ļ��ʾ���ָ��");
			    	//GUI.ProRec.append("ȡַ�ɹ�����Ļ��ʾ���ָ��"+"\n");
			    }
			    else if(pcb.Instruc[Instruc_ID].Instruc_State==4)
			    {
			    	Memory.PtabelIns_info+=("ȡַ�ɹ��������ļ�������ָ��"+"\n");
			    	System.out.println("ȡַ�ɹ��������ļ�������ָ��");
			    	//GUI.ProRec.append("ȡַ�ɹ��������ļ�������ָ��"+"\n");
			    }
			    else if(pcb.Instruc[Instruc_ID].Instruc_State==5)
			    {
			    	Memory.PtabelIns_info+=("ȡַ�ɹ��������ļ�д����ָ��"+"\n");
			    	System.out.println("ȡַ�ɹ��������ļ�д����ָ��");
			    	//GUI.ProRec.append("ȡַ�ɹ��������ļ�д����ָ��"+"\n");
			    }
			    else if(pcb.Instruc[Instruc_ID].Instruc_State==6)
			    {
			    	Memory.PtabelIns_info+=("ȡַ�ɹ�����ӡ����ָ��"+"\n");
			    	System.out.println("ȡַ�ɹ�����ӡ����ָ��");
			    	//GUI.ProRec.append("ȡַ�ɹ�����ӡ����ָ��"+"\n");
			    }
		}	
	}
	//����ҳ��
	public void Ptabel(PCB p)
	{
		
		Memory.PtabelIns_info+="----------------����"+p.ProID+"ҳ��---------------------\n";
		Memory.PtabelIns_info+="Pagenum  Blocknum  State  Visit   Modify  Address\n";
		for(int i=0;i<3;i++)
		{
			Memory.PtabelIns_info+=Process_PageList[i].Pagenum+"\t    "+Process_PageList[i].Blocknum
					+"\t     "+Process_PageList[i].State+"\t   "+Process_PageList[i].Visit+"\t   "
					+Process_PageList[i].Modify+"\t  "+Process_PageList[i].Address[0]+" "+Process_PageList[i].Address[1]+"\t\n";
		}
	}
	
	
}
