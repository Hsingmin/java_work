/**
* @version : 1.10
* @author : Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class TimerTest{
	public static void main(String[] args){
		TalkingClock clock = new TalkingClock(1000,true);
		clock.start();
		
		//keep program running until user selects OK
		JOptionPane.showMessageDialog(null,"Quit program?");
		System.exit(0);
	}
}

/**
* A clock that prints the time in regular intervals.
*/
class TalkingClock{
	public TalkingClock(int interval,boolean beep){
		this.interval = interval;
		this.beep = beep;
	}
	
	public void start(){
		ActionListener listener = new TimePrinter();
		Timer t = new Timer(interval,listener);
		t.start();
	}
	
	private int interval;
	private boolean beep;
	
	public class TimerPrinter implements ActionListener{
		public void actionPerformed(ActionEvent event){
			Date now = new Date();
			System.out.println("At the tone, the time is " + now);
			if(beep) 
				Toolkit.getDefaultToolkit().beep();
		}
	}
}