package RunProcess;

import CreateJobs.Instruction;

public class JCB {//作业程序块

	int ProID;//作业序号
	int Priority;//作业优先级
	int InTimes;//作业请求时间
	int InstrucNum;//作业包含的程序指令数目
	int HardDiskAddress[]=new int[2];//磁道扇区
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

	//磁道号
	public int getHardDiskAddress0() {
		return HardDiskAddress[0];
	}
	public void setHardDiskAddress0(int hardDiskAddress) {
		HardDiskAddress[0] = hardDiskAddress;
	}
	//扇区号
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
