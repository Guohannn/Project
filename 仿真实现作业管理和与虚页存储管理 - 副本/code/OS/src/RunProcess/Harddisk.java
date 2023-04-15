package RunProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import CreateJobs.CreateJobs;
import CreateJobs.Instruction;


public class Harddisk {
	//������Ϊ���һ���ŵ� ����Ϊ�ļ���
	int Harddisk_size=10*32*64*1024;//���̴�С20MB 20480�� 10������   1������32���ŵ�    1���ŵ�64������   һ������1024b
	static int HardDiskAddress[][]=new int[32][64];//��Ҫ��0������32*64=2^11��
	int[] Page=new int[24];//��������С��24�� 3*8
	static int JobNum=5;//��ҵ�������ݶ�Ϊ5��֮�����ʱ�����ҵ
	static Queue<JCB> BackupQueue = new LinkedList<JCB>();//�󱸶���
	
	int[] Visit=new int[24];//������ҳ���δ����ʱ��
	int flag[]=new int[10];//��־��ҵ�Ƿ񱻴���  δ������0 ����1 Ŀǰ�����10����ҵ
	JCB jcb[]=new JCB[10];//��ҵ����
	Harddisk()
	{
		for(int i=0;i<24;i++)
		{
			Page[i]=-1;
			Visit[i]=0;
		}
		for(int i=0;i<10;i++)
		{
			flag[i]=0;
		}
	}
	//��ҵ���ļ���������
	public void ReadJobs(String Filename,int array[])
	{
		try 
		{
			//������ҵ����棬����JCB
			String str;
			File file=new File(Filename);
			FileReader fr=new FileReader(file);
			BufferedReader in=new BufferedReader(fr);
		    //in.readLine();
            int h=0;
            int i=0,j=0;
            while((str=in.readLine())!=null)
		    {	   
		    	while(str.charAt(j)==',')
		        {
		        	str=str.substring(j+1);//��סλ��
		        }
		    	int pos=str.indexOf(",");
		    	while(pos!=-1)
		    	{
		    		array[h++]=Integer.parseInt(str.substring(0,pos));//��סλ��
		    		str=str.substring(pos+1);//
		    		while(str.charAt(j)==',')
		            {
		            	str=str.substring(j+1);//
		            }
		    		pos=str.indexOf(",");
		    	}
		    	array[h++]= Integer.parseInt(str);    	//ת��������
		    }		    			
			fr.close();
			in.close();
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
		}
	}
	public void Submit_ExtraJob()
	{
		int arry[][]=new int[2][200];
		String FileName[]={"6.txt","7.txt","8.txt","9.txt","10.txt"};	
		int i,j=4*JobNum,k,n,i0,j0;
		
		ReadJobs("19219124-jobs-input.txt",arry[0]);//����������Ϣ->arry[0]����
		
			jcb[JobNum]=new JCB();//����JCB	
			ReadJobs(FileName[JobNum-5],arry[1]);//��ָ�->arry[1]����
			jcb[JobNum].ProID=arry[0][j++];
			jcb[JobNum].Priority=arry[0][j++];
			jcb[JobNum].InTimes=arry[0][j++];
			jcb[JobNum].InstrucNum=arry[0][j++];
			jcb[JobNum].HardDiskAddress[0]=arry[0][j++];//��ҵ�������ĵ�ַ
			jcb[JobNum].HardDiskAddress[1]=arry[0][j++];
			for(j0=0;j0<8;j0++)
			{
				//��Ǵ���
				HardDiskAddress[jcb[JobNum].HardDiskAddress[0]][jcb[JobNum].HardDiskAddress[1]+j0]=jcb[JobNum].ProID;//�����ѱ������
			}
			n=0;
			for(k=1;k<=jcb[JobNum].InstrucNum;k++)
			{
				jcb[JobNum].Instruc[k-1]=new Instruction();
				jcb[JobNum].Instruc[k-1].setInstruc_ID(arry[1][n++]);			
				jcb[JobNum].Instruc[k-1].setInstruc_State(arry[1][n++]);
				jcb[JobNum].Instruc[k-1].setL_Address(arry[1][n++]);
				jcb[JobNum].Instruc[k-1].setInRunTimes((arry[1][n++]));
				
			}
			JobNum++;
			
	}
	
