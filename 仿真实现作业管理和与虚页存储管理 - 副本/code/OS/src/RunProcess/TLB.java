package RunProcess;

public class TLB {
	//快表可存储8个页表项
		int Pagenum;//页号
		int Blocknum;//物理块号
		int State;
		int Visit;//访问字段，用于记录本页最近访问次数
		public TLB()
		{
			Pagenum=-1;
			Blocknum=-1;
			State=0;
			Visit=0;		
		}
}
