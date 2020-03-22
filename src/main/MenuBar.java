package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;


public class MenuBar {

	//-------------------------------------------------------------------------------------------------------------
	
	private JMenuBar menuBar;
	//-------------------------------------------------------------------------------------------------------------
	// Formatting valules (Fonts, Borders, decimalLayouts etc):	
	
    private Color labelColor;
    
	Font smallFont			  = new Font("Verdana", Font.LAYOUT_LEFT_TO_RIGHT, 10);
    //-------------------------------------------------------------------------------------------------------------
    // Global GUI components:
	
	//-------------------------------------------------------------------------------------------------------------
    // Content Lists 

    //--------------
	
	public MenuBar(PlotFrame plotFrame ) {
		
       menuBar = new JMenuBar();
	   menuBar.setOpaque(true);
	   menuBar.setPreferredSize(new Dimension(1200, 25));
       
       //Build the first menu.
       JMenu mainMenu = new JMenu("Main");
       mainMenu.setOpaque(true);
    		mainMenu.setBackground(labelColor);
       mainMenu.setFont(smallFont);
       mainMenu.setMnemonic(KeyEvent.VK_A);
       // mainMenu.setIcon(icon_BlueBook);
       menuBar.add(mainMenu);
       
       JMenu menuItem_01 = new JMenu("Select Plot                  "); 
       menuItem_01.setForeground(Color.gray);
       menuItem_01.setFont(smallFont);
       mainMenu.add(menuItem_01);       
       
       menuItem_01.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
               	   
                 		
                   } });
       mainMenu.addSeparator();
       
       ButtonGroup group_sc = new ButtonGroup();
       
       JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem("Show Quaternions");
       menuItem.setForeground(labelColor);
       menuItem.setFont(smallFont);
       menuItem.setSelected(true);
       menuItem_01.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
               	   // action 
                	  plotFrame.setPlotOption(1);
                   } });
       group_sc.add(menuItem);
       menuItem_01.add(menuItem);

       menuItem = new JRadioButtonMenuItem("Show Euler Angles");
       menuItem.setForeground(labelColor);
       menuItem.setFont(smallFont);
       menuItem_01.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
               	   //  action
                	  plotFrame.setPlotOption(2);
                   } });
       group_sc.add(menuItem);
       menuItem_01.add(menuItem);

	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
	
}
