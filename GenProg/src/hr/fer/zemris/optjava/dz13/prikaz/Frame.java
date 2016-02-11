package hr.fer.zemris.optjava.dz13.prikaz;

import hr.fer.zemris.optjava.dz13.mapa.Mapa;
import hr.fer.zemris.optjava.dz13.mapa.VrijednostiMape;
import hr.fer.zemris.optjava.dz13.solution.Pozicija;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame extends JFrame{
	private LinkedList<Pozicija> pozicije;
	private int pozicijaPrikaza;
	private Mapa map;
	private Panel pan;
	private JPanel tempPane;
	private JButton but, but2, but3;
	private Thread mojaDretva;
	public Frame(LinkedList<Pozicija> pozicije, Mapa map){
		this.pozicijaPrikaza = 0;
		this.pozicije = pozicije;
		this.map = map;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(512, 600));
		dodajKomponente();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	
	private void dodajKomponente() {
		
		Container framePane = this.getContentPane();
		but = new JButton("Sljedeci korak");
		but2 = new JButton("Automatski");
		but3 = new JButton("Restart");
		
		but3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pan.crtajNormalno=false;
				pan.zaustavi = true;
				pan.pozicijePrikaza = 0;
				pan.map = map.copyOf();
				pan.repaint();
			}
		});
		
		but2.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {

				pan.zaustavi = false;
				mojaDretva = new Thread(new AutomatskiPrikaz(pan));
				mojaDretva.start();
			}
			
		});
		but.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pan.sljedeciKorak();
			}
		});
		but.setVisible(true);
		tempPane = new JPanel();
		tempPane.setVisible(true);
		tempPane.add(but,BorderLayout.EAST);
		tempPane.add(but2,BorderLayout.WEST);
		tempPane.add(but3,BorderLayout.WEST);
		this.getContentPane().add(tempPane, BorderLayout.NORTH);
		
		pan = new Panel(map.copyOf(), pozicije, pozicijaPrikaza);
		pan.setSize(512,512);
		pan.setVisible(true);
		pan.setBackground(Color.white);
		this.getContentPane().add(pan, BorderLayout.CENTER);
		
	}
}
