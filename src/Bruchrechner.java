import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.GridLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.border.CompoundBorder;
import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.UIManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import javax.swing.JRadioButton;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Bruchrechner extends JFrame {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private String erglist = "";
	private JTextArea erg;
	private JComboBox<String> comboBox;
	private JMenuBar menuBar;
	private JMenu mnOptions;
	private JMenuItem menuListe;
	private JMenuItem menuTastatur;
	private JPanel panel;
	private JScrollPane scrollPane_1;
	private JPanel panel_1;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;
	private JButton button_6;
	private JButton button_7;
	private JButton button_8;
	private JButton button_9;
	private JButton button_10;
	private JButton button_11;
	private JButton button_12;
	private JButton button_13;
	private JButton button_14;
	private JButton button_15;
	private JButton button_16;
	private JButton button_17;
	private JButton button_18;
	private JButton button_19;
	private JButton button_20;
	private JButton button_21;
	private JButton button_22;
	private JButton button_23;
	private JButton button_24;
	private JButton button_25;
	private JButton button_26;
	private JButton button_27;
	private JTextArea text;
	private boolean tasten = true;
	private boolean rechenschritte = true;
	private String lerg = " ";
	private JMenuItem menuRechenschritte;
	private JMenuItem menuErgebnisliste;
	private JSlider nk;
	private JComboBox ra;
	private JLabel lblAnzahlNk;
	private JPanel panel_3;
	private JLabel lblLetzteRechnungen;
	private JPanel panel_4;
	private JMenu mnFile;
	private JMenuItem mntmVerlaufSichern;
	private JMenuItem mntmRechnungenSichern;
	private JMenuItem mntmVerlaufLesen;
	private JMenuItem mntmRechnungenLesen;
	final JFileChooser fc = new JFileChooser();
	private JMenuItem mntmAlleRechnungenAusfhren;
	private JCheckBox rz;

	private String delkom = "[K]"; // Kommentar in der Eingabe
	private String delra = "[R]"; // Rundungsformat in der Eingabe

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bruchrechner frame = new Bruchrechner();
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
	public Bruchrechner() {
		setTitle("Bruchrechner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);

		FileFilter filter = new FileNameExtensionFilter("Text", "txt");
		fc.setFileFilter(filter);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

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
				fc.setSelectedFile(new File("rechnungen.txt"));
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					comboBox.removeAllItems();
					List<String> lines = Collections.emptyList();
					try {
						lines = Files.readAllLines(Paths.get(fc.getSelectedFile().getPath()), StandardCharsets.UTF_8);
						comboBox.removeAllItems();
						for (String str : lines) {
							comboBox.addItem(str);
						}

					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}

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
					for (int i = 0; i < comboBox.getItemCount(); i++)
						erg.append(comboBox.getItemAt(i) + "\n");
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
				comboBox.removeAllItems();
			}
		});

		mntmAlleRechnungenAusfhren = new JMenuItem("alle Rechnungen ausf\u00FChren");
		mntmAlleRechnungenAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < comboBox.getItemCount(); i++) {
					text.setText(comboBox.getItemAt(i));
					berechnen();
				}
			}
		});
		mnOptions.add(mntmAlleRechnungenAusfhren);
		mnOptions.add(menuListe);

		menuTastatur = new JMenuItem("Tastatur ausblenden");
		menuTastatur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tasten) {
					menuTastatur.setText("Tastatur einblenden");
					panel_1.setVisible(false);
					tasten = false;
				} else {
					menuTastatur.setText("Tastatur ausblenden");
					panel_1.setVisible(true);
					tasten = true;
				}
			}
		});

		menuRechenschritte = new JMenuItem("Rechenschritte ausblenden");
		menuRechenschritte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rechenschritte) {
					menuRechenschritte.setText("Rechenschritte einblenden");
					rechenschritte = false;
				} else {
					menuRechenschritte.setText("Rechenschritte ausblenden");
					rechenschritte = true;
				}
			}
		});

		menuErgebnisliste = new JMenuItem("Verlauf anzeigen");
		menuErgebnisliste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				erg.setText(erglist);
			}
		});
		mnOptions.add(menuErgebnisliste);
		mnOptions.add(menuRechenschritte);
		mnOptions.add(menuTastatur);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(
				new TitledBorder(null, "Eingabe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(scrollPane_1, BorderLayout.NORTH);
		text = new JTextArea();
		text.setFont(new Font("Monospaced", Font.PLAIN, 17));
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					berechnen();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					textein("d");
				}
			}
		});

		text.setRows(3);
		text.requestFocus();
		scrollPane_1.setViewportView(text);

		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(
				new TitledBorder(null, "Ausgabe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		erg = new JTextArea();
		erg.setToolTipText("");
		erg.setEditable(false);
		erg.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(erg);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 5, 0, 0));

		button = new JButton("1");
		button.setFont(new Font("Tahoma", Font.BOLD, 15));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("1");
			}
		});
		panel_1.add(button);

		button_1 = new JButton("2");
		button_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("2");
			}
		});

		panel_1.add(button_1);

		button_2 = new JButton("3");
		button_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("3");
			}
		});

		panel_1.add(button_2);

		button_3 = new JButton("del");
		button_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_3.setToolTipText("letzte Ziffer vor Cursor l\u00F6schen");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("d");
			}
		});
		panel_1.add(button_3);

		button_27 = new JButton("\\");
		button_27.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_27.setToolTipText("Kommentar hinzügen");
		button_27.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("\\");
			}
		});
		panel_1.add(button_27);

		button_5 = new JButton("4");
		button_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("4");
			}
		});

		panel_1.add(button_5);

		button_6 = new JButton("5");
		button_6.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("5");
			}
		});

		panel_1.add(button_6);

		button_7 = new JButton("6");
		button_7.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("6");
			}
		});

		panel_1.add(button_7);

		button_8 = new JButton("*");
		button_8.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("*");
			}
		});

		panel_1.add(button_8);

		button_9 = new JButton("/");
		button_9.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("/");
			}
		});

		panel_1.add(button_9);

		button_10 = new JButton("7");
		button_10.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("7");
			}
		});

		panel_1.add(button_10);

		button_11 = new JButton("8");
		button_11.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("8");
			}
		});

		panel_1.add(button_11);

		button_12 = new JButton("9");
		button_12.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("9");
			}
		});

		panel_1.add(button_12);

		button_13 = new JButton("+");
		button_13.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("+");
			}
		});

		panel_1.add(button_13);

		button_14 = new JButton("-");
		button_14.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("-");
			}
		});
		panel_1.add(button_14);

		button_15 = new JButton("<");
		button_15.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_15.setToolTipText("Cursor nach links");
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("<");
			}
		});
		panel_1.add(button_15);

		button_16 = new JButton("0");
		button_16.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("0");
			}
		});
		panel_1.add(button_16);

		button_17 = new JButton(">");
		button_17.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_17.setToolTipText("Cursor nach rechts");
		button_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein(">");
			}
		});
		panel_1.add(button_17);

		button_18 = new JButton("(");
		button_18.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("()");
				textein("<");
			}
		});
		panel_1.add(button_18);

		button_19 = new JButton(")");
		button_19.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein(")");
			}
		});
		panel_1.add(button_19);

		button_20 = new JButton("_");
		button_20.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_20.setToolTipText("Brucheingabe 1_2|5");
		button_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("_");
			}
		});
		panel_1.add(button_20);

		button_21 = new JButton("|");
		button_21.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_21.setToolTipText("Brucheingabe 1|4");
		button_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("|");
			}
		});
		panel_1.add(button_21);

		button_22 = new JButton(".");
		button_22.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_22.setToolTipText("Dezimalb\u00FCche  2.35");
		button_22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein(".");
			}
		});
		panel_1.add(button_22);

		button_26 = new JButton("E");
		button_26.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_26.setToolTipText("10er Potenzschreibweise f\u00FCr Dezimalbr\u00FCche 2.5E-5");
		button_26.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("E");
			}
		});
		panel_1.add(button_26);

		button_23 = new JButton("^");
		button_23.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_23.setToolTipText("Potenzen    2^3");
		button_23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textein("^");
			}
		});
		panel_1.add(button_23);

		panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));

		button_4 = new JButton("AC");
		panel_4.add(button_4);
		button_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_4.setToolTipText("Eingabe l\u00F6schen");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text.setText("");
				text.requestFocus();
			}
		});

		button_24 = new JButton("ANS");
		button_24.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_24.setToolTipText("letztes Ergebnis");
		panel_4.add(button_24);
		button_24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lerg.substring(0, 1).equals("-")) {
					textein("(" + lerg + ")");
				} else {
					textein(lerg);
				}
			}
		});

		button_25 = new JButton("      =       ");
		panel_4.add(button_25);
		button_25.setSize(50, 50);
		button_25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				berechnen();
			}
		});

		panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);

		lblLetzteRechnungen = new JLabel("Rechnungen: ");
		lblLetzteRechnungen.setFont(new Font("Tahoma", Font.PLAIN, 15));

		lblAnzahlNk = new JLabel("Ausgabe :");
		lblAnzahlNk.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAnzahlNk.setHorizontalAlignment(SwingConstants.CENTER);

		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				text.setText((String) comboBox.getSelectedItem());
				text.requestFocus();

			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"2_8|6     K Bruchdarstellung (RB)", "13_2|3     RG3 K Runden auf 3 geltende Ziffern", "13_2|3     RN3 K Runden auf 3 Nachkommastellen", ".5E-15     RG4 K Dezimalbr\u00FCche mit 10er Potenzen", "2(2+3)(5-3)6     RB K Malpunkte kann man bei Klammern weglassen", "2^(1/2)     RN3 K bei rationale Exponenten muss man runden"}));

		JLabel lblAnzahlStellen = new JLabel("Anzahl Stellen");
		lblAnzahlStellen.setFont(new Font("Tahoma", Font.PLAIN, 15));

		ra = new JComboBox();
		ra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ra.getSelectedIndex() == 0)
					nk.setValue(0);
			}
		});

		ra.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ra.setModel(
				new DefaultComboBoxModel(new String[] {"Bruchdarstellung (RB)", "Nachkommastellen (RNn)", "geltende Ziffern (RGn)"}));

		nk = new JSlider();
		nk.setMinorTickSpacing(1);
		nk.setMajorTickSpacing(5);
		nk.setSnapToTicks(true);
		nk.setValue(0);
		nk.setToolTipText("Bei Bruchdarstellung ist die Anzahl Null");
		nk.setPaintTicks(true);
		nk.setPaintLabels(true);
		nk.setMaximum(10);

		rz = new JCheckBox("Rundung  \u00FCberschreiben");
		rz.setToolTipText("Kommentare werden gel\u00F6scht!");
		rz.setFont(new Font("Tahoma", Font.PLAIN, 15));

		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAnzahlNk)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(ra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAnzahlStellen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(nk, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rz)
					.addGap(37))
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(5)
					.addComponent(lblLetzteRechnungen)
					.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(478))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAnzahlNk)
								.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
									.addComponent(ra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblAnzahlStellen)))
							.addGap(19))
						.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
							.addComponent(rz, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(nk, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLetzteRechnungen)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(14))
		);
		panel_3.setLayout(gl_panel_3);

	}

	private void textein(String str) {
		switch (str) {
		case "<":
			if (text.getCaretPosition() > 0) {
				text.setCaretPosition(text.getCaretPosition() - 1);
			}
			break;
		case ">":
			if (text.getCaretPosition() < text.getText().length()) {
				text.setCaretPosition(text.getCaretPosition() + 1);
			}
			break;

		case "d":
			if (text.getCaretPosition() > 0) {
				try {
					String str1 = text.getText(0, text.getCaretPosition() - 1);
					String str2 = text.getText(text.getCaretPosition(),
							text.getText().length() - text.getCaretPosition());
					int i = text.getCaretPosition();
					text.setText(str1 + str2);
					text.setCaretPosition(i - 1);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		default:
			text.insert(str, text.getCaretPosition());
		}
		text.requestFocus();
	}

	private void berechnen() {

		String bstr = text.getText();
		if (!bstr.equals("")) {
			erg.setText("");
			String rech = "";

			String[] textkom = bstr.split(delkom);
			String[] textra = textkom[0].split(delra);
			

			if (rz.isSelected() || textra.length == 1) {

				switch (ra.getSelectedIndex()) {
				case 0:
					rech = "RB";
					break;
				case 1:
					rech = "RN" + nk.getValue();
					break;
				case 2:
					rech = "RG" + nk.getValue();
					break;
				}
				bstr = textra[0].trim() + "     " + rech;

			}

			erg.append("Rechnung: \n");
			erg.append(bstr + "\n");
			try {
				Rechner rechner = new Rechner(bstr);
				lerg = rechner.getErgebnis();
				erg.append("= " + lerg + "\n\n");
				String[][] rl = rechner.getRl();
				int[][] al = rechner.getAl();

				if (rechenschritte && al.length > 0) {
					erg.append("Rechenschritte:\n");

					for (int vi = 0; vi < al.length && al.length > 0; vi++) {
						erg.append(vi + 1 + ". ");
						if (rl[al[vi][1]][1].substring(0, 1).equals("-")) {
							erg.append("(" + rl[al[vi][1]][1] + ") ");
						} else {
							erg.append(" " + rl[al[vi][1]][1]);
						}

						erg.append(" " + rl[al[vi][0]][0] + " ");

						if (rl[al[vi][2]][1].substring(0, 1).equals("-")) {
							erg.append(" (" + rl[al[vi][2]][1] + ")");

						} else {
							erg.append(" " + rl[al[vi][2]][1]);
						}

						erg.append(" = " + rl[al[vi][0]][1] + "\n");
					}

				}

				boolean item = true;
				for (int i = 0; i < comboBox.getItemCount(); i++) {

					if (bstr.equals(comboBox.getItemAt(i))) {
						item = false;
					}
				}
				if (item) {
					comboBox.addItem(bstr);
					comboBox.setSelectedIndex(comboBox.getItemCount() - 1);
				}

				text.setText("");

			} catch (Exception e1) {
				String[] ftxt = e1.getMessage().split("\\:");
				erg.append(e1.getMessage() + "\n\n");
				if (ftxt[0].equals("Syntax-Fehler Pos")) {
					text.setCaretPosition(Integer.parseInt(ftxt[1]));
				}
			}

			erglist += erg.getText();
			erglist += "________________________\n";
			text.requestFocus();
		}
	}
}
