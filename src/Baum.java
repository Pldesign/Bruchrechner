import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Baum extends JPanel {

	private Termrechner rech;

	private Term term;
	private TermA[] al;
	private String[] rl;

	private TermA a;
	private String art;
	private int z1l;
	private String z1;
	private String z2;
	private int z2l;
	private int kx = 50;
	private int ky = 50;
	private int xpos = 0;
	private int my;
	private int indm;
	private int ind;
	private int h;
	private JCheckBox werte;
	private String rzpat = "[\\+\\-\\*\\/\\^\\s]";
	
	
	/**
	 * Create the panel.
	 */
	public Baum(Termrechner rech) {
		setBackground(Color.WHITE);

		this.rech = rech;
		this.setPreferredSize(new Dimension(100	, 100));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));

		JSlider slider = new JSlider();
		slider.setMinimum(30);
		slider.setValue(40);
		panel.add(slider);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				kx = slider.getValue();
				repaint();
			}
		});
		slider.setMaximum(200);

		werte = new JCheckBox("Werte");
		werte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
		});
		werte.setSelected(true);
		panel.add(werte);
		
		JSlider slider_1 = new JSlider();
		slider_1.setValue(90);
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ky = 120 - slider_1.getValue();
				repaint();
			}
		});
		slider_1.setMinimum(20);
		slider_1.setOrientation(SwingConstants.VERTICAL);
		add(slider_1, BorderLayout.WEST);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		term = rech.getTerm();
		art = rech.getArt();
		FontMetrics fm = g.getFontMetrics();
		

		if (term != null) {

			al = term.getAusgabeliste();
			rl = term.getRl();

			h = fm.getHeight();
			
			ind = 0;
			
			for (int i = 1; i < rl.length-1;  i++) {
				z1l = fm.stringWidth(rl[i]);
				g.drawString(rl[i], i * kx - z1l / 2, 50);
			}
				
			
			
			
			
			if (al.length > 0) {
					
				for (int vi = 0; vi < al.length && al.length > 0; vi++) {
					a = al[vi];
					boolean ergebnis = false;
					
					my = vi * 2 * ky + 100;

					if (a.getInd2() > indm) {
						indm = a.getInd2();
					}
					
					if (werte.isSelected()) {
						z1 = a.getZ1().getZahl(art);
						z2 = a.getZ2().getZahl(art);
					} else {
						if (rl[a.getInd1()].matches(rzpat)) {
							z1 = "      ";
						} else {
							z1 = rl[a.getInd1()];
						}
						if (rl[a.getInd2()].matches(rzpat)) {
							z2 = "      ";
						} else {
							z2 = rl[a.getInd2()];
						}

					}
					int i = 1;
					while ( vi + i < al.length&& !ergebnis) {
						TermA an = al[vi+i];
						if (an.getInd1() == a.getInd()||an.getInd2() == a.getInd()) {
							ergebnis = true;
						} else {
						i++;
						}
					}
					if (!ergebnis) {
						i=1;
					}
						
					
					z1l = fm.stringWidth(z1);
					z2l = fm.stringWidth(z2);
					g.drawString(z1, a.getInd1() * kx - z1l / 2, my);
					g.drawString(z2, a.getInd2() * kx - z2l / 2, my);
					g.drawString(a.getRz(), a.getInd() * kx - h / 5, my + ky);
					g.drawRect(a.getInd1() * kx - z1l / 2 - h / 2, my - h - h / 4, z1l + h, 2 * h);
					g.drawRect(a.getInd2() * kx - z2l / 2 - h / 2, my - h - h / 4, z2l + h, 2 * h);
					g.drawOval(a.getInd() * kx - h / 2, my - 3 * h / 4 + ky, h, h);
					g.drawLine(a.getInd1() * kx, my + 3 * h / 4, a.getInd1() * kx, my + ky - h / 4);
					g.drawLine(a.getInd1() * kx, my + ky - h / 4, a.getInd() * kx - h / 2, my + ky - h / 4);
					g.drawLine(a.getInd2() * kx, my + 3 * h / 4, a.getInd2() * kx, my + ky - h / 4);
					g.drawLine(a.getInd2() * kx, my + ky - h / 4, a.getInd() * kx + h / 2, my + ky - h / 4);
				
					g.drawLine(a.getInd() * kx, my + ky + h / 4, a.getInd() * kx, my + i*2 * ky - 5 * h / 4);
					
					
					if (werte.isSelected()) {
						z1 = a.getZe().getZahl(art);
					} else {
						z1 = "      ";
					}
					z1l = fm.stringWidth(z1);

				}

				g.drawString(z1, a.getInd() * kx - z1l / 2, my + 2 * ky);
				g.drawRect(a.getInd() * kx - z1l / 2 - h / 2, my + 2 * ky - h - h / 4, z1l + h, 2 * h);
				this.setPreferredSize(new Dimension(indm * kx , my + 2 * ky));
				
			}
		}

	}

}
