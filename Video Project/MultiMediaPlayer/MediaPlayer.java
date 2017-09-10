package javaDesign;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.media.*;
import java.io.*;
import java.util.*;//Ϊ�˵���Vector
//import com.sun.java.swing.plaf.windows.*;

public class MediaPlayer extends JFrame implements ActionListener,Runnable
{
 private JMenuBar          bar;//�˵���
 private JMenu             fileMenu,choiceMenu,aboutMenu;
 private JMenuItem         openItem,openDirItem,closeItem,about,infor;
 private JCheckBoxMenuItem onTop;
 private boolean           top=false,loop;//�趨�����Ƿ�����ǰ��
 private Player            player;//Play�Ǹ�ʵ��Controller�Ľӿ�
 private File              file,listFile;//����File����JFileChooser�����ļ��򿪲���,������list.txt�й�
 private Container         c;
 //private UIManager.LookAndFeelInfo[] look;
 private String            title,listIniAddress;//����
 private FileDialog        fd;
 private JPanel            panel,panelSouth;
 private Icon              icon; //��ʼ�����ʱ��Ҫ��ʾ��ͼ�꣬��Ϊ�����࣬�����Լ�����
 private JLabel            label,listB;//������ʾͼ��
 
    private JList             list;//�����嵥
    private JScrollPane       scroll;//ʹ�����嵥���й�������
    private ListValues        listWriteFile;//�������ļ��ж�ȡ����
    private ObjectInputStream input;//����������
    private ObjectOutputStream output;//���������
    
    private JPopupMenu        popupMenu;//����Ҽ������˵�
    private JMenuItem         del,delAll,reName;      //�����˵���ʾ�Ĳ˵���,����ɾ��,ȫ��ɾ����������
    
    private Vector            fileName,dirName,numList;
    private String            files,dir;
    private int               index;//��Ŀָ��
    private Properties        prop;//���ϵͳ����
    private int               indexForDel;//��־Ҫɾ�����б���Ŀ������
    private ButtonGroup       buttonGroup;//���ư�ť��
    private JRadioButtonMenuItem[]    buttonValues;
    private String[]          content={"�������","˳�򲥷�","����ѭ��"};
    
