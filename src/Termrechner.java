import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;
import javax.swing.UIManager;
import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.ScrollPaneConstants;
import java.awt.Dimension;

public class Termrechner extends JFrame {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JMenu mnOptions;
	private JMenuItem menuListe;
	private JMenuItem menuTastatur;
	private JMenuItem menuErgebnisliste;
	private JMenu mnFile;
	private JMenuItem mntmVerlaufSichern;
	private JMenuItem mntmRechnungenSichern;
	private JMenuItem mntmVerlaufLesen;
	private JMenuItem mntmRechnungenLesen;
	final JFileChooser fc = new JFileChooser();
	private JMenuItem mntmAlleRechnungenAusfhren;
	private boolean tasten = true;
	private boolean rechenschritte = true;
	private JPanel panel;
	private JScrollPane scrollPane_1;
	public JPanel tastatur;
	public JPanel baum;

	private JSlider nk;
	private JComboBox<String> ra;
	private String art;
	private JPanel panel_3;
	private JLabel lblLetzteRechnungen;
	private JLabel lblverlauf;

	private String delkom = "[K]"; // Kommentar in der Eingabe
	private Term term;
	private TermA[] al;
	public JTextArea text;
	private int pos = 0;
	public String erglist = "";
	public JTextArea erg;
	public JComboBox<String> rechnungen;
	public JComboBox<String> verlauf;

	TreeMap<String, Term> rechmap = new TreeMap<String, Term>();

	TreeMap<String, Term> varmap = new TreeMap<String, Term>();
	private Term lterm = new Term("0",varmap);
	
