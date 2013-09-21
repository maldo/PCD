package lab2.entrega;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

class Client extends Thread
{
	Socket sk;

	DataInputStream dis;

	DataOutputStream dos;

	final Random r = new Random();

	public Client(Socket sk) throws IOException
	{
		this.sk = sk;
		InputStream skin = sk.getInputStream();
		dis = new DataInputStream(skin);
		OutputStream skout = sk.getOutputStream();
		dos = new DataOutputStream(skout);
	}

	public int read(int pos) throws IOException
	{
		dos.writeInt(Constants.Reader);
		dos.writeInt(pos);

		dos.flush();

		return dis.readInt();
	}

	public void write(int pos, int val) throws IOException
	{
		dos.writeInt(Constants.Writer);
		dos.writeInt(pos);
		dos.writeInt(val);

		dos.flush();
	}

	public void exit() throws IOException
	{
		dos.writeInt(Constants.exit);

		dos.flush();

		close();
	}

	public void close()
	{
		try
		{
			dos.close();
			dis.close();
			sk.close();
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

class ClientReader extends Client
{
	public ClientReader(Socket sk) throws IOException
	{
		super(sk);
	}

	@Override
	public void run()
	{
		try
		{
			read(r.nextInt(Constants.V.length));
			exit();
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

class ClientWriter extends Client
{
	public ClientWriter(Socket sk) throws IOException
	{
		super(sk);
	}

	@Override
	public void run()
	{
		try
		{
			write(r.nextInt(Constants.V.length), r.nextInt());
			exit();
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
