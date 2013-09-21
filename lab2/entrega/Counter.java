package lab2.entrega;

/** Uso para contar esperas de lectores/escritores */
public class Counter
{
	private int value;

	/** Incrementa el contador */
	public synchronized void add()
	{
		++value;
	}

	/** Devuelve el contador */
	public synchronized int get()
	{
		return value;
	}

	/** Decrementa el contador */
	public synchronized void remove()
	{
		--value;
	}
}
