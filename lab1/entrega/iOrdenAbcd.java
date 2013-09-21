package lab1.entrega;

public class iOrdenAbcd
{
	/** Letra inicial */
	// private static final char letra = 'a';
	/**
	 * Numeros de letras a imprimir, ademas NUM nos indica el numero maximo de
	 * turno que puede haber en el programa
	 */
	//private static final int NUM = 4;

	/** Total de veces a imprimir cada letra */
	private static final int TOTAL = 20;

	public static void main(String[] args)
	{
		/** Orden de las letras a y d */
		iControlTurn ad = new iControlTurn(2);
		/** Orden de las letras c y b */
		iControlTurn cb = new iControlTurn(2);

		iOrden a = new iOrden('a', TOTAL, ad, 0);
		iOrden b = new iOrden('b', TOTAL, cb, 0);
		iOrden c = new iOrden('c', TOTAL, cb, 1);
		iOrden d = new iOrden('d', TOTAL, ad, 1);
		a.start();
		b.start();
		c.start();
		d.start();

	}
}

class iOrden extends Thread
{
	private char aux;

	private int total;

	private int turn;

	private iControlTurn ct;

	public iOrden(int i, int total, iControlTurn ct, int t)
	{
		this.aux = (char) i;
		this.total = total;
		this.turn = t;
		this.ct = ct;
	}

	public void run()
	{
		for (; total > 0;)
		{
			synchronized (this)
			{
				if (ct.checkTurn(turn))
				{
					System.out.print("" + aux + "");
					--total;
					ct.refreshTurn(turn);
				}
			}
		}
	}
}

class iControlTurn
{
	private int NUM;

	private int lastTurn;

	public iControlTurn(int num)
	{
		this.NUM = num;
		this.lastTurn = 0;
	}

	/**
	 * Actualizamos el ultimo turno
	 * 
	 * @param turn
	 *            La letra de este turno ha sido imprimida, tenomos que
	 *            actualizar el ultimo turno
	 */
	public synchronized void refreshTurn(int turn)
	{
		if (turn != lastTurn)
		{
			// SALIR
			System.out.print("SALGO");
			System.exit(-1);
			return;
		}

		lastTurn++;
		lastTurn %= NUM;

		notifyAll();
	}

	/**
	 * Comprobamos si le toca al thread poseedor de <i> turn<i>
	 * 
	 * @param turn
	 *            Turno del thread para entrar a imprimir
	 * @return <b>true<b> si el turno actual es el que tiene que entrar, en
	 *         otro caso false
	 */
	public synchronized boolean checkTurn(int turn)
	{
		if (turn == lastTurn)
		{
			return true;
		}
		else
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				System.err.println("Okay, Houston, we've had a problem here.");
				return false;
			}
			return false;
		}
	}
}
