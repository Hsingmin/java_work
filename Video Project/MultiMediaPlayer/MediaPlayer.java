package javaDesign;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.media.*;
import java.io.*;
import java.util.*;//为了导入Vector
//import com.sun.java.swing.plaf.windows.*;

public class MediaPlayer extends JFrame implements ActionListener,Runnable
{
 private JMenuBar          bar;//菜单条
 private JMenu             fileMenu,choiceMenu,aboutMenu;
 private JMenuItem         openItem,openDirItem,closeItem,about,infor;
 private JCheckBoxMenuItem onTop;
 private boolean           top=false,loop;//设定窗口是否在最前面
 private Player            player;//Play是个实现Controller的接口
 private File              file,listFile;//利用File类结合JFileChooser进行文件打开操作,后则与list.txt有关
 private Container         c;
 //private UIManager.LookAndFeelInfo[] look;
 private String            title,listIniAddress;//标题
 private FileDialog        fd;
 private JPanel            panel,panelSouth;
 private Icon              icon; //开始进入的时候要显示的图标，它为抽象类，不能自己创建
 private JLabel            label,listB;//用来显示图标
 
    private JList             list;//播放清单
    private JScrollPane       scroll;//使播放清单具有滚动功能
    private ListValues        listWriteFile;//用于向文件中读取对象
    private ObjectInputStream input;//对象输入流
    private ObjectOutputStream output;//对象输出流
    
    private JPopupMenu        popupMenu;//鼠标右键弹出菜单
    private JMenuItem         del,delAll,reName;      //弹出菜单显示的菜单项,包括删除,全部删除和重命名
    
    private Vector            fileName,dirName,numList;
    private String            files,dir;
    private int               index;//曲目指针
    private Properties        prop;//获得系统属性
    private int               indexForDel;//标志要删除的列表项目的索引
    private ButtonGroup       buttonGroup;//控制按钮组
    private JRadioButtonMenuItem[]    buttonValues;
    private String[]          content={"随机播放","顺序播放","单曲循环"};
    
    private DialogDemo        dialog1;
    //private JDialogTest       dialog2;//用于显示播放清单
    
    
 
 
 MediaPlayer()//构造函数
 {
  super("java音频播放器1.1版");//窗口标题
     
     c=getContentPane();
  c.setLayout(new BorderLayout());
  //c.setBackground(new Color(40,40,95));
     
  fileName=new Vector(1);
  dirName=new Vector(1);
  numList=new Vector(1);//构造三个容器用于支持播放清单

  listFile=new File("list.txt");//直接存于此目录
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
  list.setToolTipText("点右键显示更多功能");//创建播放清单并设置各个属性
  list.addMouseListener(new MouseAdapter()
  {
   public void mouseClicked(MouseEvent e)
   {
                if (e.getClickCount() == 2) //判断是否双击
                {
                   index = list.locationToIndex(e.getPoint());//将鼠标坐标转化成list中的选项指针
                   createPlayer2();
                   //System.out.println("Double clicked on Item " + index);，此是测试的时候加的
                }
            }
           /* public void mousePressed(MouseEvent e)
            {
             checkMenu(e);//自定义函数，判断是否是右键，来决定是否显示菜单
            }*/
            public void mouseReleased(MouseEvent e)
            {
             checkMenu(e);//与上面的一样，判断是否鼠标右键
            }

  }
  );
 
  scroll=new JScrollPane(list);//用于存放播放列表

  
  readToList.start();//启动先程，加载播放列表
  try
  {
   Thread.sleep(10);
  }
  catch(InterruptedException e)
  {
   e.printStackTrace();
  }
  
  bar=new JMenuBar();
  setJMenuBar(bar);//此两行创建菜单栏并放到此窗口程序
  //bar.setBackground(new Color(48,91,183));
  fileMenu=new JMenu("文件");
  bar.add(fileMenu);
  
  choiceMenu=new JMenu("控制");
  bar.add(choiceMenu);
  
  aboutMenu=new JMenu("帮助");
  bar.add(aboutMenu);
  
  openItem    =new JMenuItem("打开文件");
  openDirItem =new JMenuItem("打开目录");
  closeItem   =new JMenuItem("退出程序");
  openItem.addActionListener(this);
  openDirItem.addActionListener(this);
  closeItem.addActionListener(this);
  fileMenu.add(openItem);
  fileMenu.add(openDirItem);
  fileMenu.add(closeItem);
  
  onTop=new JCheckBoxMenuItem("播放时位于最前面",top);
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
  
  
  
  choiceMenu.addSeparator();//加分割符号
  
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
  
  infor=new JMenuItem("软件简介");
  aboutMenu.add(infor);
  infor.addActionListener(this);
     
     about=new JMenuItem("关于作者");
  about.addActionListener(this);
  aboutMenu.add(about);
  //菜单栏设置完毕
  
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
  del      =new JMenuItem("删除");//鼠标右键弹出菜单对象实例化
  popupMenu.add(del);
  del.addActionListener(this);
  
  delAll   =new JMenuItem("全部删除");
  popupMenu.add(delAll);
  delAll.addActionListener(this);
  reName   =new JMenuItem("重命名");
  popupMenu.add(reName);
  reName.addActionListener(this);
  
 
  
  scroll=new JScrollPane(list);//用于存放播放列表
  listB=new JLabel(new ImageIcon("icon\\qingdan.gif"),SwingConstants.CENTER);
  
  panelSouth.add(listB,BorderLayout.NORTH);
  panelSouth.add(scroll,BorderLayout.CENTER);
  
  
  dialog1=new DialogDemo(MediaPlayer.this,"软件说明");
  
  this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//设定窗口关闭方式
  //this.setTitle("d");编译通过，说明可以再次设定标题
  this.setLocation(400,250);//设定窗口出现的位置
  //this.setSize(350,320);//窗口大小
  setSize(350,330);
  this.setResizable(false);//设置播放器不能随便调大小
  this.setVisible(true);//此句不可少，否则窗口会不显示
  
  
 }
 