	public void Submit_Job()
	{
		int arry[][]=new int[2][200];
		String FileName[]={"1.txt","2.txt","3.txt","4.txt","5.txt"};	
		int i,j=0,k,n,i0,j0;
		for(i0=0;i0<32;i0++)
		{
			HardDiskAddress[i0]=new int[64];
			for(j0=0;j0<64;j0++)
			{
				HardDiskAddress[i0][j0]=0;//���Ӳ���Ƿ�ռ�ã�0δ��ռ�ã�������ҵID���
			}
		}
		
		ReadJobs("19219124-jobs-input.txt",arry[0]);//����������Ϣ->arry[0]����
		for(i=0;i<JobNum;i++)//��ʼ��JCB
		{
			jcb[i]=new JCB();//����JCB
			System.out.println("����JCB"+i);
			ReadJobs(FileName[i],arry[1]);//��ָ�->arry[1]����
		
			jcb[i].ProID=arry[0][j++];
			
			jcb[i].Priority=arry[0][j++];
			jcb[i].InTimes=arry[0][j++];
			jcb[i].InstrucNum=arry[0][j++];
			jcb[i].HardDiskAddress[0]=1;//��ҵ�������ĵ�ַ
			jcb[i].HardDiskAddress[1]=1;
		
            
			for(j0=0;j0<8;j0++)
			{
				//��Ǵ���
				HardDiskAddress[jcb[i].HardDiskAddress[0]][jcb[i].HardDiskAddress[1]+j0]=jcb[i].ProID;//�����ѱ������
			}
			
            
			n=0;
			ReadJobs(FileName[i],arry[1]);//��ָ�->arry[1]����
			for(k=1;k<=jcb[i].InstrucNum;k++)
			{
				jcb[i].Instruc[k-1]=new Instruction();
				jcb[i].Instruc[k-1].setInstruc_ID(arry[1][n++]);			
				jcb[i].Instruc[k-1].setInstruc_State(arry[1][n++]);
				jcb[i].Instruc[k-1].setL_Address(arry[1][n++]);
				jcb[i].Instruc[k-1].setInRunTimes(arry[1][n++]);
				//System.out.println(jcb[i].Instruc[k-1].getInstruc_ID());
				//System.out.println(jcb[i].Instruc[k-1].getInstruc_State());
				
				
			}
		}		
	}
	
	//����󱸶���
	public void InBackupQueue(ClockInterrupt c)
	{
		int i;
		for(i=0;i<CreateJobs.JobNum;i++)
		{
			if(c.time>=jcb[i].InTimes&&jcb[i].InTimes!=0&&flag[i]==0)
			{
				System.out.println("��ҵ��"+jcb[i].getProID()+"����ʱ�䣺"+jcb[i].InTimes+"����󱸶���");
				BackupQueue.add(jcb[i]);
				flag[i]=1;
			}
						
		}
		
	}
	//�޸Ľ�����ҳ��ķ���ʱ��
	public void SwapArea_Visit(int j)
	{
		for(int i=0;i<24;i++)
		{
			if(Page[i]>=0&&Page[i]!=j)
				Visit[i]++;
		}
	}
	//�ж϶Ի����Ƿ�����
	public boolean SwapArea_FULL()
	{
		for(int i=0;i<24;i++)
		{
			if(Page[i]<0)
				return false;	//δ��
		}
		return true;
	}
	//����ҳ�����Ի���
	public void In_SwapArea(int j)
	{
		for(int i=0;i<24;i++)
		{
			if(Page[i]<0)
			{
				Page[i]=j;	
				Visit[i]=0;
				SwapArea_Visit(j);
				break;
			}
		}
	}
	//�鿴�Ի����Ƿ����������Ҫ�����ҳ��
	public boolean SwapArea_HIT(int p)
	{
		for(int i=0;i<24;i++)
		{
			if(Page[i]==p)
			{
				Visit[i]=0;
				SwapArea_Visit(p);
				System.out.println("�Ի���������������Ҫ��ҳ��\n");

				return true;
			}
		}
		return false;
	}
	
	//�Ի����������滻�Ի�����ҳ�棬���ҵ��Ի��������δ���ʵ�ҳ�棺������->��棬�����ڴ滻����ҳ�棺�ڴ�->�Ի���
	public void SwapArea_Update(int p)
	{
		int max=0;
		for(int i=1;i<24;i++)
		{
			if(Visit[i]>Visit[max])
				max=i;
		}
		System.out.println(Page[max]+"ҳ��д����棬"+p+"ҳ��д��Ի���");
		//GUI.ProRec.append(Page[max]+"ҳ��д����棬"+p+"ҳ��д��Ի���"+"\n");
		Page[max]=p;
		Visit[max]=0;
		SwapArea_Visit(p);
	}
	
	/*���󱸶��г�ʼ��
	public static void main(String []args)
	{
		HardDisk h=new HardDisk();
		
	}
	*/
	
}
