package RunProcess;

public class CPU {

	int IR;//����ִ�е�ָ����
	int PC;//��һ��ָ��ı��
	static int flag=0;//flag=0ʱCPU���� flag=1ʱCPUæ
	PCB pcb_cpu =new PCB();//��ǰ����ʹ��cpu�Ľ���
	public CPU()
	{
		
	}
	public void protect(PCB pcb_cpu)//�ֳ�����   �ѵ�ǰ�������е���Ϣ������pcb��
	{
		IR=pcb_cpu.IR;
		PC=pcb_cpu.PC;
		flag=0;//cpu��Ϊ����
	}
	public void recover(PCB pcb_cpu)//�ֳ��ָ�
	{
		pcb_cpu.IR=IR;
		pcb_cpu.PC=PC;
		flag=1;//cpu��Ϊæµ
	}
	
}
