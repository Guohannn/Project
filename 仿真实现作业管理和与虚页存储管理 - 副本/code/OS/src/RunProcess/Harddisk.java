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
	//交换区为最后一个磁道 其他为文件区
	int Harddisk_size=10*32*64*1024;//磁盘大小20MB 20480块 10个柱面   1个柱面32个磁道    1个磁道64个扇区   一个扇区1024b
	static int HardDiskAddress[][]=new int[32][64];//主要在0号柱面32*64=2^11块
	int[] Page=new int[24];//交换区大小是24块 3*8
	static int JobNum=5;//作业数量，暂定为5，之后可随时添加作业
	static Queue<JCB> BackupQueue = new LinkedList<JCB>();//后备队列
	
	int[] Visit=new int[24];//交换区页面的未访问时间
	int flag[]=new int[10];//标志作业是否被创建  未被创建0 创建1 目前设最多10个作业
	JCB jcb[]=new JCB[10];//作业数量
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
	//作业从文件读入数组
	public void ReadJobs(String Filename,int array[])
	{
		try 
		{
			//读入作业到外存，创建JCB
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
		        	str=str.substring(j+1);//记住位置
		        }
		    	int pos=str.indexOf(",");
		    	while(pos!=-1)
		    	{
		    		array[h++]=Integer.parseInt(str.substring(0,pos));//记住位置
		    		str=str.substring(pos+1);//
		    		while(str.charAt(j)==',')
		            {
		            	str=str.substring(j+1);//
		            }
		    		pos=str.indexOf(",");
		    	}
		    	array[h++]= Integer.parseInt(str);    	//转换成整数
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
		
		ReadJobs("19219124-jobs-input.txt",arry[0]);//读到进程信息->arry[0]数组
		
			jcb[JobNum]=new JCB();//创建JCB	
			ReadJobs(FileName[JobNum-5],arry[1]);//读指令集->arry[1]数组
			jcb[JobNum].ProID=arry[0][j++];
			jcb[JobNum].Priority=arry[0][j++];
			jcb[JobNum].InTimes=arry[0][j++];
			jcb[JobNum].InstrucNum=arry[0][j++];
			jcb[JobNum].HardDiskAddress[0]=arry[0][j++];//作业存入外存的地址
			jcb[JobNum].HardDiskAddress[1]=arry[0][j++];
			for(j0=0;j0<8;j0++)
			{
				//标记磁盘
				HardDiskAddress[jcb[JobNum].HardDiskAddress[0]][jcb[JobNum].HardDiskAddress[1]+j0]=jcb[JobNum].ProID;//磁盘已被分配过
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
				HardDiskAddress[i0][j0]=0;//标记硬盘是否被占用，0未被占用，其余作业ID标记
			}
		}
		
		ReadJobs("19219124-jobs-input.txt",arry[0]);//读到进程信息->arry[0]数组
		for(i=0;i<JobNum;i++)//初始化JCB
		{
			jcb[i]=new JCB();//创建JCB
			System.out.println("创建JCB"+i);
			ReadJobs(FileName[i],arry[1]);//读指令集->arry[1]数组
		
			jcb[i].ProID=arry[0][j++];
			
			jcb[i].Priority=arry[0][j++];
			jcb[i].InTimes=arry[0][j++];
			jcb[i].InstrucNum=arry[0][j++];
			jcb[i].HardDiskAddress[0]=1;//作业存入外存的地址
			jcb[i].HardDiskAddress[1]=1;
		
            
			for(j0=0;j0<8;j0++)
			{
				//标记磁盘
				HardDiskAddress[jcb[i].HardDiskAddress[0]][jcb[i].HardDiskAddress[1]+j0]=jcb[i].ProID;//磁盘已被分配过
			}
			
            
			n=0;
			ReadJobs(FileName[i],arry[1]);//读指令集->arry[1]数组
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
	
	//加入后备队列
	public void InBackupQueue(ClockInterrupt c)
	{
		int i;
		for(i=0;i<CreateJobs.JobNum;i++)
		{
			if(c.time>=jcb[i].InTimes&&jcb[i].InTimes!=0&&flag[i]==0)
			{
				System.out.println("作业："+jcb[i].getProID()+"请求时间："+jcb[i].InTimes+"进入后备队列");
				BackupQueue.add(jcb[i]);
				flag[i]=1;
			}
						
		}
		
	}
	//修改交换区页面的访问时间
	public void SwapArea_Visit(int j)
	{
		for(int i=0;i<24;i++)
		{
			if(Page[i]>=0&&Page[i]!=j)
				Visit[i]++;
		}
	}
	//判断对换区是否已满
	public boolean SwapArea_FULL()
	{
		for(int i=0;i<24;i++)
		{
			if(Page[i]<0)
				return false;	//未满
		}
		return true;
	}
	//主存页面进入对换区
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
	//查看对换区是否存在我们需要调入的页面
	public boolean SwapArea_HIT(int p)
	{
		for(int i=0;i<24;i++)
		{
			if(Page[i]==p)
			{
				Visit[i]=0;
				SwapArea_Visit(p);
				System.out.println("对换区存在我们所需要的页面\n");

				return true;
			}
		}
		return false;
	}
	
	//对换区已满，替换对换区的页面，即找到对换区中最久未访问的页面：交换区->外存，将从内存换出的页面：内存->对换区
	public void SwapArea_Update(int p)
	{
		int max=0;
		for(int i=1;i<24;i++)
		{
			if(Visit[i]>Visit[max])
				max=i;
		}
		System.out.println(Page[max]+"页面写入外存，"+p+"页面写入对换区");
		//GUI.ProRec.append(Page[max]+"页面写入外存，"+p+"页面写入对换区"+"\n");
		Page[max]=p;
		Visit[max]=0;
		SwapArea_Visit(p);
	}
	
	/*检测后备队列初始化
	public static void main(String []args)
	{
		HardDisk h=new HardDisk();
		
	}
	*/
	
}
