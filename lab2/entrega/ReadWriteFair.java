package lab2.entrega;

//
// The Read Write Monitor Class - fair version
//
public class ReadWriteFair implements ReadWrite
{

	private int readers = 0;

	private boolean writing = false;

	private int waitingW = 0; // no of waiting Writers.

	private boolean readersturn = false;

	public synchronized void acquireRead() throws InterruptedException
	{
		while (writing || (waitingW > 0 && !readersturn))
			wait();
		++readers;
	}

	public synchronized void releaseRead()
	{
		--readers;
		readersturn = false;
		if (readers == 0) notifyAll();
	}

	public synchronized void acquireWrite() throws InterruptedException
	{
		++waitingW;
		while (readers > 0 || writing)
			wait();
		--waitingW;
		writing = true;
	}

	public synchronized void releaseWrite()
	{
		writing = false;
		readersturn = true;
		notifyAll();
	}
}
