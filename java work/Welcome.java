/**
* Helloworld - 
* 		Displays a greeting from the authors.
* @version 1.0 
* @author Alfred Lee
*/
public class Welcome{
	public static void main(String args []){
		String[] greeting = new String[3];
		greeting[0] = "Welcome to Core Java";
		greeting[1] = "by Alfred Lee";
		greeting[2] = "@@@@@@@@@@@@@@@@";
		
		for(String g : greeting)
		System.out.println(g);
	}
}