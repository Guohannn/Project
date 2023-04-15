package CreateJobs;

public class Instruction {
	
	public int Instruc_ID;//指令段编号
	public int Instruc_State;//指令类型
	public int L_Address;//指令访问的逻辑地址
	public int InRunTimes;//指令运行时间
	public int getInstruc_ID() {
		return Instruc_ID;
	}
	public void setInstruc_ID(int instruc_ID) {
		Instruc_ID = instruc_ID;
	}
	public int getInstruc_State() {
		return Instruc_State;
	}
	public void setInstruc_State(int instruc_State) {
		Instruc_State = instruc_State;
	}
	public int getL_Address() {
		return L_Address;
	}
	public void setL_Address(int l_Address) {
		L_Address = l_Address;
	}
	
	public int getInRunTimes() {
		return InRunTimes;
	}
	public void setInRunTimes(int inRunTimes) {
		InRunTimes = inRunTimes;
	}
	
}