    private DialogDemo        dialog1;
    //private JDialogTest       dialog2;//������ʾ�����嵥
    
    
 
 
 MediaPlayer()//���캯��
 {
  super("java��Ƶ������1.1��");//���ڱ���
     
     c=getContentPane();
  c.setLayout(new BorderLayout());
  //c.setBackground(new Color(40,40,95));
     
  fileName=new Vector(1);
  dirName=new Vector(1);
  numList=new Vector(1);//����������������֧�ֲ����嵥

  listFile=new File("list.txt");//ֱ�Ӵ��ڴ�Ŀ¼
  Thread readToList=new Thread(this);
  
  list=new JList();
  list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
  list.setSelectionForeground(new Color(0,150,150));
  list.setVisibleRowCount(10);
  list.setFixedCellHeight(12);
  list.setFixedCellWidth(250);
  list.setFont(new Font("Serif",Font.PLAIN,12));
  list.setBackground(new Color(40,40,95));
  list.setForeground(new Color(0,128,255));
  list.setToolTipText("���Ҽ���ʾ���๦��");//���������嵥�����ø�������
  list.addMouseListener(new MouseAdapter()
  {
   public void mouseClicked(MouseEvent e)
   {
                if (e.getClickCount() == 2) //�ж��Ƿ�˫��
                {
                   index = list.locationToIndex(e.getPoint());//���������ת����list�е�ѡ��ָ��
                   createPlayer2();
                   //System.out.println("Double clicked on Item " + index);�����ǲ��Ե�ʱ��ӵ�
                }
            }
           /* public void mousePressed(MouseEvent e)
            {
             checkMenu(e);//�Զ��庯�����ж��Ƿ����Ҽ����������Ƿ���ʾ�˵�
            }*/
            public void mouseReleased(MouseEvent e)
            {
             checkMenu(e);//�������һ�����ж��Ƿ�����Ҽ�
            }

  }
  );
 
  scroll=new JScrollPane(list);//���ڴ�Ų����б�

  
  readToList.start();//�����ỵ̄����ز����б�
  try
  {
   Thread.sleep(10);
  }
  catch(InterruptedException e)
  {
   e.printStackTrace();
  }
  
  bar=new JMenuBar();
  setJMenuBar(bar);//�����д����˵������ŵ��˴��ڳ���
  //bar.setBackground(new Color(48,91,183));
  fileMenu=new JMenu("�ļ�");
  bar.add(fileMenu);
  
  choiceMenu=new JMenu("����");
  bar.add(choiceMenu);
  
  aboutMenu=new JMenu("����");
  bar.add(aboutMenu);
  
  openItem    =new JMenuItem("���ļ�");
  openDirItem =new JMenuItem("��Ŀ¼");
  closeItem   =new JMenuItem("�˳�����");
  openItem.addActionListener(this);
  openDirItem.addActionListener(this);
  closeItem.addActionListener(this);
  fileMenu.add(openItem);
  fileMenu.add(openDirItem);
  fileMenu.add(closeItem);
  
  onTop=new JCheckBoxMenuItem("����ʱλ����ǰ��",top);
  choiceMenu.add(onTop);
  onTop.addItemListener(new ItemListener()
  {
   public void itemStateChanged(ItemEvent e)
   {
    if(onTop.isSelected())
    top=true;
    else top=false;
    setAlwaysOnTop(top);
   }
  }
  );
  
  
  
  choiceMenu.addSeparator();//�ӷָ����
  
  buttonGroup=new ButtonGroup();
  buttonValues=new JRadioButtonMenuItem[3];
  for(int bt=0;bt<3;bt++)
  {
   buttonValues[bt]=new JRadioButtonMenuItem(content[bt]);
   buttonGroup.add(buttonValues[bt]);
   choiceMenu.add(buttonValues[bt]);
  }
  buttonValues[0].setSelected(true);
  choiceMenu.addSeparator();
  
  infor=new JMenuItem("������");
  aboutMenu.add(infor);
  infor.addActionListener(this);
     
     about=new JMenuItem("��������");
  about.addActionListener(this);
  aboutMenu.add(about);
  //�˵����������
  
  panel=new JPanel();
  panel.setLayout(new BorderLayout());
  c.add(panel,BorderLayout.CENTER);
  
  panelSouth=new JPanel();
  panelSouth.setLayout(new BorderLayout());
  c.add(panelSouth,BorderLayout.SOUTH);
  
  icon=new  ImageIcon("icon\\Player.jpg");
  label=new JLabel(icon);
  panel.add(label);
  
  popupMenu=new JPopupMenu();
  del      =new JMenuItem("ɾ��");//����Ҽ������˵�����ʵ����
  popupMenu.add(del);
  del.addActionListener(this);
  
  delAll   =new JMenuItem("ȫ��ɾ��");
  popupMenu.add(delAll);
  delAll.addActionListener(this);
  reName   =new JMenuItem("������");
  popupMenu.add(reName);
  reName.addActionListener(this);
  
 
  
  scroll=new JScrollPane(list);//���ڴ�Ų����б�
  listB=new JLabel(new ImageIcon("icon\\qingdan.gif"),SwingConstants.CENTER);
  
  panelSouth.add(listB,BorderLayout.NORTH);
  panelSouth.add(scroll,BorderLayout.CENTER);
  
  
  dialog1=new DialogDemo(MediaPlayer.this,"���˵��");
  
  this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//�趨���ڹرշ�ʽ
  //this.setTitle("d");����ͨ����˵�������ٴ��趨����
  this.setLocation(400,250);//�趨���ڳ��ֵ�λ��
  //this.setSize(350,320);//���ڴ�С
  setSize(350,330);
  this.setResizable(false);//���ò���������������С
  this.setVisible(true);//�˾䲻���٣����򴰿ڻ᲻��ʾ
  
  
 }
 
