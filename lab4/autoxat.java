package lab4;

import java.applet.*; 
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class autoxat extends Applet{
 
	private static final long serialVersionUID = 7159762543801346293L;
TextArea frebretext  =  new TextArea();
  TextArea fenviartext  =  new TextArea();
 
  Socket sk = null;
  InputStream skin; 
  DataInputStream dis; 
  OutputStream skout; 
  DataOutputStream dos; 
  
  public void init() {
    try{
      sk=new Socket("127.0.0.1",8080);// port connexio           
      skin=sk.getInputStream(); 
      dis= new DataInputStream(skin);
      skout=sk.getOutputStream();
      dos= new DataOutputStream(skout);
    } catch (IOException e) {}
    
    
    //dimensiona finestra text
    frebretext.setColumns(40);
    //    frebretext.setRows(20);
    fenviartext.setColumns(20);
    //frebretext.setRows(20);
    add(frebretext);
    add(fenviartext);
    
  
    TLector lec = new TLector(dis,frebretext);
    TEscriptor esc = new TEscriptor(dos,fenviartext);
    lec.start();
    esc.start();
  }

  public void start(){
   
  }
 
  public void stop(){ 
    try{
      dos.close();
      skout.close();
      dis.close();
      skin.close();
      sk.close();
    } catch (IOException e) {}
  }
}



