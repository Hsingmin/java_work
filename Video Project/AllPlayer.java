/**
* @Description: A media player based on JMF  
* @Author: Alfred Lee
* @Modified on Aug. 21st, 2015
*/

import java.net.URL;
import java.net.MalformedURLException;

import java.awt.*;
import java.awt.event.*;
import javax.media.*;
import javax.swing.*;


class AllPlayer extends JFrame implements ActionListener, ControllerListener, ItemListener {
	
	public AllPlayer(String title) {
		super(title);
		 
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
			public void windowClosed(WindowEvent e) {
				if (player != null)
					player.close();
				System.exit(0);
			}
		});
		setupMenu();
		setSize(450, 450);
		setVisible(true);
	}
	
	public void setupMenu() {
		
		//menu File
		Menu file = new Menu("File");
		MenuItem openItem = new MenuItem("Open");
		openItem.addActionListener(this);
		file.add(openItem);
		file.addSeparator();
		CheckboxMenuItem loopItem = new CheckboxMenuItem("Loop", false);
		loopItem.addItemListener(this);
		file.add(loopItem);
		file.addSeparator();
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.addActionListener(this);
		file.add(quitItem);
		
		//menu URL
		Menu openurl = new Menu("URL");
		MenuItem input = new MenuItem("Input");
		input.addActionListener(this);
		openurl.add(input);
		openurl.addSeparator();
		
		//menu control
		Menu control = new Menu("Control");
		MenuItem move = new MenuItem("Play");
		move.addActionListener(this);
		control.add(move);
		control.addSeparator();
		MenuItem pause = new MenuItem("Pause");
		pause.addActionListener(this);
		control.add(pause);
		control.addSeparator();
		MenuItem stop = new MenuItem("Stop");
		stop.addActionListener(this);
		control.add(stop);
		control.addSeparator();
		
		//menu list
		Menu list = new Menu("List");
		
		MenuBar menubar = new MenuBar();
		menubar.add(file);
		menubar.add(openurl);
		menubar.add(control);
		menubar.add(list);
		
		setMenuBar(menubar);
	}
	
	public void actionPerformed(ActionEvent e) {
		String currentfile;
		String selectfile;
		String url;
	
		if (e.getActionCommand().equals("Quit")) { 
			dispose();
			return;
		}
		
		if (e.getActionCommand().equals("Play")) {
			if (player != null) {
				player.start();
			}
			return;
		}
		
		if (e.getActionCommand().equals("Pause")) {
			if (player != null) {
				player.stop();
			}
			return;
		}

		if (e.getActionCommand().equals("Stop")) {
			if (player != null) {
				player.stop();
				player.setMediaTime(new Time(0));
			}
			return;
		}
		
		if(e.getActionCommand().equals("Input")){
			if(dialog == null)
				dialog = new InputDialog(AllPlayer.this);
				
			//file:D:/FFOutput/silicon valley.mpg
			url = dialog.getUrl();	
			System.out.println("url : " + url);
			
			if (player != null)
			player.close();
			
			try {
				System.out.println("Try to open url : " + url);
				player = Manager.createPlayer(new MediaLocator(url));
			} 
			catch (java.io.IOException e2) {
				System.out.println(e2);
				return;
			} 
			catch (NoPlayerException e2) {
				System.out.println("Cannot find the player.");
				return;
			}
			if (player == null) {
				System.out.println("Cannot create the player.");
				return;
			}
			first = false;
			setTitle(url);
		
			player.addControllerListener(this);
			player.prefetch();
			
		}
		
		if (e.getActionCommand().equals("Open")) {
			FileDialog fd = new FileDialog(this, "Open Media File", FileDialog.LOAD);
			fd.setDirectory(currentDirectory);
			fd.show();
			
			if (fd.getFile() == null)
				return;
			
			selectfile = fd.getFile();
			currentDirectory = fd.getDirectory();
			currentfile = currentDirectory + selectfile;
			
			//set the selected file as a menu item
			MenuItem menuItem = new MenuItem(selectfile);
			menuItem.setActionCommand(currentfile);
			MenuBar menubar = getMenuBar();
			Menu menu = menubar.getMenu(3);
			menuItem.addActionListener(this);
			menu.add(menuItem);
			
			url = "file:" + currentfile;
			
			System.out.println("Open file url : " + url);
			
			if (player != null)
			player.close();
			
			try {
				System.out.println("Try to open url : " + url);
				player = Manager.createPlayer(new MediaLocator(url));
			} 
			catch (java.io.IOException e2) {
				System.out.println(e2);
				return;
			} 
			catch (NoPlayerException e2) {
				System.out.println("Cannot find the player.");
				return;
			}
			if (player == null) {
				System.out.println("Cannot create the player.");
				return;
			}
			first = false;
			setTitle(url);
		
			player.addControllerListener(this);
			player.prefetch();
		} 
		else {
			//get the file name including directory in the list
			currentfile = e.getActionCommand();
			selectfile = currentfile;
			url = "file:" + currentfile;
		}
		
		//if another played exists, then close it
		/**
		if (player != null)
			player.close();
			
		try {
			System.out.println("Try to open url : " + url);
			player = Manager.createPlayer(new MediaLocator(url));
		} 
		catch (java.io.IOException e2) {
			System.out.println(e2);
			return;
		} 
		catch (NoPlayerException e2) {
			System.out.println("Cannot find the player.");
			return;
		}
		if (player == null) {
			System.out.println("Cannot create the player.");
			return;
		}
		first = false;
		setTitle(url);
		
		player.addControllerListener(this);
		player.prefetch();
	*/
	}
	
	public void controllerUpdate(ControllerEvent e) {
		Container tainer = getContentPane();
		
		if (e instanceof ControllerClosedEvent) {
			if (vc != null) {
				remove(vc);
				vc = null;
			}
			if (cc != null) {
				remove(cc);
				cc = null;
			}
			return;
		}
		
		if (e instanceof EndOfMediaEvent) {
			player.setMediaTime(new Time(0));
			if (loop) {
				player.start();
			}
			return;
		}
		
		if (e instanceof PrefetchCompleteEvent) {
			player.start();
			return;
		}
		
		if (e instanceof RealizeCompleteEvent) {
			vc = player.getVisualComponent();
			if (vc != null)
				tainer.add(vc, BorderLayout.CENTER);
			
			cc = player.getControlPanelComponent();
			cc.setBackground(Color.BLUE);
			if (cc != null)
				tainer.add(cc, BorderLayout.SOUTH);
			
			gc = player.getGainControl();
			gcc = gc.getControlComponent();
			if (gcc != null)
				tainer.add(gcc, BorderLayout.NORTH);
			
			if (vc != null) {
				pack();
				return;
			} 
			else {
				setSize(300, 75);
				setVisible(true);
				return;
			}
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		loop = !loop;
	}	

	
	public void update(Graphics g) {
		paint(g);
	}
	
	public static void main(String[] args) {
		new AllPlayer("Simple Media Player");
	}
	public void paint(Graphics g) {
		super.paint(g);
		if (first) {
			int width = getSize().width;
			int height = getSize().height;
			g.setColor(Color.magenta);
			g.fillRect(0, 0, width, height);
			Font font = new Font("DialogInput", Font.BOLD, 18);
			g.setFont(font);
			FontMetrics fontmetrics = g.getFontMetrics();
			int swidth = fontmetrics.stringWidth("Simple Media Player");
			g.setColor(Color.white);
			g.drawString("Simple Media Player",(width - swidth) / 2,(height + getInsets().top) / 2);
		}
	}
	
		//MediaPlayer class member declare
	Player player;
	private InputDialog dialog;
	private MediaLocator urll;
	
	//vc as visual component
	//cc as controller component
	//gcc as gain control component
	Component vc, cc, gcc;	
	GainControl gc;
	//first value control start welcome picture
	boolean first = true;
	boolean loop = false;
		
	String currentDirectory;
	String subStr;
}

class InputDialog extends JDialog{
	public InputDialog(JFrame owner){
		super(owner,"Input URL",true);
		
		final JTextField inputField = new JTextField();
		add(inputField,BorderLayout.NORTH);
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				urlValue = inputField.getText().toString();
				setVisible(false);
			}
		});
		
		//add actionListener 
		
		JPanel panel = new JPanel();
		panel.add(ok);
		add(panel,BorderLayout.SOUTH);
		
		setSize(WIDTH,HEIGHT);
		setVisible(true);
		
	}
	
	public String getUrl(){
		//return inputArea.getText();
		//urlValue = inputField.getText().toString();
		return urlValue;
	}
	
	private JTextField inputField;
	private String urlValue;
	//private static final int LINE = 1;
	//private static final int COLUM = 30;
	private static final int WIDTH = 250;
	private static final int HEIGHT = 150;
}
