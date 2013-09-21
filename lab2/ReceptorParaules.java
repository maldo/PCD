package lab2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceptorParaules
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket ss;
		Socket sk;
		InputStream skin;
		DataInputStream dis;
		
		ss = new ServerSocket(9500);
		sk = ss.accept();
		skin = sk.getInputStream();
		dis = new DataInputStream(skin);

		String st = new String(dis.readUTF());
		while (st.length() > 0)
		{
			System.out.print(st);
			st = dis.readUTF();
		}

		dis.close();
		skin.close();
		sk.close();
	}

}
