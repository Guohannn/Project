package RunProcess;

import java.util.LinkedList;
import java.util.Queue;

public class Memory {

	//�û�������16ҳ PCB8ҳ+������8(24-31)ҳ 
	int Memory_Size=24*1024;//�û�������
	int Buffer_Size=8*1024;//������
	static String PtabelIns_info="";//��¼���н���ҳ��ı仯��Ϣ��ȡָ�����ҳ�棩ִ�й���
	int Memory_Pro[]=new int[32];//�ڴ��������������
	static int Memory_State[]=new int[32];//�ڴ������״̬���Ƿ�ռ�ã���0δռ�ã�1������ռ��
	  //������Ϣ
	Queue<PCB> ReadyQueue = new LinkedList<PCB>();//��������
	Queue<PCB> BlockQueue_Keyboard=new LinkedList<PCB>();//������������������1
	Queue<PCB> BlockQueue_Screen=new LinkedList<PCB>();//��Ļ��ʾ����������2
	Queue<PCB> BlockQueue_DiskRead=new LinkedList<PCB>();//�����ļ�����������������3
	Queue<PCB> BlockQueue_DiskWrite=new LinkedList<PCB>();//�����ļ�д��������������4
	Queue<PCB> BlockQueue_Print=new LinkedList<PCB>();//��ӡ��������������5
	Queue<PCB> RunningQueue = new LinkedList<PCB>();//���ж���
	LinkedList<PCB> PcbList=new LinkedList<PCB>();//PCB��
	public Memory()//�ڴ��ʼ��
	{
		for(int i=0;i<32;i++)
		{
			Memory_State[i]=0;
			Memory_Pro[i]=-1;
		}
			
	}
}
