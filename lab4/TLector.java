package lab4;

import java.io.*;
import java.net.*;
import java.awt.*;

public class TLector extends Thread{
  
  private DataInputStream dis;

  private String s;
  private TextArea finestra;

  public TLector (DataInputStream d, TextArea fin){
    dis=d;
    finestra=fin;
  }
 
  public void run(){
  
    try{
      finestra.append("Lector engegat\n");
      s=dis.readUTF();
      while (s.length()>0){
	finestra.append(s);
	s =dis.readUTF();
      }
     } catch (IOException e) {}
  } 
}
