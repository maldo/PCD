package lab1;

public class DosValorsExec
{

	public static void main(String[] args)
	{
		DosValors h = new DosValors();
		DosValorsFil d = new DosValorsFil(h);
		DosValorsFil e = new DosValorsFil(h);
		d.start();
		e.start();
	}

}

/*-
 * Explicar traza ejemplo
 * 0 0 0 0 p1:x0 y0 s0 T?
 * 1 2 3 0	p2:x0 y0 s0 t?
 * 1 2 3 3	p1:			t0  --> 0 0 0 0
 * 2 4 6 3		x1 y2 s3
 * 2 4 6 6  p2: 		t0 --> 1 2 3 0
 * 3 6 9 6		x1 y2 s3
 *			p1: 			--> 1 2 3 3
 */

// se entrega con portada con titulo sesion X y nombre
class DosValors
{
	private int x, y, s, t;

	// { s = x +y, t = x + y}
	public synchronized void SetVal(int a, int b)
	{
		x = a;
		y = b;
		t = a + b;

		// Killer block app
		/*
		 * try{ Thread.sleep(1000); } catch (InterruptedException e) { return; }
		 */

		s = a + b;
		System.out.println(x + "+" + y + "=" + t + "=" + s);
	}
}

class DosValorsFil extends Thread
{
	private DosValors v;

	public DosValorsFil(DosValors h)
	{
		v = h;
	}// Igualtat de variables

	public void run()
	{
		for (int i = 0; i < 20; ++i)
		{
			v.SetVal(i, i * 2);
		}
	}
}
