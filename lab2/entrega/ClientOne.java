package lab2.entrega;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class ClientOne
{
	public static void main(String[] args)
	{
		final Random r = new Random();

		/** Inicializacion del vector */
		init_V();

		/** Creamos Constants.thread clientes */
		for (int i = 0; i < Constants.thread; i++)
		{
			Socket s;
			try
			{
				s = new Socket(Constants.IP, Constants.PORT);

				Client cl;

				/** Seleccion aleatoria de escritor o lector */
				if (r.nextBoolean())
				{
					cl = new ClientWriter(s);
				}
				else
				{
					cl = new ClientReader(s);
				}

				cl.start();
			}
			catch (IOException e)
			{
				System.err.println(e.getMessage());
			}
		}
	}

	private static void init_V()
	{
		final Random r = new Random();

		for (int i = 0; i < Constants.V.length; ++i)
		{
			Constants.V[i] = r.nextInt();
		}
	}
}
