package javaDesign;

//�б��ļ�Ҫ�õĶ��� 
import java.io.Serializable; 

class ListValues implements Serializable 
{ 
private String fileName; 
private String dirFileName; 

ListValues() 
{ 
setFileName("��������"); 
setDirFileName("E:\\��������"); 
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

