package lab1;

public class PingPongJoc
{

	public static void main(String[] args)
	{
		PingPong ping = new PingPong("ping", 33);
		ping.start();
		PingPong pong = new PingPong("PONG", 100);
		pong.start();
	}
}

class PingPong extends Thread
{

	String word;

	int delay;

	PingPong(String whatToSay, int delayTime)
	{
		word = whatToSay;
		delay = delayTime;
	}

	public void run()
	{
		try
		{
			for (int i = 0; i < 30; ++i)
			{
				System.out.print(word + " ");
				sleep(delay);
			}
		}
		catch (InterruptedException e)
		{
			return;
		}
	}
}