	private JMenuBar menuBar;
	private JScrollPane scrollPane_2;
	private JTextArea var;
	private JPanel panel_1;
	private JSplitPane splitPane_1;
	private JPanel panel_2;
	private JPanel panel_4;
	private JButton btnNewButton;
	private JCheckBox chckbxVariablenbernehmen;
	private JSplitPane splitPane;
	private JScrollPane scrollPane_3;
	private JMenuItem mntmSchriftart;
	private JMenuItem mntmRechenschritteAusblenden;
	private JMenuItem mntmRechenbaumSichern;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Termrechner frame = new Termrechner();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Termrechner() {
		setTitle("Bruchrechner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		UIManager.put("Button.font", new FontUIResource("Tahoma", Font.BOLD, 15));
		art = "B";
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		FileFilter filter = new FileNameExtensionFilter("Text", "txt");
		fc.setFileFilter(filter);

		mnFile = new JMenu("Datei");
		menuBar.add(mnFile);

		mntmVerlaufLesen = new JMenuItem("Verlauf lesen");
		mntmVerlaufLesen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc.setSelectedFile(new File("verlauf.txt"));
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					List<String> lines = Collections.emptyList();
					try {
						erglist = "";

						lines = Files.readAllLines(Paths.get(fc.getSelectedFile().getPath()), StandardCharsets.UTF_8);
						erglist = "";
						for (String str : lines) {
							erglist += str + "\n";
						}

						erg.setText(erglist);

					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		mnFile.add(mntmVerlaufLesen);

		mntmVerlaufSichern = new JMenuItem("Verlauf sichern");
		mntmVerlaufSichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setSelectedFile(new File("verlauf.txt"));
				int returnVal = fc.showSaveDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					erg.setText(erglist);
					try {
						erg.write(new OutputStreamWriter(new FileOutputStream(fc.getSelectedFile()), "utf-8"));
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		mnFile.add(mntmVerlaufSichern);

		mntmRechnungenLesen = new JMenuItem("Rechnungen lesen");
		mntmRechnungenLesen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fc.setSelectedFile(new File("Rechnungen.txt"));
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					rechnungen.removeAllItems();
					List<String> lines = Collections.emptyList();
					try {
						lines = Files.readAllLines(Paths.get(fc.getSelectedFile().getPath()), StandardCharsets.UTF_8);
						rechnungen.removeAllItems();
						for (String str : lines) {
							rechnungen.addItem(str);
						}

					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
				alleRechnungen();

			}

		});
		mnFile.add(mntmRechnungenLesen);

		mntmRechnungenSichern = new JMenuItem("Rechnungen sichern");
		mntmRechnungenSichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				fc.setSelectedFile(new File("rechnungen.txt"));
				int returnVal = fc.showSaveDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					erg.setText("");
					for (int i = 0; i < rechnungen.getItemCount(); i++)
						erg.append(rechnungen.getItemAt(i) + "\n");
					try {
						erg.write(new OutputStreamWriter(new FileOutputStream(fc.getSelectedFile()), "utf-8"));
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}

			}
		});
		mnFile.add(mntmRechnungenSichern);
		
	
		mnOptions = new JMenu("Optionen");
		menuBar.add(mnOptions);

		menuListe = new JMenuItem("Verlauf l\u00F6schen");
		menuListe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				erg.setText("");
				erglist = "";
				var.setText("");
				rechnungen.removeAllItems();
				verlauf.removeAllItems();
				rechmap.clear();
				varmap.clear();
			}
		});

		mntmAlleRechnungenAusfhren = new JMenuItem("alle Rechnungen ausf\u00FChren");
		mntmAlleRechnungenAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alleRechnungen();

			}
		});
		mnOptions.add(mntmAlleRechnungenAusfhren);
		mnOptions.add(menuListe);

		menuTastatur = new JMenuItem("Tastatur ausblenden");
		menuTastatur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tasten) {
					menuTastatur.setText("Tastatur einblenden");
					tastatur.setVisible(false);
					tasten = false;
				} else {
					menuTastatur.setText("Tastatur ausblenden");
					tastatur.setVisible(true);
					tasten = true;
				}
			}
		});

		menuErgebnisliste = new JMenuItem("Verlauf anzeigen");
		menuErgebnisliste.setSelected(true);
		menuErgebnisliste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				erg.setText(erglist);
			}
		});
		mnOptions.add(menuErgebnisliste);
		
		mntmRechenschritteAusblenden = new JMenuItem("Rechenschritte ausblenden");
		mntmRechenschritteAusblenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rechenschritte) {
					menuTastatur.setText("Rechenschritte einblenden");
					rechenschritte = false;
				} else {
					menuTastatur.setText("Rechenschritte ausblenden");
					rechenschritte = true;
				}
				rund();
			}
		});
		mnOptions.add(mntmRechenschritteAusblenden);
		mnOptions.add(menuTastatur);
		
		mntmSchriftart = new JMenuItem("Schriftart");
		mntmSchriftart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFontChooser font = new JFontChooser();
				font.setSelectedFont(new Font("Tahoma", Font.PLAIN, 15));
				font.showDialog(erg);
				erg.setFont(font.getSelectedFont());
				text.setFont(font.getSelectedFont());
				var.setFont(font.getSelectedFont());
				baum.setFont(font.getSelectedFont());
				repaint();
			}
		});
		mnOptions.add(mntmSchriftart);

		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(
				new TitledBorder(null, "Eingabe", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPane.add(scrollPane_1, BorderLayout.NORTH);
		text = new JTextArea();
		text.setFont(new Font("Tahoma", Font.PLAIN, 14));
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addEingabe("=");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addEingabe("del");
				}
			}
		});

		text.setRows(3);
		text.requestFocus();
		scrollPane_1.setViewportView(text);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));

		tastatur = new Tastatur(this);
		panel.add(tastatur, BorderLayout.CENTER);

		panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);

		lblLetzteRechnungen = new JLabel("Rechnung: ");

		rechnungen = new JComboBox<String>();
		rechnungen.setModel(
				new DefaultComboBoxModel(new String[] { "(a^2+b^2)^.5>c;3>a;4>b", "1_2|7", "2.5E-6", "4.6E20+4E16" }));
		panel_3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel_3.add(lblLetzteRechnungen);
		panel_3.add(rechnungen);

		btnNewButton = new JButton("eingeben");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				berechneRechnung();
			}
		});
		panel_3.add(btnNewButton);

		chckbxVariablenbernehmen = new JCheckBox("Variablen \u00FCberschreiben");
		chckbxVariablenbernehmen.setSelected(true);
		panel_3.add(chckbxVariablenbernehmen);
		lblverlauf = new JLabel("Verlauf: ");
		panel_3.add(lblverlauf);
		verlauf = new JComboBox<String>();
		verlauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rechnungen.setSelectedItem(verlauf.getSelectedItem());
			}
		});
		panel_3.add(verlauf);

		splitPane_1 = new JSplitPane();
		splitPane_1.setOneTouchExpandable(true);
		contentPane.add(splitPane_1, BorderLayout.CENTER);

		panel_2 = new JPanel();
		splitPane_1.setRightComponent(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.3);
		splitPane.setOneTouchExpandable(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_2.add(splitPane);
		
				scrollPane = new JScrollPane();
				scrollPane.setPreferredSize(new Dimension(2, 200));
				splitPane.setLeftComponent(scrollPane);
				scrollPane.setViewportBorder(
						new TitledBorder(null, "Ausgabe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				erg = new JTextArea();
				erg.setFont(new Font("Tahoma", Font.PLAIN, 14));
				erg.setEditable(false);
				scrollPane.setViewportView(erg);
				erg.setText("");
				
				scrollPane_3 = new JScrollPane();
				scrollPane_3.setViewportBorder(new TitledBorder(null, "Rechenbaum", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				
				splitPane.setRightComponent(scrollPane_3);
				
				
				baum = new Baum (this);
				
				scrollPane_3.setViewportView(baum);
				

		panel_4 = new JPanel();
		splitPane_1.setLeftComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		scrollPane_2 = new JScrollPane();
		panel_4.add(scrollPane_2, BorderLayout.CENTER);
		scrollPane_2.setViewportBorder(
				new TitledBorder(null, "Variablen", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		var = new JTextArea();
		var.setRows(10);
		var.setFont(new Font("Tahoma", Font.PLAIN, 14));
		var.setEditable(false);
		scrollPane_2.setViewportView(var);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Ausgabe runden", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(3, 1, 0, 0));

		ra = new JComboBox<String>();
		panel_1.add(ra);
		ra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rund();
			}

		});

		ra.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Br\u00FCche", "Nachkommastellen", "geltende Ziffern " }));

		JLabel lblAnzahlStellen = new JLabel("Dezimalstellen:");
		lblAnzahlStellen.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblAnzahlStellen);

		nk = new JSlider();
		nk.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		nk.setPaintTrack(false);
		nk.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) {
				rund();
			}

		});
		panel_1.add(nk);
		nk.setMinorTickSpacing(1);
		nk.setMajorTickSpacing(5);
		nk.setSnapToTicks(true);
		nk.setValue(0);
		nk.setToolTipText("Bei Bruchdarstellung ist die Anzahl Null");
		nk.setPaintTicks(true);
		nk.setPaintLabels(true);
		nk.setMaximum(10);

		alleRechnungen();
		erglist = "";
		varmap.clear();
		var.setText("");
		erg.setText("");
		verlauf.removeAllItems();
		term = null;

	}

	private void rund() {
		int ind = ra.getSelectedIndex();
		switch (ind) {
		case 0:
			art = "B";
			nk.setValue(0);
			break;
		case 1:
			art = "N" + nk.getValue();
			break;
		case 2:
			if (nk.getValue() == 0)
				nk.setValue(1);
			art = "G" + nk.getValue();
		}
		setVarlist();
		if (term != null) {
			erg.setText("");
			erg.setText(getAusgabe(term, art));
		}
		repaint();
	}

	public void berechneTerm(String ein) {

		String[] eingabe = ein.split(delkom);
		if (!ein.equals("")) {
			erg.setText("");
			try {
				term = new Term(eingabe[0], varmap);
				erg.setText(getAusgabe(term, art));
				varmap.put("A", term);
				erglist += erg.getText();
				rechmap.put(term.getHashString(), term);
				verlauf.addItem(term.getHashString());
				text.setText("");
				pos = 0;

			} catch (Exception e1) {
				String[] ftxt = e1.getMessage().split("\\:");
				erg.setText(e1.getMessage() + "\n");

				if (ftxt[0].equals("Syntax-Fehler Pos")) {
					pos = Integer.parseInt(ftxt[1]);

				}
			}
		}
		repaint();

	}

	public void setVarlist() {
		var.setText("");
		for (Map.Entry e : varmap.entrySet()) {
			Term ter = (Term) e.getValue();
			if (!e.getKey().equals("A")) {
				ter = new Term(ter.getEingabe(), varmap);
				varmap.put((String) e.getKey(), ter);
			}
			var.append(e.getKey() + " = " + ter.getEingabe() + " = " + ter.getErgebnis().getZahl(art) + "\n");

		}

	}

	public void alleRechnungen() {
		for (int i = 0; i < rechnungen.getItemCount(); i++) {
			term = rechmap.get(rechnungen.getItemAt(i));
			if (term == null) {
				term = new Term((String) rechnungen.getItemAt(i), varmap);
			}
			if (chckbxVariablenbernehmen.isSelected()) {
				TreeMap<String, Term> va = new TreeMap<String, Term>();
				va = term.getVarmap();
				for (Map.Entry e : va.entrySet()) {
					Term ter = (Term) e.getValue();
					varmap.put((String) e.getKey(), ter);
				}
			}
			berechneTerm(term.getEingabe());

		}
		setRechnungen();
		setVarlist();
		erg.setText(erglist);
	}

	public void berechneRechnung() {
		Term te = rechmap.get(rechnungen.getSelectedItem());
		if (te == null) {
			te = new Term((String) rechnungen.getSelectedItem(), null);
		}
		if (chckbxVariablenbernehmen.isSelected()) {
			TreeMap<String, Term> va = new TreeMap<String, Term>();
			va = te.getVarmap();
			for (Map.Entry e : va.entrySet()) {
				Term ter = (Term) e.getValue();
				varmap.put((String) e.getKey(), ter);
			}
		}
		berechneTerm(te.getEingabe());
		
		setRechnungen();
		setVarlist();
		text.setText(term.getEingabe());
	}

	public void setRechnungen() {
		rechnungen.removeAllItems();
		for (Map.Entry e : rechmap.entrySet()) {
			rechnungen.addItem((String) e.getKey());
		}
		rechnungen.setSelectedItem(verlauf.getSelectedItem());
	}

	public String getAusgabe(Term term, String art) {

		String ausgabe = "";
		if (term != null) {

			ausgabe += "Rechnung: \n";
			ausgabe += term.getEingabe() + "\n";

			ausgabe += "= " + term.getErgebnis().getZahl(art) + "\n";

			

			al = term.getAusgabeliste();
			
			if (al.length > 0 && rechenschritte) {
				ausgabe += "\nRechenschritte:\n";

				for (int vi = 0; vi < al.length && al.length > 0; vi++) {
					TermA a = al[vi];
					ausgabe += vi + 1 + ". ";

					if (a.getZ1().getZahl(art).substring(0, 1).equals("-")) {
						ausgabe += "(" + a.getZ1().getZahl(art) + ") ";
					} else {
						ausgabe += " " + a.getZ1().getZahl(art);
					}

					ausgabe += " " + a.getRz() + " ";

					if (a.getZ2().getZahl(art).substring(0, 1).equals("-")) {
						ausgabe += "(" + a.getZ2().getZahl(art) + ") ";
					} else {
						ausgabe += (" " + a.getZ2().getZahl(art));
					}

					ausgabe += " = " + a.getZe().getZahl(art) + "\n";
				}

			}
			ausgabe += "________________________________________\n";
		}

		return ausgabe;
	}

	public void addEingabe(String str) {
		String ein = text.getText();
		pos = text.getCaretPosition();

		switch (str) {
		case "<":
			if (pos > 0) {
				pos -= 1;
			}
			break;
		case ">":
			if (pos < ein.length()) {
				pos += 1;
			}
			break;

		case "del":
			if (pos > 0) {
				ein = ein.substring(0, pos - 1) + ein.substring(pos);
				text.setText(ein);
				pos -= 1;
			}
			break;
		case "STO":
			varmap.clear();
			var.setText("");
		case "VAR":
		case "KON":
			break;

		case "AC":
			text.setText("");
			erg.setText("");
			ein = "";
			break;

		case "=":
			if (!ein.equals("")) {
				berechneTerm(ein);
				setRechnungen();
				setVarlist();
			}
			break;

		case "(":
			ein = ein.substring(0, pos) + "()" + ein.substring(pos);
			pos += str.length();
			text.setText(ein);
			break;
		case "ANS": // kein break, es soll "A angehängt werden.
			str = "A";
			

		default:
			if (str.matches("\\>[a-z]")) {
				if (!ein.equals("")) {
					ein += str;
					berechneTerm(ein);
					setRechnungen();
					setVarlist();
				}

			} else {
				ein = ein.substring(0, pos) + str + ein.substring(pos);
				pos += str.length();
				text.setText(ein);
			}
		}

		if (pos > ein.length()) {
			pos = ein.length();
		}

		text.setCaretPosition(pos);
		text.requestFocus();
	}
	public Term getTerm() {
		return term;
	}
	public String getArt() {
		return art;
	}
}
