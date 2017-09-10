package filetrans;

import java.awt.EventQueue;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.lang.Integer;

/**
* @author : Alfred Lee
* @date : Sept. 24th, 2015
*/

public class SyncCodeClient{
	public static void main(String [] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				JFrame frame = new SyncCodeClientFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}	


class SyncCodeClientFrame extends JFrame{
	public SyncCodeClientFrame(){
		setTitle("SyncCode");
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
	
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
	
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
	
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
	
		JMenuItem openItem = new JMenuItem("Open");
		menu.add(openItem);
		openItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				int result = chooser.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION){
					path = chooser.getSelectedFile().getPath();
					//opration except getting file path

				}
			}
		});
		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});

		final JTextField ipField = new JTextField();
		final JTextField portField = new JTextField();
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2, 2));
		northPanel.add(new JLabel("IP:", SwingConstants.RIGHT));
		northPanel.add(ipField);
		northPanel.add(new JLabel("Port:", SwingConstants.RIGHT));
		northPanel.add(portField);
		add(northPanel, BorderLayout.NORTH);

		displayArea = new JTextArea(ROWS, COLUMNS);
		JPanel displayPanel = new JPanel();
		displayPanel.add(displayArea);
		add(displayPanel, BorderLayout.CENTER);

		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				//create socket here
				try{
					
					String ip = ipField.getText();
					String portNum = portField.getText();
					int port = Integer.parseInt(portNum.trim());
					s = new Socket();
					s.connect(new InetSocketAddress(ip, port), timeout);
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}		
		});
		JButton synchronizeButton = new JButton("Synchronize");
		synchronizeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				//create the synchronization thread here
				Runnable r = new SyncCodeRunnable(s, path);
				Thread rt = new Thread(r);
				rt.start();
			}		
		});
		JPanel southPanel = new JPanel();
		southPanel.add(connectButton);
		southPanel.add(synchronizeButton);
		add(southPanel, BorderLayout.SOUTH);
	}

	protected final JTextArea displayArea;
	protected String path;
	protected Socket s;
	private JFileChooser chooser;
	private static final int timeout = 5000;
	private static final int ROWS = 8;
	private static final int COLUMNS = 20;	
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
}

