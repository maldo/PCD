package lab4.entrega.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Client implements Runnable
{
	/** Socket para la conexion con el server */
	private final Socket sk;

	private final DataOutputStream dos;

	private final DataInputStream dis;

	/** Para la comunicacion con el applet */
	private ClientListener listener;

	private char id;

	private final int maxAscii = 127;

	public Client(String host, int port) throws UnknownHostException,
			IOException
	{
		/** Iniciamos conexion */
		sk = new Socket(host, port);
		// Debug
		System.out.println("connected to " + sk);

		dis = new DataInputStream(sk.getInputStream());
		dos = new DataOutputStream(sk.getOutputStream());

		id = (char) new Random().nextInt(maxAscii);
		if (id <= 32)
		{
			id += 33;
		}

		/** El thread lo usaremos para recibir los mensajes */
		new Thread(this).start();
	}

	public void processMessage(String message) throws IOException
	{
		/** Enviamos al server */
		dos.writeUTF(this.getClass().getSimpleName() + " " + id + ": "
				+ message);

		listener.sendClearApplet();
	}

	public void run()
	{
		try
		{
			while (true)
			{
				String message = dis.readUTF();

				listener.receiveSetTextApplet(message + "\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				listener.close();
				dis.close();
				sk.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	interface ClientListener
	{
		public void close();

		public void receiveSetTextApplet(String sms);

		public void sendClearApplet();
	}

	public void setListener(ClientListener listener)
	{
		this.listener = listener;
	}

	public void stop() throws IOException
	{
		dis.close();
		dos.close();
		sk.close();
	}
}
