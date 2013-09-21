package lab1;

public class UsaValorEnter
{

	public static void main(String[] args)
	{
		ValorEnter h = new ValorEnter();
		EscriuEnter p = new EscriuEnter(h);
		LlegeixEnter c = new LlegeixEnter(h);
		p.start();
		c.start();

	}

}

class ValorEnter
{
	private int Enter;

	private boolean llegible = false;

	public synchronized void SetEnter(int val)
	{
		while (llegible)
		{
			try
			{
				wait();
			}
			catch (Exception e)
			{

			}
		}
		Enter = val;
		llegible = true;
		notify();
	}

	public synchronized int GetEnter()
	{
		while (!llegible)
		{
			try
			{
				wait();
			}
			catch (Exception e)
			{

			}
		}
		llegible = false;
		notify();
		return Enter;
	}
}

class EscriuEnter extends Thread
{
	private ValorEnter valor;

	public EscriuEnter(ValorEnter h)
	{
		valor = h; // Valor inicial
	}

	public void run()
	{
		for (int i = 0; i < 20; ++i)
		{
			valor.SetEnter(i);
			System.out.println("Escriptor escriu " + i);
			// sleeping app
			try
			{
				sleep((int) Math.random() * 3000);
			}
			catch (InterruptedException e)
			{
				System.err.println("Exception " + e.toString());
			}
		}
	}
}

class LlegeixEnter extends Thread
{
	private ValorEnter valor;

	public LlegeixEnter(ValorEnter h)
	{
		valor = h;
	}

	public void run()
	{
		int val;
		for (int i = 0; i < 20; ++i)
		{
			val = valor.GetEnter();
			System.out.println("Lector llegeix " + val);

			try
			{
				sleep(1000);
			}
			catch (InterruptedException e)
			{
				System.err.println("Exception " + e.toString());
			}
		}
	}
}
