/**
* @Description: Source code got from CSDN blog.
* @Modified by Alfred Lee
*	Aug.21st, 2015
*/

//package com.bird.jmf; 
 
import java.awt.BorderLayout; 
import java.awt.Component; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.Panel; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
import java.io.IOException; 
import java.net.MalformedURLException; 
import java.net.URL; 
 
import javax.media.CannotRealizeException; 
import javax.media.ControllerEvent; 
import javax.media.ControllerListener; 
import javax.media.EndOfMediaEvent; 
import javax.media.Manager; 
import javax.media.MediaLocator; 
import javax.media.NoPlayerException; 
import javax.media.Player; 
import javax.media.PrefetchCompleteEvent; 
import javax.media.RealizeCompleteEvent; 
import javax.media.Time; 
 
@SuppressWarnings({ "restriction", "unused" }) 

public class JMFSample implements ControllerListener { 
    public static void main(String[] args) { 
        JMFSample sp = new JMFSample(); 
        sp.play(); 
    } 
        
    public void play(){ 
        f = new Frame("JMF Sample1"); 
        f.addWindowListener(new WindowAdapter() { 
            public void windowClosing(WindowEvent we) { 
                if(player != null) { 
                    player.close(); 
                } 
                System.exit(0); 
            } 
        }); 
        
		f.setSize(500,400); 
        f.setVisible(true); 
        URL url = null; 
        try {  
            url = new URL("http://play.baidu.com/?__m=mboxCtrl.playSong&__a=245925137&__o=song/245925137||playBtn&fr=altg3||www.baidu.com#"); 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        }        
        try { 
            player = Manager.createPlayer(url); 
        } catch (NoPlayerException e1) { 
            e1.printStackTrace(); 
        } catch (IOException e1) { 
            e1.printStackTrace(); 
        }  
 
        player.addControllerListener(this); 
        player.realize(); 
    } 
    
    private int videoWidth = 0; 
    private int videoHeight = 0; 
    private int controlHeight = 30; 
    private int insetWidth = 10; 
    private int insetHeight = 30; 
    
    public void controllerUpdate(ControllerEvent ce) { 
        if (ce instanceof RealizeCompleteEvent) { 
            player.prefetch(); 
        } 
		else if (ce instanceof PrefetchCompleteEvent) { 
            if (visual != null) 
                return; 
 
            if ((visual = player.getVisualComponent()) != null) { 
                Dimension size = visual.getPreferredSize(); 
                videoWidth = size.width; 
                videoHeight = size.height; 
                f.add(visual); 
            } 
			else { 
                videoWidth = 320; 
            } 
             
            if ((control = player.getControlPanelComponent()) != null) { 
                controlHeight = control.getPreferredSize().height; 
                f.add(control, BorderLayout.SOUTH); 
            } 
            
            f.setSize(videoWidth + insetWidth, videoHeight + controlHeight + insetHeight); 
            f.validate(); 
           
            player.start(); 
            mediaPlayer.start(); 
        } 
		else if (ce instanceof EndOfMediaEvent) {  
            player.setMediaTime(new Time(0)); 
            player.start(); 
        } 
    } 
	
	private Player mediaPlayer; 
    private Frame f; 
    private Player player; 
    private Panel panel; 
    private Component visual; 
    private Component control = null; 
} 