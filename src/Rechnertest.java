

public class Rechnertest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] str = new String[] { " 2++3","2^1|2 RB" ,"((-2(-3)^3*2.5 RN3", "2 / 1_1|3 RG3", 

				"(2-5) (4+1)  RB", "2+3(4) RB", "(-1_1|2)^(-4) RG3", "-2^(-3)RB", "-2+3RB", "2*(3+4)^3RB", "-1_1|2 + .4RB" };
		Rechner rechner = new Rechner("0");

		for (int i = 0; i < str.length; i++) {

			System.out.println();

			System.out.println("Eingabe:   " + str[i]);

			try {
				rechner.setRechner(str[i]);
				String[][] rl = rechner.getRl();
				int[][] al = rechner.getAl();
				System.out.print("Rechnung: ");

				for (int vi = 0; vi < rl.length; vi++) {
					System.out.print(rl[vi][0] + " ");
				}
				System.out.print(" = " + rechner.getErgebnis());
				System.out.println();
				System.out.println();
				System.out.println("Rechenschritte:");
				System.out.println();

				
				for (int vi = 0; vi < al.length; vi++) {
					System.out.println(vi + 1 + ". (" + rl[al[vi][1]][1] + ") " + rl[al[vi][0]][0] + " ("
							+ rl[al[vi][2]][1] + ") = " + rl[al[vi][0]][1]);
				}
				System.out.println();
				System.out.println("_________________________________________________________________________________________________________");

			} catch (ArithmeticException e) {
				System.out.println(e.getMessage());
				System.out.println();

			}

		}

	}

}
