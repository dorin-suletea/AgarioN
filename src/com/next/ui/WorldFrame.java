package com.next.ui;/*
	WorldFrame.... the main GUI frame
*/

import com.next.Sim2D;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class WorldFrame extends JFrame implements ActionListener, ItemListener {
	static Sim2D MC;
	static WorldPanel thePanel;
	static MenuBar theMenuBar;
	JLabel simTime,muttonCounter,gluttonCounter;
	CheckboxMenuItem runMenuItem,paintMenuItem,writeJPEGMenuItem,writeInfoMenuItem;
	JPanel infoPanel = new JPanel();
	static int width,height;
	

	public WorldFrame (Sim2D MC,int width, int height) {
		WorldFrame.MC = MC;
		WorldFrame.width = width;
		WorldFrame.height = height;
		this.setTitle ("Sim2D... replace with your simulation name");
		this.setSize (width+10,height+30);
		this.setBackground(Color.black);
		makeMenuBar();
		this.setVisible(true);
		
		
		thePanel = new WorldPanel (width,height, this); 	// panel where the players will be drawn
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add (thePanel, BorderLayout.CENTER);
		thePanel.initialize();
		
		this.setVisible(true);
		
	}
	
	public void showAll() {
		thePanel.clearImage();
		thePanel.drawBoxAroundWorld();
		thePanel.drawAllThings();
		WorldPanel.drawInfoString(Sim2D.getInfoString());
		thePanel.repaint();
	}
	
	public void showInfoOnly() {
		WorldPanel.clearInfoArea();
		WorldPanel.drawInfoString(Sim2D.getInfoString());
		thePanel.repaint();
	}
	
	public void actionPerformed (ActionEvent e) {

	}
	
	public void itemStateChanged( ItemEvent event ) {
		Object src = event.getSource( );

		if ( src == runMenuItem ) {
			Sim2D.running = runMenuItem.getState( );
		}
		
		if ( src == paintMenuItem ) {
			Sim2D.paintOn = paintMenuItem.getState( );
		}
		
		if ( src == writeJPEGMenuItem ) {
			if (writeJPEGMenuItem.getState()) {
				Sim2D.moviePath = FileOps.getDirectoryToSave(" Create folder for JPEG files...",this);
				if (Sim2D.moviePath != null) {
					int lastbitIndex = Sim2D.moviePath.lastIndexOf(File.separator);
					Sim2D.movieFileName = Sim2D.moviePath.substring(lastbitIndex+1);
					Sim2D.movieCounter = Sim2D.movieStep;		// take first JPEG now
					Sim2D.makeMovie = true;
					return;
				}
			}
			writeJPEGMenuItem.setState(false);
			Sim2D.makeMovie = false;
			Sim2D.moviePath = null;
			Sim2D.movieFileName = null;
		}
		
		if ( src == writeInfoMenuItem ) {
			if (writeInfoMenuItem.getState()) {
				String infoPath = FileOps.getDirectoryToSave(" Create folder for Info file...",this);
				if (infoPath != null) {
					int lastbitIndex = infoPath.lastIndexOf(File.separator);
					String infoName = infoPath.substring(lastbitIndex+1);
					FileOps.mkInfoFile(infoPath + File.separator + infoName);
					Sim2D.infoWriteCounter = Sim2D.infoWriteStep;		// write first Info line now
					Sim2D.infoWrite = true;
					return;
				}
			}
			writeInfoMenuItem.setState(false);
			Sim2D.infoWrite = false;
		}
	}
	
	public void makeMenuBar () {
		theMenuBar = new MenuBar();
		this.setMenuBar( theMenuBar );

		// Add menu for program control
		Menu m = new Menu ("Program");
		m.addActionListener(this);
		
		runMenuItem = new CheckboxMenuItem ("Run",Sim2D.running);
		runMenuItem.addItemListener(this);
		m.add(runMenuItem);

		paintMenuItem = new CheckboxMenuItem ("Paint",Sim2D.paintOn);
		paintMenuItem.addItemListener(this);
		m.add(paintMenuItem);
		
		m.add("--------");
		
		writeJPEGMenuItem = new CheckboxMenuItem("Write JPEGs",Sim2D.makeMovie);
		writeJPEGMenuItem.addItemListener(this);
		m.add(writeJPEGMenuItem);
		
		writeInfoMenuItem = new CheckboxMenuItem("Write Info To File",Sim2D.infoWrite);
		writeInfoMenuItem.addItemListener(this);
		m.add(writeInfoMenuItem);
		
		theMenuBar.add(m);

	}
	

}
