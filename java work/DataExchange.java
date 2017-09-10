import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* @version : 1.33
* @author : Cay Horstmann
*/
public class DataExchange{
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				DataExchangeFrame frame = new DataExchangeFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

class DataExchangeFrame extends JFrame{
	public DataExchangeFrame(){
		setTitle("DataExchange");
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		
		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);
		JMenu fileMenu = new JMenu("File");
		mbar.add(fileMenu);
		
		JMenuItem connectItem = new JMenuItem("Connect");
		connectItem.addActionListener(new ConnectAction());
		fileMenu.add(connectItem);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});
		
		fileMenu.add(exitItem);
		textArea = new JTextArea();
		add(new JScrollPane(textArea),BorderLayout.CENTER);
	}
	
	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 200;
	
	private PasswordChooser dialog = null;
	private JTextArea textArea;
	
	/**
	* The connnect action pops up the password.
	*/
	private class ConnectAction implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(dialog == null)
				dialog = new PasswordChooser();
				
			dialog.setUser(new User("yournname",null));
			
			//pop up dialog
			if(dialog.showDialog(DataExchangeFrame.this,"Connect")){
				User u = dialog.getUser();
				textArea.append("user name = " + u.getName() + "password = " + 
								(new String(u.getPassword())) + "\n");
			}
		}
	}
}

class PasswordChooser extends JPanel{
	public PasswordChooser(){
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2));
		panel.add(new JLabel("User name:"));
		panel.add(username = new JTextField(""));
		panel.add(new JLabel("Password:"));
		panel.add(password = new JPasswordField(""));
		add(panel,BorderLayout.CENTER);
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				ok = true;
				dialog.setVisible(false);
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				dialog.setVisible(false);
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel,BorderLayout.SOUTH);
	}
	
	/**
	* Set the dialog defaults.
	*/
	public void setUser(User u){
		username.setText(u.getName());
	}
	
	//get the dialog entries.
	public User getUser(){
		return new User(username.getText(),password.getPassword());
	}
	
	/**
	* Show the chooser panel in a dialog.
	*/ 
	public boolean showDialog(Component parent,String title){
		ok = false;
		
		Frame owner = null;
		if(parent instanceof Frame)
			owner = (Frame) parent;
		else
			owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class,parent);
			
		if(dialog == null || dialog.getOwner() != owner){
			dialog = new JDialog(owner,true);
			dialog.add(this);
			dialog.getRootPane().setDefaultButton(okButton);
			dialog.pack();
		}
		
		dialog.setTitle(title);
		dialog.setVisible(true);
		return ok;
	}
	
	private JTextField username;
	private JPasswordField password;
	private JButton okButton;
	private boolean ok;
	private JDialog dialog;
}

/**
* A user has a name and password.
*/
class User{
	public User(String aName,char[] aPassword){
		name = aName;
		password = aPassword;
	}
	
	public String getName(){
		return name;
	}
	
	public char[] getPassword(){
		return password;
	}
	
	public void setName(String aName){
		name = aName;
	}
	
	public void setPassword(char[] aPassword){
		password = aPassword;
	}
	
	private String name;
	private char[] password;
}