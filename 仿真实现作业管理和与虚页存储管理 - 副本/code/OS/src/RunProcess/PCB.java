package RunProcess;

import java.util.Queue;

import java.util.Stack;

import CreateJobs.Instruction;



public class PCB {//���̿��ƿ�

	int ProID;//���̱��
	int Priority;//���������� ������ԽС ���ȼ�Խ��
	int InTimes;//���̴���ʱ��
	int EndTimes;//���̽���ʱ��
	int PSW;//����״̬  //0�½�̬,1����̬,2����̬,3����̬ 4��ֹ̬
	int RunTimes;//��������ʱ���б�
	int TurnTimes;//������תʱ��ͳ��
	int InstrucNum;//���̰�����ָ����Ŀ
	int PC;//��һ��ָ��ı��
	int IR;//����ִ�е�ָ����
	int mem;
	int HardDiskAddress[][]=new int[2][];//����ַ
	Instruction Instruc[]=new Instruction[128];//һҳ����128��ָ��
	PageScheduling page;//���̵�ҳ�����
	public PCB()
	{
		ProID=-1;
		Priority=-1;
		InTimes=-1;
		InstrucNum=0;
		PSW=0;
		IR=0;
		RunTimes=0;	
		HardDiskAddress[0]=new int[32];
		HardDiskAddress[1]=new int[64];
	}
	//���̴���ԭ��(�󱸶���->��������)
		public void CreatePrimitive(Memory memory,JCB jcb,Harddisk harddisk)
		{
			//��ʼ��PCB
			int i;
			this.ProID=jcb.ProID;
			this.Priority=jcb.Priority;
			this.InTimes=jcb.InTimes;
			this.InstrucNum=jcb.InstrucNum;
			for(i=0;i<8;i++)		
			{
				this.HardDiskAddress[1][i]=jcb.HardDiskAddress[1]+i;
			}
			this.HardDiskAddress[0][0]=jcb.HardDiskAddress[0];//�ŵ���
			for(i=0;i<this.InstrucNum;i++)
			{
				Instruc[i]=new Instruction();
				this.Instruc[i].Instruc_ID=jcb.Instruc[i].Instruc_ID;
				this.Instruc[i].Instruc_State=jcb.Instruc[i].Instruc_State;
				this.Instruc[i].L_Address=jcb.Instruc[i].L_Address;
				this.Instruc[i].InRunTimes=jcb.Instruc[i].InRunTimes;
			}
			//********
			//�����ڴ�
			page=new PageScheduling();
			if(this.page.AllocatedPage(memory, this))
			{
				System.out.println("����"+this.ProID+"�����ڴ�ռ�ɹ�!");
				//GUI.ProRec.append("����"+this.ProID+"�����ڴ�ռ�ɹ�!"+"\n");
				ClockInterrupt.Scheduling_info+=("����"+this.ProID+"�����ڴ�ռ�ɹ�!"+"\n");
				this.mem=1;
			}
			else
			{
				
				this.mem=0;
			}
			//********
			PSW=1;//����̬
			memory.ReadyQueue.add(this);//����������

			memory.PcbList.add(this);//����PCB��
			System.out.println(ClockInterrupt.time+"��"+"[�������̣�"+this.ProID+"]");
			GUI.ProRec.append(ClockInterrupt.time+"��"+"[�������̣�"+this.ProID+"]\n");
			ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��"+"[�������̣�"+this.ProID+"]\n");
			
			ClockInterrupt.Scheduling_info+=("FFFF:[�������� 1��");
			PrintfReadyQueue(memory.ReadyQueue);
			
			
			
		}
		

