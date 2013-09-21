package lab1.entrega;

import java.util.Stack;

public class Prosumer
{
	/** Tamaño maximo de la pila que vamos a usar */
	private static final int MAX = 50;

	public static void main(String[] args)
	{
		/** Creacion de la pila especial */
		Spc st = new Spc(MAX);
		/** Creacion del productor y consumidor */
		Productor p = new Productor(st);
		Consumidor c = new Consumidor(st);
		/** Inicio de los threads */
		p.start();
		c.start();
	}
}

class Productor extends Thread
{
	private Spc st;

	/** Constructora */
	public Productor(Spc stack)
	{
		this.st = stack;
	}

	public void run()
	{
		while (true)
		{
			st.insertar();
			st.tam();
			try
			{
				sleep((long) Math.random() * 5000);
			}
			catch (InterruptedException e)
			{
				System.err.println("El Produtor tiene este problema"
						+ e.toString());
			}
		}
	}
}

class Consumidor extends Thread
{
	private Spc st;

	/** Constructora */
	public Consumidor(Spc stack)
	{
		this.st = stack;
	}

	public void run()
	{
		while (true)
		{
			st.sacar();
			st.tam();
			try
			{
				sleep((long) Math.random() * 5000);
			}
			catch (InterruptedException e)
			{
				System.err.println("El Consumidor tiene este problema"
						+ e.toString());
			}
		}
	}
}

class Spc extends Stack<Object>
{

	private static final long serialVersionUID = 6671367263571231852L;

	private int MAX;

	public Spc(int max)
	{
		this.MAX = max;
	}

	public synchronized void insertar()
	{
		while (this.size() == MAX)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				System.err.println("Insertar espera " + e.toString());
			}
		}

		this.push(new Object());

		System.out.println("Productor ha producido");

		notify();
	}

	public synchronized void sacar()
	{
		while (this.isEmpty())
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				System.err.println("Error " + e.toString());
			}
		}

		this.pop();

		System.out.println("Consumidor ha consumido");

		notify();
	}

	public void tam()
	{
		System.out.println("Tamaño " + this.size());
	}

}
