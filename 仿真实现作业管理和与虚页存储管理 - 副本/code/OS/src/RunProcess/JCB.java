package RunProcess;

import CreateJobs.Instruction;

public class JCB {//��ҵ�����

	int ProID;//��ҵ���
	int Priority;//��ҵ���ȼ�
	int InTimes;//��ҵ����ʱ��
	int InstrucNum;//��ҵ�����ĳ���ָ����Ŀ
	int HardDiskAddress[]=new int[2];//�ŵ�����
	public Instruction Instruc[]=new Instruction[30];
	
	public int getProID() {
		return ProID;
	}
	public void setProID(int proID) {
		ProID = proID;
	}
	public int getPriority() {
		return Priority;
	}
	public void setPriority(int priority) {
		Priority = priority;
	}
	public int getInTimes() {
		return InTimes;
	}
	public void setInTimes(int inTimes) {
		InTimes = inTimes;
	}
	public int getInstrucNum() {
		return InstrucNum;
	}
	public void setInstrucNum(int instrucNum) {
		InstrucNum = instrucNum;
	}

	//�ŵ���
	public int getHardDiskAddress0() {
		return HardDiskAddress[0];
	}
	public void setHardDiskAddress0(int hardDiskAddress) {
		HardDiskAddress[0] = hardDiskAddress;
	}
	//������
	public int getHardDiskAddress1() {
		return HardDiskAddress[1];
	}
	public void setHardDiskAddress1(int hardDiskAddress) {
		HardDiskAddress[1] = hardDiskAddress;
	}
	public Instruction[] getInstruc() {
		return Instruc;
	}
	public void setInstruc(Instruction[] instruc) {
		Instruc = instruc;
	}
	
}
