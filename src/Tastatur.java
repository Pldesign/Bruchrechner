import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Dimension;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Tastatur extends JPanel implements ActionListener {
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	private JButton button8;
	private JButton button9;
	private JButton button0;
	private JButton buttonpot;
	private JButton buttonmul;
	private Termrechner rechner;
	private JPanel panel;
	private JButton buttondiv;
	private JButton buttonadd;
	private JButton buttonsub;
	private JButton buttonBE;
	private JButton buttonB1;
	private JButton buttonB2;
	private JButton buttonBP;
	private JButton buttondel;
	private JButton buttonAC;
	private JButton buttonKA;
	private JButton buttonKE;
	private JButton buttonCL;
	private JButton buttonCR;
	private JComboBox<String> var;
	private JComboBox<String> kon;
	private JButton buttonANS;
	private JButton buttonGL;
	private JComboBox<String> STO;

	/**
	 * Create the panel.
	 */
	public Tastatur(Termrechner rech) {

		rechner = rech;
		setLayout(new GridLayout(0, 1, 0, 0));
		UIManager.put("Button.font", new Font("Tahoma", Font.PLAIN, 15));
		panel = new JPanel();
		add(panel);
		panel.setAutoscrolls(true);
		panel.setLayout(new GridLayout(0, 5, 0, 0));

		button1 = new JButton("1");
		button1.addActionListener(this);

		STO = new JComboBox<String>();
		STO.setToolTipText(
				"Variable wird auf den Inhalt der Rechnung gesetzt! \r\nBei Auswahl von STO werden alle Variablen zur\u00FCckgesetzt.");
		STO.setFont(UIManager.getFont("Button.font"));
		STO.setModel(new DefaultComboBoxModel<String>(new String[] { "STO", ">a", ">b", ">c", ">d", ">f", ">g", ">h",
				">r", ">s", ">t", ">u", ">v", ">w", ">x", ">y", ">z" }));
		STO.addActionListener(this);
		panel.add(STO);

		var = new JComboBox<String>();
		panel.add(var);
		var.setFont(UIManager.getFont("Button.font"));
		var.setModel(new DefaultComboBoxModel<String>(new String[] { "VAR", "a", "b", "c", "d", "f", "g", "h", "r", "s",
				"t", "u", "v", "w", "x", "y", "z" }));
		var.addActionListener(this);

		kon = new JComboBox<String>();
		panel.add(kon);
		kon.setFont(UIManager.getFont("Button.font"));
		kon.setModel(new DefaultComboBoxModel<String>(new String[] { "KON", "p", "e" }));
		kon.addActionListener(this);

		buttondel = new JButton("del");
		buttondel.setToolTipText("Loschen des vorherigen Buchstabens!");
		panel.add(buttondel);
		buttondel.addActionListener(this);

		buttonAC = new JButton("AC");
		buttonAC.setToolTipText("Eingabe l\u00F6schen!");
		panel.add(buttonAC);
		buttonAC.addActionListener(this);
		panel.add(button1);

		button2 = new JButton("2");
		button2.addActionListener(this);
		panel.add(button2);

		button3 = new JButton("3");
		button3.addActionListener(this);
		panel.add(button3);

		button4 = new JButton("4");
		button4.addActionListener(this);

		buttonCL = new JButton("<");
		buttonCL.setToolTipText("Cursor nach links");
		buttonCL.addActionListener(this);
		panel.add(buttonCL);

		buttonCR = new JButton(">");
		buttonCR.setToolTipText("Cursor nach rechts");
		buttonCR.addActionListener(this);
		panel.add(buttonCR);
		panel.add(button4);

		button5 = new JButton("5");
		button5.addActionListener(this);
		panel.add(button5);

		button6 = new JButton("6");
		button6.addActionListener(this);
		panel.add(button6);

		button7 = new JButton("7");
		button7.addActionListener(this);

		buttonKA = new JButton("(");
		panel.add(buttonKA);
		buttonKA.addActionListener(this);

		buttonKE = new JButton(")");
		panel.add(buttonKE);
		buttonKE.addActionListener(this);
		panel.add(button7);

		button8 = new JButton("8");
		button8.addActionListener(this);
		panel.add(button8);

		button9 = new JButton("9");
		button9.addActionListener(this);
		panel.add(button9);

		button0 = new JButton("0");
		button0.addActionListener(this);

		buttonmul = new JButton("*");
		panel.add(buttonmul);
		buttonmul.addActionListener(this);

		buttondiv = new JButton("/");
		panel.add(buttondiv);
		buttondiv.addActionListener(this);
		panel.add(button0);

		buttonB1 = new JButton("_");
		buttonB1.setToolTipText("Bruchformat ");
		buttonB1.addActionListener(this);

		buttonBP = new JButton(".");
		buttonBP.addActionListener(this);
		panel.add(buttonBP);

		buttonpot = new JButton("^");
		panel.add(buttonpot);
		buttonpot.addActionListener(this);

		buttonadd = new JButton("+");
		panel.add(buttonadd);
		buttonadd.addActionListener(this);

		buttonsub = new JButton("-");
		panel.add(buttonsub);
		buttonsub.addActionListener(this);
		panel.add(buttonB1);

		buttonB2 = new JButton("|");
		buttonB2.setToolTipText("Bruchformat");
		buttonB2.addActionListener(this);
		panel.add(buttonB2);

		buttonBE = new JButton("E");
		buttonBE.setToolTipText("10er Potenzschreibweise");
		panel.add(buttonBE);

		buttonANS = new JButton("ANS");
		buttonANS.setToolTipText("Inhalt der letzten Rechnung Variable  A");
		panel.add(buttonANS);

		buttonGL = new JButton("=");
		panel.add(buttonGL);
		buttonGL.addActionListener(this);
		buttonANS.addActionListener(this);
		buttonBE.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String str = "";
		String cl = e.getSource().getClass().getName();

		if (cl.equals("javax.swing.JButton")) {
			JButton button = (JButton) e.getSource();
			str = button.getText();
		}

		if (cl.equals("javax.swing.JComboBox")) {
			JComboBox<String> cb = (JComboBox<String>) e.getSource();

			str = cb.getSelectedItem() + "";
			cb.setSelectedIndex(0);
		}

		rechner.addEingabe(str);

	}

}
