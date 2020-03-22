package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import utils.FilePaths;


public class Main {

	/**
	 * 
	 * Simple breadboard test unit for visualization of 3D rotations
	 * 
	 * Author Max
	 * @param args
	 */
	//----------------------------------------------------------------------------------------
	// GUI elements background color 
	private static Color backgroundColor = new Color(41,41,41);	
	// GUI elements label color 
	private static Color labelColor = new Color(220,220,220);  
	//----------------------------------------------------------------------------------------
	// Chart Frame shown data frequency [Hz]
	private static double plotFrequency = 5.0;
	// Chart Frame shown time scale [s]
	private static int plotHorizon = 60;
	//----------------------------------------------------------------------------------------
	// 3D Model 
	private static String strModel = "millenium-falcon.obj";
	//----------------------------------------------------------------------------------------
	public static void main(String[] args) {
		JFrame frame = new JFrame("3D Model - Breadboard Test Unit");
		frame.setSize(400,400);
		frame.setLayout(new BorderLayout());

		String objectFilePath = FilePaths.modelPath + strModel;
		
		//System.out.println(objectFilePath);
		
		AttitudeFrame panel = new AttitudeFrame(objectFilePath);
		panel.getMainPanel().setPreferredSize(new Dimension(1200,800));
		frame.add(panel.getMainPanel(), BorderLayout.CENTER);
		
		ControlFrame controlFrame = new ControlFrame(panel, labelColor);
		controlFrame.setBackgroundColor(backgroundColor);
		frame.add(controlFrame.getMainPanel(), BorderLayout.WEST);
		
		
		PlotFrame plotFrame = new PlotFrame(controlFrame,  plotHorizon,  plotFrequency);
		frame.add(plotFrame.getMainPanel() , BorderLayout.SOUTH);
		
		MenuBar menuBar = new MenuBar(plotFrame);
		frame.add(menuBar.getMenuBar(), BorderLayout.NORTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		frame.pack();
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
	}
	
	
}
