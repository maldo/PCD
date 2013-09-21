package lab3.entrega;

import lab3.entrega.Bank.Busy;

class Account
{
	private int balance;

	private Bank bank;

	Account(int cash, Bank bank)
	{
		this.balance = cash;
		this.bank = bank;
	}

	public synchronized void income(int cash)
	{
		balance += cash;
		notifyAll();
	}

	public synchronized void outcome(int cash) throws Busy
	{
		while (balance < cash)
		{			
			bank.needCash();
			
			if (bank.gotoClose())
			{
				bank.closedClient();

				System.out.println("EL BANCO ESTA CERRADO");

				throw bank.new Busy();
			}			

			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		balance -= cash;
	}

	public synchronized void freezeAccount()
	{
		notifyAll();
	}
}
