package javaDesign;

//软件简介框 
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

class DialogDemo extends JDialog 
{ 
JTextArea field; 
Container c; 
String sValue= "本播放器基于Java Media Framework制作,功能简单\n" +
		       "只具有简单的音乐播放功能和视频播放功能,\n" + 
		       "其中播放列表位于软件同目录下的\"list.txt\"文件\n" +
		       "由于水平有限,作品中难免有漏洞和不足,希望老师指正"
			   
; 

DialogDemo(Frame frame,String title) 
{ 
super(frame,title); 

field=new JTextArea(); 
field.setText(sValue); 
field.setEditable(false); 
c=getContentPane(); 
c.setLayout(new BorderLayout()); 
c.add(field,BorderLayout.CENTER);//默认为BorderLayout布局 
setLocation(80,250); 
setSize(295,100); 

setResizable(false); 
//setVisible(true); 
} 


}
