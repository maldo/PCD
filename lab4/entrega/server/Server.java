package lab4.entrega.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

import lab4.entrega.Constants;

public class Server
{
	public static void main(String args[]) throws Exception
	{
		new Server();
	}

	private final ServerSocket ss;

	/**
	 * Guardamos los sockets de los clientes con sus DataOutputStream, asi los
	 * tendremos cuando queramos enviar mensajes al cliente
	 */
	private final Hashtable<Socket, DataOutputStream> skOutput = new Hashtable<Socket, DataOutputStream>();

	public Server() throws IOException
	{
		ss = new ServerSocket(Constants.PORT);

		System.out.println("Listening on " + ss);

		while (true)
		{
			Socket sk = ss.accept();

			System.out.println("Connection from " + sk);

			DataOutputStream dout = new DataOutputStream(sk.getOutputStream());
			skOutput.put(sk, dout);

			new ServerThread(this, sk);
		}
	}

	Enumeration<DataOutputStream> getskOutput()
	{
		return skOutput.elements();
	}

	void spread(String message)
	{
		/** Sincronizamos el acceso a los outputs para no tener problemas */
		synchronized (skOutput)
		{
			/** Para cada cliente le mandamos el mensaje */
			for (Enumeration<DataOutputStream> e = getskOutput(); e
					.hasMoreElements();)
			{
				DataOutputStream dos = e.nextElement();

				try
				{
					dos.writeUTF(message);
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
	}

	/**
	 * Cuando un socket se ha cerrado debemos borrarlo, para que no tengamos
	 * problemas al mandar mensajes a un cliente que ya no esta
	 */
	void removeConnection(Socket sk)
	{
		synchronized (skOutput)
		{
			System.out.println("Removing connection to " + sk);

			skOutput.remove(sk);

			try
			{
				sk.close();
			}
			catch (IOException e)
			{
				System.out.println("Error closing " + sk);
				e.printStackTrace();
			}
		}
	}
}
