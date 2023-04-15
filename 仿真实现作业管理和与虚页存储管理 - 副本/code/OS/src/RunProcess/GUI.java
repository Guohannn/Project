package RunProcess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import CreateJobs.CreateJobs;
import CreateJobs.ExtraJobs;


public class GUI extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel JP[]=new JPanel[6];
	private JPanel jp[]=new JPanel[6];
	private JTable JT[]=new JTable[7];//六个表：表格
	private TableModel TM[]=new TableModel[8];//表模型：存放数据
	private JLabel JL[]=new JLabel[40];//40个标签
	private JLabel Memory_JL[]=new JLabel[32];//32个标签
	private static JButton JB_START;
	private static JButton JB_Logout;
	private static JButton JB_BuildJobs;
	private static JButton JB_Add;
	private JButton JB[]=new JButton[5];
	public static JTextArea ProRec;
	private JScrollPane JS;
	ClockInterrupt c=new ClockInterrupt();
	public static void main(String[] args) {	
		try {		
			
			ClockInterrupt clock=new ClockInterrupt();
			CheckWakeup check=new CheckWakeup(clock.harddisk,clock.memory,clock.cpu,clock.scheduling,clock.mmu,clock);
			Keyboard keyboard=new Keyboard(clock.harddisk,clock.memory,clock.cpu,clock.scheduling,clock.mmu,clock);
			Screen screen=new Screen(clock.harddisk,clock.memory,clock.cpu,clock.scheduling,clock.mmu,clock);
			Disk_Read disk_read=new Disk_Read(clock.harddisk,clock.memory,clock.cpu,clock.scheduling,clock.mmu,clock);
			Disk_Write disk_write=new Disk_Write(clock.harddisk,clock.memory,clock.cpu,clock.scheduling,clock.mmu,clock);
			JobsScheduling HLS=new JobsScheduling(clock.harddisk,clock.memory,clock.cpu,clock.scheduling,clock.mmu,clock);
			GUI SUI=new GUI(clock);
			Thread UI=new Thread(SUI);
			//JB_BuildJobs为生成作业
			JB_BuildJobs.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try
					{
						CreateJobs.main(args);
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
					}	
				}
			});
			//JB_START为START按钮
			JB_START.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try
					{
						clock.harddisk.Submit_Job();//提交作业
						clock.start();
						check.start();
						keyboard.start();
						screen.start();
						disk_read.start();
						disk_write.start();
						HLS.start();
						UI.start();
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
					}	
				}
			});
			//JB_Add为随时添加作业
			JB_Add.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try
					{
						ExtraJobs.main(args);//创建额外的作业
						clock.harddisk.Submit_ExtraJob();//提交作业
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
					}	
				}
			});
			//JB_Logout为退出
			JB_Logout.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try
					{
						System.exit(0);
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
					}	
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//构造函数：可视化界面
	public GUI(ClockInterrupt clock) 
	{
		//JFrame->panel->JP[i]
		super("可视化仿真实现作业管理与虚页内存管理");//界面标题
		this.c=clock;
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭界面
		setBounds(300, 0, 1200, 700);
		panel= new JPanel();
		//JP.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(panel);
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BorderLayout(0, 0));

		//面板1WEST：JP[0]
		//进程调度信息面板
		JP[0] = new JPanel();
		panel.add(JP[0], BorderLayout.WEST);
		JP[0].setLayout(new BorderLayout(0, 0));
		JP[0].setBorder(new LineBorder(Color.BLACK, 2));
		//进程调度信息标题
		JL[0] = new JLabel("进程调度信息");
	    JP[0].add(JL[0], BorderLayout.NORTH);
		//进程调度信息内容
		ProRec= new JTextArea();
		ProRec.setColumns(40);
		JS = new JScrollPane(ProRec);
		JP[0].add(JS, BorderLayout.CENTER);
		
		//面板2SOUTH：JP[1]
		//时间、资源、按钮等
		JP[1]=new JPanel();
		panel.add(JP[1],BorderLayout.SOUTH);
		JP[1].setBorder(new LineBorder(Color.BLACK, 2));
		JP[1].setLayout(new BorderLayout(0, 0));
		//标题
		JL[26]=new JLabel("INFO");
		JL[26].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JP[1].add(JL[26],BorderLayout.NORTH);
		//内容
		jp[0]=new JPanel();
		jp[0].setLayout(new BorderLayout(0,0));
		JP[1].add(jp[0],BorderLayout.CENTER);
		
		jp[4]=new JPanel();
		jp[4].setLayout(new GridLayout(2,5));
		jp[0].add(jp[4],BorderLayout.NORTH);
		jp[4].setBackground(Color.WHITE);
		jp[5]=new JPanel();
		jp[5].setLayout(new FlowLayout());
		jp[0].add(jp[5],BorderLayout.CENTER);
		
	    JL[1]=new JLabel("Time",JLabel.CENTER);
		JL[6]=new JLabel("",JLabel.CENTER);//jp[0]
		
	    JL[4]=new JLabel("ReachedProcess",JLabel.CENTER);
		JL[9]=new JLabel("",JLabel.CENTER);//jp[0]
		JL[5]=new JLabel("FinishedProcess",JLabel.CENTER);
		JL[10]=new JLabel("",JLabel.CENTER);

		jp[4].add(JL[1]);
		jp[4].add(JL[4]);
		jp[4].add(JL[5]);
		jp[4].add(JL[6]);
		jp[4].add(JL[9]);
		jp[4].add(JL[10]);
	    JB_BuildJobs=new JButton("创建作业");
	    //JB_BuildJobs.setBackground(Color.RED);
	    JB_START=new JButton("开始调度");
	    //JB_START.setBackground(Color.GREEN); 
	    JB_Add=new JButton("额外添加作业");
	   // JB_Add.setBackground(Color.ORANGE);
	    JB_Logout=new JButton("退出程序");
	    //JB_Logout.setBackground(Color.YELLOW);
		JB[0]=new JButton("作业文件");
		JB[1]=new JButton("PageResults.txt");
		JB[2]=new JButton("ProcessResults-277.txt");
		JB[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try
				{
					Process process = Runtime.getRuntime().exec("cmd.exe /c notepad.exe "+System.getProperty("user.dir")+"//19219124-jobs-input.txt");
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}	
			}
		});
		JB[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try
				{
					Process process = Runtime.getRuntime().exec("cmd.exe /c notepad.exe "+System.getProperty("user.dir")+"//PageResults.txt");
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}	
			}
		});
		JB[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try
				{
					Process process = Runtime.getRuntime().exec("cmd.exe /c notepad.exe "+System.getProperty("user.dir")+"//ProcessResults-277.txt");
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}	
			}
		});
		jp[5].add(JB_BuildJobs);
		jp[5].add(JB_START);
		jp[5].add(JB_Add);	
		jp[5].add(JB[0]);
		jp[5].add(JB[1]);
		jp[5].add(JB[2]);
		jp[5].add(JB_Logout);
		//面板3EAST：JP[2]
		//运行态进程
		JP[2]=new JPanel();
		panel.add(JP[2],BorderLayout.EAST);
		JP[2].setBorder(new LineBorder(Color.BLACK, 2));
		JP[2].setLayout(new BorderLayout(0, 0));
		//标题
		JL[25]=new JLabel("运行态进程");
		JL[25].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JP[2].add(JL[25],BorderLayout.NORTH);
		//内容
		jp[1]=new JPanel();
		jp[1].setLayout(new GridLayout(7,2));
		JP[2].add(jp[1],BorderLayout.CENTER);
		jp[1].setBackground(Color.WHITE);
		JL[11]=new JLabel("CPU_ProID：",JLabel.RIGHT);
		JL[12]=new JLabel("",JLabel.CENTER);
		JL[13]=new JLabel("CPU_Priority：",JLabel.RIGHT);
		JL[14]=new JLabel("",JLabel.CENTER);
		JL[15]=new JLabel("CPU_InTimes：",JLabel.RIGHT);
		JL[16]=new JLabel("",JLabel.CENTER);
		JL[17]=new JLabel("CPU_InstructNum：",JLabel.RIGHT);
		JL[18]=new JLabel("",JLabel.CENTER);
		JL[19]=new JLabel("CPU_IR：",JLabel.RIGHT);
		JL[20]=new JLabel("",JLabel.CENTER);
		JL[21]=new JLabel("CPU_Runtime：",JLabel.RIGHT);
		JL[22]=new JLabel("",JLabel.CENTER);
		JL[23]=new JLabel("CPU_TimeSlice：",JLabel.RIGHT);
		JL[24]=new JLabel("",JLabel.CENTER);
		for(int i=11;i<25;i++)
		{
			jp[1].add(JL[i]);
			//jp[0].add(JTF[i+1]);
		}
		//面板4NORTH，JP[3]
		//队列
		JP[3]=new JPanel();
		panel.add(JP[3],BorderLayout.NORTH);
		JP[3].setBorder(new LineBorder(Color.BLACK, 2));
		JP[3].setLayout(new BorderLayout(0,0));
		//标题
		JL[27]=new JLabel("队列信息");
		JL[27].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JP[3].add(JL[27],BorderLayout.NORTH);
		//内容
		jp[2]=new JPanel();
		//jp[2].setLayout(new FlowLayout());
		JP[3].add(jp[2],BorderLayout.CENTER);
		jp[2].setBackground(Color.WHITE);	
		GridBagLayout gbl_panel = new GridBagLayout();	
		gbl_panel.columnWidths = new int[]{0,0,0,0,0,0};//columnWidths：设置列数
		gbl_panel.rowHeights = new int[]{0,0};//rowHeights：设置行数；
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0,1.0};//columnWeights：设置各列所占宽度比例；
		gbl_panel.rowWeights = new double[]{0.2,0.8};//rowWeights：设置各行所占的高度比例
		jp[2].setLayout(gbl_panel);
		//第一行
		String Name[]={"后备作业队列","就绪队列","阻塞队列1","阻塞队列2","阻塞队列3","阻塞队列4","阻塞队列5"};
		GridBagConstraints queueinfo = new GridBagConstraints();
		queueinfo.insets = new Insets(0, 0, 5, 5);
		for(int i=0;i<7;i++)
		{
			JL[28+i]=new JLabel(Name[i]);
			queueinfo.gridx =i;
			queueinfo.gridy = 0;
			jp[2].add(JL[28+i],queueinfo);
		}
		//第二行
		TM[0] = GetJTableModel(clock.harddisk.BackupQueue);
		TM[1] = GetPTableModel(clock.memory.ReadyQueue);
		TM[2] = GetPTableModel(clock.memory.BlockQueue_Keyboard);
		TM[3] = GetPTableModel(clock.memory.BlockQueue_Screen);
		TM[4] = GetPTableModel(clock.memory.BlockQueue_DiskRead);
		TM[5] = GetPTableModel(clock.memory.BlockQueue_DiskWrite);
		TM[6] = GetPTableModel(clock.memory.BlockQueue_Print);
		
		for(int i=0;i<7;i++)
		{
			JT[i] = new JTable(TM[i]);
			JT[i].setColumnSelectionAllowed(true);
			JT[i].setBorder(new LineBorder(new Color(0, 0, 0)));
			queueinfo.gridy=1;
			queueinfo.gridx=i;
			jp[2].add(JT[i],queueinfo);
		}


	    
	    
		//面板4CENTER，JP[4]
		//队列
		JP[4]=new JPanel();
		panel.add(JP[4],BorderLayout.CENTER);
		JP[4].setBorder(new LineBorder(Color.BLACK, 2));
		JP[4].setLayout(new BorderLayout(0,0));
		//标题
		JL[34]=new JLabel("内存区");
		JL[34].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JP[4].add(JL[34],BorderLayout.NORTH);
		//内容
		jp[3]=new JPanel();
		jp[3].setLayout(new GridLayout(4,8));
		JP[4].add(jp[3],BorderLayout.CENTER);
		jp[3].setBackground(Color.WHITE);
		for(int i=0;i<32;i++)
		{
			Memory_JL[i]=new JLabel();
			jp[3].add(Memory_JL[i]);
		}
		
		



	}
	//界面更新
	public void run()
	{
		while(!JobsScheduling.HLSFinish)
		{
			//JT[0].validate();
			JP[0].validate();
			JP[1].validate();
			JP[2].validate();
			//JP[3].validate();
			//JP[4].validate();
			//运行态进程区和info
			JL[6].setText(String.valueOf(ClockInterrupt.time));
			JL[9].setText(String.valueOf(c.scheduling.ReachedProcess));
			JL[10].setText(String.valueOf(c.scheduling.FinishedProcess));
			
			JL[12].setText(String.valueOf(c.cpu.pcb_cpu.ProID));
			JL[14].setText(String.valueOf(c.cpu.pcb_cpu.Priority));
			JL[16].setText(String.valueOf(c.cpu.pcb_cpu.InTimes));
			JL[18].setText(String.valueOf(c.cpu.pcb_cpu.InstrucNum));
			JL[20].setText(String.valueOf(c.cpu.pcb_cpu.IR));
			JL[22].setText(String.valueOf(c.cpu.pcb_cpu.RunTimes));
			JL[24].setText(String.valueOf(c.scheduling.TimeSlice));
			//队列信息
			JT[0].updateUI();
			JT[1].updateUI();
			JT[2].updateUI();
			JT[3].updateUI();
			JT[4].updateUI();
			JT[5].updateUI();
			JT[6].updateUI();
			//内存区和缓冲区
			for(int i=0;i<32;i++)
			{
				Memory_JL[i].setText(String.valueOf(c.memory.Memory_State[i]));
				Memory_JL[i].setHorizontalAlignment(SwingConstants.CENTER);
			}
		
			
			try{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}
	}
	public String[][] JCBData(Queue<JCB> q)
	{
		int n=q.size();
		String[][] t=new String[n+1][2];
		t[0][0]="ProID";
		t[0][1]="InTimes";
			for(int i=1;i<n+1;i++)
			{
				JCB p=new JCB();
				p=q.peek();
				t[i][0]=String.valueOf(p.ProID);
				t[i][1]=String.valueOf(p.InTimes);
				q.remove();
				q.offer(p);
			}
			
		return t;
	}
	public String[][] PCBData(Queue<PCB> q)
	{
		int n=q.size();
		String[][] t=new String[n+1][2];
		t[0][0]="ProID";
		t[0][1]="InTimes";
			for(int i=1;i<n+1;i++)
			{
				PCB p=new PCB();
				p=q.peek();
				t[i][0]=String.valueOf(p.ProID);
				t[i][1]=String.valueOf(p.InTimes);
				q.remove();
				q.offer(p);
			}
			
		return t;
	}
	public AbstractTableModel GetJTableModel(Queue<JCB> p) {
		return new AbstractTableModel() 
		{
			public int getColumnCount() {
				return 2;//2列
			}
			public int getRowCount() {
				return 8;//8行
			}
			public Object getValueAt(int row, int col) {
				int i;
				String[][] flag=new String [p.size()+1][2];
				flag=JCBData(p);
				if(row<p.size()+1)
					return flag[row][col];
				else
					return "NULL";
			}
		};
	}
	public AbstractTableModel GetPTableModel(Queue<PCB> p) {
		return new AbstractTableModel() 
		{
			public int getColumnCount() {
				return 2;//2列
			}
			public int getRowCount() {
				return 8;//8行
			}
			public Object getValueAt(int row, int col) {
				int i;
				String[][] flag=new String [p.size()+1][2];
				flag=PCBData(p);
				if(row<p.size()+1)
					return flag[row][col];
				else
					return "NULL";
			}
		};
	}
}
