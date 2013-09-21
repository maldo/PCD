package lab2.entrega;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server
{
	private ReadWrite monitor;

	private int[] data;

	private Random rnd = new Random();

	/** Contador para los lectores en espera */
	private Counter wr = new Counter();

	/** Contador para los escritores en espera */
	private Counter ww = new Counter();

	/** Contador total (debe ser igual a Constants.thread) */
	private Counter total = new Counter();

	/** Contador total de Lectores */
	private Counter r = new Counter();

	/** Contador total de Escritores */
	private Counter w = new Counter();

	public Server()
	{
		monitor = new ReadWriteSafe();
		data = new int[Constants.V.length];
	}

	public static void main(String[] args) throws IOException
	{
		Server s = new Server();

		s.start();
	}

	public void start() throws IOException
	{
		ServerSocket ss;
		ss = new ServerSocket(Constants.PORT);

		while (true)
		{
			Socket sk;
			sk = ss.accept();

			ThreadServer f = new ThreadServer(sk);

			f.start();
		}
	}

	public void delay()
	{
		try
		{
			Thread.sleep(rnd.nextInt(200));
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	class ThreadServer extends Thread
	{
		private Socket sk;

		private int id;

		ThreadServer(Socket sk)
		{
			this.sk = sk;
			synchronized (total)
			{
				id = total.get();
				// System.out.println("ESTE es el ID de creacion" + id);
				total.add();
				// System.out.println("Este sera el siguiente al id" +
				// total.get());
			}
		}

		public void run()
		{
			try
			{
				InputStream skin = sk.getInputStream();
				DataInputStream dis = new DataInputStream(skin);
				OutputStream skout = sk.getOutputStream();
				DataOutputStream dos = new DataOutputStream(skout);

				int Stat = dis.readInt();
				int pos, val;

				while (Stat != Constants.exit)
				{
					switch (Stat)
					{
						case Constants.Reader:
							r.add();
							pos = dis.readInt();
							val = read(pos);

							dos.writeInt(val);
							dos.flush();
							break;

						case Constants.Writer:
							w.add();
							pos = dis.readInt();
							val = dis.readInt();

							write(pos, val);

							break;

						default:
							System.err.println("Execution Abort");
							return;
					}
					Stat = dis.readInt();
				}

				getStat();

				dis.close();
				dos.close();
				skin.close();
				sk.close();
			}
			catch (IOException e)
			{
				System.err.println(e.toString());
				e.printStackTrace();
			}
			finally
			{
				getStat();
			}

		}

		int read(int pos)
		{
			int val = -1;

			try
			{
				wr.add();
				monitor.acquireRead();
				wr.remove();
				delay();
				val = data[pos];
				monitor.releaseRead();
			}
			catch (InterruptedException e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			return val;
		}

		void write(int pos, int val)
		{
			try
			{
				ww.add();
				monitor.acquireWrite();
				ww.remove();
				delay();
				data[pos] = val;
				monitor.releaseWrite();
			}
			catch (InterruptedException e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}

		void getStat()
		{
			System.out.println("Soy el numero " + id + " Total: " + total.get()
					+ " repartidos en:\tLectores " + r.get() + " Escritores: "
					+ w.get() + "\nLectores esperando: " + wr.get()
					+ " Escritores esperando: " + ww.get() + "\n");
		}
	}
}
