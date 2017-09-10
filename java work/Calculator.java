import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* @version : 1.33
* @author : Cay Horstmann
*/
public class Calculator{
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				CalculatorFrame frame = new CalculatorFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
* Frame with a calculator panel.
*/
class CalculatorFrame extends JFrame{
	public CalculatorFrame(){
		setTitle("Calculator");
		CalculatorPanel panel = new CalculatorPanel();
		add(panel);
		pack();
	}
}

/**
* Panel with calculator buttons and result display.
*/
class CalculatorPanel extends JPanel{
	public CalculatorPanel(){
		setLayout(new BorderLayout());
		
		result = 0;
		lastCommand = "=";
		start = true;
		
		//add the display
		display = new JButton("0");
		display.setEnabled(false);
		add(display,BorderLayout.NORTH);
		
		ActionListener insert = new InsertAction();
		ActionListener command = new CommandAction();
		
		//add the buttons in a 4X4 grid
		panel = new JPanel();
		panel.setLayout(new GridLayout(4,4));
		
		addButton("7",insert);
		addButton("8",insert);
		addButton("9",insert);
		addButton("/",command);
		
		addButton("4",insert);
		addButton("5",insert);
		addButton("6",insert);
		addButton("*",command);
		
		addButton("1",insert);
		addButton("2",insert);
		addButton("3",insert);
		addButton("-",insert);
		
		addButton("0",insert);
		addButton(".",insert);
		addButton("=",command);
		addButton("+",command);
		
		add(panel,BorderLayout.CENTER);
	}
	
	/**
	* Adds a button to the center panel.
	*/
	private void addButton(String label,ActionListener listener){
		JButton button = new JButton(label);
		button.addActionListener(listener);
		panel.add(button);
	}
	
	/**
	* Insert the button action string to the end of the display text.
	*/
	private class InsertAction implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String input = event.getActionCommand();
			if(start){
				display.setText("");
				start = false;
			}
			//flush the display text
			display.setText(display.getText() + input);
		}
	}
	
	/**
	* Executes the command that the button action string denotes.
	*/
	private class CommandAction implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String command = event.getActionCommand();
			
			if(start){
				if(command.equals("-")){
					display.setText(command);
					start = false;
				}
				else
					lastCommand = command;
			}
			else{
				calculate(Double.parseDouble(display.getText()));
				lastCommand = command;
				start = true;
			}
		}
	}
	
	/**
	* Carries out the pending calculation.
	*/
	public void calculate(double x){
		if(lastCommand.equals("+"))
			result += x;
		else if(lastCommand.equals("-"))
			result -= x;
		else if(lastCommand.equals("*"))
			result *= x;
		else if(lastCommand.equals("/"))
			result /= x;
		else if(lastCommand.equals("="))
			result = x;
		display.setText("" + result);
	}
	
	private JButton display;
	private JPanel panel;
	private double result;
	private String lastCommand;
	private boolean start;
}