		//���̻���ԭ��(��������1->��������)
		public void WakeupPrimitive1(Memory memory)
		{
			PCB pcb_tmp=new PCB();
			if(!memory.BlockQueue_Keyboard.isEmpty())
			{
				pcb_tmp=DeQueue(memory.BlockQueue_Keyboard,this);//����������1
				System.out.println("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����");//ϵͳ����ָ��
				//GUI.ProRec.append("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����"+"\n");
				ClockInterrupt.Scheduling_info+=("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����"+"\n");
				pcb_tmp.IR++;
				System.out.println(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]");
				GUI.ProRec.append(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]\n");
				ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]\n");
				pcb_tmp.PSW=1;//����״̬
				memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=1;//PCB�����״̬
				memory.ReadyQueue.add(pcb_tmp);//����������
				ClockInterrupt.Scheduling_info+=("FFFF:[�������� 1��");
				PrintfReadyQueue(memory.ReadyQueue);
				
			}
			else
			{
				System.out.println("��������1Ϊ��");
				//GUI.ProRec.append("��������1Ϊ��"+"\n");
				ClockInterrupt.Scheduling_info+=("��������1Ϊ��"+"\n");
			}
		}
		//���̻���ԭ��(��������2->��������)
				public void WakeupPrimitive2(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.BlockQueue_Screen.isEmpty())
					{
						pcb_tmp=DeQueue(memory.BlockQueue_Screen,this);//����������
						System.out.println("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����");//ϵͳ����ָ��
						//GUI.ProRec.append("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����"+"\n");
						ClockInterrupt.Scheduling_info+=("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����"+"\n");
						pcb_tmp.IR++;
						System.out.println(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]");
						GUI.ProRec.append(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]"+"\n");
						pcb_tmp.PSW=1;//����״̬
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=1;//PCB�����״̬
						memory.ReadyQueue.add(pcb_tmp);//����������
						ClockInterrupt.Scheduling_info+=("FFFF:[�������� 1��");
						PrintfReadyQueue(memory.ReadyQueue);
						}
					else
					{
						System.out.println("��������2Ϊ��");
						//GUI.ProRec.append("��������2Ϊ��"+"\n");
						ClockInterrupt.Scheduling_info+=("��������2Ϊ��"+"\n");
					}
				}
				//���̻���ԭ��(��������3->��������)
				public void WakeupPrimitive3(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.BlockQueue_DiskRead.isEmpty())
					{
						
						pcb_tmp=DeQueue(memory.BlockQueue_DiskRead,this);//����������
						System.out.println("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����");//ϵͳ����ָ��
						//GUI.ProRec.append("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����"+"\n");
						ClockInterrupt.Scheduling_info+=("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����"+"\n");
						pcb_tmp.IR++;
						System.out.println(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]");
						GUI.ProRec.append(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]"+"\n");
						pcb_tmp.PSW=1;//����״̬
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=1;//PCB�����״̬
						memory.ReadyQueue.add(pcb_tmp);//����������
						ClockInterrupt.Scheduling_info+=("FFFF:[�������� 1��");
						PrintfReadyQueue(memory.ReadyQueue);
						
					}
					else
					{
						System.out.println("��������3Ϊ��");
						//GUI.ProRec.append("��������3Ϊ��"+"\n");
						ClockInterrupt.Scheduling_info+=("��������3Ϊ��"+"\n");
					}
				}
				//���̻���ԭ��(��������4->��������)
				public void WakeupPrimitive4(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.BlockQueue_DiskWrite.isEmpty())
					{
						pcb_tmp=DeQueue(memory.BlockQueue_DiskWrite,this);//����������
						System.out.println("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����");//ϵͳ����ָ��
						//GUI.ProRec.append("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����"+"\n");
						ClockInterrupt.Scheduling_info+=("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����"+"\n");
						pcb_tmp.IR++;
						System.out.println(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]");
						GUI.ProRec.append(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]"+"\n");
						pcb_tmp.PSW=1;//����״̬
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=1;//PCB�����״̬
						memory.ReadyQueue.add(pcb_tmp);//����������
						ClockInterrupt.Scheduling_info+=("FFFF:[�������� 1��");
						PrintfReadyQueue(memory.ReadyQueue);
						
					}
					else
					{
						System.out.println("��������4Ϊ��");
						//GUI.ProRec.append("��������4Ϊ��"+"\n");
						ClockInterrupt.Scheduling_info+=("��������4Ϊ��"+"\n");
					}
				}
				//���̻���ԭ��(��������5->��������)
				public void WakeupPrimitive5(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.BlockQueue_Print.isEmpty())
					{
						pcb_tmp=DeQueue(memory.BlockQueue_Print,this);//����������
						System.out.println("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����");//ϵͳ����ָ��
						//GUI.ProRec.append("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����"+"\n");
						ClockInterrupt.Scheduling_info+=("����"+pcb_tmp.ProID+"�ĵ�"+pcb_tmp.IR+"��ָ��ִ�����"+"\n");
						pcb_tmp.IR++;
						System.out.println(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]");
						GUI.ProRec.append(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[���½���������У�"+this.ProID+"]"+"\n");
						pcb_tmp.PSW=1;//����״̬
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=1;//PCB�����״̬
						memory.ReadyQueue.add(pcb_tmp);//����������
						ClockInterrupt.Scheduling_info+=("FFFF:[�������� 1��");
						PrintfReadyQueue(memory.ReadyQueue);
						
					}
					else
					{
						System.out.println("��������5Ϊ��");
						//GUI.ProRec.append("��������5Ϊ��"+"\n");
						ClockInterrupt.Scheduling_info+=("��������5Ϊ��"+"\n");
					}
				}
		//��������ԭ��(���ж���->��������1)
		public void BlockingPrimitive1(Memory memory)
		{
			PCB pcb_tmp=new PCB();
			if(!memory.RunningQueue.isEmpty())
			{
				pcb_tmp=DeQueue(memory.RunningQueue,this);//�����ж���
				
				pcb_tmp.PSW=2;//����״̬
				memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=2;//PCB�����״̬
				memory.BlockQueue_Keyboard.add(pcb_tmp);//����������1
				System.out.println(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��1]");
				GUI.ProRec.append(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��1]"+"\n");
				ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��1]"+"\n");
				ClockInterrupt.Scheduling_info+=("FFFF:[�������� 1��");
				PrintfBlockQueue(memory.BlockQueue_Keyboard,2);
			}
			else
			{
				System.out.println("���ж���Ϊ��");
				//GUI.ProRec.append("���ж���Ϊ��"+"\n");
				ClockInterrupt.Scheduling_info+=("���ж���Ϊ��"+"\n");
			}
			
		}
		//��������ԭ��(���ж���->��������2)
				public void BlockingPrimitive2(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.RunningQueue.isEmpty())
					{
						pcb_tmp=DeQueue(memory.RunningQueue,this);//�����ж���
						
						pcb_tmp.PSW=2;//����״̬
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=2;//PCB�����״̬
						memory.BlockQueue_Screen.add(pcb_tmp);//����������
						System.out.println(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��2]");
						GUI.ProRec.append(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��2]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��2]"+"\n");
						ClockInterrupt.Scheduling_info+=("FFFF:[�������� 2��");
						PrintfBlockQueue(memory.BlockQueue_Screen,1);
						//System.out.println("�������У�"+memory.BlockingQueue);//???????����ֱ��������������⣬��֤
					}
					else
					{
						System.out.println("���ж���Ϊ��");
						//GUI.ProRec.append("���ж���Ϊ��"+"\n");
						ClockInterrupt.Scheduling_info+=("���ж���Ϊ��"+"\n");
					}
					
				}
				//��������ԭ��(���ж���->��������3)
				public void BlockingPrimitive3(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.RunningQueue.isEmpty())
					{
						pcb_tmp=DeQueue(memory.RunningQueue,this);//�����ж���
						
						pcb_tmp.PSW=2;//����״̬
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=2;//PCB�����״̬
						memory.BlockQueue_DiskRead.add(pcb_tmp);//����������
						System.out.println(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��3]");
						GUI.ProRec.append(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��3]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��3]"+"\n");
						ClockInterrupt.Scheduling_info+=("FFFF:[��������3��");
						PrintfBlockQueue(memory.BlockQueue_DiskRead,3);
					}
					else
					{
						System.out.println("���ж���Ϊ��");
						//GUI.ProRec.append("���ж���Ϊ��"+"\n");
						ClockInterrupt.Scheduling_info+=("���ж���Ϊ��"+"\n");
					}
					
				}
				//��������ԭ��(���ж���->��������4)
				public void BlockingPrimitive4(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.RunningQueue.isEmpty())
					{
						pcb_tmp=DeQueue(memory.RunningQueue,this);//�����ж���
					
						pcb_tmp.PSW=2;//����״̬
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=2;//PCB�����״̬
						memory.BlockQueue_DiskWrite.add(pcb_tmp);//����������4
						System.out.println(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��4]");
						GUI.ProRec.append(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��4]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��4]"+"\n");
						ClockInterrupt.Scheduling_info+=("FFFF:[�������� 4��");
						PrintfBlockQueue(memory.BlockQueue_DiskWrite,4);
					}
					else
					{
						System.out.println("���ж���Ϊ��");
						//GUI.ProRec.append("���ж���Ϊ��"+"\n");
						ClockInterrupt.Scheduling_info+=("���ж���Ϊ��"+"\n");
					}
					
				}
				//��������ԭ��(���ж���->��������5)
				public void BlockingPrimitive5(Memory memory)
				{
					PCB pcb_tmp=new PCB();
					if(!memory.RunningQueue.isEmpty())
					{
						pcb_tmp=DeQueue(memory.RunningQueue,this);//�����ж���
						System.out.println(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��5]");
						GUI.ProRec.append(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��5]"+"\n");
						ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[�������̣�"+this.ProID+"��5]"+"\n");
						pcb_tmp.PSW=2;//����״̬
						memory.PcbList.get(memory.PcbList.indexOf(pcb_tmp)).PSW=2;//PCB�����״̬
						memory.BlockQueue_Print.add(pcb_tmp);//����������5
						
						ClockInterrupt.Scheduling_info+=("FFFF:[��������5��");
						PrintfBlockQueue(memory.BlockQueue_Print,4);
					}
					else
					{
						System.out.println("���ж���Ϊ��");
						//GUI.ProRec.append("���ж���Ϊ��"+"\n");
						ClockInterrupt.Scheduling_info+=("���ж���Ϊ��"+"\n");
					}
					
				}
		//���̳���ԭ��(��������->��ֹ����)
		public void RevokePrimitive(Memory memory)
		{
			DeQueue(memory.RunningQueue,this);//�����ж���
			//memory.PcbList.remove(memory.PcbList.indexOf(this));
			memory.PcbList.remove(this);//��PCB��ɾ���ý���
			System.out.println(ClockInterrupt.time+"��[��ֹ���̣�"+this.ProID+"]");	
			GUI.ProRec.append(ClockInterrupt.time+"��[��ֹ���̣�"+this.ProID+"]"+"\n");
			ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"��[��ֹ���̣�"+this.ProID+"]"+"\n");
			PSW=5;//��ֹ̬
			memory.PcbList.remove(this);//��PCB��ɾ��
		}
		//ָ�����̳�����
		public PCB DeQueue(Queue<PCB> queue,PCB pcb)
		{
			int i=0;
			PCB pcb_tmp1=new PCB();
			PCB pcb_tmp2=new PCB();
			while(i<queue.size())
			{
				pcb_tmp1=queue.remove();
				if(pcb_tmp1.ProID==this.ProID)
				{
					pcb_tmp2=pcb_tmp1;
					i++;
					continue;
				}
				queue.add(pcb_tmp1);
				i++;
			}
			return pcb_tmp2;
		}
		//������������
		public void PrintfReadyQueue(Queue<PCB> queue)
		{
			int i;
			PCB pcb_tmp1=new PCB();
			int size=queue.size();
			for(i=0;i<size;i++)
			{
				pcb_tmp1=queue.remove();
				queue.add(pcb_tmp1);
				ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"=����"+pcb_tmp1.ProID+"��");
			}
			
			ClockInterrupt.Scheduling_info+=("]\n");
		}
		//������������
		public void PrintfBlockQueue(Queue<PCB> queue,int j)
		{
			int i;
			PCB pcb_tmp1=new PCB();
			int size=queue.size();
			for(i=0;i<size;i++)
			{
				pcb_tmp1=queue.remove();
				
				
				queue.add(pcb_tmp1);
				ClockInterrupt.Scheduling_info+=(ClockInterrupt.time+"/"+(ClockInterrupt.time+j)+"=����"+pcb_tmp1.ProID);
			}
			
			ClockInterrupt.Scheduling_info+=("]\n");
		}
		

	}
