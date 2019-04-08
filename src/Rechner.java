

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Rechner {
	private int ebene = 0;
	private int rli = 0;
	private int ali = 0;
	private int zus = 0;
	private int ebenemax;
	private long ze = 0;
	private long ne = 1;
	private long z1 = 0;
	private long n1 = 1;
	private long z2 = 0;
	private long n2 = 1;
	private double de = 0;
	private double d1 = 0;
	private double d2 = 0;
	private String bruchpat = "(\\d*[\\_]?\\d+[\\|]?\\d+)|(\\d*[\\.]?\\d+)|(\\d*[\\.]?\\d+E[\\-\\+]?\\d{1,3})";
	private String rzpat = "[\\(\\)\\+\\-\\*\\/\\^\\s]";
	private String delb1 = "[\\_]";
	private String delb2 = "[\\|]";
	private String deld1 = "[\\.]";
	private String deld2 = "[E]";
	private String ch = "";
	private String brtxt = "";
	int bvi = 0;
	int bni = 0;
	int ver = 1;
	int nk = 0;

	private String[][] rl;
	private int[][] vl;
	private int[][] al;
	private long[][] bl;
	private double[] dl;

	private String str = "";
	private String ergebnis = "";

	public Rechner(String bstring, int n) {
		setRechner(bstring, n);
	}

	public void setRechner(String bstring, int n) {
		nk = n;
		str = bstring;
		ergebnis = "";
		zerlegeString();
		rechneRl();
		if (ali == 0) {
			al = new int[0][3];
			
		}
	}

	public String getErgebnis() {
		if (nk == 0) {
			if (ze % ne == 0) {
				ergebnis = ze / ne + "";
			} else if (ze / ne == 0) {
				ergebnis = ze + "|" + ne;
			} else {
				ergebnis = ze / ne + "_" + Math.abs(ze) % ne + "|" + ne;
			}
		} else {
			
			BigDecimal bdec = new BigDecimal(de);
			ergebnis = bdec.round(new MathContext(nk, RoundingMode.HALF_UP)) + "";
			
		}

		return ergebnis;
	}

	public int[][] getAl() {
		return al;
	}

	public String[][] getRl() {
		return rl;
	}

	private void zerlegeString() {

		rli = 0;
		ali = 0;

		ebene = 1;
		ebenemax = 1;
		zus = 0;
		rl = new String[1][2];
		vl = new int[1][5];
		bl = new long[1][2];
		al = new int[1][3];
		dl = new double[1];

		for (int i = 0; i < str.length(); i++) {

			ch = str.substring(i, i + 1);
			if (ch.equals("\\")) {
				i = str.length();
			} else {
				int si = 0;
				if (ch.matches(rzpat)) {
					if (ch.matches("\\s")) {
						ch = " ";
					}
				} else {
					brtxt ="";
					while (si < (str.length() - i) && ch != "N") {
						if (str.substring(i, str.length() - si).matches(bruchpat)) {
							ch = "N";
							brtxt = str.substring(i, str.length() - si);
						}
						si++;
					}
					if (ch !="N"){
						throw new ArithmeticException(
						"Syntax-Fehler Pos:" + i + ": Falsches Zahlenformat!");
					}
				}

				if (i == 0) {
					rl[0][0] = "(";
					rl[0][1] = "0";
					vl[0][0] = 5;
					vl[0][1] = ebene;
				}

				switch (zus) {
				case 0:
					switch (ch) {
					case "N":
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
			throw new ArithmeticException("Syntax-Fehler Pos:" + str.length() + ": Fehlende Endklammer!");
		} else if (ebene < 0) {
			throw new ArithmeticException("Syntax-Fehler Pos:" + 0 + ": Fehlende Anfangsklammer!");
		}

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
						rz = rl[i][0].substring(0, 1);
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
						if (nk == 0) {
							z1 = bl[bvi][0];
							n1 = bl[bvi][1];
							z2 = bl[bni][0];
							n2 = bl[bni][1];
						} else {
							d1 = dl[bvi];
							d2 = dl[bni];
						}
						switch (rz) {
						case "+":
							addiereBruch();
							break;
						case "-":
							subtrahiereBruch();
							break;
						case "*":
							multipliziereBruch();
							break;
						case "/":
							dividiereBruch();
							break;
						case "^":
							potenziereBruch();
							break;

						}
						vl[i][0] = 1;
						vl[i][2] = ver;
						vl[i][3] = bvi;
						vl[i][4] = bni;
						if (nk == 0) {
							kuerzeBruch();
							bl[i][0] = ze;
							bl[i][1] = ne;
						} else {
							dl[i] = de;
						}
						rl[i][1] = getErgebnis();
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
					vl[i][0] = 0;
					rl[i + bni][1] = rl[i][1];
					vl[i + bni][1] = ei - 1;
					vl[i + bni][0] = 1;
					bl[i + bni][0] = bl[i][0];
					bl[i + bni][1] = bl[i][1];
					dl[i + bni] = dl[i];
					vl[i - bvi][0] = 0;

				}

			}
			zus = 4;

		}

	}

	private void schreibeTabelle(String bruch, int zus, int ebene) {

		rli++;
		if (rli > 0) {
			String[][] rtemp = rl;
			int[][] vtemp = vl;
			long[][] btemp = bl;
			double[] dtemp = dl;

			rl = new String[rli + 1][2];
			vl = new int[rli + 1][5];
			bl = new long[rli + 1][2];
			dl = new double[rli + 1];

			System.arraycopy(rtemp, 0, rl, 0, rli);
			System.arraycopy(vtemp, 0, vl, 0, rli);
			System.arraycopy(btemp, 0, bl, 0, rli);
			System.arraycopy(dtemp, 0, dl, 0, rli);
		}

		rl[rli][0] = bruch;
		rl[rli][1] = "0";
		vl[rli][0] = zus;
		vl[rli][1] = ebene;

		String b2bruch[] = bruch.split(delb2);
		String b1bruch[] = b2bruch[0].split(delb1);

		String d2bruch[] = bruch.split(deld2);
		String d1bruch[] = d2bruch[0].split(deld1);

		if (zus == 1) {
			ze = 0;
			ne = 1;
			de = 0;
			if (b2bruch.length > 1) {
				if (b1bruch.length > 1) {
					ze = Long.parseLong(b1bruch[0]) * Long.parseLong(b2bruch[1]) + Long.parseLong(b1bruch[1]);
					ne = Long.parseLong(b2bruch[1]);
				} else {
					ze = Long.parseLong(b2bruch[0]);
					ne = Long.parseLong(b2bruch[1]);
				}

			} else if (d1bruch.length > 1 || d2bruch.length > 1) {
				if (d1bruch.length > 1) {
					ze = Long.parseLong(d1bruch[0] + d1bruch[1]);
					ne = (long) Math.pow(10, d1bruch[1].length());
				} else {
					ze = Long.parseLong(d1bruch[0]);
					ne = 1;
				}

				de = (double) ze / (double) ne;
				if (d2bruch.length > 1) {
					if (nk > 0) {
						if (Math.abs(Double.parseDouble(d2bruch[1]))>300) {
							throw new ArithmeticException("Exponent zu groß: " + d2bruch[1]);
						}
						de = (double) ze / (double) ne * Math.pow(10, Double.parseDouble(d2bruch[1]));
					} else {
						if (Math.abs(Double.parseDouble(d2bruch[1]))>30) {
							throw new ArithmeticException("Exponent für Brüche zu groß: " + d2bruch[1]);
						}
						if (d2bruch[1].substring(0, 1).equals("-")) {
							ne = Math.multiplyExact(ne, (long) Math.pow(10, Long.parseLong(d2bruch[1].substring(1))));
						} else {
							ze = Math.multiplyExact(ze, (long) Math.pow(10, Long.parseLong(d2bruch[1])));
						}
					}
				}
			} else {
				ze = Long.parseLong(bruch);
				ne = 1;
			}

			if (nk == 0) {
				kuerzeBruch();
				bl[rli][0] = ze;
				bl[rli][1] = ne;

			} else {
				if (de == 0) {
					de = (double) ze / (double) ne;
				}
				dl[rli] = de;
			}
		}

		rl[rli][1] = getErgebnis();

	}

	private void schreibeATab(int ind, int vor, int nach) {

		if (ali > 0) {
			int[][] atemp = al;
			al = new int[ali + 1][3];
			System.arraycopy(atemp, 0, al, 0, ali);
		}

		al[ali][0] = ind;
		al[ali][1] = vor;
		al[ali][2] = nach;
		ali++;

	}

	private void addiereBruch() {
		if (nk == 0) {
			ze = Math.addExact(Math.multiplyExact(z1, n2), Math.multiplyExact(z2, n1));
			ne = Math.multiplyExact(n1, n2);
		} else {
			de = d1 + d2;
		}
	}

	private void subtrahiereBruch() {
		if (nk == 0) {
			ze = Math.addExact(Math.multiplyExact(z1, n2), Math.negateExact(Math.multiplyExact(z2, n1)));
			ne = Math.multiplyExact(n1, n2);
		} else {
			de = d1 - d2;
		}
	}

	private void multipliziereBruch() {
		if (nk == 0) {
			ze = Math.multiplyExact(z1, z2);
			ne = Math.multiplyExact(n1, n2);
		} else {
			de = d1 * d2;
		}
	}

	private void dividiereBruch() {
		if (nk == 0) {
			ze = Math.multiplyExact(z1, n2);
			ne = Math.multiplyExact(n1, z2);
		} else {
			de = d1 / d2;
		}
	}

	private void potenziereBruch() {
		if (nk == 0) {
			ne = bl[bvi][1];
			ze = bl[bvi][0];
			long hoch = bl[bni][0];
			long z = ze;
			long n = ne;
			long x;
			if (bl[bni][1] > 1) {
				throw new ArithmeticException("Keine ganzzahliger Exponent: " + hoch + "|" + bl[bni][1]);

			} else if (hoch < 0) {
				x = ze;
				ze = ne;
				ne = x;
				x = z;
				z = n;
				n = x;

			}
			if (hoch == 0) {
				ze = 1;
				ne = 1;
			}

			for (int i = 2; i <= Math.abs(hoch); i++) {
				ze = Math.multiplyExact(ze, z);
				ne = Math.multiplyExact(ne, n);
			}
		} else {
			de = Math.pow(d1, d2);
		}
	}

	private void kuerzeBruch() {
		if (ne == 0) {
			throw new ArithmeticException("Division durch Null: " + ze + ":" + ne);

		} else {
			if (ne < 0) {
				ze = Math.negateExact(ze);
				ne = Math.negateExact(ne);
			}

			long x = getGgT(ze, ne);

			ne = Math.abs(ne / x);
			ze = ze / x;
		}
	}

	private long getGgT(long a, long b) {
		long x = b;
		while (a % b != 0) {
			x = a % b;
			a = b;
			b = x;
		}

		return Math.abs(x);
	}
}
