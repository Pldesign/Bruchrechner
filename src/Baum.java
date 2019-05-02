import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.awt.RenderingHints;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Baum extends JComponent {

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
	private int rzl;
	private int kx = 50;
	
	private int my;
	private int indm;
	private int weite;
	private int r;
	private int h;
	private int hz;
	private int h1;
	private int ha;
	private boolean werte = true;
	private String rzpat = "[\\+\\-\\*\\/\\^\\s]";
	private Graphics2D g2d;
	
	
	
	/**
	 * Create the Component.
	 */
	public Baum(Termrechner rech) {
		this.rech = rech;
		
	}

	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		hz = rech.getHoehe();
		ha = rech.getHoehenabstand();
		werte = rech.getWerte();
		kx = rech.getKx();
		r = rech.getRadius();
		weite = rech.getWeite();
		term = rech.getTerm();
		art = rech.getArt();
		
		FontMetrics fm = g.getFontMetrics();
		

		if (term != null) {

			al = term.getAusgabeliste();
			rl = term.getRl();
			
			h= fm.getAscent();
			h1 = 2*h+ha;
			
			
			int bx =(rl.length) * kx;
			int by =(al.length+2)* 2 * h1; 
			this.setSize(new Dimension(bx , by));
			this.setPreferredSize(new Dimension(bx, by));
			BufferedImage  image = new BufferedImage(bx,by, BufferedImage.TYPE_INT_RGB);
			
            g2d = (Graphics2D) image.getGraphics();
            g2d.setFont(g.getFont());
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                  RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.black);
       
       	
			for (int i = 1; i < rl.length-1;  i++) {
				z1l = fm.stringWidth(rl[i]);
				g2d.drawString(rl[i], i * kx - z1l / 2, h1);
			}
				
			
			if (al.length > 0) {
					
				for (int vi = 0; vi < al.length && al.length > 0; vi++) {
					a = al[vi];
					boolean ergebnis = false;
					
					my = vi * 2* h1 + 2*h1;

					if (a.getInd2() > indm) {
						indm = a.getInd2();
					}
					
					if (werte) {
						z1 = a.getZ1().getZahl(art);
						z2 = a.getZ2().getZahl(art);
					} else {
						if (rl[a.getInd1()].matches(rzpat)) {
							z1 = " ";
						} else {
							z1 = rl[a.getInd1()];
						}
						if (rl[a.getInd2()].matches(rzpat)) {
							z2 = " ";
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
					
					rzl = fm.stringWidth(a.getRz());
					
					g2d.drawString(z1, a.getInd1() * kx - z1l / 2, my);
					g2d.drawString(z2, a.getInd2() * kx - z2l / 2, my);
					g2d.drawString(a.getRz(), a.getInd() * kx - rzl/2, my + h1);
					
					g2d.drawRect(a.getInd1() * kx - z1l / 2 - weite, my - h-hz, z1l + 2*weite, h+2*hz);
					g2d.drawRect(a.getInd2() * kx - z2l / 2 - weite, my - h-hz, z2l + 2*weite, h+2*hz);
					g2d.drawOval(a.getInd() * kx -h/2 - r, my +  h1 - h -r , h+2*r, h+2*r);
					
				//senkrecht
					g2d.drawLine(a.getInd1() * kx, my +  hz, a.getInd1() * kx, my + h1- h/2);
					g2d.drawLine(a.getInd2() * kx, my +  hz, a.getInd2() * kx, my + h1- h/2);
				//waagrecht
					g2d.drawLine(a.getInd1() * kx, my + h1 - h/2 , a.getInd() * kx -h/2- r, my + h1 - h/2);
					g2d.drawLine(a.getInd2() * kx, my + h1 - h/2 , a.getInd() * kx +h/2+ r, my + h1 - h/2);
				//von rz zur nächsten Box
					g2d.drawLine(a.getInd() * kx, my + h1 + r , a.getInd() * kx, my + i *2* h1 -  h-hz);
					
					
					if (werte) {
						z1 = a.getZe().getZahl(art);
					} else {
						z1 = " ";
					}
					z1l = fm.stringWidth(z1);

				}

				g2d.drawString(z1, a.getInd() * kx - z1l / 2, my + 2 *h1);
				g2d.drawRect(a.getInd() * kx - z1l / 2 - weite, my + 2 *h1 - h-hz, z1l + 2*weite, h+2*hz);
				
			}
			rech.setBaumbild(image);
			g.drawImage(image, 0,0 , null);
		}

	}

}
