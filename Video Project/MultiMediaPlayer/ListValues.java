package javaDesign;

//列表文件要用的对象 
import java.io.Serializable; 

class ListValues implements Serializable 
{ 
private String fileName; 
private String dirFileName; 

ListValues() 
{ 
setFileName("歌曲名字"); 
setDirFileName("E:\\歌曲名字"); 
} 
ListValues(String fileNameC,String dirFileNameC) 
{ 
setFileName(fileNameC); 
setDirFileName(dirFileNameC); 
} 

void setFileName(String fileNameC) 
{ 
fileName=fileNameC; 
} 
void setDirFileName(String dirFileNameC) 
{ 
dirFileName=dirFileNameC; 
} 
String getFileName() 
{ 
return fileName; 
} 
String getDirName() 
{ 
return dirFileName; 
} 
}  

