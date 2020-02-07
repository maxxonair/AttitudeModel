package main;

import java.awt.BorderLayout;
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
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("3D Model - Breadboard Test Unit");
		frame.setSize(400,400);
		frame.setLayout(new BorderLayout());

		String objectFilePath = FilePaths.modelPath+ "millenium-falcon.obj";
		
		//System.out.println(objectFilePath);
		
		AttitudeFrame panel = new AttitudeFrame(objectFilePath);
		panel.getMainPanel().setPreferredSize(new Dimension(1200,800));
		frame.add(panel.getMainPanel(), BorderLayout.CENTER);
		
		ControlFrame controlFrame = new ControlFrame(panel);
		frame.add(controlFrame.getMainPanel(), BorderLayout.WEST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		frame.pack();
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
	}
}
