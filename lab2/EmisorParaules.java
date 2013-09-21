package lab2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

class EmisorParaules
{
	public static void main(String[] args) throws IOException
	{
		final String miss = "Hola";
		Socket sk;
		OutputStream skout;
		DataOutputStream dos;

		/** Ip destino */
		sk = new Socket("10.10.73.43", 9500);
		skout = sk.getOutputStream();
		dos = new DataOutputStream(skout);

		for (int i = 0; i < 100; i++)
		{
			dos.writeUTF(miss);
		}

		dos.writeUTF("");

		dos.close();
		skout.close();
		sk.close();
	}
}
