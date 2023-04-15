package CreateJobs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import RunProcess.ClockInterrupt;
import RunProcess.JCB;


public class ExtraJobs {

	int C,S;
	ExtraJobs()
	{
	}
	public void CExtraJonIns()
	{
		try{
			int i,j;
			Random rand = new Random();
			CreateJobs.P[CreateJobs.JobNum]=new JCB();
			File Insf[]=new File[5];

			File file=new File("19219124-jobs-input.txt");
			FileWriter fin=new FileWriter(file);
			BufferedWriter bin=new BufferedWriter(fin);	
			CreateJobs.P[CreateJobs.JobNum]=new JCB();
			CreateJobs.P[CreateJobs.JobNum].setProID(CreateJobs.JobNum+1);
			CreateJobs.P[CreateJobs.JobNum].setPriority((rand.nextInt(5)+1));
			CreateJobs.P[CreateJobs.JobNum].setInTimes(ClockInterrupt.time);
			CreateJobs.P[CreateJobs.JobNum].setInstrucNum((rand.nextInt(10)+20));
			CreateJobs.P[CreateJobs.JobNum].setHardDiskAddress0(rand.nextInt(31));//磁道号
			C=CreateJobs.P[CreateJobs.JobNum].getHardDiskAddress0();
			CreateJobs.P[CreateJobs.JobNum].setHardDiskAddress1(rand.nextInt(56));//扇区号
			S=CreateJobs.P[CreateJobs.JobNum].getHardDiskAddress1();	
			for(j=0;j<8;j++)
			{
				CreateJobs.HardDiskA[C][S+j]=1;//磁盘已被分配过
			}
			//暂时生成一个外存地址，连续8个扇区
			while(HaveAllocation()==false){
				for(j=0;j<8;j++)
				{
					CreateJobs.HardDiskA[C][S+j]=0;//磁盘已被分配过
				}
				CreateJobs.P[CreateJobs.JobNum].setHardDiskAddress0(rand.nextInt(31));//磁道号
				CreateJobs.C=CreateJobs.P[CreateJobs.JobNum].getHardDiskAddress0();
				CreateJobs.P[CreateJobs.JobNum].setHardDiskAddress1(rand.nextInt(56));//扇区号
				CreateJobs.S=CreateJobs.P[CreateJobs.JobNum].getHardDiskAddress1();
				//System.out.println(C+" "+S);
			};//被占用

			for(j=0;j<8;j++)
			{
				CreateJobs.HardDiskA[C][S+j]=1;//磁盘已被分配过
			}
				
			System.out.println();
			CreateJobs.str+=(CreateJobs.P[CreateJobs.JobNum].getProID()
					+","+CreateJobs.P[CreateJobs.JobNum].getPriority()
					+","+CreateJobs.P[CreateJobs.JobNum].getInTimes()
					+","+CreateJobs.P[CreateJobs.JobNum].getInstrucNum());	

			//j=1
			CreateJobs.P[CreateJobs.JobNum].Instruc[0]=new Instruction();
			CreateJobs.P[CreateJobs.JobNum].Instruc[0].setInstruc_ID(1);
			CreateJobs.P[CreateJobs.JobNum].Instruc[0].setInstruc_State(1);	//随机生成指令类型 
			CreateJobs.P[CreateJobs.JobNum].Instruc[0].setL_Address(12);
			CreateJobs.P[CreateJobs.JobNum].Instruc[0].setInRunTimes(2);
			
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
			
			for(j=2;j<=CreateJobs.P[CreateJobs.JobNum].getInstrucNum();j++)
			{
				Random m=new Random();
				int k=m.nextInt(2);//[0,1]
				CreateJobs.P[CreateJobs.JobNum].Instruc[j-1]=new Instruction();
				CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setInstruc_ID(j);
				CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setInstruc_State(rand.nextInt(4));	//随机生成指令类型
				
				if(CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].Instruc_State==0)
				{
					CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setInRunTimes(1);
					
					int b=arr[j];
					if(k==0)
						CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(b);
					else
						CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(b+2);
				}
				if(CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].Instruc_State==2)
				{
					CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setInRunTimes(2);
					
					int b=arr[j];
					if(k==0)
						CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(b);
					else
						CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(b+2);
				}
				if(CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].Instruc_State==3)
				{
					CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setInRunTimes(3);
					
					int b=arr[j];
					if(k==0)
						CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(b);
					else
						CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(b+2);
					
				}
				if(CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].Instruc_State==6)
				{
					CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setInRunTimes(4);
					
					int b=arr[j];
					if(k==0)
						CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(b);
					else
						CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(b+2);
				}
				if(CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].Instruc_State==1)
				{
					CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setInRunTimes(2);
					CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(rand.nextInt(10)+10);
					
				}
				if(CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].Instruc_State==4)
				{
					CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setInRunTimes(3);
					CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(rand.nextInt(10)+10);
					
				}
				if(CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].Instruc_State==5)
				{
					CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setInRunTimes(4);
					CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].setL_Address(rand.nextInt(10)+10);
					
				}
			}
			String n[]={"6.txt","7.txt","8.txt","9.txt","10.txt"};
			for(i=0;i<5;i++)
			{
				Insf[i]=new File(n[i]);
			}
			FileWriter f=new FileWriter(Insf[CreateJobs.JobNum-5]);
			BufferedWriter b=new BufferedWriter(f);

			b.write(CreateJobs.wri);
			
			for(j=1;j<=CreateJobs.P[CreateJobs.JobNum].getInstrucNum();j++)
			{
				
				CreateJobs.wri=(CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].getInstruc_ID()+","
						+CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].getInstruc_State()+","
						+CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].getL_Address()+","
						+CreateJobs.P[CreateJobs.JobNum].Instruc[j-1].getInRunTimes());			    
				
				CreateJobs.wri+=("\r\n");
				b.write(CreateJobs.wri);
			}

			b.close();
			f.close();
			
			bin.write(CreateJobs.str);	
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
			if(CreateJobs.HardDiskA[C][S+i]==1||S>56)
			{
				return false;//分配不成功
			}
		}
		return true;
	}	
	
	
	public static void main(String []args)
	{
		ExtraJobs e=new ExtraJobs();
		e.CExtraJonIns();
		CreateJobs.JobNum++;
	}
}