 public void actionPerformed(ActionEvent e)
 {
  if(e.getSource()==openItem)//getSource()�жϷ���ʱ������
  {
   openFile();
  }
  if(e.getSource()==openDirItem)//��Ŀ¼
  {
   openDir();
  }
  if(e.getSource()==closeItem)//�Ƴ�������
  {
   exity_n();
   //System.exit(0);
  }
  if(e.getSource()==about)
  {
   JOptionPane.showMessageDialog(this,"�˲������ɼ����ѧԺ072014��\n"
   +"Τ���ġ����ƺء�л��ΰ\n  "+"        ��ͬ���            ",
   "������",
   JOptionPane.INFORMATION_MESSAGE);
  }
  if(e.getSource()==del)
  {
   //index
   //delPaintList(index);
   fileName.removeElementAt(indexForDel);
   dirName.removeElementAt(indexForDel);
   numList.removeAllElements();//���������������Ƴ�����
   Enumeration enumFile=fileName.elements();
   while(enumFile.hasMoreElements())
   {
    numList.addElement((numList.size()+1)+"."+enumFile.nextElement());
    //numList���Ԫ�أ���ʾ���������
   }
   //list.setListData(fileName);
   list.setListData(numList);
   if(index<indexForDel)
   list.setSelectedValue(numList.elementAt(index),true);
   else
   {
    if(index==indexForDel);
    else
    if(index!=0)
       list.setSelectedValue(numList.elementAt(index-1),true);
   }
   
   //list.setSelectedIndex(index);
  }
  
  if(e.getSource()==delAll)//ȫ��ɾ��
  {
   fileName.removeAllElements();
   dirName.removeAllElements();
   numList.removeAllElements();
   list.setListData(numList);
  }
  
  if(e.getSource()==reName)//������
  {
   String name;//=JOptionPane.showInputDialog(this,"�������µ�����");
   try
   {
    name=reNames();
       fileName.setElementAt(name,indexForDel);
       numList.setElementAt((indexForDel+1)+"."+name,indexForDel);
   }
   catch(ReName e2)//�Զ�����쳣
   {
   }
   
  }
  if(e.getSource()==infor)
  {
   dialog1.setVisible(true);
  }
 }
 
 public static void main(String[] args)
 {
  final MediaPlayer mp=new MediaPlayer();
  //mp.setIconImage(new ImageIcon("icon\\mPlayer.jpg").getImage());//�ı�Ĭ��ͼ��
  mp.addWindowListener(new WindowAdapter()//ע�ᴰ���¼�
  {
   public void windowClosing(WindowEvent e)
      {
        //System.exit(0);
        mp.exity_n();
      }
  }
  
  );
  System.out.println("ע�⣺�����ļ��б�����������رղ�����"
  +"\nȻ���ٹرմ�DOS���ڣ������²����б��ܱ��棡��");
 }
 
 private void openFile()//Ϊ�˽���ԭ�򣬴˴�����д�����Ƽ����Բ�����
 {
    fd = new FileDialog(MediaPlayer.this);
   
   fd.setVisible(true);
   if (fd.getFile() != null)
   {
    title = fd.getDirectory() + fd.getFile();//ԭ�����ͬĿ¼�µ�FileDialogDemo.java�ļ�
    files=fd.getFile();
    file=new File(title);
    createPlayer();
    
   }
 }
 private void openDir()
 {
  JFileChooser fileChooser=new JFileChooser();
  fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  int result=fileChooser.showOpenDialog(MediaPlayer.this);
  if(result==JFileChooser.CANCEL_OPTION)
  return;
  file=fileChooser.getSelectedFile();
  if(file==null||file.getName().equals(""))
  JOptionPane.showMessageDialog(this,"�����·��",
  "������",JOptionPane.ERROR_MESSAGE);
  String[] sFiles=file.list();
  for(int i=0;i<sFiles.length;i++)
  {
   fileName.addElement(sFiles[i]);
   numList.addElement((numList.size()+1)+"."+sFiles[i]);
   dirName.addElement(file.getAbsolutePath()+"\\"+sFiles[i]);
  }
  list.setListData(numList);
  
 }
 
