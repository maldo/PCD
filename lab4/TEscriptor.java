package lab4;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class TEscriptor extends Thread{
  
  private DataOutputStream dos;
  private TextArea finestra;
  
  public TEscriptor (DataOutputStream d,TextArea fin){
    dos=d;
    finestra=fin;
  }
 
  public void run(){
    ReturnKey premut= new ReturnKey(dos,finestra); 
    finestra.addKeyListener(premut);
    finestra.append("Escriptor engegat\n");
    try{
      dos.writeUTF("Connexio establerta");
    } catch (IOException e) {}
  }
  
  class ReturnKey extends KeyAdapter{
   
    private DataOutputStream RKdos;
    private String RKs;
    private TextArea RKfinestra;
 
    //Netscape obliga a redeclarar les variables dos i finestra dins ReturnKey
    public ReturnKey(DataOutputStream d,TextArea fin){
      RKdos=d;
      RKfinestra=fin;
    }
    public void keyPressed(KeyEvent tecla){
      if (tecla.getKeyCode()==KeyEvent.VK_ENTER){
	try{
	  RKs=RKfinestra.getText();
	  RKdos.writeUTF(RKs);
	  RKfinestra.setText("");
	    //	  System.out.print(s);
	} catch (IOException e) {}
      }
    }
  } 
}
