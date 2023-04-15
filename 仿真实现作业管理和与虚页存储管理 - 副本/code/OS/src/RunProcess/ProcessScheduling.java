package RunProcess;

import java.util.Queue;

public class ProcessScheduling extends Thread{
		int x;//ָ������˵��
		static int TimeSlice=2;//ʱ��Ƭ
		static JCB jcb_tmp=new JCB();
		PCB pcb[]=new PCB[10];
		
	     int ReachedProcess;//�Ѵ���������Ŀ
	     int FinishedProcess;//�������н�����Ŀ
	    Harddisk h=new Harddisk();
		
		int Work[];//����Ϊ5�Ĺ�������
		boolean Finish[];//����Ϊ5�Ĳ����͹�������	
		int deadlock[];//���������Ľ�������
		
		static int Blocktime;//����ʱ��
		static boolean CheckFinish=false;
		
		ProcessScheduling()
		{	
			
			TimeSlice=2;
			ReachedProcess=0;
			FinishedProcess=0;	
			Work=new int[2];
			Finish=new boolean[10];	
			deadlock=new int[10];
		}
		
		public int SystemRun(Memory memory,Harddisk harddisk,CPU cpu,int time,MMU mmu,ClockInterrupt c)
		{
			if(cpu.flag==1)//Cpu��ת  cpuæ
			{
				//ȡָ
				if(cpu.pcb_cpu.IR<cpu.pcb_cpu.InstrucNum)//��ǰִ�е�ָ��С��ָ������
				{
					
					c.Scheduling_info+=("ȡַ������"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.IR+"��ָ��"+"\n");
					memory.PtabelIns_info+=("ȡַ������"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.IR+"��ָ��"+"\n");
					System.out.println("ȡַ������"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.IR+"��ָ��");
					//GUI.ProRec.append("ȡַ������"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.IR+"��ָ��"+"\n");				
					cpu.pcb_cpu.page.GetInstruc(cpu.pcb_cpu, mmu, cpu.pcb_cpu.IR, memory,harddisk,cpu);
					cpu.pcb_cpu.page.Ptabel(cpu.pcb_cpu);
					System.out.println("ָ�����ͣ�"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State);
					
					//ִָ
					if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==0)
					{	
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						GUI.ProRec.append(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"0���û�̬���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						c.Scheduling_info+=(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"0���û�̬���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"0���û�̬���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						
						cpu.pcb_cpu.IR++;
						TimeSlice--;
						
					}
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==1)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						c.Scheduling_info+=(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"1���û�̬���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"1���û�̬���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"1���û�̬���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.IR++;
						
						if(TimeSlice==1)
						{
							TimeSlice--;
						}
						else 
						{
							TimeSlice--;
							TimeSlice--;
						}
						
						
						
						
						
						
					}
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==2)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						c.Scheduling_info+=(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"2���������������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"2���������������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"2���������������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.BlockingPrimitive1(memory);//����->����1
						
						Keyboard.Keyboardstart(cpu);
						
						cpu.flag=0;//�ó�cpu��cpu����
					}	
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==3)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						
						c.Scheduling_info+=(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"3����Ļ��ʾ���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"3����Ļ��ʾ���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"3����Ļ��ʾ���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.BlockingPrimitive2(memory);//����->����2
						
						Screen.Screenstart(cpu);
						
						cpu.flag=0;//�ó�cpu��cpu����
					}
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==4)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						c.Scheduling_info+=(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"4�������ļ���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"4�������ļ���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"4�������ļ���������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.BlockingPrimitive3(memory);//������ָ��+1,����->����3
						
						Disk_Read.Disk_Readstart(memory,cpu);
						
						cpu.protect(cpu.pcb_cpu);//�����ֳ�
						
						cpu.recover(cpu.pcb_cpu);//�ָ��ֳ�
						//cpu.flag=0;//�ó�cpu��cpu����
						
					}
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==5)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						c.Scheduling_info+=(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"5�������ļ�д������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"5�������ļ�д������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"5�������ļ�д������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.BlockingPrimitive4(memory);//������ָ��+1,����->����4
						
						cpu.pcb_cpu.page.Allocatedbuffer(memory,cpu.pcb_cpu);//���仺����
						Disk_Write.Disk_Writestart(memory,cpu);
						cpu.protect(cpu.pcb_cpu);//�����ֳ�
						
						cpu.recover(cpu.pcb_cpu);//�ָ��ֳ�
						//cpu.flag=0;//�ó�cpu��cpu����
						
					}
					else if(cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_State==6)
					{
						int physicaladdress=0;
						for(int i=0;i<3;i++)
						{
							if(cpu.pcb_cpu.page.Process_PageList[i].Pagenum==cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address)
							{
								physicaladdress=cpu.pcb_cpu.page.Process_PageList[i].Blocknum*1024;
							}
							
						}
						c.Scheduling_info+=(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"6�� ��ӡ������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						System.out.println(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"6�� ��ӡ������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						GUI.ProRec.append(c.time+"��[���н��̣�"+cpu.pcb_cpu.ProID+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].Instruc_ID+"��"+"6�� ��ӡ������"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].L_Address+"��"+physicaladdress+"��"+cpu.pcb_cpu.Instruc[cpu.pcb_cpu.IR].InRunTimes+"]\n");
						cpu.pcb_cpu.BlockingPrimitive5(memory);//����->����5
						for(int i=0;i<4;i++)
						{
							while(TimeSlice>0)
							{
								TimeSlice--;
								break;
							}
							TimeSlice=2;
							
						}
						cpu.flag=0;//�ó�cpu��cpu����
						cpu.pcb_cpu.WakeupPrimitive5(memory);
					}
					else
					{
						c.Scheduling_info+=("Ѱַ����!"+"\n");
						System.out.println("Ѱַ����!");
						//GUI.ProRec.append("Ѱַ����!"+"\n");
					}
				}
				else//��С��ָ������
				{
					//���ָ���Ƿ�ȫ��ִ�����
					CheckFinishedProcess(cpu.pcb_cpu,memory,cpu,c,harddisk);//����->��ֹ
				}
			}
			if(cpu.flag==0)//Cpu����
			{
				Ready_To_Running(memory,cpu,c);//����->����				
			}
			Running_To_Ready(memory,cpu,c);//����->����
			return Work_Finish(cpu);
		}
	
		//����̬������̬
		public void Ready_To_Running(Memory memory,CPU cpu,ClockInterrupt c)
		{
			if(!memory.ReadyQueue.isEmpty())
			{
				cpu.flag=1;
				Suffle(memory.ReadyQueue);
				cpu.pcb_cpu=memory.ReadyQueue.remove();//�ҳ������������ȼ���ߵĽ��̣�����������
				memory.RunningQueue.add(cpu.pcb_cpu);//�������ж���
				cpu.pcb_cpu.PSW=3;
				//memory.PcbList.get(memory.PcbList.indexOf(cpu.pcb_tmp)).ProState=3;//PCB�����״̬	
				TimeSlice=2;
				if(cpu.pcb_cpu!=null)
				{
					memory.PcbList.get(memory.PcbList.indexOf(cpu.pcb_cpu)).PSW=3;
				}
				
			}
		}
		//ʱ��Ƭ���ˣ�����̬������̬
		public void Running_To_Ready(Memory memory,CPU cpu,ClockInterrupt c)
		{
			if(cpu.pcb_cpu.PSW==3&&TimeSlice==0)//ʱ��Ƭ��ʣ
			{
				System.out.println(c.time+"��[���½���������У�"+cpu.pcb_cpu.ProID+"]");
				GUI.ProRec.append(c.time+"��[���½���������У�"+cpu.pcb_cpu.ProID+"]\n");
				c.Scheduling_info+=(c.time+"��[���½���������У�"+cpu.pcb_cpu.ProID+"]\n");
				memory.RunningQueue.remove();
				memory.ReadyQueue.add(cpu.pcb_cpu);
				cpu.pcb_cpu.PSW=1;
				memory.PcbList.get(memory.PcbList.indexOf(cpu.pcb_cpu)).PSW=1;//PCB�����״̬			
				cpu.flag=0;//Cpu����
			}
			
		}
		
		//���ָ���Ƿ�ִ���꣬�������Ƿ����������̬����ֹ̬ ����̬����ֹ̬
		public void CheckFinishedProcess(PCB pcb,Memory memory,CPU cpu,ClockInterrupt c,Harddisk harddisk)
		{
			if(pcb.PSW==3&&pcb.IR>=pcb.InstrucNum)
			{
				int i;
				pcb.RevokePrimitive(memory);
				pcb.RunTimes=c.time-pcb.RunTimes;
				System.out.println("����"+pcb.ProID+"����ʱ����"+pcb.RunTimes);
				GUI.ProRec.append("����"+pcb.ProID+"����ʱ����"+pcb.RunTimes+"\n");
				c.Scheduling_info+=("����"+pcb.ProID+"����ʱ����"+pcb.RunTimes+"\n");
				
				cpu.flag=0;
				FinishedProcess++;
			}
		}
		//�������Ƿ�ȫ�����
		public int Work_Finish(CPU cpu)
		{
			if(FinishedProcess==ReachedProcess&&ReachedProcess>0)
			{			
				cpu.pcb_cpu.ProID=-1;
				cpu.pcb_cpu.Priority=-1;
				cpu.pcb_cpu.InTimes=-1;
				cpu.pcb_cpu.InstrucNum=0;
				cpu.pcb_cpu.PSW=0;
				cpu.pcb_cpu.RunTimes=0;	
				TimeSlice=2;		
				return 1;
			}
			else
			{
				return 0;
			}
		}

		//�������а������ȼ�����ÿ���н��̽���������ж�Ҫ����)
		public void Suffle(Queue<PCB> queue)
		{
			int priority=-1;
			int size=queue.size();
			int i=0,j;
			PCB p[]=new PCB[size];
			PCB t=new PCB();
			//�����и�ֵ��p����
			while(i<size)
			{
				//System.out.println("ddd"+queue.size());
				p[i]=new PCB();
				p[i]=queue.remove();
				//System.out.println(p[i].Priority);
				i++;
			}	
			//����
			for(i=0;i<size;i++)
			{
				priority=p[i].Priority;
				for(j=i+1;j<size;j++)
				{
					if(p[j].Priority<p[i].Priority)
					{
						t=p[i];
						p[i]=p[j];
						p[j]=t;
					}
				}			
			}
			//�����
			i=0;
			while(i<size)
			{
				//System.out.println(p[i].Priority);
				queue.add(p[i]);
				i++;
			}	
		}

		
}		

		
	

