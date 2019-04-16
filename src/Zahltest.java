

public class Zahltest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[][] str = new String[][] { 
		{"3",""},
		{"6|8",""},
		{"2_22|10",""},
		{ "2.5","B"},
		{"2.5E-4",""},
		{"2.5E-4","G3"},
		{"2.5E-4","N3"},
		{"2.5E4",""},
		{"2.5E4","G3"},
		{"2.5E4","N3"},
		{"2.5E17",""},
		{"2.5E17","G3"},
		{"2.5E17","N3"},
		{"2.5E-17",""},
		{"2.5E-17","G3"},
		{"2.5E-17","N3"},
		{"2.5E70",""},
		{"2.5E70","G3"},
		{"2.5E70","N3"},
		{"2.5E-70",""},
		{"2.5E-70","G3"},
		{"2.5E-70","N3"},
		{"2.5E350",""},
		{"2.5E-350",""}};

		Zahl za = new Zahl("0");

		for (int i = 0; i < str.length; i++) {

			System.out.println();

			System.out.println(  "Zahl: " + str[i][0]+  "  Ausgabeart: "+ str[i][1]);

			try {
				za.setZahl(str[i][0]);
				System.out.print("Format "+ za.getZahl(str[i][1]));
				
				System.out.println("\n_________________________________________________________________________________________________________");

			} catch (ArithmeticException e) {
				System.out.println(e.getMessage());
				System.out.println();

			}

		}

	}

}
