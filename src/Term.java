import java.util.TreeMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term {
	private int ebene = 0;
	private int rli = 0;
	private int ali = 0;
	private int zus = 0;
	private int ebenemax;
	private Zahl ze = new Zahl("0");
	private Zahl z1 = new Zahl("0");
	private Zahl z2 = new Zahl("0");
	private String bruchpat = "(\\d*[\\_]?\\d+[\\|]?\\d+)|(\\d*[\\.]?\\d+)|(\\d*[\\.]?\\d+E[\\-\\+]?\\d{1,3})";
	private String rzpat = "[\\(\\)\\+\\-\\*\\/\\^\\s]";
	private String varpat = "[a-zA]";
	private String[] vartest;

	private String neuevar = "";
	private String ch = "";
	private String brtxt = "";
	private int bvi = 0;
	private int bni = 0;
	private int ver = 1;

	private String[] rl;
	private int[][] vl;
	private TermA[] al;
	private Zahl[] zl;

	private TreeMap<String, Term> varmap = new TreeMap<String, Term>();
	private TreeMap<String, Term> varaus = new TreeMap<String, Term>();

	private String eingabe = "";

	public Term(String eingabe, TreeMap<String, Term> varmap) {
		if (varmap == null) {
			varmap = new TreeMap<String, Term>();
		}
		this.varmap = varmap;
		this.eingabe = eingabe;

		String hashstring[] = eingabe.split("\\;");
		for (int i = 1; i < hashstring.length; i++) {
			Term te = new Term(hashstring[i], varmap);
		}

		eingabe = hashstring[0];
		vartest = eingabe.split("\\>");

		testVariablen();
		zerlegeString();
		rechneRl();

		if (!neuevar.equals("")) {
			varmap.put(neuevar, this);
			this.eingabe += ">" + neuevar;
		}
		if (ali == 0) {
			al = new TermA[0];
		}
	}

	public TermA[] getAusgabeliste() {
		return al;
	}
	public String[] getRl() {
		return rl;
	}

	public TreeMap<String, Term> getVarmap() {
		return varaus;
	}

	public Zahl getErgebnis() {
		return ze;
	}

	public String getEingabe() {
		return eingabe;
	}

	public String getHashString() {
		String hashstring = eingabe;
		for (Map.Entry e : varaus.entrySet()) {
			Term te = (Term) e.getValue();
			hashstring += " ; " + te.getEingabe();
		}

		return hashstring;
	}

	private void testVariablen() {

		String varlist = "";

		if (vartest.length > 1) {
			varlist = vartest[1].substring(0, 1);
			if (varlist.matches("[a-z]")) {
				neuevar = varlist;
			}
		}

		boolean test = true;
		boolean var = true;
		boolean txyz = false;

		if (varlist.matches("[txyz]")) {
			txyz = true;
		}
		varlist = "V" + varlist;
		eingabe = vartest[0];

		String teststring = vartest[0];
		String v = "";
		String pat2 = "";

		while (test) {

			Pattern p = Pattern.compile("[" + varlist + "]");
			Matcher m = p.matcher(teststring);
			if (m.find()) {
				var = false;
			}
			test = false;

			for (int i = 0; i < teststring.length(); i++) {
				v = teststring.substring(i, i + 1);
				if (v.matches(varpat) && !v.matches("[" + varlist + "]")) {

					if (varmap.get(v) == null) {
						switch (v) {
						case "p":
						case "e":
							break;
						default:
							varmap.put(v, new Term("0", varmap));
							teststring = teststring.substring(i + 1) + varmap.get(v).getEingabe();
							i = 0;
							pat2 += v;
							test = true;
						}
					}

				}
				if (v.matches("[A]") && !neuevar.equals("")) {
					var = false;
				}
				varlist += pat2;

			}

			if (txyz && varlist.length() > 2) {
				throw new ArithmeticException("Die Variablen (txyz) dürfen keine Parameter haben: " + varlist);

			} else if (!var) {
				throw new ArithmeticException("Zirkelbezug der Variablen: " + varlist);

			}
		}

	}

	private void zerlegeString() {

		rli = 0;
		ali = 0;

		ebene = 1;
		ebenemax = 1;
		zus = 0;
		rl = new String[1];
		vl = new int[1][5];
		al = new TermA[1];
		zl = new Zahl[1];

		for (int i = 0; i < eingabe.length(); i++) {

			ch = eingabe.substring(i, i + 1);
			if (ch.equals("\\")) {
				i = eingabe.length();
			} else {
				int si = 0;
				if (ch.matches(rzpat)) {
					if (ch.matches("\\s")) {
						ch = " ";
					}
				} else if (ch.matches(varpat)) {
					brtxt = ch;
					ch = "V";
				} else {
					brtxt = "";
					while (si < (eingabe.length() - i) && ch != "N") {
						if (eingabe.substring(i, eingabe.length() - si).matches(bruchpat)) {
							ch = "N";
							brtxt = eingabe.substring(i, eingabe.length() - si);
						}
						si++;
					}

					if (ch != "N") {
						throw new ArithmeticException("Syntax-Fehler Pos:" + i + ": Falsches Zahlenformat!");
					}
				}

				if (i == 0) {
					rl[0] = "(";
					vl[0][0] = 5;
					vl[0][1] = ebene;
				}

				switch (zus) {
				case 0:
					switch (ch) {
					case "N":
					case "V":
						zus = 1;
						schreibeTabelle(brtxt, 1, ebene);
						i += brtxt.length() - 1;
						break;
					case "(":
						ebene++;
						if (ebene > ebenemax) {
							ebenemax = ebene;
						}
						schreibeTabelle(ch, 5, ebene);
						break;
					case "-":
						schreibeTabelle("0", 1, ebene);
						schreibeTabelle(ch, 2, ebene);
						zus = 2;
						break;
					case "+":
					case " ":
						zus = 2;
						break;
					default:
						throw new ArithmeticException(
								"Syntax-Fehler Pos:" + i + ": Nach einer Anfangsklammer muss eine Zahl kommen!");
					}
					break;
				case 1:
					switch (ch) {
					case "(":
						schreibeTabelle("*", 3, ebene);
						ebene++;
						if (ebene > ebenemax) {
							ebenemax = ebene;
						}
						schreibeTabelle(ch, 5, ebene);
						zus = 0;
						break;
					case "V":
						schreibeTabelle("*", 3, ebene);
						schreibeTabelle(brtxt, 1, ebene);
						zus = 1;
						break;

					case ")":
						schreibeTabelle(ch, 6, ebene);
						zus = 3;
						ebene--;
						break;
					case "+":
					case "-":
						schreibeTabelle(ch, 2, ebene);
						zus = 2;
						break;
					case "*":
					case "/":
						schreibeTabelle(ch, 3, ebene);
						zus = 2;
						break;
					case "^":
						schreibeTabelle(ch, 4, ebene);
						zus = 2;
						break;
					case " ":
						break;
					default:
						throw new ArithmeticException(
								"Syntax-Fehler Pos:" + i + ": Nach einer Zahl muss ein Symbol kommen!");
					}
					break;
				case 2:
					switch (ch) {
					case "N":
					case "V":
						schreibeTabelle(brtxt, 1, ebene);
						i += brtxt.length() - 1;
						zus = 1;
						break;
					case "(":
						ebene++;
						if (ebene > ebenemax) {
							ebenemax = ebene;
						}
						schreibeTabelle(ch, 5, ebene);
						zus = 0;
						break;
					case " ":
						break;
					default:
						throw new ArithmeticException(
								"Syntax-Fehler Pos:" + i + ": Nach einem Rechenzeichen muss eine Zahl kommen!");
					}
					break;
				case 3:
					switch (ch) {
					case "(":
						schreibeTabelle("*", 3, ebene);
						ebene++;
						if (ebene > ebenemax) {
							ebenemax = ebene;
						}
						schreibeTabelle(ch, 5, ebene);
						zus = 0;
						break;
					case "N":
					case "V":
						schreibeTabelle("*", 3, ebene);
						schreibeTabelle(brtxt, 1, ebene);
						zus = 1;
						break;

					case ")":
						schreibeTabelle(ch, 6, ebene);
						zus = 3;
						ebene--;
						break;
					case "+":
					case "-":
						schreibeTabelle(ch, 2, ebene);
						zus = 2;
						break;
					case "*":
					case "/":
						schreibeTabelle(ch, 3, ebene);
						zus = 2;
						break;
					case "^":
						schreibeTabelle(ch, 4, ebene);
						zus = 2;
						break;
					case " ":
						break;
					default:
						throw new ArithmeticException(
								"Syntax-Fehler Pos:" + i + ": Nach einer Zahl muss ein Symbol kommen!");
					}
				}
			}
		}

		schreibeTabelle(")", 6, ebene);
		ebene--;
		if (ebene > 0) {
			throw new ArithmeticException("Syntax-Fehler Pos:" + eingabe.length() + ": Fehlende Endklammer!");
		} else if (ebene < 0) {
			throw new ArithmeticException("Syntax-Fehler Pos:" + 0 + ": Fehlende Anfangsklammer!");
		}

	}

	private void schreibeTabelle(String bruch, int zus, int ebene) {

		rli++;
		if (rli > 0) {
			String[] rtemp = rl;
			int[][] vtemp = vl;
			Zahl[] ztemp = zl;

			rl = new String[rli + 1];
			vl = new int[rli + 1][5];
			zl = new Zahl[rli + 1];

			System.arraycopy(rtemp, 0, rl, 0, rli);
			System.arraycopy(vtemp, 0, vl, 0, rli);
			System.arraycopy(ztemp, 0, zl, 0, rli);
		}

		rl[rli] = bruch;
		vl[rli][0] = zus;
		vl[rli][1] = ebene;

		if (zus == 1) {
			if (bruch.matches(varpat)) {
				switch (bruch) {
				case "p":
					ze = new Zahl(Math.PI);
					break;
				case "e":
					ze = new Zahl(Math.E);
					break;
				default:
					ze = (Zahl) varmap.get(bruch).getErgebnis();
					varaus.put(bruch, varmap.get(bruch));
					break;
				}
			} else {
				ze = new Zahl(bruch);
			}
		}
		zl[rli] = ze;

	}

	private void rechneRl() {
		String rz = "";
		bvi = 0;
		bni = 0;
		ver = 1;
		zus = 4;
		for (int ei = ebenemax; ei > 0; ei--) {
			while (zus > 1) {

				for (int i = 0; i < rli; i++) {

					if (vl[i][1] == ei && vl[i][0] == zus) {
						rz = rl[i].substring(0, 1);
						bvi = i - 1;
						while (vl[bvi][0] != 1 || vl[bvi][1] != ei) {
							if (vl[bvi][0] != 0) {
								throw new ArithmeticException("Syntax-Fehler Pos:" + i + ": Kein passender Vorgänger!");
							} else {

								bvi--;
							}
						}

						bni = i + 1;
						while (vl[bni][0] != 1 || vl[bni][1] != ei) {
							if (vl[bni][0] != 0 && vl[bni][0] != 6) {
								throw new ArithmeticException(
										"Syntax-Fehler Pos:" + i + ": Kein passender Nachfolger!");
							}
							if (bni < rli) {
								bni++;
							} else {
								throw new ArithmeticException(
										"Syntax-Fehler Pos:" + i + ": Kein passender Nachfolger!");
							}
						}

						z1 = zl[bvi];
						z2 = zl[bni];
						try {
							switch (rz) {
							case "+":
								ze = Zahl.addiereZahl(z1, z2, "B");
								break;
							case "-":
								ze = Zahl.subtrahiereZahl(z1, z2, "B");
								break;
							case "*":
								ze = Zahl.multipliziereZahl(z1, z2, "B");
								break;
							case "/":
								ze = Zahl.dividiereZahl(z1, z2, "B");
								break;
							case "^":
								ze = Zahl.potenziereZahl(z1, z2, "B");
								break;
							}
						} catch (Exception e) {
							switch (rz) {
							case "+":
								ze = Zahl.addiereZahl(z1, z2, "D");
								break;
							case "-":
								ze = Zahl.subtrahiereZahl(z1, z2, "D");
								break;
							case "*":
								ze = Zahl.multipliziereZahl(z1, z2, "D");
								break;
							case "/":
								ze = Zahl.dividiereZahl(z1, z2, "D");
								break;
							case "^":
								ze = Zahl.potenziereZahl(z1, z2, "D");
								break;
							}

						}
						vl[i][0] = 1;
						vl[i][2] = ver;
						vl[i][3] = bvi;
						vl[i][4] = bni;
						zl[i] = ze;
						vl[bvi][0] = 0;
						vl[bni][0] = 0;
						schreibeATab(i, bvi, bni);
						ver++;
					}
				}
				zus--;
			}

			for (int i = 0; i < rli; i++) {
				if (vl[i][0] == 1 && vl[i][1] == ei) {

					bvi = 1;
					while (vl[i - bvi][0] != 5 || vl[i - bvi][1] != ei) {
						if (vl[i - bvi][0] != 0 && vl[i - bvi][0] != 5 && vl[i - bvi][1] != 0) {
							throw new ArithmeticException(
									"Syntax-Fehler vor: " + zus + " Ebene: " + ei + " Index: " + i);
						}
						bvi++;
					}

					bni = 1;
					while (vl[i + bni][0] != 6 || vl[i + bni][1] != ei) {
						if (vl[i + bni][0] != 0 && vl[i + bni][0] != 6 && vl[i + bni][1] != 0) {
							throw new ArithmeticException(
									"Syntax-Fehler nach: " + zus + " Ebene: " + ei + " Index: " + i);
						}
						if (bni <= rli) {
							bni++;
						} else {
							throw new ArithmeticException(
									"Syntax-Fehler nach: " + zus + " Ebene: " + ei + " Index: " + i);
						}

					}
					vl[i][0] = 1;
					vl[i][1] = ei - 1;
					vl[i + bni][0] = 0;
					vl[i - bvi][0] = 0;

				}

			}
			zus = 4;

		}

	}

	private void schreibeATab(int ind, int vor, int nach) {

		if (ali > 0) {
			TermA[] atemp = al;
			al = new TermA[ali + 1];
			System.arraycopy(atemp, 0, al, 0, ali);
		} else {
			al = new TermA[1];
		}

		al[ali] = new TermA(rl[ind], ind, zl[ind], vor, zl[vor], nach, zl[nach]);
		ali++;

	}

}
