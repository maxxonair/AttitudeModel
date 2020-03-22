package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javaFx.AttitudeFx;
import javafx.application.Platform;
import utils.GuiComponents;
import utils.Mathbox;
import utils.Quaternion;
import utils.Vector3;

public class ControlFrame {

	private JPanel mainPanel;
	
	public static Font smallFont			  = new Font("Verdana", Font.LAYOUT_LEFT_TO_RIGHT, 10);
	
	private Quaternion q;		// Input quaternion vector
	private Quaternion qN;		// Normalized quaternion vector 
	
	private Vector3 Euler;
	
	JLabel nqw;
	JLabel nqy;
	JLabel nqx;
	JLabel nqz ;
	
	 JLabel labelEuler1 ;
	 JLabel labelEuler2 ;
	 JLabel labelEuler3 ;
	 
	 
	    public static DecimalFormat numberFormat =  new DecimalFormat("##.###");
	    public static DecimalFormat quatFormat =  new DecimalFormat("#.#####");
	
	AttitudeFx aFrame;
	AttitudeFrame attitudeFrame;
	
	private Color backgroundColor = Color.gray;
	@SuppressWarnings("unused")
	private Color labelColor = Color.gray;
	
	public ControlFrame(AttitudeFrame frame, Color labelColor) {
		this.aFrame = frame.getSpaceShipView();
		this.attitudeFrame = frame; 
		this.labelColor=labelColor;
		
		q = new Quaternion(1,0,0,0);
		qN = new Quaternion(1,0,0,0);
		Euler = new Vector3(0,0,0);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4,5));
		mainPanel.setBackground(backgroundColor);
		mainPanel.setPreferredSize(new Dimension(420,450));
		
		JLabel labelQw = new JLabel(""+q.w);
		labelQw.setForeground(labelColor);
		labelQw.setSize(40, 40);
		JLabel labelQx = new JLabel(""+q.x);
		JLabel labelQy = new JLabel(""+q.y);
		JLabel labelQz = new JLabel(""+q.z);
		 nqw = new JLabel("qw= "+q.w/getQNorm());
		 nqw.setForeground(labelColor);
		 nqx = new JLabel("qx= "+q.x/getQNorm());
		 nqx.setForeground(labelColor);
		 nqy = new JLabel("qy= "+q.y/getQNorm());
		 nqy.setForeground(labelColor);
		 nqz = new JLabel("qz= "+q.z/getQNorm());
		 nqz.setForeground(labelColor);
		 
		  labelEuler1 = new JLabel("E1[deg]= 0");
		  labelEuler1.setForeground(labelColor);
		  labelEuler2 = new JLabel("E2[deg]= 0");
		  labelEuler2.setForeground(labelColor);
		  labelEuler3 = new JLabel("E3[deg]= 0");
		  labelEuler3.setForeground(labelColor);
		  
		 int sliderLength=80;
		 int sliderMin = -50;
		 int sliderMax = 50;
		JSlider sliderQw = GuiComponents.getGuiSlider(smallFont, sliderLength, sliderMin, sliderMax);
		sliderQw.setValue((int) q.w*10); 
		sliderQw.setForeground(labelColor);
		sliderQw.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double newValue = ((double) sliderQw.getValue())/10;
				q.w = newValue;
				labelQw.setText(""+newValue);
				updateQNormalized();
			}
			
		});
		
		JSlider sliderQx = GuiComponents.getGuiSlider(smallFont, sliderLength, sliderMin, sliderMax);
		sliderQx.setValue((int) q.x*10); 
		sliderQx.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double newValue = ((double) sliderQx.getValue())/10;
				q.x = newValue;
				labelQx.setText(""+newValue);
				updateQNormalized();
			}
			
		});
		
		
		JSlider sliderQy = GuiComponents.getGuiSlider(smallFont, sliderLength, sliderMin, sliderMax);
		sliderQy.setValue((int) q.y*10); 
		sliderQy.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double newValue = ((double) sliderQy.getValue())/10;
				q.y = newValue;
				labelQy.setText(""+newValue);
				updateQNormalized();
			}
			
		});
		
		
		JSlider sliderQz = GuiComponents.getGuiSlider(smallFont, sliderLength, sliderMin, sliderMax);
		sliderQz.setValue((int) q.z*10); 
		sliderQz.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double newValue = ((double) sliderQz.getValue())/10;
				q.z = newValue;
				labelQz.setText(""+newValue);
				updateQNormalized();
			}
			
		});
		
		  sliderLength=80;
		  sliderMin = -1800;
		  sliderMax = 1800;
		JSlider sliderE1 = GuiComponents.getGuiSlider(smallFont, sliderLength, sliderMin, sliderMax);
		sliderE1.setValue((int) Euler.x*10); 
		sliderE1.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double newValue = ((double) sliderE1.getValue())/10;
				Euler.x = newValue;
				labelEuler1.setText("E1[deg]= "+newValue);
				updateEuler();
			}
			
		});
		
		JSlider sliderE2 = GuiComponents.getGuiSlider(smallFont, sliderLength, sliderMin, sliderMax);
		sliderE2.setValue((int) Euler.y*10); 
		sliderE2.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double newValue = ((double) sliderE2.getValue())/10;
				Euler.y = newValue;
				labelEuler2.setText("E2[deg]= "+newValue);
				updateEuler();
			}
			
		});
		
		JSlider sliderE3 = GuiComponents.getGuiSlider(smallFont, sliderLength, sliderMin, sliderMax);
		sliderE3.setValue((int) Euler.z*10); 
		sliderE3.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double newValue = ((double) sliderE3.getValue())/10;
				Euler.z = newValue;
				labelEuler3.setText("E3[deg]= "+newValue);
				updateEuler();
			}
			
		});
		
		
		mainPanel.add(sliderQw);
		mainPanel.add(nqw);
		mainPanel.add(sliderE1);
		mainPanel.add(labelEuler1);
		
		mainPanel.add(sliderQx);
		mainPanel.add(nqx);
		mainPanel.add(sliderE2);
		mainPanel.add(labelEuler2);
		
		mainPanel.add(sliderQy);
		mainPanel.add(nqy);
		mainPanel.add(sliderE3);
		mainPanel.add(labelEuler3);
		
		mainPanel.add(sliderQz);
		mainPanel.add(nqz);

	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	private double getQNorm() {
		return Math.sqrt(q.w*q.w + q.x*q.x + q.y*q.y + q.z*q.z);
	}
	
	private void updateQNormalized(){
		 nqw.setText("qw= "+quatFormat.format(q.w/getQNorm()));
		 nqx.setText("qx= "+quatFormat.format(q.x/getQNorm()));
		 nqy.setText("qy= "+quatFormat.format(q.y/getQNorm()));
		 nqz.setText("qz= "+quatFormat.format(q.z/getQNorm()));
		 
		 qN.w = q.w/getQNorm();
		 qN.x = q.x/getQNorm();
		 qN.y = q.y/getQNorm();
		 qN.z = q.z/getQNorm();
		 
		 Quaternion quat = new Quaternion(qN.w, qN.x, qN.z, qN.y);
		 
		double[][] qVec = {{qN.w},{qN.x},{qN.y},{qN.z}};
		double[][] EulerAngle = Mathbox.Quaternions2Euler(qVec);
		
		Euler.x=EulerAngle[0][0];
		Euler.x=EulerAngle[1][0];
		Euler.x=EulerAngle[2][0];
		
		labelEuler1.setText("E1[deg]= "+numberFormat.format(Math.toDegrees(EulerAngle[0][0])));
		labelEuler2.setText("E2[deg]= "+numberFormat.format(Math.toDegrees(EulerAngle[1][0])));
		labelEuler3.setText("E3[deg]= "+numberFormat.format(Math.toDegrees(EulerAngle[2][0])));
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				aFrame.modelRotation(quat);	
			}
		
		});
	}
	
	public void updateEuler() {
		
		 qN = Mathbox.Euler2Quarternions(Euler);
		
		 nqw.setText("qw= "+quatFormat.format(qN.w/getQNorm()));
		 nqx.setText("qx= "+quatFormat.format(qN.x/getQNorm()));
		 nqy.setText("qy= "+quatFormat.format(qN.y/getQNorm()));
		 nqz.setText("qz= "+quatFormat.format(qN.z/getQNorm()));
		 
		 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					aFrame.modelRotation(qN);	
				}
			
			});
		 
	}

	public Quaternion getqN() {
		//updateQNormalized();
		return qN;
	}	
	
	

	public Vector3 getEuler() {
		return Euler;
	}

	public void setLabelColor(Color labelColor) {
		this.labelColor = labelColor;
			 nqw.setForeground(labelColor);
			 nqx.setForeground(labelColor);
			 nqy.setForeground(labelColor);
			 nqz.setForeground(labelColor);
		  labelEuler1.setForeground(labelColor);
		  labelEuler2.setForeground(labelColor);
		  labelEuler3.setForeground(labelColor);
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		this.mainPanel.setBackground(backgroundColor);
	}	
	
}