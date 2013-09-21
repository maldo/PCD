package lab1.entrega;

public class NoOrdenAbcd
{
	/** Letra inicial */
	private static char letra = 'a';

	/** Numeros de letras a imprimir */
	private static final int NUM = 4;

	/** Total de veces a imprimir cada letra */
	private static final int TOTAL = 50;

	public static void main(String[] args)
	{
		for (int i = 0; i < NUM; ++i)
		{
			DesOrden ds = new DesOrden(letra + i, TOTAL);
			ds.start();
		}
	}
}

class DesOrden extends Thread
{
	char aux;

	int total;

	public DesOrden(int i, int total)
	{
		this.aux = (char) i;
		this.total = total;
	}

	public void run()
	{
		for (; total > 0; --total)
		{
			System.out.print(aux);
		}
	}

}
