package lab3.entrega;

import lab3.entrega.Bank.Busy;

class Wicket
{
	private int id;

	private boolean free = true;

	private boolean needMoney = false;

	private Bank bank;

	Wicket(int id, Bank bank)
	{
		this.id = id;
		this.bank = bank;
	}

	public void get()
	{
		free = false;

		System.out.println("Ventana " + id + " en uso");
	}

	public void setFree()
	{
		free = true;

		System.out.println("Ventana " + id + " libre");

		bank.nextTurn();
		
		bank.synBank1();
	}

	/**
	 * Realiza la retirada de efectivo del banco y si surgen problemas se
	 * encarga de echar al cliente de su ventanilla
	 * 
	 * @param cash
	 *            dinero a retirar
	 * @throws Busy
	 *             Excepcion que marca que ese cliente ha de salir del banco
	 */
	public void opOutCome(int cash) throws Busy
	{
		needMoney = true;

		System.out.println("Ventana " + id + " desean retirar " + cash + "€");

		try
		{
			bank.a.outcome(cash);
		}
		catch (Busy b)
		{
			System.out.println("Ventana " + id + " no han podido retirar "
					+ cash + "€");

			bank.synBank();

			throw b;
		}
		finally
		{
			needMoney = false;
		}
		System.out.println("Ventana " + id + " se ha retirado " + cash
				+ "€ con exito");
	}

	public void opInCome(int cash)
	{
		bank.a.income(cash);

		System.out.println("Ventana " + id + " ingreso de " + cash + " €");
	}

	public boolean isFree()
	{
		return free;
	}

	public boolean needingMoney()
	{
		return needMoney;
	}

	public int getID()
	{
		return id;
	}

}
