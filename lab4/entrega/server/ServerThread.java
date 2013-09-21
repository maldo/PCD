package lab4.entrega.server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread
{
	private final Server server;

	private final Socket sk;

	public ServerThread(Server server, Socket socket)
	{
		this.server = server;
		this.sk = socket;

		this.start();
	}

	@Override
	public void run()
	{
		try
		{
			/** DataInputStream para poder leer del cliente */
			DataInputStream dis = new DataInputStream(sk.getInputStream());

			while (true)
			{
				String sms = dis.readUTF();
				System.out.println("Sending " + sms);
				server.spread(sms);
			}
		}
		catch (EOFException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			/**
			 * Si la conexion se cierra debemos actualizar los sockets que
			 * tenemos en el Server
			 */
			server.removeConnection(sk);
		}
	}
}
