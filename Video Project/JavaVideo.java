import java.awt.EventQueue.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.media.*;

/**
* @Description : source got form csdn main method lacked.
* @Author : Alfred Lee
* @Date : Aug. 18th, 2015
*/

class JavaVideo extends Applet implements ControllerListener,Runnable,ItemListener
{
	Player player;
	String str;
	Thread mythread;
	Choice choice;
	Component visualCompoment,controlCompoment,progressBar;
	String mediaFile;
	URL mediaURL,codeBase;
	Frame frame;
	public void init()
	{
		str = "music.MPE";
		mythread = new Thread(this);
		choice = new Choice();
		choice.add("music01.MPG");
		choice.add("music02.avi");
		choice.add("music03.avi");
		choice.addItemListener(this);
		frame = new Frame("Movie System");
		frame.setSize(640,480);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				if(player != null)
				{
					player.stop();
					player.deallocate();
				}
				System.exit(0);
			}
		});
		add(choice);
	}
	
	public void stop()
	{
		if(player != null)
		{
			player.stop();
		}
	}
	public synchronized void controllerUpdate(ControllerEvent event)
	{
		player.getDuration();
		if(event instanceof RealizeCompleteEvent)
		{
			if((visualCompoment = player.getVisualComponent()) != null)
			{
				frame.add("Center",visualCompoment);
			}
			if((controlCompoment = player.getControlPanelComponent()) != null)
				if(visualCompoment != null)
					frame.add("South",controlCompoment);
				else
					frame.add("Center",controlCompoment);
				frame.validate();
				frame.pack();
		}
		else if(event instanceof PrefetchCompleteEvent)
		{
			player.start();
		}
	}
	public void itemStateChanged(ItemEvent e)
	{
		str=choice.getSelectedItem();
		if(player == null) {}
		else
		{
			player.stop();
			player.deallocate();
		}
		frame.removeAll();
		frame.setVisible(true);
		frame.setBounds(300,100,150,100);
		frame.validate();
		if(!(mythread.isAlive()))
		{
			mythread = new Thread(this);
		}
		try{
			mythread.start();
		}
		catch(Exception ee) {}
	}
	
	public synchronized void run()
	{
		try{
			mediaURL = new URL(codeBase,str);
			player = Manager.createPlayer(mediaURL);
			player.getDuration();
			if(player != null)
			{
				player.addControllerListener(this);
			}
			else
			{
				System.out.println("failed to creat player for" + mediaURL);
			}
		}
		catch(MalformedURLException e)
		{
			System.out.println("URL for" + mediaFile + "is invalid");
		}
		catch(IOException e)
		{
			System.out.println("URL for" + mediaFile + "is invalid");
		}
		catch(NoPlayerException e)
		{
			System.out.println("Can't find a player" + mediaURL);
		}
		if(player != null)
		{
			player.prefetch();
		}
	}

	public static void main(String[] args){
		new JavaVideo();
	}
}