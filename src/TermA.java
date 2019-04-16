
public class TermA {
	private String rz;
	private int ind;
	private Zahl ze;
	private int ind1;
	private Zahl z1;
	private int ind2;
	private Zahl z2;
	
	TermA(String r, int i, Zahl e, int i1, Zahl z1, int i2, Zahl z2){
		this.rz = r;
		this.ind  = i;
		this.ind1 = i1;
		this.ind2 = i2;
		this.ze = e;
		this.z1 = z1;
		this.z2 = z2;
	}

	public String getRz() {
		return rz;
	}
	public int getInd() {
		return ind;
	}
	public Zahl getZe() {
		return ze;
	}

	public int getInd1() {
		return ind1;
	}

	public Zahl getZ1() {
		return z1;
	}

	public int getInd2() {
		return ind2;
	}

	public Zahl getZ2() {
		return z2;
	}

}
