package javaDesign;

//������� 
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

class DialogDemo extends JDialog 
{ 
JTextArea field; 
Container c; 
String sValue= "������������Java Media Framework����,���ܼ�\n" +
		       "ֻ���м򵥵����ֲ��Ź��ܺ���Ƶ���Ź���,\n" + 
		       "���в����б�λ�����ͬĿ¼�µ�\"list.txt\"�ļ�\n" +
		       "����ˮƽ����,��Ʒ��������©���Ͳ���,ϣ����ʦָ��"
			   
; 

DialogDemo(Frame frame,String title) 
{ 
super(frame,title); 

field=new JTextArea(); 
field.setText(sValue); 
field.setEditable(false); 
c=getContentPane(); 
c.setLayout(new BorderLayout()); 
c.add(field,BorderLayout.CENTER);//Ĭ��ΪBorderLayout���� 
setLocation(80,250); 
setSize(295,100); 

setResizable(false); 
//setVisible(true); 
} 


}