 private void createPlayer()
 {
  closePreviosPlayer();//�ر���ǰ��ý�岥����
  String extendName="�˲�������֧��"+title.substring(title.lastIndexOf(".")+1)+"��ʽ";
  try
  {
   player=Manager.createPlayer(file.toURL());//javax.media.Managerֱ�Ӽ̳���java.lang.object,����Ϊfinal,���ܱ��̳�
   
   player.addControllerListener(new ControllerHand());
   player.start();
   addList(files);
   index=fileName.size()-1;
   list.setSelectedValue(numList.elementAt(index),true);
   setTitle(title);
  }
  catch(Exception e)
  {
   JOptionPane.showMessageDialog(this,extendName,"������!!",JOptionPane.ERROR_MESSAGE);
   setTitle(extendName);
  }
  
  
  
 }
 
 private void closePreviosPlayer()
 {
  if(player==null)
  return;

  player.stop();      
     player.deallocate(); //ֹͣ���Ų�������װ��DateSource
  
  Component visual =player.getVisualComponent();
  Component control=player.getControlPanelComponent();
  
  if(visual!=null)
  {
   panel.remove(visual);
  }
  if(control!=null)
  {
   panel.remove(control);
  }
  
 }
 
 private class ControllerHand implements ControllerListener
 {
  public void controllerUpdate(ControllerEvent e)
  {
   if(e instanceof RealizeCompleteEvent)
   {
    Component visual=player.getVisualComponent();
    
    if(visual!=null)
    {
     //System.out.println("��Ƶ��������֧����Ƶͼ����");
     //setTitle("��Ƶ��������֧����Ƶͼ����");
     
     panel.removeAll();
        panel.add(visual,BorderLayout.CENTER);
    }
    else
    {
     panel.add(label,BorderLayout.CENTER);
    }///��else�����Է�ֹ��Ϊԭ��������Ƶͼ����Ժ�û�н���
    
    
    Component control=player.getControlPanelComponent();
    
    if(control!=null)
    {
     
     
     panel.add(control,BorderLayout.SOUTH);
    }
    
    //c.validate();
    panel.doLayout();
    return;
   }
   
   if (e instanceof EndOfMediaEvent) 
            { 
                if(buttonValues[0].isSelected())
                {
                 if(fileName.size()==0)
                 return;
                 index=(int)(Math.random()*fileName.size());
                }
                if(buttonValues[1].isSelected())
                {
                 if(fileName.size()==0)
                 return;
                 index=(index+1)%fileName.size();
                }
                if(buttonValues[2].isSelected())
                {
                    player.setMediaTime (new Time (0)); 
                    player.start();  
                } 
                createPlayer2();
                
      }
     }
 }
 private void exity_n()
 {
  saveList();
  System.exit(0);
  
 }
  private void addList(String vf)
 {
   fileName.addElement(vf);
   numList.addElement((numList.size()+1)+"."+vf);
   //fileName.addElement(++i+"."+vf);
   dirName.addElement(title);
   list.setListData(numList);
 }
 private void createPlayer2()
 {
  
  try{title=dirName.elementAt(index).toString();}
  //title=dirName.elementAt(index).toString();
  catch(ArrayIndexOutOfBoundsException e)
  {return;}
  file=new File(title);
  closePreviosPlayer();//�ر���ǰ��ý�岥����
  String extendName="�˲�������֧��"+title.substring(title.lastIndexOf(".")+1)+"��ʽ";
  try
  {
   player=Manager.createPlayer(file.toURL());//javax.media.Managerֱ�Ӽ̳���java.lang.object,����Ϊfinal,���ܱ��̳�
   
   player.addControllerListener(new ControllerHand());
   player.start();
   //list.setSelectedIndex(index);
   list.setSelectedValue(numList.elementAt(index),true);
   //list.setSelectionForeground(Color.blue);
   //list.setSelectedIndex(index);
   //addList(files);
   setTitle(title);
   
   //addList("files");//�������嵥
  
  }
  catch(Exception e)
  {
   //JOptionPane.showMessageDialog(this,extendName,"������!!",JOptionPane.ERROR_MESSAGE);
   //setTitle(extendName);
   String ex=null;
   try{ex=fileName.elementAt(index).toString();
   }
   catch(Exception e1){return;}
   fileName.removeElementAt(index);
   numList.removeAllElements();
   Enumeration enumFile=fileName.elements();
   while(enumFile.hasMoreElements())
   {
    numList.addElement((numList.size()+1)+"."+enumFile.nextElement());
    
   }
   
   dirName.removeElementAt(index);
   //list.setListData(fileName);
   list.setListData(numList);
  
   
   System.out.println("�Ѿ��Ӳ����б���ɾ�� "+"\""+ex+"\""+" �ļ�,"
   +"��Ϊ�˲�������֧��"+ex.substring(ex.lastIndexOf(".")+1)+"��ʽ,"
   +"����û�д�Ӳ������ɾ��");
   if(numList.size()!=0)
   {
    index%=numList.size();
       createPlayer2();
   }
  }
  
 }
 private void saveList()
 {
  Enumeration enumFile=fileName.elements();
  Enumeration enumDir =dirName.elements();
  try
  {
   output=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(listFile)));
   while(enumFile.hasMoreElements())
   {
    listWriteFile=new ListValues(enumFile.nextElement().toString(),enumDir.nextElement().toString());
    output.writeObject(listWriteFile);
   }
   output.flush();
   output.close();
   
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
  
  
 }
 public void run()
 {
  try
  {
   Thread.sleep(1);
  }
  catch(InterruptedException e)
  {
  }
  
  try
  {
   if(!listFile.exists())
   {
    listFile.createNewFile();//��ֹ�����ڴ��ļ�������ȡ���������д��뱣֤�����ڵ�������Զ�����
    return;
   }
   
   input=new ObjectInputStream(new BufferedInputStream(new FileInputStream(listFile)));
   while(true)
   {
    listWriteFile=(ListValues)input.readObject();
    fileName.addElement(listWriteFile.getFileName());
    numList.addElement((numList.size()+1)+"."+listWriteFile.getFileName());
    dirName.addElement(listWriteFile.getDirName());
       
   }
  }
  catch(EOFException e)
  {
   try
   {
    //if(!fileName.isEmpty())
    input.close();//ȷ����Ԫ�ش��ڲ�������Ϻ�ر�������
   }
   catch(IOException e1)
   {
    JOptionPane.showMessageDialog(MediaPlayer.this,"�ļ����������ر�",
    "�Ƿ��ر�",JOptionPane.ERROR_MESSAGE);
   }
   
  }
  catch(ClassNotFoundException e)
  {
   JOptionPane.showMessageDialog(MediaPlayer.this,"���ܴ�������","���󴴽�ʧ��",JOptionPane.ERROR_MESSAGE);
  }
  catch(IOException e)
  {
   JOptionPane.showMessageDialog(MediaPlayer.this,"���ܶ�ȡ�ļ�",
   "��ȡ�ļ�ʧ��",JOptionPane.ERROR_MESSAGE);
  }
  finally
  {
   try
   {
    if(input!=null)
    input.close();
   }
   catch(IOException e)
   {
   }
   
   if(dirName.isEmpty())//��ֹVectorԽ��
      {
        return;
      }
   index=(int)(Math.random()*(fileName.size()));//�����漴���������漴����
   list.setListData(numList);
   createPlayer2();
  }
  
 }
 private void checkMenu(MouseEvent e)
 {
  
  if(e.isPopupTrigger())
  {
   indexForDel=list.locationToIndex(e.getPoint());
   int[] selected={index,indexForDel};
   //Point p=new Point(e.getX(),e.getY());
   
   list.setSelectedIndices(selected);
   popupMenu.show(list,e.getX(),e.getY());
   
  }
  //list.setSelectedIndex(index);
 }
 String reNames() throws ReName//�ļ���������
 {
  String name=JOptionPane.showInputDialog(this,"�������µ�����",fileName.elementAt(indexForDel));
  if(name==null||name.equals("")) throw new ReName();
  return name;
 }
 class ReName extends Exception//�Զ����쳣�������ļ�������ʱ��������Ϊ�յ�����
 {
 }
}
