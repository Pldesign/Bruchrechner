
public class Variable {
	private String varname = "";
	private Term term = new Term("0",null);

	Variable(String name ,Term term){
		this.varname = name;
		this.term =term;
	}

	public String getVarname() {
		return varname;
	}

	public void setVarname(String varname) {
		this.varname = varname;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}
}
