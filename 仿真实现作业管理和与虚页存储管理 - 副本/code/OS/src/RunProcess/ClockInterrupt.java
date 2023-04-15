package RunProcess;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class ClockInterrupt extends Thread{

	public static int time=0;//ʱ�Ӽ�¼
	static boolean Finish=false;
	static String Scheduling_info="";
	CPU cpu=new CPU();
	Memory memory=new Memory();
	Harddisk harddisk=new Harddisk();
	MMU mmu=new MMU();
	ProcessScheduling scheduling=new ProcessScheduling();
	int situation=0;//�Ƿ�ִ�����
	ClockInterrupt()
	{
		
	}
	public void run()
	{
		while(!ClockInterrupt.Finish)
		{
			try 
			{
				time++;
				
				
				harddisk.InBackupQueue(this);//����󱸶���
				situation=scheduling.SystemRun(memory,harddisk,cpu,time,mmu,this);//situation=1,��ȫִ�����
				if(CPU.flag==0)
				{
					System.out.println(time+"��[CPU����]");
					GUI.ProRec.append(time+"��[CPU����]"+"\n");
				}
				if(situation==1)
				{
					try
					{
						File f1=new File("PageResults.txt");//���̽��
						FileWriter fin =new FileWriter(f1);//�����
						BufferedWriter bin=new BufferedWriter(fin);//������
						bin.write(memory.PtabelIns_info);//ҳ���ָ��ִ�У�����ҳ�棩��Ϣ�仯
						bin.close();
						fin.close();
						File f2=new File("ProcessResults-277.txt");
						FileWriter f=new FileWriter(f2);
						BufferedWriter b=new BufferedWriter(f);
						b.write(Scheduling_info);//���̵�����Ϣ�仯
						b.close();
						f.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
					System.out.println("��ǰ������ȫ��ִ�����");
					GUI.ProRec.append("��ǰ������ȫ��ִ�����");
					for(int i=0;i<32;i++)
					{
						Memory.Memory_State[i]=0;
					}
					ClockInterrupt.Finish=true;
					JobsScheduling.Checktime=ClockInterrupt.time;
				}
			
				sleep(100);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
