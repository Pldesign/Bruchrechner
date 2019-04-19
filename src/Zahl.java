import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Zahl {
	/*
	 * mögliche Zahlenformate Bruch Ganze_Zähler|Nenner, Dezimalzahl
	 * Ganze.Nachkommastellen, Gleitkommazahl Ganze.Nachkommastellen E Zehnerpotenz,
	 */
	private String zahlpat = "(\\d*[\\_]?\\d+[\\|]?\\d+)|(\\d*[\\.]?\\d+)|(\\d*[\\.]?\\d+E[\\-\\+]?\\d{1,3})";
	private static String artpat = "[BDNG]\\d*";
	private String delb1 = "[\\_]";
	private String delb2 = "[\\|]";
	private String deld1 = "[\\.]";
	private String deld2 = "[E]";
	

	private long zaehler;
	private long nenner;
	private double decim;
	private String art;

	Zahl(String zahlstring) {
		setZahl(zahlstring);
	}

	Zahl(long z, long n) {
		this.zaehler = z;
		this.nenner = n;
		kuerzeBruch();
		this.decim = (double) z / (double) n;
		this.art = "B";
	}

	Zahl(Zahl za) {
		this.zaehler = za.zaehler;
		this.nenner = za.nenner;
		this.decim = za.decim;
		this.art = za.art;
	}
	Zahl(double z) {
		this.decim = z;
		this.art = "D";
	}

	public String getZahl(String a) {

		if (a.equals(""))
			a = "B";

		if (!a.matches(artpat)) {
			throw new ArithmeticException("Falsche Zahlenart: " + a);
		}
		String str;

		if (a.equals("B") && this.art.equals("B")) {
			if (zaehler % nenner == 0) {
				str = zaehler / nenner + "";
			} else if (zaehler / nenner == 0) {
				str = zaehler + "|" + nenner;
			} else {
				str = zaehler / nenner + "_" + Math.abs(zaehler) % nenner + "|" + nenner;
			}
		} else {
			try {
				BigDecimal bdec = new BigDecimal(decim);

				if (a.substring(0, 1).equals("G")) {
					str = bdec.round(new MathContext(Integer.parseInt(a.substring(1)), RoundingMode.HALF_UP)) + "";
				} else if (Math.abs(decim) < 1.0E12) {
					if (a.substring(0, 1).equals("N")) {
						str = bdec.setScale(Integer.parseInt(a.substring(1)), BigDecimal.ROUND_HALF_UP) + "";
					} else {
						str = bdec.round(new MathContext(12, RoundingMode.HALF_UP)) + "";
					}

				} else {
					str = bdec.round(new MathContext(12, RoundingMode.HALF_UP)) + "";
				}
			} catch (Exception e1) {
				throw new ArithmeticException(e1.getMessage());
			}

		}

		return str;

	}

	public void setZahl(String zahlstring) {

		if (!zahlstring.matches(zahlpat)) {
			throw new ArithmeticException("Falsche Zahlenart: " + zahlstring);
		}

		art = "B";
		zaehler = 0;
		nenner = 1;
		decim = 0;

		String b2bruch[] = zahlstring.split(delb2);
		String b1bruch[] = b2bruch[0].split(delb1);
		String d2bruch[] = zahlstring.split(deld2);
		String d1bruch[] = d2bruch[0].split(deld1);
		int d3 = 0;
		if (b2bruch.length > 1) {
			if (b1bruch.length > 1) {
				zaehler = Math.addExact(Math.multiplyExact(Long.parseLong(b2bruch[1]), Long.parseLong(b1bruch[0])),
						Long.parseLong(b1bruch[1]));
				nenner = Long.parseLong(b2bruch[1]);
			} else {
				zaehler = Long.parseLong(b1bruch[0]);
				nenner = Long.parseLong(b2bruch[1]);
			}
		} else {

			if (d2bruch.length > 1) {
				d3 = Integer.parseInt(d2bruch[1]);
			}
			if (d1bruch.length > 1 || d3 > 0) {
				if (d1bruch.length > 1) {
					zaehler = Long.parseLong(d1bruch[0] + d1bruch[1]);
					nenner = (long) Math.pow(10, d1bruch[1].length());
				} else {
					zaehler = Long.parseLong(d1bruch[0]);
				}
				decim = (double) zaehler / (double) nenner;
				if (d2bruch.length > 1) {
					if (Math.abs(d3) < 16) {
						if (d3 < 0) {
							nenner = Math.multiplyExact(nenner, (long) Math.pow(10, Math.abs(d3)));
						} else {
							zaehler = Math.multiplyExact(zaehler, (long) Math.pow(10, Math.abs(d3)));
						}

					} else {
						decim = (double) zaehler / (double) nenner * Math.pow(10, d3);
						art = "D";
					}
				}
			} else {
				zaehler = Long.parseLong(d1bruch[0]);
			}
		}

		if (art.equals("B")) {
			kuerzeBruch();
			decim = (double) zaehler / (double) nenner;
		}

	}

	static public Zahl addiereZahl(Zahl z1, Zahl z2, String a) {

		Zahl ze = new Zahl("0");
		ze.setArt(z1.art, z2.art, a);

		if (ze.art.equals("B")) {
			ze.zaehler = Math.addExact(Math.multiplyExact(z1.zaehler, z2.nenner),
					Math.multiplyExact(z2.zaehler, z1.nenner));
			ze.nenner = Math.multiplyExact(z1.nenner, z2.nenner);
			ze.kuerzeBruch();
			ze.decim = (double) ze.zaehler / (double) ze.nenner;
		} else {
			ze.decim = z1.decim + z2.decim;
		}
		return ze;
	}

	static public Zahl subtrahiereZahl(Zahl z1, Zahl z2, String a) {

		Zahl ze = new Zahl("0");
		ze.setArt(z1.art, z2.art, a);

		if (ze.art.equals("B")) {
			ze.zaehler = Math.addExact(Math.multiplyExact(z1.zaehler, z2.nenner),
					Math.negateExact(Math.multiplyExact(z2.zaehler, z1.nenner)));
			ze.nenner = Math.multiplyExact(z1.nenner, z2.nenner);
			ze.kuerzeBruch();
			ze.decim = (double) ze.zaehler / (double) ze.nenner;
		} else {
			ze.decim = z1.decim - z2.decim;
		}
		return ze;
	}

	static public Zahl multipliziereZahl(Zahl z1, Zahl z2, String a) {

		Zahl ze = new Zahl("0");
		ze.setArt(z1.art, z2.art, a);

		if (ze.art.equals("B")) {
			ze.zaehler = Math.multiplyExact(z1.zaehler, z2.zaehler);
			ze.nenner = Math.multiplyExact(z1.nenner, z2.nenner);
			ze.kuerzeBruch();
			ze.decim = (double) ze.zaehler / (double) ze.nenner;
		} else {
			ze.decim = z1.decim * z2.decim;
		}
		return ze;
	}

	static public Zahl dividiereZahl(Zahl z1, Zahl z2, String a) {

		Zahl ze = new Zahl("0");
		ze.setArt(z1.art, z2.art, a);

		if (ze.art.equals("B")) {
			ze.zaehler = Math.multiplyExact(z1.zaehler, z2.nenner);
			ze.nenner = Math.multiplyExact(z1.nenner, z2.zaehler);
			ze.kuerzeBruch();
			ze.decim = (double) ze.zaehler / (double) ze.nenner;
		} else {

			ze.decim = z1.decim / z2.decim;
		}
		return ze;

	}

	static public Zahl potenziereZahl(Zahl z1, Zahl z2, String a) {

		Zahl ze = new Zahl(z1);
		ze.setArt(z1.art, z2.art, a);

		if (z2.nenner > 1) {
			ze.art = "D";
		}

		if (ze.art.equals("B")) {
			long z = ze.zaehler;
			long n = ze.nenner;
			long x;
			if (z1.zaehler < 0) {
				x = ze.zaehler;
				ze.zaehler = ze.nenner;
				ze.nenner = x;
				x = z;
				z = n;
				n = x;
			}
			if (z1.zaehler == 0) {
				ze.zaehler = 1;
				ze.nenner = 1;
			}

			for (int i = 2; i <= Math.abs(z2.zaehler); i++) {
				ze.zaehler = Math.multiplyExact(ze.zaehler, z);
				ze.nenner = Math.multiplyExact(ze.nenner, n);
			}
			ze.kuerzeBruch();
			ze.decim = (double) ze.zaehler / (double) ze.nenner;
		} else {
			ze.decim = Math.pow(z1.decim, z2.decim);
		}
		return ze;
	}

	private void setArt(String a1, String a2, String a) {
		if (a.equals(""))
			a = "B";
		if (!a.matches(artpat)) {
			throw new ArithmeticException("Falsche Zahlenart: " + a);
		}

		if (a1.equals("B") && a2.equals("B") && a.equals("B")) {
			this.art = "B";
		} else {
			this.art = "D";
		}
	}

	private void kuerzeBruch() {

		if (nenner == 0) {
			throw new ArithmeticException("Division durch Null: " + zaehler + ":" + nenner);

		} else {
			if (nenner < 0) {
				zaehler = Math.negateExact(zaehler);
				nenner = Math.negateExact(nenner);
			}

			long x = getGgT(zaehler, nenner);

			nenner = Math.abs(nenner / x);
			zaehler = zaehler / x;
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
