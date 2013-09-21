package lab1.entrega;

public class OrdenAbcd
{
	/** Letra inicial */
	private static final char letra = 'a';

	/**
	 * Numeros de letras a imprimir, ademas NUM nos indica el numero maximo de
	 * turno que puede haber en el programa
	 */
	private static final int NUM = 4;

	/** Total de veces a imprimir cada letra */
	private static final int TOTAL = 20;

	public static void main(String[] args)
	{
		ControlTurn ct = new ControlTurn(NUM);

		for (int i = 0; i < NUM; ++i)
		{
			Orden ds = new Orden(letra + i, TOTAL, ct, i);
			ds.start();
		}
	}
}

class Orden extends Thread
{
	private char aux;

	private int total;

	private int turn;

	private ControlTurn ct;

	public Orden(int i, int total, ControlTurn ct, int t)
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

class ControlTurn
{
	private int NUM;

	private int lastTurn;

	public ControlTurn(int num)
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
			System.err.print("SALGO");
			System.exit(-1);
			return;
		}

		lastTurn = (++lastTurn) % NUM;

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
