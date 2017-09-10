import java.text.DateFormatSymbols;
import java.util.*;

/**
* @version : 1.4
* @author : Alfred Lee
*/

public class CalendarTest{
	public static void main(String[] args){
		//construct deadline as current date
		GregorianCalendar deadline = new GregorianCalendar();
		
		int today = deadline.get(Calendar.DAY_OF_MONTH);
		int month = deadline.get(Calendar.MONTH);
		
		//set deadline to start date of the month
		deadline.set(Calendar.DAY_OF_MONTH,1);
		
		int weekday = deadline.get(Calendar.DAY_OF_WEEK);
		
		//get first day of week
		int firstDayOfWeek = deadline.getFirstDayOfWeek();
		
		//determine the required indentation for the first line
		int indent = 0;
		while(weekday != firstDayOfWeek){
			indent++;
			deadline.add(Calendar.DAY_OF_MONTH,-1);
			weekday = deadline.get(Calendar.DAY_OF_WEEK);
		}
		
		//print weekday names
		String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();
		do{
			System.out.printf("%4s",weekdayNames[weekday]);
			deadline.add(Calendar.DAY_OF_MONTH,1);
			weekday = deadline.get(Calendar.DAY_OF_WEEK);
		}
		while(weekday != firstDayOfWeek);
		System.out.println();
		
		for(int i = 1;i <= indent;i++)
			System.out.print("    ");
		
		deadline.set(Calendar.DAY_OF_MONTH,1);
		do{
			//print day
			int day = deadline.get(Calendar.DAY_OF_MONTH);
			System.out.printf("%3d",day);
			
			//mark current day with *
			if(day == today)
				System.out.print("*");
			else 
				System.out.print(" ");
				
			//advance deadline to next day
			deadline.add(Calendar.DAY_OF_MONTH,1);
			weekday = deadline.get(Calendar.DAY_OF_WEEK);
			
			//start a new line at the start of week
			if(weekday == firstDayOfWeek)
				System.out.println();
		}
		//the loop exits when deadline is the first day of the next month
		while(deadline.get(Calendar.MONTH) == month);
		
		//print final end of line if necessary
		if(weekday != firstDayOfWeek)
			System.out.println();
	}
}