import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* @version : 1.33
* @author : Cay Horstmann
*/
public class DialogTest{
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				DialogFrame frame = new DialogFrame();
				frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
* Frame with menu whose file->about action shows a dialog.
*/
class DialogFrame extends JFrame{
	public DialogFrame(){
		setTitle("DialogTest");
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		
		//construct a file menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("file");
		menuBar.add(fileMenu);
		
		JMenuItem aboutItem = new JMenuItem("about");
		aboutItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(dialog == null)
					dialog = new AboutDialog(DialogFrame.this);
				dialog.setVisible(true);
			}
		});
		fileMenu.add(aboutItem);
		
		//exit item exits the program
		JMenuItem exitItem = new JMenuItem("exit");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});
		fileMenu.add(exitItem);
	}
	
	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT  = 200;
	
	private AboutDialog dialog;
}

/**
* Sample modal dialog that display a message and wait for user clicking OK button.
*/
class AboutDialog extends JDialog{
	public AboutDialog(JFrame owner){
		super(owner,"About DialogTest",true);
		
		//add HTML label
		add(new JLabel("<html><h1><i>Core Java</i></h1><hr>By Cay Horstmann and Gary Cornell</html>"),
			BorderLayout.CENTER);
			
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				setVisible(false);
			}
		});
		
		JPanel panel = new JPanel();
		panel.add(ok);
		add(panel,BorderLayout.SOUTH);
		
		setSize(250,150);
	}
}