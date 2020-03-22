package main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import realTimePlot.RealTimePlotElement;
import utils.Quaternion;
import utils.Vector3;

public class PlotFrame {
	private JPanel mainPanel;
	
	//public static Font smallFont	 = new Font("Verdana", Font.LAYOUT_LEFT_TO_RIGHT, 10);

	private Quaternion qN;		// Normalized quaternion vector 
	private Vector3 euler;
	private ControlFrame contrlFrame ; 
	private RealTimePlotElement realtimePlotElement;

	private boolean inputListener=true;
	private boolean plotThreadRun=true;
	
	private double plotFrequency;
	
	ThreadInputRead plotThread;
	//----------------------------------------------------------------------------------------
	private int plotOption=2;
	// Plot option: 
	// 1 - plot quaternions
	// 2 - plot euler angles 
	//----------------------------------------------------------------------------------------

	public PlotFrame(ControlFrame contrlFrame, int plotHorizon, double plotFrequency) {
		this.contrlFrame = contrlFrame;
		this.plotFrequency=plotFrequency;
		
		qN = new Quaternion(1,0,0,0);
		
		int globalX = 1200;
		int globalY = 250;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(globalX,globalY));
		  
		 if(inputListener) {
			  plotThread=new ThreadInputRead();  
           plotThread.start();
		 }
		 
		 realtimePlotElement = new RealTimePlotElement( plotHorizon, plotFrequency);
		 mainPanel.add(realtimePlotElement.createPlotElement(), BorderLayout.CENTER);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	
	
	class ThreadInputRead extends Thread{  
		public void run(){  
			while(plotThreadRun) {
		    		try {
		    			qN = contrlFrame.getqN();
		    			euler = contrlFrame.getEuler();
		    			//iSystem.out.println(qN.w);
		    			if(plotOption==1) {
		    				realtimePlotElement.updateChart(qN);
		    			} else {
		        	    		realtimePlotElement.updateChart(euler);
		        	    }
		        	    
		        		}  catch (Exception plotExcp) {
		        			System.err.println(plotExcp);
		        		}
		    		
		    		try {
		    			int delayTime = (int) (1/plotFrequency * 1000);
						Thread.sleep(delayTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}

	}


	public void setPlotOption(int plotOption) {
		
		this.plotOption = plotOption;
		
		//plotThread.interrupt();
		plotThreadRun=false;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		plotThreadRun=true;
		
		 if(inputListener) {
			  plotThread=new ThreadInputRead();  
          plotThread.start();
		 }
	}
	
	
}
