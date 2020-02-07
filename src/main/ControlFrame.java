package main;

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
import utils.Vector4;

public class ControlFrame {

	private JPanel mainPanel;
	
	public static Font smallFont			  = new Font("Verdana", Font.LAYOUT_LEFT_TO_RIGHT, 10);
	
	private Vector4 q;		// Input quaternion vector
	private Vector4 qN;		// Normalized quaternion vector 
	
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
	
	public ControlFrame(AttitudeFrame frame) {
		this.aFrame = frame.getSpaceShipView();
		this.attitudeFrame = frame; 
		
		q = new Vector4(1,0,0,0);
		qN = new Vector4(1,0,0,0);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4,4));
		mainPanel.setPreferredSize(new Dimension(450,450));
		
		JLabel labelQw = new JLabel(""+q.w);
		labelQw.setSize(40, 40);
		JLabel labelQx = new JLabel(""+q.x);
		JLabel labelQy = new JLabel(""+q.y);
		JLabel labelQz = new JLabel(""+q.z);
		 nqw = new JLabel("qw= "+q.w/getQNorm());
		 nqx = new JLabel("qx= "+q.x/getQNorm());
		 nqy = new JLabel("qy= "+q.y/getQNorm());
		 nqz = new JLabel("qz= "+q.z/getQNorm());
		 
		  labelEuler1 = new JLabel("");
		  labelEuler2 = new JLabel("");
		  labelEuler3 = new JLabel("");
		
		 int sliderLength=80;
		 int sliderMin = -50;
		 int sliderMax = 50;
		JSlider sliderQw = GuiComponents.getGuiSlider(smallFont, sliderLength, sliderMin, sliderMax);
		sliderQw.setValue((int) q.w*10); 
		sliderQw.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double newValue = ((double) sliderQw.getValue())/10;
				q.w = newValue;
				labelQw.setText(""+newValue);
				updateNormalized();
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
				updateNormalized();
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
				updateNormalized();
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
				updateNormalized();
			}
			
		});
		
		mainPanel.add(sliderQw);
		mainPanel.add(nqw);
		mainPanel.add(labelEuler1);
		
		mainPanel.add(sliderQx);
		mainPanel.add(nqx);
		mainPanel.add(labelEuler2);
		
		mainPanel.add(sliderQy);
		mainPanel.add(nqy);
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
	
	private void updateNormalized(){
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

	public Vector4 getqN() {
		return qN;
	}
	
	
	
	
}