 public void actionPerformed(ActionEvent e)
 {
  if(e.getSource()==openItem)//getSource()判断发生时间的组键
  {
   openFile();
  }
  if(e.getSource()==openDirItem)//打开目录
  {
   openDir();
  }
  if(e.getSource()==closeItem)//推出播放器
  {
   exity_n();
   //System.exit(0);
  }
  if(e.getSource()==about)
  {
   JOptionPane.showMessageDialog(this,"此播放器由计算机学院072014班\n"
   +"韦力文、王云贺、谢荣伟\n  "+"        共同完成            ",
   "参与者",
   JOptionPane.INFORMATION_MESSAGE);
  }
  if(e.getSource()==del)
  {
   //index
   //delPaintList(index);
   fileName.removeElementAt(indexForDel);
   dirName.removeElementAt(indexForDel);
   numList.removeAllElements();//从三个容器里面移除此项
   Enumeration enumFile=fileName.elements();
   while(enumFile.hasMoreElements())
   {
    numList.addElement((numList.size()+1)+"."+enumFile.nextElement());
    //numList添加元素，显示播放里表中
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
  
  if(e.getSource()==delAll)//全部删除
  {
   fileName.removeAllElements();
   dirName.removeAllElements();
   numList.removeAllElements();
   list.setListData(numList);
  }
  
  if(e.getSource()==reName)//重命名
  {
   String name;//=JOptionPane.showInputDialog(this,"请输入新的名字");
   try
   {
    name=reNames();
       fileName.setElementAt(name,indexForDel);
       numList.setElementAt((indexForDel+1)+"."+name,indexForDel);
   }
   catch(ReName e2)//自定义的异常
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
  //mp.setIconImage(new ImageIcon("icon\\mPlayer.jpg").getImage());//改变默认图标
  mp.addWindowListener(new WindowAdapter()//注册窗口事件
  {
   public void windowClosing(WindowEvent e)
      {
        //System.exit(0);
        mp.exity_n();
      }
  }
  
  );
  System.out.println("注意：更新文件列表后，请先正常关闭播放器"
  +"\n然后再关闭此DOS窗口，否则导致播放列表不能保存！！");
 }
 
 private void openFile()//为了界面原因，此代码重写，估计兼容性不好了
 {
    fd = new FileDialog(MediaPlayer.this);
   
   fd.setVisible(true);
   if (fd.getFile() != null)
   {
    title = fd.getDirectory() + fd.getFile();//原因请见同目录下的FileDialogDemo.java文件
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
  JOptionPane.showMessageDialog(this,"错误的路径",
  "出错了",JOptionPane.ERROR_MESSAGE);
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
  closePreviosPlayer();//关闭先前的媒体播放器
  String extendName="此播放器不支持"+title.substring(title.lastIndexOf(".")+1)+"格式";
  try
  {
   player=Manager.createPlayer(file.toURL());//javax.media.Manager直接继承于java.lang.object,且它为final,不能被继承
   
   player.addControllerListener(new ControllerHand());
   player.start();
   addList(files);
   index=fileName.size()-1;
   list.setSelectedValue(numList.elementAt(index),true);
   setTitle(title);
  }
  catch(Exception e)
  {
   JOptionPane.showMessageDialog(this,extendName,"出错了!!",JOptionPane.ERROR_MESSAGE);
   setTitle(extendName);
  }
  
  
  
 }
 
 private void closePreviosPlayer()
 {
  if(player==null)
  return;

  player.stop();      
     player.deallocate(); //停止播放并且重新装载DateSource
  
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
     //System.out.println("音频播放器不支持视频图象功能");
     //setTitle("音频播放器不支持视频图象功能");
     
     panel.removeAll();
        panel.add(visual,BorderLayout.CENTER);
    }
    else
    {
     panel.add(label,BorderLayout.CENTER);
    }///此else语句可以防止因为原来播放视频图象后以后没有界面
    
    
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
  closePreviosPlayer();//关闭先前的媒体播放器
  String extendName="此播放器不支持"+title.substring(title.lastIndexOf(".")+1)+"格式";
  try
  {
   player=Manager.createPlayer(file.toURL());//javax.media.Manager直接继承于java.lang.object,且它为final,不能被继承
   
   player.addControllerListener(new ControllerHand());
   player.start();
   //list.setSelectedIndex(index);
   list.setSelectedValue(numList.elementAt(index),true);
   //list.setSelectionForeground(Color.blue);
   //list.setSelectedIndex(index);
   //addList(files);
   setTitle(title);
   
   //addList("files");//到播放清单
  
  }
  catch(Exception e)
  {
   //JOptionPane.showMessageDialog(this,extendName,"出错了!!",JOptionPane.ERROR_MESSAGE);
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
  
   
   System.out.println("已经从播放列表中删除 "+"\""+ex+"\""+" 文件,"
   +"因为此播放器不支持"+ex.substring(ex.lastIndexOf(".")+1)+"格式,"
   +"不过没有从硬盘真正删除");
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
    listFile.createNewFile();//防止不存在此文件发生读取错误，这两行代码保证不存在的情况下自动建立
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
    input.close();//确认有元素存在并加载完毕后关闭输入流
   }
   catch(IOException e1)
   {
    JOptionPane.showMessageDialog(MediaPlayer.this,"文件被非正常关闭",
    "非法关闭",JOptionPane.ERROR_MESSAGE);
   }
   
  }
  catch(ClassNotFoundException e)
  {
   JOptionPane.showMessageDialog(MediaPlayer.this,"不能创建对象","对象创建失败",JOptionPane.ERROR_MESSAGE);
  }
  catch(IOException e)
  {
   JOptionPane.showMessageDialog(MediaPlayer.this,"不能读取文件",
   "读取文件失败",JOptionPane.ERROR_MESSAGE);
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
   
   if(dirName.isEmpty())//防止Vector越界
      {
        return;
      }
   index=(int)(Math.random()*(fileName.size()));//产生随即数，进行随即播放
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
 String reNames() throws ReName//文件该名函数
 {
  String name=JOptionPane.showInputDialog(this,"请输入新的名字",fileName.elementAt(indexForDel));
  if(name==null||name.equals("")) throw new ReName();
  return name;
 }
 class ReName extends Exception//自定义异常来处理文件该名的时候发生输入为空的情形
 {
 }
}
