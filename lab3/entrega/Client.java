package lab3.entrega;

import java.util.Random;

import lab3.entrega.Bank.Busy;

class Client
{
	/**
	 * Numero total de clientes, cantidad baja para hacer el conjunto de prueba
	 */
	static final int CLIENTS = 100;// XXX

	public static void main(String[] args)
	{
		final Bank b = new Bank();

		for (int i = 0; i < CLIENTS; i++)
		{
			Clibank c = new Clibank(i, b);

			c.start();
		}
	}
}

class Clibank extends Thread
{
	static final int NMAX = 5;

	static final int MONEY = 100;

	private final Bank b;

	private final Random rnd = new Random();

	private final int id;

	/**
	 * Turno que le asigna el banco al cliente
	 */
	public int Ticket;

	private final double p = 0.70;

	public Clibank(int i, Bank b)
	{
		this.id = i;
		this.b = b;
	}

	@Override
	public void run()
	{
		/**
		 * Obtiene un turno del banco
		 */
		Ticket = b.getTicket();

		while (true)
		{
			/**
			 * El cliente mria el turno del banco y si el turno del cliente es
			 * menor que el del banco, el cliente puede entrar y hacer sus
			 * operaciones
			 */
			if (Ticket <= b.lookTicket())
			{
				Wicket w = b.getWicket(id);

				System.out.println("Soy el cliente " + id
						+ " y estoy en la ventana " + w.getID());

				interact(w);

				System.out.println("Soy el cliente " + id
						+ " y me voy de la ventana " + w.getID());

				w.setFree();

				try
				{
					Thread.sleep(rnd.nextInt(1000));
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				/**
				 * El cliente ya ha acabado lo que tenia que hacer en el banco y
				 * debe volver a obtener un turno del banco
				 */
				Ticket = b.getTicket();
			}
			else
			{
				try
				{
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}

		}
	}

	public void interact(Wicket w)
	{
		for (int ops = NMAX; ops > 0; --ops)
		{
			int cash = rnd.nextInt(MONEY);

			/**
			 * Segun la probabilidad p al cliente le tocara ingresar o retirar
			 * efectivo del banco
			 */

			if (rnd.nextDouble() > p)
			{
				System.out.println("Soy el cliente " + id
						+ " y deseo ingresar " + cash);
				w.opInCome(cash);
			}
			else
			{
				System.out.println("Soy el cliente " + id + " y deseo retirar "
						+ cash);
				try
				{
					w.opOutCome(cash);
				}
				catch (Busy b)
				{
					System.out.println("-->la ventana " + w.getID()
							+ " echa al cliente " + id);
					break;
				}
			}

			try
			{
				Thread.sleep(rnd.nextInt(1000));
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
