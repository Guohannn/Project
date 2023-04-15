package RunProcess;

public class CPU {

	int IR;//正在执行的指令编号
	int PC;//下一条指令的编号
	static int flag=0;//flag=0时CPU空闲 flag=1时CPU忙
	PCB pcb_cpu =new PCB();//当前正在使用cpu的进程
	public CPU()
	{
		
	}
	public void protect(PCB pcb_cpu)//现场保护   把当前进程运行的信息保存在pcb里
	{
		IR=pcb_cpu.IR;
		PC=pcb_cpu.PC;
		flag=0;//cpu变为空闲
	}
	public void recover(PCB pcb_cpu)//现场恢复
	{
		pcb_cpu.IR=IR;
		pcb_cpu.PC=PC;
		flag=1;//cpu变为忙碌
	}
	
}
