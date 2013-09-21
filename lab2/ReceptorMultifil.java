/*-package lab2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceptorMultifil
{

	public static void main(String[] args) throws IOException
	{
		ServerSocket ss = null;
		ss = new ServerSocket(1500);
		while (true)
		{
			Socket sk = null;
			sk = ss.accept();
			ThreadServidorThread t = new EmisorMultifil(sk);
			t.start();
		}

	}

}*/
