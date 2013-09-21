package lab3.entrega;

import java.util.Random;

/**
 * Un banco esta compuesto de multiples ventanas y de un numero de cuentas
 * 
 */
public class Bank
{

	class Busy extends Exception
	{
		private static final long serialVersionUID = 4757572996503931253L;

		Busy()
		{

		}

		Busy(String msg)
		{
			super(msg);
		}
	}

	private static final int NFIN = 7;

	final private Random rnd = new Random();

	final Account a = new Account(rnd.nextInt(2000), this);

	private Wicket w[] = new Wicket[NFIN];

	private boolean close = false;

	private int closes = 0;
	
	/**
	 * Contador de turnos
	 */
	private int turn = 0;
	
	/**
	 * Turno actual
	 */
	private int currentTurn = 7;

	Bank()
	{
		for (int i = 0; i < w.length; ++i)
		{
			w[i] = new Wicket(i, this);
		}
	}

	/**
	 * Para despertar a todas las ventanillas del banco
	 */
	public synchronized void synBank()
	{
		notifyAll();
	}

	/**
	 * Para despertar a una ventanilla del banco
	 */
	public synchronized void synBank1()
	{
		notify();
	}

	/**
	 * Asignacion de una ventanilla a un cliente, solo se podra hacer si el
	 * banco esta abierto y queda alguna ventanilla libre
	 * @param id 
	 * 
	 * @return Ventanilla que estara en uso por un cliente
	 */
	public synchronized Wicket getWicket(int id)
	{
		Wicket wk = null;

		System.out.println(close);

		while (wk == null)
		{
			while (close)
			{
				try
				{
					wait();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
			int i = 0;
			while (i < w.length && wk == null)
			{
				if (w[i].isFree())
				{
					wk = w[i];
				}
				++i;
			}	

			if (wk == null)
			{
				try
				{
					wait();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

		wk.get();

		return wk;
	}

	/**
	 * Se encarga de revisar las ventanillas para comprobar si todas estan
	 * esperando dinero, si este es el caso se encarga de iniciar el cierre
	 * temporal del banco
	 */
	synchronized void needCash()
	{
		boolean allNeed = true;

		int i = 0;
		while (i < w.length && allNeed)
		{
			if (!w[i].needingMoney())
			{
				allNeed = false;
			}		
			++i;
		}

		if (allNeed)
		{
			close = true;

			System.out.println("-->VAMOS A CERRAR");

			notifyAll();
			a.freezeAccount();
		}
	}

	public boolean gotoClose()
	{
		return close;
	}

	/**
	 * Echamos a los clientes que toque y si los hemos echado a todos, volvemos
	 * abrir el banco
	 */
	public synchronized void closedClient()
	{
		++closes;
		if (closes == w.length)
		{
			close = false;

			closes = 0;

			System.out.println("-->ABRIMOS EL BANCO");

			notifyAll();
		}
	}
		
	/**
	 * Da un turno a un cliente 
	 */
	public synchronized int getTicket()
	{	
		return ++turn;
	}
	
	/**
	 * Mira el turno acutal
	 */
	public synchronized int lookTicket()
	{
		return currentTurn;
	}
	
	/**
	 * Actualizamos el contador para que pueda entrar uno mas 
	 */
	public synchronized void nextTurn()
	{
		++currentTurn;
	}
	
}
