package CreateJobs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import RunProcess.JCB;



public class CreateJobs {
	
	static int HardDiskA[][]=new int[32][64];//假设在一个柱面
	static int C,S;
	public static int JobNum=5;//假设有五个作业
	static String str=new String();
	static String wri=new String();
	public static JCB P[]=new JCB[10];
	Random rand = new Random();
    CreateJobs()
    {
    }
    public void CJobIns()
    {
		try{
			int i,j;		
			String FileName[]={"1.txt","2.txt","3.txt","4.txt","5.txt"};
			File Insf[]=new File[5];
			for(i=0;i<5;i++)
			{
				Insf[i]=new File(FileName[i]);
			}
			File file=new File("19219124-jobs-input.txt");
			
			FileWriter fin=new FileWriter(file);
			BufferedWriter bin=new BufferedWriter(fin);	
			//str="ProID,Priority,Intimes,InstrucNum"+"\r\n";
			for(i=0;i<32;i++)//初始化硬盘
			{
				HardDiskA[i]=new int[64];
				for(j=0;j<64;j++)
				{
					HardDiskA[i][j]=0;
				}
			}
			for(i=1;i<=5;i++)
			{
				P[i-1]=new JCB();
				P[i-1].setProID(i);
				P[i-1].setPriority((rand.nextInt(5)+1));
				P[i-1].setInTimes((7*P[i-1].getProID()));
				P[i-1].setInstrucNum((rand.nextInt(10)+20));
				P[i-1].setHardDiskAddress0(rand.nextInt(31));//磁道号
				C=P[i-1].getHardDiskAddress0();
				P[i-1].setHardDiskAddress1(rand.nextInt(56));//扇区号
				S=P[i-1].getHardDiskAddress1();	
						
				for(j=0;j<8;j++)
				{
					HardDiskA[C][S+j]=1;//磁盘已被分配过
				}
				//暂时生成一个外存地址，连续8个扇区
				while(HaveAllocation()==false){
					for(j=0;j<8;j++)
					{
						HardDiskA[C][S+j]=0;//磁盘已被分配过
					}
					P[i-1].setHardDiskAddress0(rand.nextInt(31));//磁道号
					C=P[i-1].getHardDiskAddress0();
					P[i-1].setHardDiskAddress1(rand.nextInt(56));//扇区号
					S=P[i-1].getHardDiskAddress1();
					//System.out.println(C+" "+S);
				};//被占用
				
				for(j=0;j<8;j++)
				{
					HardDiskA[C][S+j]=1;//磁盘已被分配过
				}
				
				System.out.println();
				
				str+=(P[i-1].getProID()+","+P[i-1].getPriority()+","+P[i-1].getInTimes()
						+","+P[i-1].getInstrucNum()+"\r\n");		

				int num=0;
				int n=P[i-1].getInstrucNum()*7/10;
				//j=1
				P[i-1].Instruc[0]=new Instruction();
				P[i-1].Instruc[0].setInstruc_ID(1);
				P[i-1].Instruc[0].setInstruc_State(1);	//随机生成指令类型 
				P[i-1].Instruc[0].setL_Address(12);
				P[i-1].Instruc[0].setInRunTimes(2);
				
				int []arr=new int[30];
				arr[0]=12;
				Random r=new Random();
				int q=r.nextInt(2);//[0,1]
				for(int k=1;k<30;k++)
				{
					if(q==0)
					{
						arr[k-1]=arr[k];
					}
					else 
					{
						arr[k-1]=arr[k]+2;
					}
				}
				
				for(j=2;j<=P[i-1].getInstrucNum();j++)
				{
					Random m=new Random();
					int k=m.nextInt(2);//[0,1]
					P[i-1].Instruc[j-1]=new Instruction();
					P[i-1].Instruc[j-1].setInstruc_ID(j);
					P[i-1].Instruc[j-1].setInstruc_State(rand.nextInt(4));	//随机生成指令类型
					
					if(P[i-1].Instruc[j-1].Instruc_State==0)
					{
						P[i-1].Instruc[j-1].setInRunTimes(1);
						
						int b=arr[j];
						if(k==0)
							P[i-1].Instruc[j-1].setL_Address(b);
						else
							P[i-1].Instruc[j-1].setL_Address(b+2);
					}
					if(P[i-1].Instruc[j-1].Instruc_State==2)
					{
						P[i-1].Instruc[j-1].setInRunTimes(2);
						
						int b=arr[j];
						if(k==0)
							P[i-1].Instruc[j-1].setL_Address(b);
						else
							P[i-1].Instruc[j-1].setL_Address(b+2);
					}
					if(P[i-1].Instruc[j-1].Instruc_State==3)
					{
						P[i-1].Instruc[j-1].setInRunTimes(3);
						
						int b=arr[j];
						if(k==0)
							P[i-1].Instruc[j-1].setL_Address(b);
						else
							P[i-1].Instruc[j-1].setL_Address(b+2);
						
					}
					if(P[i-1].Instruc[j-1].Instruc_State==6)
					{
						P[i-1].Instruc[j-1].setInRunTimes(4);
						
						int b=arr[j];
						if(k==0)
							P[i-1].Instruc[j-1].setL_Address(b);
						else
							P[i-1].Instruc[j-1].setL_Address(b+2);
					}
					if(P[i-1].Instruc[j-1].Instruc_State==1)
					{
						P[i-1].Instruc[j-1].setInRunTimes(2);
						P[i-1].Instruc[j-1].setL_Address(rand.nextInt(10)+10);
						
					}
					if(P[i-1].Instruc[j-1].Instruc_State==4)
					{
						P[i-1].Instruc[j-1].setInRunTimes(3);
						P[i-1].Instruc[j-1].setL_Address(rand.nextInt(10)+10);
						
					}
					if(P[i-1].Instruc[j-1].Instruc_State==5)
					{
						P[i-1].Instruc[j-1].setInRunTimes(4);
						P[i-1].Instruc[j-1].setL_Address(rand.nextInt(10)+10);
						
					}
				}
			}
			
			
			FileWriter f[]=new FileWriter[5];
			BufferedWriter b[]=new BufferedWriter[5];
			for(i=1;i<=5;i++)
			{
				f[i-1]=new FileWriter(Insf[i-1]);
				b[i-1]=new BufferedWriter(f[i-1]);
				
				for(j=1;j<=P[i-1].getInstrucNum();j++)
				{
					wri=(P[i-1].Instruc[j-1].getInstruc_ID()+","
				        +P[i-1].Instruc[j-1].getInstruc_State()+","
						+P[i-1].Instruc[j-1].getL_Address()+","
						+P[i-1].Instruc[j-1].getInRunTimes());			    
					
					wri+=("\r\n");
					b[i-1].write(wri);
				}

				b[i-1].close();
				f[i-1].close();
			}
			bin.write(str);	
			bin.close();
			fin.close();
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
		}
    }

	public boolean HaveAllocation()
	{
		for(int i=0;i<8;i++)
		{
			if(HardDiskA[C][S+i]==1||S>56)
			{
				return false;//分配不成功
			}
		}
		return true;
	}	
	public static void main (String args[])
	{
		CreateJobs CJ=new CreateJobs();
		CJ.CJobIns();
	}
}
