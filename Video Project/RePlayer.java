/**
* @Description : A media palyer for local file and web file.
* @Author : Alfred Lee
* @Date : Aug. 29th, 2015
* @Email : alfred_bit@sina.cn
*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.media.*;
import java.net.URL;
import java.net.MalformedURLException;

public class RePlayer{
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new MultiPlayer("Open Source");
				System.out.println("Create new player.");
			}
		});
	}
}

class MultiPlayer extends JFrame implements ControllerListener, ItemListener{
	public MultiPlayer(String title){
		super(title);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent event){
				dispose();
			}
			
			public void windowClosed(WindowEvent event){
				if(player != null)
					player.close();
				System.exit(0);
			}
		});
		
		setupMenu();
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		setVisible(true);
	}
	
	public void setupMenu(){
		//File menu
		Menu fileMenu = new Menu("File");
		MenuItem openItem = new MenuItem("Open");
		openItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				FileDialog fd = new FileDialog(new Frame(),"Open Media File",FileDialog.LOAD);
				fd.setDirectory(currentDirectory);
				fd.show();
				
				if(fd.getFile() == null)
					return;
				
				selectfile = fd.getFile();
				currentDirectory = fd.getDirectory();
				currentfile = currentDirectory + selectfile;
				url = "file:" + currentfile;
				
				//add new item into list menu when openning a local media file
				MenuItem menuItem = new MenuItem(selectfile);
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent event){
						url = "file:" + selectfile;
						System.out.println("Open file : " + url);
						setPlayer(url);
					}
				});
				MenuBar menuBar = getMenuBar();
				Menu listMenu = menuBar.getMenu(3);
				listMenu.add(menuItem);
				
				setPlayer(url);
			}
		});
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		
		//loop play mode
		CheckboxMenuItem loopItem = new CheckboxMenuItem("Loop",false);
		loopItem.addItemListener(this);
		fileMenu.add(loopItem);
		fileMenu.addSeparator();
		
		//quit player
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dispose();
				return;
			}
		});
		fileMenu.add(quitItem);
		
		//URL input menu
		Menu urlMenu = new Menu("URL");
		MenuItem urlItem = new MenuItem("Input");
		urlItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(dialog == null)
					dialog = new InputDialog(MultiPlayer.this);
	
				url = dialog.getUrl();
				System.out.println("Open url : " + url);
				setPlayer(url);
			
				//add a new item into list menu when openning a url
				MenuItem menuItem = new MenuItem(url);
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent event){
						setPlayer(url);
					}
				});
				MenuBar menuBar = getMenuBar();
				Menu listMenu = menuBar.getMenu(3);
				listMenu.add(menuItem);
			}
		});
		urlMenu.add(urlItem);		
		
		//Player control menu
		Menu controlMenu = new Menu("Control");
		MenuItem playItem = new MenuItem("Play");
		playItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(player != null)
					player.start();
				return;
			}
		});
		controlMenu.add(playItem);
		controlMenu.addSeparator();
		
		MenuItem pauseItem = new MenuItem("Pause");
		pauseItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(player != null)
					player.stop();
				return;
			}
		});
		controlMenu.add(pauseItem);
		controlMenu.addSeparator();
		
		MenuItem stopItem = new MenuItem("Stop");
		stopItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(player != null){
					player.stop();
					player.setMediaTime(new Time(0));
				}
				return;
			}
		});
		controlMenu.add(stopItem);
		
		//Player list menu
		Menu listMenu = new Menu("List");
		
		MenuBar menuBar = new MenuBar();
		menuBar.add(fileMenu);
		menuBar.add(urlMenu);
		menuBar.add(controlMenu);
		menuBar.add(listMenu);
		setMenuBar(menuBar);
	}
	
	public void setPlayer(String url){
		if(player != null)
			player.close();
		try{
			player = Manager.createPlayer(new MediaLocator(url));
		}
		catch(java.io.IOException e2){
			System.out.println(e2);
			return;
		}
		catch(NoPlayerException e2){
			System.out.println("Cannot find the player!");
			return;
		}
					
		if(player == null){
			System.out.println("Cannot create the player!");
			return;
		}
					
		setTitle(url);
		player.addControllerListener(MultiPlayer.this);
		player.prefetch();
	}
	
	public void controllerUpdate(ControllerEvent e){
		Container tainer = getContentPane();
		
		if(e instanceof ControllerClosedEvent){
			if(vc != null){
				remove(vc);
				vc = null;
			}
			if(cc != null){
				remove(cc);
				cc = null;
			}
			return;
		}
		
		if(e instanceof EndOfMediaEvent){
			player.setMediaTime(new Time(0));
			if(loop){
				player.start();
			}
			return;
		}
		
		if(e instanceof PrefetchCompleteEvent){
			player.start();
			return;
		}
		
		if(e instanceof RealizeCompleteEvent){
			vc = player.getVisualComponent();
			if(vc != null)
				tainer.add(vc,BorderLayout.CENTER);
			
			cc = player.getControlPanelComponent();
			cc.setBackground(Color.BLUE);
			if(cc != null)
				tainer.add(cc,BorderLayout.SOUTH);
				
			gc = player.getGainControl();
			
			gcc = gc.getControlComponent();
			if(gcc != null)
				tainer.add(gcc,BorderLayout.NORTH);
				
			if(vc != null){
				pack();
				return;
			}
			
			else{
				setSize(300,75);
				setVisible(true);
			}
		}
	}
	
	public void itemStateChanged(ItemEvent e){
		loop = !loop;
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		if(first){
			int width = getSize().width;
			int height = getSize().height;
			g.setColor(Color.magenta);
			g.fillRect(0,0,width,height);
			
			Font font = new Font("DialogInput",Font.BOLD,18);
			g.setFont(font);
			
			FontMetrics fontMetrics = g.getFontMetrics();
			int swidth = fontMetrics.stringWidth("Repro Player");
			
			g.setColor(Color.white);
			g.drawString("Repro Player",(width - swidth)/2,(height + getInsets().top)/2);
		}
	}
	
	private Player player;
	private InputDialog dialog;
	private String url;
	private String selectfile;
	private String currentfile;
	private Component vc, cc, gcc;
	private GainControl gc;
	
	private boolean first = true;
	private boolean loop = false;
	
	private String currentDirectory; 
	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HEIGHT = 450;
}

class InputDialog extends JDialog{
	public InputDialog(JFrame owner){
		super(owner,"Input Dialog",true);
		
		final JTextField inputField = new JTextField();
		add(inputField,BorderLayout.NORTH);
		
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				urlValue = inputField.getText().toString();
				setVisible(false);
			}
		});
		
		JPanel panel = new JPanel();
		panel.add(ok);
		add(panel,BorderLayout.SOUTH);
		setSize(DIALOG_WIDTH,DIALOG_HEIGHT);
		setVisible(true);
	}
	
	public String getUrl(){
		return urlValue;
	}
	
	private JTextField inputField;
	private String urlValue;
	private static final int DIALOG_WIDTH = 250;
	private static final int DIALOG_HEIGHT = 150;
}