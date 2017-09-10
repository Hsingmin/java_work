import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;

/**
* This applet displays a greeting from the authors.
* @version : 1.22 
* @author : Alfred Lee
*/

public class WelcomeApplet extends JApplet{
	public void init(){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				setLayout(new BorderLayout());
				
				JLabel label = new JLabel(getParameter("greeting"),SwingConstants.CENTER);
				label.setFont(new Font("Serif",Font.BOLD,18));
				add(label,BorderLayout.CENTER);
				
				JPanel panel = new JPanel();
				
				JButton alfButton = new JButton("Alfred Lee");
				alfButton.addActionListener(makeAction("http://http://www.renren.com/235714616"));
				panel.add(alfButton);
				
				JButton hsButton = new JButton("Hsingmin Lee");
				hsButton.addActionListener(makeAction("http://user.qzone.qq.com/845781042"));
				panel.add(hsButton);
				
				add(panel,BorderLayout.SOUTH);
			}
		});
	}
	
	private ActionListener makeAction(final String urlString){
		return new ActionListener(){
			public void actionPerformed(ActionEvent event){
				try{
					getAppletContext().showDocument(new URL(urlString));
				}
				catch (MalformedURLException e){
					e.printStackTrace();
				}
			}
		};
	}
}