/*-package lab2;
 
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class EmisorMultifil extends Thread
{
	Socket sk = null;
	public EmisorMultifil(Socket SK)
	{
		sk = SK;
	}
	
	public void run()
	{
		try{
			InputStream skin = sk.getInputStream();
			DataInputStream dis = new DataInputStream(skin);
			String s = new String(dis.readUTF());
			
			while(true)
			{
				s = dis.readUTF();
			}
			dis.close();
			skin.close();
			sk.close();
		} catch (IOException e){}
	}
}*/
